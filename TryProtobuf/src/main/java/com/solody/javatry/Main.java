package com.solody.javatry;

import com.example.tutorial.protos.Person;
import com.google.protobuf.InvalidProtocolBufferException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws InvalidProtocolBufferException {
        Person.Builder personBuilder = Person.newBuilder();
        personBuilder
                .setId(1)
                .setEmail("123@123.com");

        Person person = personBuilder.build();
        Person person2 = Person.parseFrom(person.toByteArray());

        System.out.println(person2.getId());
    }
}