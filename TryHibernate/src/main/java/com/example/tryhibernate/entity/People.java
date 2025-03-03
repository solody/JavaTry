package com.example.tryhibernate.entity;

public class People {
    private Long id;
    private String name;
    private int age;

    public People() {

    }

    public People(String name, int age) {
        this.setName(name);
        this.setAge(age);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
