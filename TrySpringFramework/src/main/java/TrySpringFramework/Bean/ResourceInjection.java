package TrySpringFramework.Bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Scanner;

@Component
public class ResourceInjection {

    private final Resource xmlFile;

    ResourceInjection(@Value("${xmlFile.path}") Resource xmlFile) {
        this.xmlFile = xmlFile;
    }

    public void read() throws IOException {
        Scanner xmlSanner = new Scanner(this.xmlFile.getInputStream());
        while (xmlSanner.hasNextLine()) {
            System.out.println(xmlSanner.nextLine());
        }
    }
}
