package com.example.trymybatis.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class People {
    private Long id;
    private String name;
    private int age;

    private Address address;
    private List<Article> articles;
}
