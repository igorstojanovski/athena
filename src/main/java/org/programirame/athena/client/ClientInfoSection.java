package org.programirame.athena.client;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import org.programirame.athena.model.Clients;

/**
 * Contains all the elements to display basic Client info.
 *
 */
public class ClientInfoSection extends FormLayout {

    private Label nameTextLabel;
    private Label surnameLabel;
    private Label taxNumberLabel;
    private Label uidLabel;

    public ClientInfoSection() {
        nameTextLabel = new Label();
        nameTextLabel.setCaption("Name");

        surnameLabel = new Label();
        surnameLabel.setCaption("Surname");
        surnameLabel.setIcon(FontAwesome.USER);

        uidLabel = new Label();
        uidLabel.setCaption("UID");

        taxNumberLabel = new Label();
        taxNumberLabel.setCaption("Tax Number");
    }

    public void refreshClientInfo(Clients client) {
        nameTextLabel.setValue(client.getName());
        surnameLabel.setValue(client.getSurname());

        uidLabel.setValue(client.getUid());
        uidLabel.setIcon(FontAwesome.KEY);

        taxNumberLabel.setValue(client.getTaxNumber());
        taxNumberLabel.setIcon(FontAwesome.DOLLAR);

        addComponent(nameTextLabel);

        if(client.getType().getId() == 1) {
            nameTextLabel.setIcon(FontAwesome.USER);
            addComponent(surnameLabel);
            addComponent(uidLabel);
        } else {
            nameTextLabel.setIcon(FontAwesome.BUILDING);
            addComponent(uidLabel);
            addComponent(taxNumberLabel);
        }
    }

}
