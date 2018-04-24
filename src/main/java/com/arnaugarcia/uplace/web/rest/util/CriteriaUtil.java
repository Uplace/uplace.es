package com.arnaugarcia.uplace.web.rest.util;

import com.arnaugarcia.uplace.domain.Apartment;
import com.arnaugarcia.uplace.domain.Office;
import com.arnaugarcia.uplace.domain.Property;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Root;
import java.util.*;


// TODO : Implement ENUM on OPERATION and EnumConverter
enum OperationType {
    EQUAL, MORE_THAN, LESS_THAN, SPECIFIED
}

class Param {
    private String attribute;
    private String operation;
    private List<String> values;

    protected Param(String attribute, String operation, List<String> values) {
        this.attribute = attribute;
        this.operation = operation;
        this.values = values;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    @Override
    public String toString() {
        return "Param{" +
            "attribute='" + attribute + '\'' +
            ", operation='" + operation + '\'' +
            ", values=" + values +
            '}';
    }
}

@Component
public class CriteriaUtil {

    public Specification createSpecification(Map<String, String> criteria) {

        List<Param> params = convertCriteria(criteria);
        Specifications specification = Specifications.where(null);

        for (Param param : params) {
            specification = specification.and(buildSpecification(param));
        }

        return specification;
    }

    private Specification buildSpecification(Param param) {
        return (root, criteriaQuery, builder) -> {
            root = criteriaQuery.from(Property.class);
            Root<Apartment> apartmentRoot = builder.treat(root, Apartment.class);
            Root<Office> officeRoot = builder.treat(root, Office.class);
            switch (param.getOperation()) {
                case "equals":
                    return builder.equal(root.get(param.getAttribute()), param.getValues().get(0));
                case "greaterThan":
                    return builder.greaterThan(root.get(param.getAttribute()), Integer.parseInt(param.getValues().get(0)));
                case "greaterOrEqualThan":
                    return builder.greaterThanOrEqualTo(root.get(param.getAttribute()), Integer.parseInt(param.getValues().get(0)));
                case "lessThan":
                    return builder.lessThan(root.get(param.getAttribute()), Integer.parseInt(param.getValues().get(0)));
                case "lessOrEqualThan":
                    return builder.lessThanOrEqualTo(root.get(param.getAttribute()), Integer.parseInt(param.getValues().get(0)));
                default:
                    return null;
            }
        };
    }

    private List<Param> convertCriteria(Map<String, String> criteriaMap) {
        List<Param> paramList = new ArrayList<>();
        criteriaMap.forEach((k, v) -> {
            Param param = createParam(k, v);
            if (param != null) paramList.add(param);
        });
        paramList.forEach(System.out::println);
        return paramList;
    }

    private Param createParam(String key, String value) {
        String[] attributeOperation = key.split("\\.");
        List<String> values = Arrays.asList(value.split(","));
        if (attributeOperation.length > 1 && !value.isEmpty()) {
            return new Param(attributeOperation[0], attributeOperation[1], values);
        } else {
            return null;
        }
    }

}
