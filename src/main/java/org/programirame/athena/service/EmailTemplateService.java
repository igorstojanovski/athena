package org.programirame.athena.service;

import org.programirame.athena.model.EmailTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmailTemplateService {

    public List<EmailTemplate> getTemplates() {

        List<EmailTemplate> emailTemplates = new ArrayList<>();

        EmailTemplate basicTemplate = new EmailTemplate();
        basicTemplate.setName("Basic Template");

        basicTemplate.setContent("<p>Dear Sir/Madam,&nbsp;</p>\n" +
                "<p>&nbsp;</p>\n" +
                "<p>We would like to inform you that you are late on your payment of <strong>1000</strong> denars for your invoice with invoice id <strong>84354646.</strong></p>\n" +
                "<p>&nbsp;</p>\n" +
                "<p>Best Regards,&nbsp;</p>\n" +
                "<p>Igor Stojanovski</p>");

        EmailTemplate specialemplate = new EmailTemplate();
        specialemplate.setName("Special Template");

        specialemplate.setContent("<p>Dear Special Sir/Madam,&nbsp;</p>\n" +
                "<p>&nbsp;</p>\n" +
                "<p>We would like to inform you that you are late on your payment of <strong>1000</strong> denars for your invoice with invoice id <strong>84354646.</strong></p>\n" +
                "<p>&nbsp;</p>\n" +
                "<p>Best Regards,&nbsp;</p>\n" +
                "<p>Igor Stojanovski</p>");


        emailTemplates.add(specialemplate);

        return emailTemplates;
    }
}
