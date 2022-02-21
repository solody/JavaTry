package TrySpringFramework;

import TrySpringFramework.Bean.ResourceInjection;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;

@Configuration
@ComponentScan(basePackages = "TrySpringFramework.Bean")
@PropertySource("classpath:application.properties")
public class ResourceInjectionApp {
    public static void main(String[] args) throws IOException {
        ApplicationContext context = new AnnotationConfigApplicationContext(ResourceInjectionApp.class);
        // Read resource by annotation.
        ResourceInjection ri = context.getBean(ResourceInjection.class);
        ri.read();
    }
}
