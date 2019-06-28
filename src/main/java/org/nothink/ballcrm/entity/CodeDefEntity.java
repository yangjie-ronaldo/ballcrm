package org.nothink.ballcrm.entity;

public class CodeDefEntity {
    private String code;
    private String category;
    private String definition;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    @Override
    public String toString() {
        return "CodeDefEntity{" +
                "code='" + code + '\'' +
                ", category='" + category + '\'' +
                ", definition='" + definition + '\'' +
                '}';
    }
}