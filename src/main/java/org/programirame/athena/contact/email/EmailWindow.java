package org.programirame.athena.contact.email;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.programirame.athena.model.EmailTemplate;
import org.programirame.athena.search.SearchViewState;

import javax.annotation.PostConstruct;
import java.util.List;


@UIScope
@SpringComponent
public class EmailWindow extends Window implements EmailWindowInterface {

    private final EmailWindowListenerInterface emailViewPresenter;

    private VerticalLayout mainLayout = new VerticalLayout();
    private HorizontalLayout displayHLayout = new HorizontalLayout();
    private HorizontalLayout buttonLayout = new HorizontalLayout();
    private VerticalLayout previewLayout = new VerticalLayout();
    private HorizontalLayout navigationLayout = new HorizontalLayout();
    private VerticalLayout infoVLayout = new VerticalLayout();

    private BeanItemContainer<EmailTemplate> emailTemplateBeanItemContainer =
            new BeanItemContainer<>(EmailTemplate.class);

    private Label emailBodyLabel = new Label();
    private Button sendButton = new Button("Send");
    private ListSelect templatesList = new ListSelect("Templates: ");
    private SearchViewState searchViewState;
    private Label emailTemplatesCounter;
    private Button previousTemplate;
    private Button nextTemplate;
    private Label emailSubjectLabel;
    private Label emailRecipientLabel;

    public EmailWindow(EmailWindowPresenter emailWindowPresenter, SearchViewState state) {
        this.emailViewPresenter = emailWindowPresenter;
        searchViewState = state;
        init();
    }

    private void setupPreviewLayout() {
        Label recipientLabel = new Label("To: ");
        recipientLabel.setWidth("100%");

        Label subjectLabel = new Label("Subject: ");
        subjectLabel.setWidth("100%");

        emailRecipientLabel = new Label();
        emailRecipientLabel.setContentMode(ContentMode.HTML);
        emailRecipientLabel.setWidth("100%");

        emailSubjectLabel = new Label();
        emailSubjectLabel.setContentMode(ContentMode.HTML);
        emailSubjectLabel.setWidth("100%");

        HorizontalLayout recipientLayout = new HorizontalLayout();
        recipientLayout.addComponents(recipientLabel, emailRecipientLabel);

        HorizontalLayout subjectLayout = new HorizontalLayout();
        subjectLayout.addComponents(subjectLabel, emailSubjectLabel);

        navigationLayout.setHeight("50px");
        navigationLayout.setWidth("100%");

        previousTemplate = new Button();
        previousTemplate.setEnabled(false);
        previousTemplate.setIcon(FontAwesome.ARROW_LEFT);
        previousTemplate.addClickListener((Button.ClickListener) clickEvent -> emailViewPresenter.positionChange(-1));

        nextTemplate = new Button();
        nextTemplate.setEnabled(false);
        nextTemplate.setIcon(FontAwesome.ARROW_RIGHT);
        nextTemplate.addClickListener((Button.ClickListener) clickEvent -> emailViewPresenter.positionChange(1));

        emailTemplatesCounter = new Label();
        emailTemplatesCounter.setStyleName("send-email-preview-counter");

        navigationLayout.addComponents(previousTemplate, emailTemplatesCounter, nextTemplate);
        navigationLayout.setExpandRatio(emailTemplatesCounter, 1.0f);

        previewLayout.setStyleName("send-email-preview-panel");
        previewLayout.setSizeFull();

        infoVLayout.setWidth("100%");
        infoVLayout.setWidth("100px");
        infoVLayout.addComponents(recipientLayout, subjectLayout);

        emailBodyLabel.setSizeFull();
        emailBodyLabel.setContentMode(ContentMode.HTML);
        emailBodyLabel.setEnabled(false);

        previewLayout.addComponents(infoVLayout, emailBodyLabel, navigationLayout);
        previewLayout.setExpandRatio(emailBodyLabel, 1.0f);
        previewLayout.setSpacing(true);

    }

    private void setupDisplayLayout() {

        displayHLayout.setStyleName("send-email-display-layout");
        displayHLayout.setHeight("100%");
        displayHLayout.setWidth("100%");

        templatesList.setHeight("100%");
        templatesList.setWidth("150px");
        templatesList.setStyleName("send-email-templates-list");

        templatesList.setContainerDataSource(emailTemplateBeanItemContainer);
        templatesList.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
        templatesList.setItemCaptionPropertyId("name");
        templatesList.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                EmailTemplate emailTemplate = (EmailTemplate) valueChangeEvent.getProperty().getValue();
                emailViewPresenter.templateSelected(emailTemplate);
            }
        });

        displayHLayout.addComponents(templatesList, previewLayout);
        displayHLayout.setExpandRatio(previewLayout, 1.0f);
    }

    private void setupMainLayout() {
        sendButton.setStyleName("send-email-send-button");

        buttonLayout.setHeight("50px");
        buttonLayout.setWidth("100%");
        buttonLayout.setStyleName("send-email-button-layout");
        buttonLayout.addComponent(sendButton);

        mainLayout.setResponsive(true);
        mainLayout.setSizeFull();
        mainLayout.addComponents(displayHLayout, buttonLayout);
        mainLayout.setExpandRatio(displayHLayout, 1.0f);
    }

    @PostConstruct
    public void init() {

        setWidth("800px");
        setHeight("500px");

        setModal(true);
        setContent(mainLayout);

        setupPreviewLayout();
        setupDisplayLayout();
        setupMainLayout();

        emailViewPresenter.viewInitialized(this);
    }

    @Override
    public void refreshTemplateList(List<EmailTemplate> templates) {
        emailTemplateBeanItemContainer.addAll(templates);
    }

    @Override
    public SearchViewState getSearchViewState() {
        return searchViewState;
    }

    @Override
    public void changeState(String emailText, String emailRecipient, String emailSubject, int arrayPoistion, int total) {
        emailBodyLabel.setValue(emailText);
        emailSubjectLabel.setValue("<b>"+emailSubject+"</b>");
        emailRecipientLabel.setValue("<b>"+emailRecipient+"</b>");

        int position = arrayPoistion+1;

        emailTemplatesCounter.setValue(position+" of "+total);

        previousTemplate.setEnabled(true);

        nextTemplate.setEnabled(true);

        if(position == 1) {
            previousTemplate.setEnabled(false);
        }

        if(position == total) {
            nextTemplate.setEnabled(false);
        }

    }
}
