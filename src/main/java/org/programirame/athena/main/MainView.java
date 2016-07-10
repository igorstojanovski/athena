package org.programirame.athena.main;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.VerticalLayout;
import org.programirame.athena.models.Client;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@SpringView(name = "")
public class MainView extends VerticalLayout implements View, MainViewInterface {

    @Autowired
    List<MainViewListenerInterface> mainViewListeners;

    private ComboBox clientCombo;
    private Client selectedClient;

    @PostConstruct
    public void init() {
        setSpacing(true);
        Button clientButton = getClientButton();
        clientCombo = getClientCombo();

        addComponent(clientCombo);
        addComponent(clientButton);

        mainViewListeners.forEach(listener -> listener.viewInitialized(this));
    }

    private Button getClientButton() {
        Button clientButton = new Button("Open Client");

        clientButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                                for (MainViewListenerInterface listener : mainViewListeners) {
                    listener.clientSelected(selectedClient);
                }
            }
        });
        return clientButton;
    }

    private ComboBox getClientCombo() {
        ComboBox clientCombo = new ComboBox("Clients", new ArrayList<>());
        clientCombo.setNullSelectionAllowed(false);
        clientCombo.setItemCaptionPropertyId("name");

        clientCombo.addValueChangeListener((Property.ValueChangeListener) valueChangeEvent
                -> selectedClient = (Client) valueChangeEvent.getProperty().getValue());
        return clientCombo;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }

    @Override
    public void refreshClientsCombo(List<Client> clients) {
        BeanItemContainer<Client> clientsContainer = new BeanItemContainer<>(Client.class, clients);
        clientCombo.removeAllItems();
        clientCombo.setContainerDataSource(clientsContainer);
    }
}
