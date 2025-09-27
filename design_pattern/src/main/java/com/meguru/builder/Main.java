package com.meguru.builder;

public class Main {
    public static void main(String[] args) {
        String string = SQL.update().table("user").where("age=19").set("name", "meguru").buildSql();
        System.out.println(string);
    }
}
