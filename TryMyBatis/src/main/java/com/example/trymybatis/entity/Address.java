package com.example.trymybatis.entity;

import lombok.Data;

@Data
public class Address {
    private Long id;
    private Long peopleId;
    private String street;
    private String city;
}
