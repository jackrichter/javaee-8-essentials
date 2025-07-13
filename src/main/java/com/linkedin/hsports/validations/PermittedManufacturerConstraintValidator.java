package com.linkedin.hsports.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class PermittedManufacturerConstraintValidator implements ConstraintValidator<PermittedManufacturer, String> {

    private static String[] permittedManufacturers = {"CompanyA", "CompanyB", "SuperGloves", "AwesomeMittens"};

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Arrays.asList(permittedManufacturers).contains(value);
    }
}
