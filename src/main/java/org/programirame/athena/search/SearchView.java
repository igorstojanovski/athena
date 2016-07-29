package org.programirame.athena.search;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.NumberRenderer;
import org.programirame.athena.contact.email.EmailWindow;
import org.programirame.athena.model.Clients;
import org.programirame.athena.model.Invoice;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

@SpringView(name = "search")
public class SearchView extends VerticalLayout implements View, SearchViewInterface {

    @Autowired
    private List<SearchViewListener> searchViewListeners;

    private TextField searchField;
    private Button searchButton;
    private Grid clientsGrid;
    private Grid invoicesGrid;
    private BeanItemContainer<Clients> clientsContainer;
    private BeanItemContainer<Invoice> invoicesContainer;
    private HorizontalLayout toolBeltLayout;
    private Button invoiceViewButton;
    private Button clientViewButton;
    private Button contacClientsButton;


    @PostConstruct
    public void init() {
        addComponent(getSearchFieldLayout());
        initializeToolBelt();
        initializeClientsGrid();
        initializeInvoicesGrid();
    }

    private void initializeInvoicesGrid() {
        invoicesGrid = new Grid();
        invoicesGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        invoicesContainer = new BeanItemContainer<>(Invoice.class, new ArrayList<>());
        invoicesGrid.setContainerDataSource(invoicesContainer);
        invoicesGrid.setWidth("100%");

        invoicesGrid.removeColumn("additionalProperties");
    }

    private void initializeToolBelt() {
        toolBeltLayout = new HorizontalLayout();
        toolBeltLayout.setMargin(new MarginInfo(true, true, true, true));
        toolBeltLayout.setMargin(true);
        toolBeltLayout.setStyleName("tool-belt");

        contacClientsButton = new Button();
        contacClientsButton.addStyleName("email-clients-button");
        contacClientsButton.setIcon(FontAwesome.ENVELOPE);
        contacClientsButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                Window w = new EmailWindow();
                w.setVisible(true);

                UI.getCurrent().addWindow(w);
            }
        });

        invoiceViewButton = new Button();
        invoiceViewButton.addStyleName("invoice-view-button");
        invoiceViewButton.setIcon(FontAwesome.FILE_PDF_O);
        invoiceViewButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                toolBeltLayout.removeComponent(invoiceViewButton);
                toolBeltLayout.addComponent(clientViewButton);

                Collection<Object> rows = clientsGrid.getSelectedRows();

                ArrayList<Invoice> invoices = new ArrayList<Invoice>();

                System.out.println("NUmber of selected rows: "+rows.size());

                for(Object row:rows) {
                    Clients client = (Clients) row;
                     invoices.addAll(client.getInvoice());
                }

                clientsGrid.getSelectionModel().reset();
                invoicesContainer.removeAllItems();
                invoicesContainer.addAll(invoices);

                removeComponent(clientsGrid);
                addComponent(invoicesGrid);
            }
        });

        clientViewButton = new Button();
        clientViewButton.addStyleName("client-view-button");
        clientViewButton.setIcon(FontAwesome.USER);
        clientViewButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                toolBeltLayout.removeComponent(clientViewButton);
                toolBeltLayout.addComponent(invoiceViewButton);

                removeComponent(invoicesGrid);
                addComponent(clientsGrid);
            }
        });

        toolBeltLayout.addComponent(contacClientsButton);
        toolBeltLayout.addComponent(invoiceViewButton);
    }

    private void initializeClientsGrid() {
        clientsGrid = new Grid();
        clientsGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        clientsContainer = new BeanItemContainer<>(Clients.class, new ArrayList<>());
        clientsGrid.setContainerDataSource(clientsContainer);
        clientsGrid.setWidth("100%");
        clientsGrid.removeColumn("additionalProperties");
        clientsGrid.removeColumn("payments");
        clientsGrid.removeColumn("type");
        clientsGrid.removeColumn("surname");

        clientsGrid.getColumn("invoice").setHeaderCaption("Number of invoices");
        clientsGrid.getColumn("invoice").setRenderer(new NumberRenderer(), new Converter<Integer, List<Invoice>>() {
            @Override
            public List<Invoice> convertToModel(Integer integer, Class<? extends List<Invoice>> aClass, Locale locale) throws ConversionException {
                return new ArrayList<Invoice>();
            }

            @Override
            public Integer convertToPresentation(List<Invoice> invoices, Class<? extends Integer> aClass, Locale locale) throws ConversionException {
                return invoices.size();
            }

            @Override
            public Class<List<Invoice>> getModelType() {
                Class<List<Invoice>> clazz = (Class) List.class;
                return clazz;
            }

            @Override
            public Class<Integer> getPresentationType() {
                return Integer.class;
            }
        });

    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        searchViewListeners.forEach(listener -> listener.viewInitialized(this));
    }

    private HorizontalLayout getSearchFieldLayout() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();

        searchField = new TextField();
        searchField.setSizeFull();

        searchButton = new Button("Search", this::search);

        horizontalLayout.addComponent(searchField);
        horizontalLayout.addComponent(searchButton);
        horizontalLayout.setExpandRatio(searchField, 1.0f);
        horizontalLayout.setSizeFull();
        horizontalLayout.setMargin(true);
        horizontalLayout.setMargin(new MarginInfo(true, true, true, true));

        return horizontalLayout;
    }

    private void search(Button.ClickEvent event) {
        searchViewListeners.forEach(searchViewListener -> searchViewListener.search(searchField.getValue()));
    }

    @Override
    public void refreshResultsTable(List<Clients> clientList) {
        clientsContainer.removeAllItems();
        clientsContainer.addAll(clientList);

        removeComponent(clientsGrid);
        removeComponent(invoicesGrid);
        removeComponent(toolBeltLayout);
        toolBeltLayout.removeComponent(clientViewButton);
        toolBeltLayout.addComponent(invoiceViewButton);

        addComponent(toolBeltLayout);
        addComponent(clientsGrid);
    }
}
