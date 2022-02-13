package TrySpringFramework;

import TrySpringFramework.Model.Person;
import TrySpringFramework.Model.PersonValidator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "TrySpringFramework.Model")
public class IoCAnnotationApp {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(IoCAnnotationApp.class);

        PersonValidator pvd = ctx.getBean(PersonValidator.class);
        System.out.println(pvd);

        Person p = (Person) ctx.getBean(Person.class);
        System.out.println(p);
    }
}
