package org.programirame.athena.client;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import org.programirame.athena.models.Client;
import org.programirame.athena.models.Invoice;
import org.programirame.athena.service.ClientService;
import org.programirame.athena.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@SpringView(name = "client")
public class ClientView extends VerticalLayout implements View {

    @Autowired
    ClientService clientService;

    @Autowired
    InvoiceService invoiceService;

    private BeanItemContainer<Invoice> invoicesContainer;
    private Grid invoiceGrid;
    private ComboBox clientCombo;

    @Autowired
    public ClientView(ClientPresenter presenter, ) {

    }

    @PostConstruct
    public void init() throws URISyntaxException {
        invoiceGrid = new Grid();
        invoicesContainer = new BeanItemContainer<>(Invoice.class, new ArrayList<>());
        invoiceGrid.setContainerDataSource(invoicesContainer);
        invoiceGrid.setWidth("100%");
        invoiceGrid.getColumn("client").setConverter(new Converter<String, Client>() {
            @Override
            public Client convertToModel(String s, Class<? extends Client> aClass, Locale locale) throws ConversionException {
                return null;
            }

            @Override
            public String convertToPresentation(Client client, Class<? extends String> aClass, Locale locale) throws ConversionException {
                return client.getName();
            }

            @Override
            public Class<Client> getModelType() {
                return Client.class;
            }

            @Override
            public Class<String> getPresentationType() {
                return String.class;
            }
        });


        List<Client> clients = clientService.getAllClients();
        BeanItemContainer<Client> clientsContainer = new BeanItemContainer<>(Client.class, clients);

        clientCombo = new ComboBox("Clients", clientsContainer);
        clientCombo.setItemCaptionPropertyId("name");



        addComponent(clientCombo);
        addComponent(invoiceGrid);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        clientCombo.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {

                Client client = (Client) valueChangeEvent.getProperty().getValue();

                if(client != null) {
                    try {
                        List<Invoice> invoices = invoiceService.getAllClientInvoices(client.getId());
                        invoicesContainer.removeAllItems();
                        invoicesContainer.addAll(invoices);
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
