package com.linkedin.hsports.validations;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import java.lang.annotation.*;

/**
 * Custom validator for alphabetic strings.
 */
@Documented
@Constraint(validatedBy = {PermittedManufacturerConstraintValidator.class})
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
public @interface PermittedManufacturer {

    String message() default "Must be permitted";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
