public class People {
    private String name;

    People(String name) {
        this.name = name;
    }
    public People setName(String name) {
        this.name = name;
        return this;
    }
    public String getName() {
        return this.name;
    }
}