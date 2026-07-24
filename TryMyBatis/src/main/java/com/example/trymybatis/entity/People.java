package com.example.trymybatis.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class People {
    private Long id;
    private String name;
    private int age;

    private Address address;
    private List<Article> articles;
}
