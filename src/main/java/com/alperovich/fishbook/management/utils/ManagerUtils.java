package com.alperovich.fishbook.management.utils;

import com.alperovich.fishbook.management.DAO.impl.CustomerDaoImpl;
import com.alperovich.fishbook.management.DAO.impl.PartnerDaoImpl;
import com.alperovich.fishbook.management.models.Customer;
import com.alperovich.fishbook.management.models.Partner;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Date;

public class ManagerUtils {

    CustomerDaoImpl cdao = new CustomerDaoImpl();
    PartnerDaoImpl pdao = new PartnerDaoImpl();

    public void serviceByLake(ChoiceBox<String> choiceBoxLake,
                              TableColumn<Partner, String> columnChooseServiceName,
                              TableColumn<Partner, String> columnChooseServiceInfo,
                              TableColumn<Partner, Double> columnChoosePrice,
                              TableView<Partner> tvServices) {
        ObservableList<Partner> serviceByLake = pdao.getServiceByLake(choiceBoxLake.getValue());
        columnChooseServiceName.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        columnChooseServiceInfo.setCellValueFactory(new PropertyValueFactory<>("serviceInfo"));
        columnChoosePrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        tvServices.setItems(serviceByLake);
    }

    public Customer insertCustomer(TextField tfCustomerName,
                                          TextField tfCustomerLastName,
                                          TextField tfCustomerPhone,
                                          ChoiceBox<String> choiceBoxLake,
                                          TableView<Partner> tvServices,
                                          DatePicker datePickerCheckIn,
                                          DatePicker datePickerCheckOut)  {
        Customer customer = new Customer();
        customer.setFirstName(tfCustomerName.getText());
        customer.setLastName(tfCustomerLastName.getText());
        customer.setPhone(tfCustomerPhone.getText());
        customer.setLakeName(choiceBoxLake.getValue());
        customer.setServiceName(tvServices.getSelectionModel().getSelectedItem().getServiceName());
        customer.setServiceInfo(tvServices.getSelectionModel().getSelectedItem().getServiceInfo());
        customer.setPrice(tvServices.getSelectionModel().getSelectedItem().getPrice());
        customer.setCheckIn(Date.valueOf(datePickerCheckIn.getValue()));
        customer.setCheckOut(Date.valueOf(datePickerCheckOut.getValue()));
        cdao.createCustomer(customer);
        return customer;
    }
}
