package com.linkedin.hsports.validations;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import java.lang.annotation.*;

/**
 * Custom validator for alphabetic strings.
 */
@Documented
@Constraint(validatedBy = {})
@Retention(RetentionPolicy.RUNTIME)
@Pattern.List(@Pattern(regexp = "^[A-Za-z]*$", message = "Must be letters"))
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
public @interface Alphabetic {

    String message() default "Must be letters";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
