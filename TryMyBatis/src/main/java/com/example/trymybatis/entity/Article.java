package com.example.trymybatis.entity;

import lombok.Data;

@Data
public class Article {
    private Long id;
    private Long peopleId;
    private String title;
    private String content;
}
