package org.example.TryJava;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Smtp {
    public static void main(String[] args) throws InterruptedException {
        String to = "164713332@qq.com"; // 收件人电子邮箱
        String from = "dmp-notification@vestomedia.net"; // 发件人电子邮箱
        final String username = "ocid1.user.oc1..aaaaaaaauzzrxgclwsqntagiqnuqzook4ahbr64fc6cgh4civ327rw52hnfq@ocid1.tenancy.oc1..aaaaaaaan4gndp5e2lhvx4rkh6b4t3aarc5hq2k764nfiaifp4cmvetdvvsq.34.com"; // 用于SMTP认证的用户名
        final String password = "+)yt-$E.xbO265>N{Il)"; // 用于SMTP认证的密码

        // 指定发送邮件的主机为 smtp.example.com
        String host = "smtp.email.us-ashburn-1.oci.oraclecloud.com";

        // 获取系统属性
        Properties properties = System.getProperties();

        // 设置邮件服务器
        properties.setProperty("mail.smtp.host", host);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true"); // 如果需要TLS的话
        properties.put("mail.smtp.port", "587"); // SMTP端口，例如587或465（对于SSL）

        // 获取默认的Session对象。注意：第三个参数是用户名/密码认证
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // 创建默认的MimeMessage对象
            MimeMessage message = new MimeMessage(session);

            // Set From: 头部头字段
            message.setFrom(new InternetAddress(from));

            // Set To: 头部头字段
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: 头部头字段
            message.setSubject("这是邮件的主题");

            // 设置消息体
            message.setText("这是邮件的正文内容");

            // 发送消息
            Transport.send(message);
            System.out.println("邮件发送成功...");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
