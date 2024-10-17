package com.example.mycalculator;

public class Calculation {
    private String expression;
    private String result;

    public Calculation(String expression, String result) {
        this.expression = expression;
        this.result = result;
    }

    public String getExpression() {
        return expression;
    }

    public String getResult() {
        return result;
    }
}
