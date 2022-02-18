package TrySpringFramework;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Scanner;

public class ResourceAccessApp {
    public static void main(String[] args) throws IOException {
        ApplicationContext context = new ClassPathXmlApplicationContext("services.xml");
        Resource resource = context.getResource("https://www.baidu.com/index.php");
        Scanner sanner = new Scanner(resource.getInputStream());
        while (sanner.hasNextLine()) {
            System.out.println(sanner.nextLine());
        }
    }
}
