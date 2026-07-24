package com.example.trymybatis.mappers;

import com.example.trymybatis.entity.People;

import java.util.HashMap;

public interface PeopleMapper {
    People selectPeople(HashMap<String, Object> params);
}
