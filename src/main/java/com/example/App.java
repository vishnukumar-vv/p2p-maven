package com.example;

/**
 * Utility class for application logic.
 */
public final class App {

    private App() {
        // Prevent instantiation
    }

    /**
     * Main entry point.
     *
     * @param args command-line arguments.
     */
    public static void main(final String[] args) {
        System.out.println("Hello World");
    }

    /**
     * Adds two integers.
     *
     * @param a first number.
     * @param b second number.
     * @return sum of a and b.
     */
    public static int add(final int a, final int b) {
        return a + b;
    }
}
