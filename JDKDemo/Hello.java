public class Hello {
    public static void main(String[] args) {
        System.out.println("Hello!");

        People people = new People("小明");
        System.out.println("Hello! " + people.getName());
    }
}
