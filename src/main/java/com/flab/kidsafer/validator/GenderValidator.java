package com.flab.kidsafer.validator;

import com.flab.kidsafer.annotation.Gender;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class GenderValidator implements ConstraintValidator<Gender, String> {

    @Override
    public void initialize(Gender constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return "F".equals(value) || "M".equals(value);
    }
}
