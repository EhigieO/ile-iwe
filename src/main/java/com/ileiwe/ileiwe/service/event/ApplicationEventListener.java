package com.ileiwe.ileiwe.service.event;
import com.ileiwe.ileiwe.service.mail.EmailService;
import com.ileiwe.ileiwe.service.mail.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
@Slf4j
public class ApplicationEventListener {

    @Autowired
    EmailService emailService;

    @Autowired
    TemplateEngine templateEngine;

    @EventListener
    public void handleRegistrationCompleteEvent
            (OnRegistrationCompleteEvent event){
        log.info("event triggered");
        this.confirmRegistration(event);

    }

    private void confirmRegistration(
            OnRegistrationCompleteEvent event){

        //String verificationToken = UUID.randomUUID().toString();
        Message message = Message.builder()
                .from("tboydv1@gmail.com")
                .to(event.getAppUser().getEmail())
                .subject("Confirmation Email")
                .body(templateEngine.process
                        ("confirmation.html",
                                new Context()))
                .build();
        emailService.send(message);
    }
}