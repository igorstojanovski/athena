package org.programirame.athena.contact.email;


import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import org.programirame.athena.model.Clients;
import org.programirame.athena.model.EmailMessageModel;
import org.programirame.athena.model.EmailTemplate;
import org.programirame.athena.model.Invoice;
import org.programirame.athena.search.SearchViewState;
import org.programirame.athena.service.EmailTemplateService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@UIScope
@SpringComponent
public class EmailWindowPresenter implements EmailWindowListenerInterface {

    public static final String INVOICE_AMOUNT = "invoice_amount";
    public static final String INVOICE_ID = "invoice_id";
    public static final String TOTAL_DEBT = "total_debt";
    public static final String CLIENT = "client";
    @Autowired
    EmailTemplateService emailTemplateService;

    @Autowired
    EmailWindowModel emailWindowModel;

    private List selectedClients;
    private EmailWindow emailView;
    private ArrayList<Invoice> selectedInvoices;

    @Override
    public void viewInitialized(EmailWindowInterface emailWindow) {
        if (emailWindow.getSearchViewState().equals(SearchViewState.CLIENT)) {
            emailWindow.refreshTemplateList(emailTemplateService.getIClientTemplates());
        } else {
            emailWindow.refreshTemplateList(emailTemplateService.getInvoiceTemplates());
        }
    }

    @Override
    public void templateSelected(EmailTemplate emailTemplate) {
        int startingPosition = 0;
        List<EmailMessageModel> emails;

        if (emailTemplate.getType().equals(CLIENT)) {
            emails = fillClientTemplate(emailTemplate, selectedClients);
        } else {
            emails = fillInvoiceTemplate(emailTemplate, selectedInvoices);
        }

        emailWindowModel.setEmailMessages(emails);
        emailWindowModel.setPosition(startingPosition);

        EmailMessageModel emailMessage = emails.get(startingPosition);
        emailView.changeState(emailMessage.getBody(), emailMessage.getRecipient(), emailMessage.getSubject(),
                startingPosition, emails.size());
    }

    @Override
    public void positionChange(int positionChange) {

        emailWindowModel.setPosition(emailWindowModel.getPosition() + positionChange);
        EmailMessageModel emailMessage = emailWindowModel.getEmailMessages().get(emailWindowModel.getPosition());

        emailView.changeState(emailMessage.getBody(), emailMessage.getRecipient(), emailMessage.getSubject(),
                emailWindowModel.getPosition(), emailWindowModel.getEmailMessages().size());
    }

    private List<EmailMessageModel> fillInvoiceTemplate(EmailTemplate emailTemplate, ArrayList<Invoice> selectedInvoices) {

        List<EmailMessageModel> emailMessages = new ArrayList<>();

        for (Invoice invoice : selectedInvoices) {
            String emailBody = emailTemplate.getContent()
                    .replaceAll(INVOICE_AMOUNT, invoice.getInvoiceAmount().toString())
                    .replaceAll(INVOICE_ID, invoice.getInvoiceId());

            EmailMessageModel emailMessage = new EmailMessageModel();
            emailMessage.setBody(emailBody);
            emailMessage.setRecipient((String) invoice.getAdditionalProperties().get("email"));
            emailMessage.setSubject(emailTemplate.getSubject());

            emailMessages.add(emailMessage);
        }

        return emailMessages;
    }

    private List<EmailMessageModel> fillClientTemplate(EmailTemplate emailTemplate, List<Clients> selectedClients) {

        List<EmailMessageModel> emailmessages = new ArrayList<>();

        for(Clients client:selectedClients) {

            EmailMessageModel emailMessage = new EmailMessageModel();
            emailMessage.setRecipient(client.getEmail());
            emailMessage.setSubject(emailTemplate.getSubject());

            BigDecimal totalOutstandingAmount = getTotalOutstandingDebt(client);
            emailMessage.setBody(emailTemplate.getContent().replaceAll(TOTAL_DEBT, totalOutstandingAmount.toString()));

            emailmessages.add(emailMessage);
        }

        return emailmessages;
    }

    private BigDecimal getTotalOutstandingDebt(Clients client) {
        BigDecimal totalOutstandingAmount = BigDecimal.ZERO;

        List<Invoice> invoices = client.getInvoice();

        for (Invoice invoice : invoices) {
            totalOutstandingAmount = totalOutstandingAmount.add(
                    (BigDecimal) invoice.getAdditionalProperties().get("outstanding")
            );
        }

        return totalOutstandingAmount;
    }

    public void setSelectedClients(List selectedClients) {
        this.selectedClients = selectedClients;
    }

    public List getSelectedClients() {
        return selectedClients;
    }

    public void setEmailView(EmailWindow emailView) {
        this.emailView = emailView;
    }

    public EmailWindow getEmailView() {
        return emailView;
    }

    public void setSelectedInvoices(ArrayList<Invoice> selectedInvoices) {
        this.selectedInvoices = selectedInvoices;
    }

    public ArrayList<Invoice> getSelectedInvoices() {
        return selectedInvoices;
    }
}
