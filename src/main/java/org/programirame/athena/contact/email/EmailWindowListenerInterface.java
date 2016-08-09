package org.programirame.athena.contact.email;


import org.programirame.athena.model.EmailTemplate;

public interface EmailWindowListenerInterface {

    void viewInitialized(EmailWindowInterface emailWindow);

    void templateSelected(EmailTemplate emailTemplate);

    void positionChange(int positionChange);
}
