package org.programirame.athena.service;

import org.programirame.athena.model.EmailTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmailTemplateService {

    public List<EmailTemplate> getInvoiceTemplates() {

        List<EmailTemplate> emailTemplates = new ArrayList<>();

        emailTemplates.add(getInvoiceTemplate());

        return emailTemplates;
    }

    public List<EmailTemplate> getIClientTemplates() {

        List<EmailTemplate> emailTemplates = new ArrayList<>();

        emailTemplates.add(getClientTemplate());

        return emailTemplates;
    }

    private EmailTemplate getClientTemplate() {
        EmailTemplate basicTemplate = new EmailTemplate();
        basicTemplate.setName("Summary Total");

        basicTemplate.setContent("<p>Dear Sir/Madam,&nbsp;</p>\n" +
                "<p>&nbsp;</p>\n" +
                "<p>We would like to inform you that you are late on your payment of <strong>total_debt</strong> denars. Please make a payment in the shortest possible term.</strong></p>\n" +
                "<p>&nbsp;</p>\n" +
                "<p>Best Regards,&nbsp;</p>\n" +
                "<p>Igor Stojanovski</p>");
        basicTemplate.setSubject("Account Past Due");
        basicTemplate.setType("client");

        return basicTemplate;
    }

    private EmailTemplate getInvoiceTemplate() {
        EmailTemplate specialemplate = new EmailTemplate();
        specialemplate.setName("Oldest Invoice");
        specialemplate.setSubject("Invoice Past Due");
        specialemplate.setContent("<p>Dear Special Sir/Madam,&nbsp;</p>\n" +
                "<p>&nbsp;</p>\n" +
                "<p>We would like to inform you that you are late on your payment of <strong>invoice_amount</strong> denars for your invoice with invoice id <strong>invoice_id.</strong></p>\n" +
                "<p>&nbsp;</p>\n" +
                "<p>Best Regards,&nbsp;</p>\n" +
                "<p>Igor Stojanovski</p>");
        specialemplate.setType("invoice");

        return specialemplate;
    }
}
