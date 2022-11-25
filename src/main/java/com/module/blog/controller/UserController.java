package com.module.blog.controller;

import com.module.error.ApiError;
import com.module.jwt.JwtUtil;
import com.module.mail.EmailServiceImpl;
import com.module.mail.EmailToken;
import com.module.security.CookieUtil;
import com.module.security.LoginForm;
import com.module.validator.PasswordValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserServiceImpl userService;
    private final EmailServiceImpl emailService;
    private final JwtUtil jwtUtil;
    private static final Logger logger = LoggerFactory
            .getLogger(UserController.class);


    @PostMapping("/api/user/save")
    public ResponseEntity<Object> userSave(@RequestBody @Valid MemberForm memberForm) {

        logger.info("바인딩 에러, 중복된 이메일 모두 해당되지 않습니다.");
        User user = User.builder().name(memberForm.getName()).email(memberForm.getEmail()).password(memberForm.getPassword()).warningCount(0).role(Role.ROLE_NOT_VERIFIED).build();
        userService.signUp(user);
        logger.info("회원가입이 완료 되었습니다");

        EmailToken confirmationToken = EmailToken.builder().createdDate(new Timestamp(new Date().getTime())).confirmationToken(emailService.setTokenString()).user(userService.findUserByEmail(user.getEmail())).build();
        emailService.save(confirmationToken);
        Session session = Session.getInstance(emailService.setSMTPSession(), emailService.setAuth());

        emailService.sendEmail(session, user.getEmail(), "회원가입 인증메일입니다.", "http://localhost:8080/user/auth/" + confirmationToken.getConfirmationToken());
        logger.info("이메일이 발송되었습니다.");

        return ResponseEntity.status(HttpStatus.CREATED).body("User is successfully saved");

    }


    @GetMapping(value = "/api/user/auth/{token}")
    public ResponseEntity<Object> userEmailVerification(@PathVariable("token") String confirmationToken) {
        EmailToken token = emailService.findByConfirmationToken(confirmationToken);
        User user = userService.findUserByEmail(token.getUser().getEmail());
        user.setRole(Role.ROLE_USER);
        userService.updateUser(user);
        return ApiError.buildApiError(ApiError.builder().timestamp(LocalDateTime.now()).message("Email verification is succeeded").status(HttpStatus.OK).build());
    }


    @PostMapping("/api/login")
    public ResponseEntity<Object> login(HttpServletResponse httpServletResponse, @RequestBody LoginForm loginForm) throws IOException {
        //if login info is not matched
        if (!userService.login(loginForm.getEmail(), loginForm.getPassword())) {
            httpServletResponse.sendError(HttpStatus.BAD_REQUEST.value(), "login information is not matched");
        }
        logger.info("login success");
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    //1st
    @GetMapping("/api/logout")
    public ResponseEntity<Object> logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        if (jwtUtil.invalidateRelatedTokens(httpServletRequest)) {
            CookieUtil.clear(httpServletResponse, "jwtToken");
            return ResponseEntity.status(HttpStatus.OK).body("logout is succeeded");
        }
        return ApiError.buildApiError(ApiError.builder().message("there is no login info").status(HttpStatus.BAD_REQUEST).timestamp(LocalDateTime.now()).build());
    }

    //there are two use cases
    //1st if the user does not have cookie(token)
    //2. if the user have the cookie(token)


    @DeleteMapping("/api/user/withdrawal")
    public ResponseEntity<Object> withdrawalMember(HttpServletRequest httpServletRequest) {
        if ((String) httpServletRequest.getAttribute("email") != null) {
            userService.deleteUser((String) httpServletRequest.getAttribute("email"));
            return ApiError.buildApiError(ApiError.builder().status(HttpStatus.OK).timestamp(LocalDateTime.now()).message("user is successfully deleted").build());
        }
        return ApiError.buildApiError(ApiError.builder().status(HttpStatus.UNAUTHORIZED).timestamp(LocalDateTime.now()).message("user token is not valid").build());

    }

    @PutMapping("/api/user/update/{content}/{updateInfo}")
    public ResponseEntity<Object> updateInfo(@PathVariable String content, @PathVariable String updateInfo, HttpServletRequest httpServletRequest) {

        if (httpServletRequest.getAttribute("email") != null) {
            User user = userService.findUserByEmail((String) httpServletRequest.getAttribute("email"));
            logger.info("user : " + user.toString());
            switch (content) {
                case "name":
                    user.setName(updateInfo);
                    break;

                case "password":
                    if (!new PasswordValidator().updatePassword(updateInfo)) {
                        return ApiError.buildApiError(ApiError.builder().message("8자 이상의 50자 이하의 숫자, 영문자, 특수문자를 포함한 비밀번호를 입력해주세요").status(HttpStatus.BAD_REQUEST).timestamp(LocalDateTime.now()).build());
                    } else {
                        user.setPassword(new BCryptPasswordEncoder().encode(updateInfo));
                    }

            }
            userService.updateUser(user);
            logger.info(user.toString());
            return ResponseEntity.ok().body("update is succeeded");
        }

        return ApiError.buildApiError(ApiError.builder().message("there is no user token").timestamp(LocalDateTime.now()).status(HttpStatus.UNAUTHORIZED).build());

    }


    //0. if the login account is not admin, it will be rejected
    // 1. if the user email is valid
    // 1.1 user warn count is under "3" add warn +1
    // 1.2 user warn count is "3" , kick the user.
    @GetMapping("/api/admin/warn/{email}")
    public ResponseEntity<Object> warningOrKick(@PathVariable String email) {
        User user = userService.findUserByEmail(email);
        logger.info("user email " + email);
        int warn = user.getWarningCount();
        if (warn < 3) {
            user.setWarningCount(++warn);
            userService.updateUser(user);
            logger.info("user warning count is increased by 1 so now is " + warn);
            return ResponseEntity.status(HttpStatus.OK).body("user warning count is increased by 1. so now is " + warn);

        } else {
            userService.deleteUser(email);
            logger.info("user is deleted");
            return ResponseEntity.status(HttpStatus.OK).body("user is kicked");
        }

    }


    @GetMapping("/api/user/info")
    public HttpEntity<Object> getMyInfo(HttpServletRequest httpServletRequest) {

        //getAttribute from JwtAuthorization successfulAuthentication, if not return bad request
        if (httpServletRequest.getAttribute("email") != null) {
            String email = (String) httpServletRequest.getAttribute("email");
            User user = userService.findUserByEmail(email);
            logger.info("user : " + user.toString());

            UserDetailDto userDetailDto = new UserDetailDto(user.getEmail(), user.getName());

            return ResponseEntity.status(HttpStatus.OK).body(userDetailDto);
        }
        return ApiError.buildApiError(ApiError.builder().message("there is no token ").timestamp(LocalDateTime.now()).status(HttpStatus.UNAUTHORIZED).build());
    }

    @GetMapping("/api/admin/user/info")
    public ResponseEntity<Object> getAllUserInfo() {

        List<User> users = userService.getAllUser();
        List<UserDetailDto> userDetailDtoList =new ArrayList<>();

         users.stream().
                 map(s-> userDetailDtoList.add(new UserDetailDto(s.getEmail(), s.getName()))).
                 collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(userDetailDtoList);

    }
}


