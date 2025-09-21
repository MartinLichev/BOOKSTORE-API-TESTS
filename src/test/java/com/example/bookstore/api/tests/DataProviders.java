package com.example.bookstore.api.tests;

import org.testng.annotations.DataProvider;

public class DataProviders {

    @DataProvider(name = "bookIds")
    public static Object[][] bookIds() {
        return new Object[][] {{1},{2},{10},{20}};
    }

    @DataProvider(name = "authorIds")
    public static Object[][] authorIds() {
        return new Object[][] {{1},{2},{5}};
    }

    @DataProvider(name = "idBooks")
    public static Object[][] idBooks() {
        return new Object[][] {{1},{2},{3}};
    }

    @DataProvider(name = "acceptMatrix")
    public static Object[][] acceptMatrix() {
        return new Object[][] {
            {"application/json"},
            {"*/*"},
            {"application/json; v=1.0"},
            {"text/plain; v=1.0"},
            {"application/xml"}
        };
    }
}
