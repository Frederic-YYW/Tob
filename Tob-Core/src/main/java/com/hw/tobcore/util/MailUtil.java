package com.hw.tobcore.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.util.Properties;

public class MailUtil {

    private static  Logger log = LoggerFactory.getLogger(MailUtil.class);
    public static MimeMessage createSimpleMail(Session session,String receiverMail,String title, String content) throws  Exception {
        //创建邮件对象
        MimeMessage mm = new MimeMessage(session);
        String mailSender = ConfigHelper.getString("mail.sender.username");
        String mailcc = ConfigHelper.getString("mail.cc.username");
//        String mailReceiver = ConfigHelper.getString(receiverMail);

        log.debug("mailsender: " + mailSender);
        log.debug("mailcc: " + mailcc);
        //设置发件人
        mm.setFrom(new InternetAddress(mailSender));
        //设置收件人
        mm.setRecipient(Message.RecipientType.TO, new InternetAddress(receiverMail));
        //设置抄送人
        if(mailcc!=null) {
            mm.setRecipient(Message.RecipientType.CC, new InternetAddress(mailcc));
        }

//        mm.setSubject(title);
        mm.setSubject(MimeUtility.encodeText(title,MimeUtility.mimeCharset("gb2312"), null));
        mm.setContent(content, "text/html;charset=gbk");


        return mm;
    }

    public static void sendMail(String receiver, String title, String content) throws Exception{
        Properties prop = new Properties();
        String mailHost = ConfigHelper.getString("mail.host");
        String mailSender = ConfigHelper.getString("mail.sender.username");
        String password = ConfigHelper.getString("mail.sender.password");

        log.debug("mailHost: " + mailHost);
        log.debug("mailSender: " + mailSender);
        log.debug("password: " + password);

        prop.put("mail.host", mailHost);
        prop.put("mail.transport.protocol", "smtp");
        prop.put("mail.smtp.auth", "true");
        //使用java发送邮件5步骤
        //1.创建sesssion
        Session session = Session.getInstance(prop);
        //开启session的调试模式，可以查看当前邮件发送状态
            session.setDebug(true);

        //2.通过session获取Transport对象（发送邮件的核心API）
        Transport ts = session.getTransport();
        //3.通过邮件用户名密码链接，阿里云默认是开启个人邮箱pop3、smtp协议的，所以无需在阿里云邮箱里设置
        ts.connect(mailSender, password);

        //4.创建邮件
        Message msg = createSimpleMail(session,receiver, title, content);
        //5.发送电子邮件

        ts.sendMessage(msg, msg.getAllRecipients());
    }

    public static void main(String[] args) throws Exception {
        try{
            sendMail("zhouyu@iristar.com.cn", "test", "test");
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
