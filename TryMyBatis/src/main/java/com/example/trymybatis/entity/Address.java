package com.example.trymybatis.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Address {
    private Long id;
    private People people;
    private String street;
    private String city;
}
