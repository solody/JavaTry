package org.example.TryOpenFeign.controller;

import org.example.TryOpenFeign.entity.People;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class HelloController {

    @GetMapping("/peoples")
    public List<People> get() {
        List<People> peopleList = new ArrayList<>();
        peopleList.add(new People("Daisy"));
        peopleList.add(new People("David"));
        return peopleList;
    }

    @PostMapping("/peoples")
    public People post(@RequestBody People people) {
        return people;
    }
}
