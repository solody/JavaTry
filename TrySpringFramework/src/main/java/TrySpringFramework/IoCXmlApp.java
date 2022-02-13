package TrySpringFramework;

import TrySpringFramework.Service.Service1;
import TrySpringFramework.Service.Service2;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class IoCXmlApp {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("services.xml");
        System.out.println(context);

        // Access a formal bean.
        Service1 s1 = (Service1)context.getBean("service1");
        s1.doSomething();

        // Nesting bean.
        Service2 s2 = (Service2)context.getBean("service2");
        s2.doSomething();
        // Access service1 through dependency injection.
        s2.service1.doSomething();

        // Access service1 by alias.
        Service1 s = (Service1)context.getBean("s");
        s.doSomething();

    }
}
