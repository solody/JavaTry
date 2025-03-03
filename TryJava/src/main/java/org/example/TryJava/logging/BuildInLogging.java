package org.example.TryJava.logging;


import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class BuildInLogging {

    static Logger logger = Logger.getLogger("MyLogger");

    public static void main(String[] args) throws IOException {

        FileHandler fileHandler = new FileHandler("mylog.log", true); // 第二个参数为true表示以追加模式写入
        logger.addHandler(fileHandler);


        SimpleFormatter formatter = new SimpleFormatter();
        fileHandler.setFormatter(formatter);

        logger.setLevel(Level.WARNING); // 设置记录INFO及以上级别的日志


        logger.severe("Fucking!!!!");
        logger.info("This is an info message");
        logger.warning("This is a warning message");
    }
}
