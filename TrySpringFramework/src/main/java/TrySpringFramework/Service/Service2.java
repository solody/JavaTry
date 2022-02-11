package TrySpringFramework.Service;

public class Service2 {
    public Service1 service1;

    public void setService1(Service1 service1) {
        this.service1 = service1;
    }

    public void doSomething() {
        System.out.println("Service2 doing something!");
    }
}
