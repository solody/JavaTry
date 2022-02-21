package TrySpringFramework;

import TrySpringFramework.Model.Person;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class DataBindingApp {
    public static void main(String[] args) {
        BeanWrapper person  = new BeanWrapperImpl(new Person());

        person.setPropertyValue("name", "Kent");
        System.out.println(person.getPropertyValue("name"));

        // Type converting is automatically.
        person.setPropertyValue("name", 123456);
        System.out.println(person.getPropertyValue("name"));

        person.setPropertyValue("name", '好');
        System.out.println(person.getPropertyValue("name"));

        person.setPropertyValue("name", true);
        System.out.println(person.getPropertyValue("name"));
    }
}
