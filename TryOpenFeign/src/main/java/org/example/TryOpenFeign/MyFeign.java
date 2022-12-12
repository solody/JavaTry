package org.example.TryOpenFeign;

import feign.Headers;
import feign.RequestLine;
import org.example.TryOpenFeign.entity.People;

import java.util.List;

public interface MyFeign {
    @RequestLine("GET /peoples")
    @Headers("Accept: application/json")
    List<People> getPeoples();

    @RequestLine("POST /peoples")
    @Headers("Content-Type: application/json")
    People createPeople(People people);
}
