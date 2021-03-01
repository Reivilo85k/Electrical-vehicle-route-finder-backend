package finalproject.evroutefinder.service;


import com.sendgrid.*;
import finalproject.evroutefinder.config.AppConfig;
import finalproject.evroutefinder.exceptions.EVRouteFinderException;
import finalproject.evroutefinder.model.NotificationEmail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {

    private final AppConfig appConfig;

    @Async
    public void sendMail(NotificationEmail notificationEmail) throws IOException {
        Email from = new Email("master@olivier-laborde.com");
        String subject = notificationEmail.getSubject();
        Email to = new Email(notificationEmail.getRecipient());
        Content content = new Content("text/plain", notificationEmail.getBody());
        Mail mail = new Mail(from, subject, to, content);

        System.out.println(appConfig.getSendgridApiKey());

        SendGrid sg = new SendGrid("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");
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            throw ex;
        }
    }

}
