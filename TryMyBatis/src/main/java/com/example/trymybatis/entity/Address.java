package com.example.trymybatis.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Address {
    private Long id;
    private Long peopleId;
    private String street;
    private String city;
}
