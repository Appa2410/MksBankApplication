package com.bank.Application.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.bank.Application.Model.ForgotPasswordRequest;
import com.bank.Application.Model.ForgotRequest;
import com.bank.Application.Model.User;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


import freemarker.template.Configuration;
import freemarker.template.TemplateException;

@Service
public class EmailService {

    final Configuration configuration;
    final JavaMailSender javaMailSender;

    public EmailService(Configuration configuration, JavaMailSender javaMailSender) {
        this.configuration = configuration;
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(User user, String register) throws MessagingException, IOException, TemplateException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject("Registration Success");
        helper.setTo(user.getEmail());
        String emailContent = getEmailContent(user,register);
        helper.setText(emailContent, true);
        javaMailSender.send(mimeMessage);
    }

    String getEmailContent(User user, String message) throws IOException, TemplateException {
        StringWriter stringWriter = new StringWriter();
        Map<String, Object> model = new HashMap<>();
        if(message.equals("register")) {
            model.put("user", user);
            configuration.getTemplate("UserTemplate.ftlh").process(model, stringWriter);
        }
        else if(message.equals("forgotUserName")){
            model.put("user", user);
            configuration.getTemplate("ForgotUserName.ftlh").process(model, stringWriter);
        }
        else {
            model.put("user", user);
            configuration.getTemplate("ForgotPassword.ftlh").process(model, stringWriter);
        }
        return stringWriter.getBuffer().toString();
    }

    public void sendUserName(ForgotRequest forgotRequest,User user,String forgotUserName) throws MessagingException, IOException, TemplateException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject("UserName Successfully Sent");
        helper.setTo(forgotRequest.getEmail());
        String emailContent = getEmailContent(user, forgotUserName);
        helper.setText(emailContent, true);
        javaMailSender.send(mimeMessage);
    }

    public void sendPassword(ForgotPasswordRequest forgotRequest, User user, String forgotPassword) throws MessagingException, IOException, TemplateException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject("Password Successfully Sent");
        helper.setTo(forgotRequest.getEmail());
        String emailContent = getEmailContent(user, forgotPassword);
        helper.setText(emailContent, true);
        javaMailSender.send(mimeMessage);
    }
}