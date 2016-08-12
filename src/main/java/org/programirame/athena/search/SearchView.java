package org.programirame.athena.search;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.DateRenderer;
import com.vaadin.ui.renderers.NumberRenderer;
import org.programirame.athena.contact.email.EmailWindow;
import org.programirame.athena.contact.email.EmailWindowPresenter;
import org.programirame.athena.model.Bucket;
import org.programirame.athena.model.Clients;
import org.programirame.athena.model.Invoice;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.vaadin.ui.themes.ValoTheme.BUTTON_LINK;

@SpringView(name = "search")
public class SearchView extends VerticalLayout implements View, SearchViewInterface {

    public static SearchViewState state = SearchViewState.CLIENT;

    @Autowired
    private List<SearchViewListenerInterface> searchViewListenerInterfaces;

    @Autowired
    private EmailWindowPresenter emailWindowPresenter;

    private TextField searchField;
    private Button searchButton;
    private Grid clientsGrid;
    private Grid invoicesGrid;
    private BeanItemContainer<Clients> clientsContainer;
    private BeanItemContainer<Invoice> invoicesContainer;
    private HorizontalLayout toolBeltLayout;
    private Button invoiceViewButton;
    private Button clientViewButton;
    private Button contactClientsButton;
    private ComboBox searchFiltersCombo;
    private HorizontalLayout resultsHLayout;
    private VerticalLayout bucketsVLayout;

    @PostConstruct
    public void init() {
        addComponent(getSearchFieldLayout());
        initializeToolBelt();
        initializeClientsGrid();
        initializeInvoicesGrid();

        resultsHLayout = new HorizontalLayout();
        bucketsVLayout = new VerticalLayout();
        bucketsVLayout.setWidth("160px");

        resultsHLayout.addComponent(bucketsVLayout);
        resultsHLayout.setWidth("100%");

        addComponent(resultsHLayout);
    }

    private void initializeInvoicesGrid() {
        invoicesGrid = new Grid();
        invoicesGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        invoicesContainer = new BeanItemContainer<>(Invoice.class, new ArrayList<>());
        invoicesGrid.setContainerDataSource(invoicesContainer);
        invoicesGrid.setWidth("100%");
        invoicesGrid.setColumnOrder("invoiceId", "invoiceDate", "invoiceAmount", "invoiceDueDate", "invoicePayedAmount");

        invoicesGrid.getColumn("invoiceDate").setRenderer(new DateRenderer(DateFormat.getDateInstance(DateFormat.LONG, Locale.ENGLISH)));
        invoicesGrid.getColumn("invoiceDueDate").setRenderer(new DateRenderer(DateFormat.getDateInstance(DateFormat.LONG, Locale.ENGLISH)));
        invoicesGrid.getColumn("additionalProperties").setHeaderCaption("Debt");
        invoicesGrid.getColumn("additionalProperties").setRenderer(new NumberRenderer(), new Converter<BigDecimal, Map<String, Object>>() {
            @Override
            public Map<String, Object> convertToModel(BigDecimal number, Class<? extends Map<String, Object>> aClass, Locale locale) throws ConversionException {
                return new HashMap<>();
            }

            @Override
            public BigDecimal convertToPresentation(Map<String, Object> stringObjectMap, Class<? extends BigDecimal> aClass, Locale locale) throws ConversionException {

                BigDecimal outstanding = (BigDecimal) stringObjectMap.get("outstanding");

                return outstanding;
            }

            @Override
            public Class<Map<String, Object>> getModelType() {
                Class<Map<String, Object>> clazz = (Class) Map.class;
                return clazz;
            }

            @Override
            public Class<BigDecimal> getPresentationType() {
                return BigDecimal.class;
            }
        });

        invoicesGrid.setCellStyleGenerator(new Grid.CellStyleGenerator() {
            @Override
            public String getStyle(Grid.CellReference cellReference) {


                Date dueDate = (Date) cellReference.getItem().getItemProperty("invoiceDueDate").getValue();
                BigDecimal outstandingAmount = (BigDecimal) ((HashMap) cellReference.getItem().getItemProperty("additionalProperties")
                        .getValue()).get("outstanding");
                if (dueDate.before(new Date())
                        && outstandingAmount.compareTo(BigDecimal.ZERO) != 0) return "indebt";
                else return null;


            }
        });

        Grid.FooterRow footerSummaryRow = invoicesGrid.appendFooterRow();
        Grid.FooterRow footerHeadingRow = invoicesGrid.prependFooterRow();

        footerHeadingRow.getCell("additionalProperties").setText("Total outstanding");
        footerSummaryRow.getCell("additionalProperties").setText(getTotalDebtValue(invoicesContainer));

    }

    private String getTotalDebtValue(BeanItemContainer<Invoice> invoicesContainer) {

        List<Invoice> invoices = invoicesContainer.getItemIds();

        BigDecimal sum = BigDecimal.ZERO;

        for (Invoice invoice : invoices) {
            BigDecimal outstanding = (BigDecimal) invoice.getAdditionalProperties().get("outstanding");
            sum = sum.add(outstanding);
        }


        return sum.toPlainString();
    }

    private void initializeToolBelt() {
        toolBeltLayout = new HorizontalLayout();
        toolBeltLayout.setMargin(new MarginInfo(true, true, true, true));
        toolBeltLayout.setMargin(true);
        toolBeltLayout.setStyleName("tool-belt");

        contactClientsButton = new Button();
        contactClientsButton.addStyleName("email-clients-button");
        contactClientsButton.setIcon(FontAwesome.ENVELOPE);
        contactClientsButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                Collection<Object> selectedItems = clientsGrid.getSelectionModel().getSelectedRows();
                List itemsList = selectedItems.stream().collect(Collectors.toList());

                EmailWindow emailWindow;

                if (state == SearchViewState.CLIENT) {
                    emailWindowPresenter.setSelectedClients(getSelectedClients());
                    emailWindow = new EmailWindow(emailWindowPresenter, state);
                } else {
                    emailWindowPresenter.setSelectedInvoices(getSelectedInvoices());
                    emailWindow = new EmailWindow(emailWindowPresenter, state);
                }

                emailWindowPresenter.setEmailView(emailWindow);
                emailWindowPresenter.setSelectedClients(itemsList);

                UI.getCurrent().addWindow(emailWindow);
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
                changeState(SearchViewState.INVOICE);
                ArrayList<Invoice> invoices = getInvoices(getSelectedClients());

                clientsGrid.getSelectionModel().reset();
                invoicesContainer.removeAllItems();
                invoicesContainer.addAll(invoices);

                invoicesGrid.getFooterRow(1).getCell("additionalProperties").setText(getTotalDebtValue(invoicesContainer));


                resultsHLayout.removeComponent(clientsGrid);
                resultsHLayout.addComponent(invoicesGrid);
                resultsHLayout.setExpandRatio(invoicesGrid, 1.0f);
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
                changeState(SearchViewState.CLIENT);
                resultsHLayout.removeComponent(invoicesGrid);
                resultsHLayout.addComponent(clientsGrid);
                resultsHLayout.setExpandRatio(clientsGrid, 1.0f);
            }
        });

        toolBeltLayout.addComponent(contactClientsButton);
        toolBeltLayout.addComponent(invoiceViewButton);
    }

    private ArrayList<Invoice> getSelectedInvoices() {
        Collection<Object> rows = invoicesGrid.getSelectedRows();

        ArrayList<Invoice> invoices = new ArrayList<>();

        for (Object row : rows) {
            Invoice invoice = (Invoice) row;
            invoices.add(invoice);
        }
        return invoices;
    }

    private ArrayList<Invoice> getInvoices(ArrayList<Clients> clients) {
        ArrayList<Invoice> invoices = new ArrayList<>();

        for (Clients client : clients) {
            invoices.addAll(client.getInvoice());
        }
        return invoices;
    }

    private ArrayList<Clients> getSelectedClients() {
        Collection<Object> rows = clientsGrid.getSelectedRows();

        ArrayList<Clients> clients = new ArrayList<>();

        for (Object row : rows) {
            Clients client = (Clients) row;
            clients.add(client);
        }
        return clients;
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
        clientsGrid.setColumnOrder("externalId", "name", "taxNumber", "uid", "invoice");

        clientsGrid.getColumn("invoice").setHeaderCaption("Number of invoices");
        clientsGrid.getColumn("invoice").setRenderer(new NumberRenderer(), new Converter<Integer, List<Invoice>>() {
            @Override
            public List<Invoice> convertToModel(Integer integer, Class<? extends List<Invoice>> aClass, Locale locale) throws ConversionException {
                return new ArrayList<>();
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
        searchViewListenerInterfaces.forEach(listener -> listener.viewInitialized(this));
    }

    private HorizontalLayout getSearchFieldLayout() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();

        searchFiltersCombo = new ComboBox();
        searchFiltersCombo.setWidth("150px");
        searchFiltersCombo.addItems("Due Date In");
        searchFiltersCombo.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                if (valueChangeEvent.getProperty().getValue() != null
                        && valueChangeEvent.getProperty().getValue().equals("Due Date In")) {
                    if (searchField.getValue().isEmpty()) {
                        searchField.setValue("DueDateIn=");
                    } else if (!searchField.getValue().contains("DueDateIn")) {
                        searchField.setValue(searchField.getValue() + ",DueDateIn=");
                    }
                }

                searchFiltersCombo.select(searchFiltersCombo.getNullSelectionItemId());
                searchField.focus();
            }
        });

        searchField = new TextField();
        searchField.setSizeFull();

        searchButton = new Button("Search", this::search);
        searchButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        horizontalLayout.addComponent(searchFiltersCombo);
        horizontalLayout.addComponent(searchField);
        horizontalLayout.addComponent(searchButton);
        horizontalLayout.setExpandRatio(searchField, 1.0f);
        horizontalLayout.setSizeFull();
        horizontalLayout.setMargin(true);
        horizontalLayout.setMargin(new MarginInfo(true, true, true, true));

        return horizontalLayout;
    }

    private void search(Button.ClickEvent event) {
        searchViewListenerInterfaces.forEach(searchViewListener -> searchViewListener.search(searchField.getValue()));
    }

    @Override
    public void refreshResultsTable(List<Clients> clientList) {
        clientsContainer.removeAllItems();
        clientsContainer.addAll(clientList);

        changeState(SearchViewState.CLIENT);

        resultsHLayout.removeComponent(clientsGrid);
        resultsHLayout.removeComponent(invoicesGrid);
        removeComponent(toolBeltLayout);
        toolBeltLayout.removeComponent(clientViewButton);
        toolBeltLayout.addComponent(invoiceViewButton);

        resultsHLayout.addComponent(clientsGrid);
        addComponent(toolBeltLayout);
        addComponent(resultsHLayout);

        resultsHLayout.setExpandRatio(clientsGrid, 1.0f);
    }

    @Override
    public void refreshAggregates(List<Bucket> buckets) {
        bucketsVLayout.removeAllComponents();

        if (buckets.size() > 0) {
            for (Bucket bucket : buckets) {
                Button b = new Button(bucket.getBucketName() + "(" + bucket.getBucketCount() + ")");
                b.setStyleName(BUTTON_LINK);
                b.addClickListener((Button.ClickListener) clickEvent -> {
                    if (!searchField.getValue().contains(",City=" + bucket.getBucketName())) {
                        searchField.setValue(searchField.getValue() + ",City=" + bucket.getBucketName());
                        searchButton.click();
                    }
                });
                bucketsVLayout.addComponent(b);
            }
        }
    }

    public void changeState(SearchViewState newState) {
        state = newState;
    }
}
