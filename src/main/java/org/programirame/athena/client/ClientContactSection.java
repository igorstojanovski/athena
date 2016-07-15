package org.programirame.athena.client;


import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.programirame.athena.models.address.Address;

import java.util.List;

public class ClientContactSection extends VerticalLayout {


    public ClientContactSection() {
        setWidth("400px");
        setMargin(true);
        setMargin(new MarginInfo(true, true, true ,true));
    }


    public void refreshContactInfo(List<Address> addresses) {
        for(Address address:addresses) {
            HorizontalLayout addressLayout = getSingleAddressLine(address);

            addressLayout.setSizeFull();
            addComponent(addressLayout);
        }
    }

    private HorizontalLayout getSingleAddressLine(Address address) {
        HorizontalLayout layout = new HorizontalLayout();

        Label iconLabel = new Label();
        iconLabel.setIcon(FontAwesome.MAP_PIN);
        iconLabel.setWidth("20px");

        Label addressLabel = new Label(address.getAddress());

        Label typeLabel = new Label(address.getAddressType().getName());

        layout.addComponents(iconLabel, addressLabel, typeLabel);
        layout.setExpandRatio(addressLabel, 3.0f);
        layout.setExpandRatio(typeLabel, 1.0f);

        return layout;
    }
}
