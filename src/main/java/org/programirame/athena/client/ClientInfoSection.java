package org.programirame.athena.client;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;
import org.programirame.athena.models.Client;

/**
 * Contains all the elements to display basic Client info.
 *
 */
public class ClientInfoSection extends FormLayout {

    private TextField nameTextField;
    private TextField surnameTextField;
    private TextField taxNumberTextField;
    private TextField uidTextField;

    public ClientInfoSection() {
        nameTextField = new TextField("Name");

        surnameTextField = new TextField("Surname");
        surnameTextField.setIcon(FontAwesome.USER);

        uidTextField = new TextField("UID");
        taxNumberTextField = new TextField("Tax Number");
    }

    public void refreshClientInfo(Client client) {
        nameTextField.setValue(client.getName());
        surnameTextField.setValue(client.getSurname());
        uidTextField.setValue(client.getUid());
        uidTextField.setIcon(FontAwesome.KEY);
        taxNumberTextField.setValue(client.getTaxNumber());
        taxNumberTextField.setIcon(FontAwesome.DOLLAR);

        addComponent(nameTextField);

        if(client.getType().getId() == 1) {
            nameTextField.setIcon(FontAwesome.USER);
            addComponent(surnameTextField);
            addComponent(uidTextField);
        } else {
            nameTextField.setIcon(FontAwesome.BUILDING);
            addComponent(uidTextField);
            addComponent(taxNumberTextField);
        }
    }

}
