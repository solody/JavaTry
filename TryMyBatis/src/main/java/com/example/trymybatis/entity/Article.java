package com.example.trymybatis.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Article {
    private Long id;
    private Long peopleId;
    private String title;
    private String content;
}
