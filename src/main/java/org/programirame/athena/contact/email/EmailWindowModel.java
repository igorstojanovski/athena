package org.programirame.athena.contact.email;

import org.programirame.athena.model.EmailMessageModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailWindowModel {
    private List<EmailMessageModel> emailMessages;
    private int position;

    public void setEmailMessages(List<EmailMessageModel> emailMessages) {
        this.emailMessages = emailMessages;
    }

    public List<EmailMessageModel> getEmailMessages() {
        return emailMessages;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }
}
