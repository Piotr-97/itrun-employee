package org.example.model;

public enum PersonType {
    INTERNAL("internal"), EXTERNAL("external");

    private final String type;
    PersonType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
