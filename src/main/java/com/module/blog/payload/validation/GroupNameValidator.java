package com.module.validator;

import com.module.group.GroupService;
import lombok.AllArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.MessageFormat;

@AllArgsConstructor
public class GroupNameValidator implements ConstraintValidator<GroupNameDuplicationCheck, String> {

    private final GroupService groupService;


    @Override
    public void initialize(GroupNameDuplicationCheck constraintAnnotation) {
    }

    @Override
    public boolean isValid(String groupName, ConstraintValidatorContext context) {

        boolean groupNameIsDuplicated = groupService.groupNameIsDuplicated(groupName);

        if (groupNameIsDuplicated) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                            MessageFormat.format("중복된 그룹 이름입니다.", groupName))
                    .addConstraintViolation();
        }
        return !groupNameIsDuplicated;
    }

}


