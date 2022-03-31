package com.example.graph_editor.model;

public class InputSanitizer {
    public static boolean isInteger(String text, int minVal, int maxVal) {
        int result;
        try {
            result = Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return false;
        }
        return minVal <= result && result <= maxVal;
    }
}
