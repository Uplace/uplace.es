package com.arnaugarcia.uplace.web.rest.util;

import com.arnaugarcia.uplace.domain.Property;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.*;

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
public class CriteriaUtil<T extends Property> {

    private List<Param> paramList = new ArrayList<>();

    public Specification<T> createSpecification(Map<String, String> criteria) {
        convertCriteria(criteria);
        Specifications<T> specification = Specifications.where(null);
        /*for (Param param : paramList) {
            specification.and(buildSpecification(param));
        }*/
        for (Param param : paramList) {
            System.out.println(param);
        }

        specification = specification.and(buildSpecification(paramList.get(0)));
        return specification;
    }

    private Specification<T> buildSpecification(Param param) {
        return (root, query, builder) -> builder.equal(root.get(param.getAttribute()), param.getValues().get(0));
    }

    private void convertCriteria(Map<String, String> criteriaMap) {
        criteriaMap.forEach((k, v) -> {
            Param param = createParam(k, v);
            if (param != null) paramList.add(param);
        });
        paramList.forEach(System.out::println);
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
