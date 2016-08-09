package org.programirame.athena.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewProvider;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.*;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This UI is the application entry point. A UI may either represent a browser window
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
@SpringUI
public class MainUI extends UI {

    @Autowired
    ViewProvider viewProvider;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        getPage().setTitle("Navigation Example");

        final VerticalLayout root = new VerticalLayout();
        root.setSizeFull();
        root.setMargin(true);
        root.setSpacing(true);
        setContent(root);






        final Panel viewContainer = new Panel();
        viewContainer.setSizeFull();


        // Create a navigator to control the views
        Navigator navigator = new Navigator(this, viewContainer);

        // Create and register the views
        MenuBar barmenu = new MenuBar();
        Command command = new Command() {
            @Override
            public void menuSelected(MenuItem menuItem) {
                navigator.navigateTo("search");

            }
        };

        MenuItem search = barmenu.addItem("Search", FontAwesome.SEARCH, command);

        root.addComponent(barmenu);
        root.addComponent(viewContainer);
        root.setExpandRatio(viewContainer, 1.0f);
        navigator.addProvider(viewProvider);
    }
}
