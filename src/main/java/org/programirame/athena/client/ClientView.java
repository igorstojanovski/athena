package org.programirame.athena.client;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import org.programirame.athena.models.Client;
import org.programirame.athena.models.Invoice;
import org.programirame.athena.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@SpringView(name = "client")
public class ClientView extends VerticalLayout implements View, ClientViewInterface {

    @Autowired
    ClientService clientService;

    @Autowired
    List<ClientViewListener> listeners;

    private BeanItemContainer<Invoice> invoicesContainer;
    private Grid invoiceGrid;
    private String clientParameter;

    @PostConstruct
    public void init() throws URISyntaxException {

        initializeGrid();

        addComponent(invoiceGrid);

    }

    private void initializeGrid() {

        System.out.println("====> URI FRAGMENT IS "+Page.getCurrent().getUriFragment());


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
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        clientParameter = viewChangeEvent.getParameters();
        System.out.println("parameter: "+viewChangeEvent.getParameters());

        listeners.forEach(listener -> listener.viewInitialized(this));
    }

    @Override
    public void refreshInvoices(List<Invoice> invoices) {
        invoicesContainer.removeAllItems();
        invoicesContainer.addAll(invoices);
    }

    @Override
    public String getClientParameter() {
        return clientParameter;
    }
}
