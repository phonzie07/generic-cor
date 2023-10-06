package com.generic.core.service;

import java.io.IOException;
import java.util.List;
import javax.mail.MessagingException;
import org.springframework.stereotype.Component;

@Component
public interface NotifService {

    String sendEmail(List<String> emailTo, List<String> ccTo, String subject, String body) throws MessagingException, IOException;
    String sendEmail(List<String> emailTo, String subject, String body) throws MessagingException, IOException;

}
