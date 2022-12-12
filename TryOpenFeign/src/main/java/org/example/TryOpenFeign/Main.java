package org.example.TryOpenFeign;

import feign.Feign;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import org.example.TryOpenFeign.entity.People;

import java.util.List;

public class Main {

    static class ForwardedForInterceptor implements RequestInterceptor {
        @Override public void apply(RequestTemplate template) {
            template.header("X-Forwarded-For", "origin.host.com");
        }
    }

    public static void main(String[] args) {
        MyFeign feign = Feign.builder()
                .requestInterceptor(new ForwardedForInterceptor())
                .decoder(new GsonDecoder())
                .encoder(new GsonEncoder())
                .target(MyFeign.class, "http://localhost:8080");

        // Fetch and print a list of the contributors to this library.
        List<People> peoples = feign.getPeoples();
        for (People people : peoples) {
            System.out.println(people.getName());
        }

        People people = new People("Apple");
        People apple = feign.createPeople(people);
        System.out.println(people);
    }
}