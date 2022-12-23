package org.example.TryJava;

public class Male extends Human<String>{
    @Override
    public String say(String name) {
        return "Come on, " + super.say(name);
    }
}
