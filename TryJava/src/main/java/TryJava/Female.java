package TryJava;

public class Female extends Human<String>{
    @Override
    public String say(String name) {
        return "Hi, " + super.say(name);
    }
}
