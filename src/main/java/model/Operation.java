package model;

public enum Operation {
    UPDATE("u"),
    QUERY("q"),
    ORDER("o");

    private String operation;

    Operation(String operation) {
        this.operation = operation;
    }

    public String getOperation() {
        return operation;
    }

    public static Operation getByCode(String operation) {
        switch (operation) {
            case "u" :
                return Operation.UPDATE;
            case "q" :
                return Operation.QUERY;
            default:
                return Operation.ORDER;
        }
    }
}
