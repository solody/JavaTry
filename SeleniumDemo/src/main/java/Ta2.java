import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class Ta2 {
    public static void main(String[] args) throws MalformedURLException {

        for (long i = 0; i < 1000L; i++){
            final long index = i;
            Thread t = new Thread() {
                public void run() {
                    System.out.println("正在运行线程"+index);
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--headless");
                    //chromeOptions.addArguments("--proxy-server=http://125.122.144.141:9999");
                    WebDriver driver = null;
                    try {
                        driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), chromeOptions);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    driver.get("https://www.baidu.com");
                    System.out.println(driver.getTitle()+index);
                    driver.quit();
                }
            };
            t.start();
            System.out.println("已创建线程"+index);
        }
    }
}
