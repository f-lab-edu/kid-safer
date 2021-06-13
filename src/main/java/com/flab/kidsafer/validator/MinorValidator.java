package com.flab.kidsafer.validator;

import com.flab.kidsafer.annotation.Minor;
import java.util.Calendar;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MinorValidator implements ConstraintValidator<Minor, String> {

    @Override
    public void initialize(Minor constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value.matches("-?\\d+")) {
            int yearToInt = Integer.parseInt(value);
            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            return yearToInt <= currentYear && currentYear - yearToInt <= 20;
        }
        return false;
    }
}
