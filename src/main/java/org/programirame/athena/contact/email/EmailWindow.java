package org.programirame.athena.contact.email;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.programirame.athena.model.EmailTemplate;
import org.programirame.athena.service.EmailTemplateService;


@SpringView(name = "send-email-window")
public class EmailWindow extends Window {

    private EmailTemplateService emailTemplateService = new EmailTemplateService();

    private VerticalLayout mainLayout = new VerticalLayout();
    private HorizontalLayout displayHLayout = new HorizontalLayout();
    private HorizontalLayout buttonLayout = new HorizontalLayout();
    private VerticalLayout previewLayout = new VerticalLayout();
    private HorizontalLayout navigationLayout = new HorizontalLayout();
    private HorizontalLayout infoHLayout = new HorizontalLayout();

    private Label previewTextArea = new Label();
    private Button sendButton = new Button("Send");
    private ListSelect templatesList = new ListSelect("Templates: ");

    public EmailWindow() {
        setWidth("800px");
        setHeight("500px");

        setModal(true);
        setContent(mainLayout);

        setupPreviewLayout();
        setupDisplayLayout();
        setupMainLayout();
    }

    private void setupPreviewLayout() {
        TextField recipientTextField = new TextField("Recipient: ");
        recipientTextField.setWidth("100%");

        navigationLayout.setHeight("50px");
        navigationLayout.setWidth("100%");

        Button left = new Button();
        left.setIcon(FontAwesome.ARROW_LEFT);

        Button right = new Button();
        right.setIcon(FontAwesome.ARROW_RIGHT);


        Label counter = new Label("1 0f 10");
        counter.setStyleName("send-email-preview-counter");


        navigationLayout.addComponents(left, counter, right);
        navigationLayout.setExpandRatio(counter, 1.0f);

        previewLayout.setStyleName("send-email-preview-panel");
        previewLayout.setSizeFull();

        infoHLayout.setWidth("100%");
        infoHLayout.setWidth("100px");
        infoHLayout.addComponent(recipientTextField);


        previewTextArea.setSizeFull();
        previewTextArea.setContentMode(ContentMode.HTML);
        previewTextArea.setValue("<p>Dear Sir/Madam,&nbsp;</p>\n" +
                "<p>&nbsp;</p>\n" +
                "<p>We would like to inform you that you are late on your payment of <strong>1000</strong> denars for your invoice with invoice id <strong>84354646.</strong></p>\n" +
                "<p>&nbsp;</p>\n" +
                "<p>Best Regards,&nbsp;</p>\n" +
                "<p>Igor Stojanovski</p>");
        previewTextArea.setEnabled(false);

        previewLayout.addComponents(infoHLayout, previewTextArea, navigationLayout);
        previewLayout.setExpandRatio(previewTextArea, 1.0f);
        previewLayout.setSpacing(true);

    }

    private void setupDisplayLayout() {

        displayHLayout.setStyleName("send-email-display-layout");
        displayHLayout.setHeight("100%");
        displayHLayout.setWidth("100%");

        templatesList.setHeight("100%");
        templatesList.setWidth("150px");
        templatesList.setStyleName("send-email-templates-list");

        BeanItemContainer<EmailTemplate> emailTemplateBeanItemContainer = new BeanItemContainer<>(EmailTemplate.class);
        emailTemplateBeanItemContainer.addAll(emailTemplateService.getTemplates());

        templatesList.setContainerDataSource(emailTemplateBeanItemContainer);
        templatesList.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
        templatesList.setItemCaptionPropertyId("name");

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


}
