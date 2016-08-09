package org.programirame.athena.contact.email;


import org.programirame.athena.model.EmailTemplate;
import org.programirame.athena.search.SearchViewState;

import java.util.List;

public interface EmailWindowInterface {


    void refreshTemplateList(List<EmailTemplate> templates);

    SearchViewState getSearchViewState();

    void changeState(String emailBody, String emailRecipient, String emailSubject, int position, int total);
}
