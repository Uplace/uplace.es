package com.arnaugarcia.uplace.domain.validators;

import com.arnaugarcia.uplace.web.rest.errors.ErrorConstants;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = {PropertyPriceValidator.class})
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PropertyPriceConstraint {

    String message() default ErrorConstants.ERR_BAD_PRICE;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
