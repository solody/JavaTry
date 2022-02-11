package TrySpringFramework;

import TrySpringFramework.Service.Service1;
import TrySpringFramework.Service.Service2;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class IoCApp {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("services.xml");
        System.out.println(context);
        Service1 s1 = (Service1)context.getBean("service1");
        s1.doSomething();
        Service2 s2 = (Service2)context.getBean("service2");
        s2.doSomething();
        s2.service1.doSomething();
    }
}
