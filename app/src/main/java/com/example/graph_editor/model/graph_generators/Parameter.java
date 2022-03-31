package com.example.graph_editor.model.graph_generators;

public class Parameter {
    private final String name;
    private final int minVal;
    private final int maxVal;

    public Parameter(String name, int minVal, int maxVal) {
        this.name = name;
        this.minVal = minVal;
        this.maxVal = maxVal;
    }

    public int getMaxVal() {
        return maxVal;
    }

    public int getMinVal() {
        return minVal;
    }

    public String getName() {
        return name;
    }
}
