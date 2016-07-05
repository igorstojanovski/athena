package org.programirame.athena.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import javax.annotation.PostConstruct;

@SpringView(name = "")
public class MainView extends VerticalLayout implements View {

    @PostConstruct
    public void init() {
        Button client = new Button("Go to clients page");
        client.addClickListener((Button.ClickListener) clickEvent -> UI.getCurrent().getNavigator().navigateTo("client"));

        addComponent(client);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
