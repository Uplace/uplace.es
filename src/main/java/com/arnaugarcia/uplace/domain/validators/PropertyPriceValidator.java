package com.arnaugarcia.uplace.domain.validators;

import com.arnaugarcia.uplace.domain.Property;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PropertyPriceValidator implements ConstraintValidator<PropertyPriceConstraint, Property> {

    @Override
    public void initialize(PropertyPriceConstraint propertyPriceConstraint) {

    }

    @Override
    public boolean isValid(Property property, ConstraintValidatorContext constraintValidatorContext) {
        /*
        Checks by Transaction type if the other prices are null
         */
        switch (property.getTransaction()) {
            case BUY:
                return (isValidPrice(property.getPriceSell()) && (property.getPriceRent() == null && property.getPriceTransfer() == null));
            case RENT:
                return (isValidPrice(property.getPriceRent()) && (property.getPriceSell() == null && property.getPriceTransfer() == null));
            case RENT_BUY:
                return ((isValidPrice(property.getPriceRent()) && isValidPrice(property.getPriceSell())) && property.getPriceTransfer() == null);
            case TRANSFER:
                return (isValidPrice(property.getPriceTransfer()) && (property.getPriceSell() == null && property.getPriceRent() == null && property.getPriceTransfer() == null));
        }
        return false;
    }

    private boolean isValidPrice(Double price) {
        return price != null && price > 0;
    }

}
