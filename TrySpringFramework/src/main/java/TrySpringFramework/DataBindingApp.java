package TrySpringFramework;

import TrySpringFramework.Model.Person;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class DataBindingApp {
    public static void main(String[] args) {
        BeanWrapper person  = new BeanWrapperImpl(new Person());
        person.setPropertyValue("name", "Kent");
        System.out.println(person.getPropertyValue("name"));
    }
}
