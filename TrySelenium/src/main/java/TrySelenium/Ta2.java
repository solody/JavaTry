package TrySelenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class Ta2 {
    public static void main(String[] args) {

        System.getProperties().put( "proxySet", "true" );
        System.getProperties().put( "proxyHost", "127.0.0.1");
        System.getProperties().put( "proxyPort", "11000");
        for (long i = 0; i < 50L; i++){
            final long index = i;
            Thread t = new Thread() {
                public void run() {
                    System.out.println("正在运行线程"+index);
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--headless");
                    //chromeOptions.addArguments("--proxy-server=http://125.122.144.141:9999");
                    WebDriver driver = null;
                    try {
                        driver = new RemoteWebDriver(new URL("http://47.243.118.133:4444/wd/hub"), chromeOptions);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    driver.get("https://debug.j18.hk");

                    // System.out.println(driver.getCurrentUrl());
                    // System.out.println(driver.getTitle());
                    // System.out.println(driver.getPageSource());

                    try {
                        Thread.sleep(10*60*1000L);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    driver.quit();
                }
            };
            t.start();
            System.out.println("已创建线程"+index);
        }
    }
}
