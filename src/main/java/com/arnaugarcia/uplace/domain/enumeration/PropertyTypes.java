package com.arnaugarcia.uplace.domain.enumeration;

public enum PropertyTypes {
    APARTMENTS("apartments"), BUILDINGS("buildings"), BUSINESS("business"), ESTABLISHMENTS("establishments"), HOTELS("hotels"), INDUSTRIAL_PLANTS("industrial-plants"), OFFICES("offices"), PROPERTIES("properties"), TERRAINS("terrains");

    private String propertyTypes;

    PropertyTypes(String apartmentType) {
        this.propertyTypes = apartmentType;
    }

    public String getTypeName() {
        return propertyTypes;
    }

    public static PropertyTypes fromTypeName(String name) {
        switch (name) {
            case "apartments":
                return PropertyTypes.APARTMENTS;
            case "buildings":
                return PropertyTypes.BUILDINGS;
            case "business":
                return PropertyTypes.BUSINESS;
            case "establishments":
                return PropertyTypes.ESTABLISHMENTS;
            case "hotels":
                return PropertyTypes.HOTELS;
            case "industrial-plants":
                return PropertyTypes.INDUSTRIAL_PLANTS;
            case "offices":
                return PropertyTypes.OFFICES;
            case "properties":
                return PropertyTypes.PROPERTIES;
            case "terrains":
                return PropertyTypes.TERRAINS;
            default:
                throw new IllegalArgumentException("Type [" + name
                    + "] not supported.");
        }
    }
}
