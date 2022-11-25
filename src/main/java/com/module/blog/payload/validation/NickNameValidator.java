package com.module.validator;

import com.module.group.GroupService;
import com.module.group.UserInGroup;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.MessageFormat;

@AllArgsConstructor
public class NickNameValidator implements ConstraintValidator<NickNameDuplicationCheck, String> {

    private final GroupService groupService;

    @Override
    public void initialize(NickNameDuplicationCheck constraintAnnotation) {
    }

    @Override
    public boolean isValid(String nickName, ConstraintValidatorContext context) {

        boolean nickNameIsDuplicated = groupService.NickNameIsDuplicated(nickName);


        if (nickNameIsDuplicated) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                            MessageFormat.format("중복된 닉네임입니다.", nickName))
                    .addConstraintViolation();

        }
        return !nickNameIsDuplicated;
    }
}
