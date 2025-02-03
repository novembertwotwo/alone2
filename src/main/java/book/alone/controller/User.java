package book.alone.controller;

import lombok.Getter;

@Getter
public class User {

    private String username;
    private int age;

    public User(String username, int age) {
        this.username = username;
        this.age = age;
    }
}
