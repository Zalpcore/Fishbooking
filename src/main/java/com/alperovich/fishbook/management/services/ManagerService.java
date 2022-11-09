package com.alperovich.fishbook.management.services;

import com.alperovich.fishbook.management.DAO.impl.CustomerDaoImpl;
import com.alperovich.fishbook.management.DAO.impl.PartnerDaoImpl;
import com.alperovich.fishbook.management.models.Customer;
import com.alperovich.fishbook.management.models.Partner;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Date;

public class ManagerService {

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
                                   DatePicker datePickerCheckOut) {
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

    public Customer updateCustomer(TextField tfCustomerName,
                                   TextField tfCustomerLastName,
                                   TextField tfCustomerPhone,
                                   ChoiceBox<String> choiceBoxLake,
                                   TableView<Customer> tvOrder,
                                   DatePicker datePickerCheckIn,
                                   DatePicker datePickerCheckOut) {
        Customer customer = new Customer();
        customer.setFirstName(tfCustomerName.getText());
        customer.setLastName(tfCustomerLastName.getText());
        customer.setPhone(tfCustomerPhone.getText());
        customer.setLakeName(choiceBoxLake.getValue());
        customer.setServiceName(tvOrder.getSelectionModel().getSelectedItem().getServiceName());
        customer.setServiceInfo(tvOrder.getSelectionModel().getSelectedItem().getServiceInfo());
        customer.setPrice(tvOrder.getSelectionModel().getSelectedItem().getPrice());
        customer.setCheckIn(Date.valueOf(datePickerCheckIn.getValue()));
        customer.setCheckOut(Date.valueOf(datePickerCheckOut.getValue()));
        customer.setId(tvOrder.getSelectionModel().getSelectedItem().getId());
        cdao.updateCustomer(customer);
        return customer;
    }

    public void customerByName(TextField tfCustomerName,
                                   TextField tfCustomerLastName,
                                   TableColumn<Customer, Integer> columnOrderId,
                                   TableColumn<Customer, String> columnOrderFirstName,
                                   TableColumn<Customer, String> columnOrderLastName,
                                   TableColumn<Customer, String> columnOrderPhone,
                                   TableColumn<Customer, String> columnOrderLake,
                                   TableColumn<Customer, String> columnOrderServiceName,
                                   TableColumn<Customer, String> columnOrderServiceInfo,
                                   TableColumn<Customer, Double> columnOrderPrice,
                                   TableColumn<Customer, Date> columnOrderCheckIn,
                                   TableColumn<Customer, Date> columnOrderCheckOut,
                                   TableView<Customer> tvOrder,
                                   DatePicker datePickerCheckIn,
                                   DatePicker datePickerCheckOut,
                                   TextField tfTotalPrice) {
        ObservableList<Customer> customerList = cdao.getCustomersByName(tfCustomerName.getText(), tfCustomerLastName.getText());
        columnOrderId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnOrderFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        columnOrderLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        columnOrderPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        columnOrderLake.setCellValueFactory(new PropertyValueFactory<>("lakeName"));
        columnOrderServiceName.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        columnOrderServiceInfo.setCellValueFactory(new PropertyValueFactory<>("serviceInfo"));
        columnOrderPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        columnOrderCheckIn.setCellValueFactory(new PropertyValueFactory<>("checkIn"));
        columnOrderCheckOut.setCellValueFactory(new PropertyValueFactory<>("checkOut"));
        tvOrder.setItems(customerList);
        int checkOut = (datePickerCheckOut.getValue()).getDayOfYear();
        int checkIn = datePickerCheckIn.getValue().getDayOfYear();
        int days = (checkOut - checkIn) + 1;
        Double count = 0.0;
        for (int i = 0; i < customerList.size(); i++) {
            count += customerList.get(i).getPrice();
        }
        tfTotalPrice.setText(Double.toString(count * days));
    }

    public void showCustomers(TableColumn<Customer, Integer> columnOrderId,
                              TableColumn<Customer, String> columnOrderFirstName,
                              TableColumn<Customer, String> columnOrderLastName,
                              TableColumn<Customer, String> columnOrderPhone,
                              TableColumn<Customer, String> columnOrderLake,
                              TableColumn<Customer, String> columnOrderServiceName,
                              TableColumn<Customer, String> columnOrderServiceInfo,
                              TableColumn<Customer, Double> columnOrderPrice,
                              TableColumn<Customer, Date> columnOrderCheckIn,
                              TableColumn<Customer, Date> columnOrderCheckOut,
                              TableView<Customer> tvOrder) {
        ObservableList<Customer> customerList = cdao.getAllCustomers();
        columnOrderId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnOrderFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        columnOrderLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        columnOrderPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        columnOrderLake.setCellValueFactory(new PropertyValueFactory<>("lakeName"));
        columnOrderServiceName.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        columnOrderServiceInfo.setCellValueFactory(new PropertyValueFactory<>("serviceInfo"));
        columnOrderPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        columnOrderCheckIn.setCellValueFactory(new PropertyValueFactory<>("checkIn"));
        columnOrderCheckOut.setCellValueFactory(new PropertyValueFactory<>("checkOut"));
        tvOrder.setItems(customerList);
    }

    public void clearCustomer(TextField tfCustomerId,
                              TextField tfCustomerName,
                              TextField tfCustomerLastName,
                              TextField tfCustomerPhone,
                              ChoiceBox<String> choiceBoxLake,
                              DatePicker datePickerCheckIn,
                              DatePicker datePickerCheckOut) {
        tfCustomerId.setText("");
        tfCustomerName.setText("");
        tfCustomerLastName.setText("");
        tfCustomerPhone.setText("");
        choiceBoxLake.setValue("");
        datePickerCheckIn.setValue(null);
        datePickerCheckOut.setValue(null);
        tfCustomerId.requestFocus();
    }

    public void searchCustomerService(TextField searching, TableView<Customer> tv){
        FilteredList<Customer> filteredList = new FilteredList<>(cdao.getAllCustomers(), b -> true);
        searching.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(Customer -> {
                if (newValue.isEmpty() || newValue.isBlank()) {
                    return true;
                }
                String tfKeywordSearchOrder = newValue.toLowerCase();

                if (Customer.getFirstName().toLowerCase().contains(tfKeywordSearchOrder)) {
                    return true;
                } else if (Customer.getLastName().toLowerCase().contains(tfKeywordSearchOrder)) {
                    return true;
                } else if (Customer.getPhone().toLowerCase().contains(tfKeywordSearchOrder)) {
                    return true;
                } else if (Customer.getLakeName().toLowerCase().contains(tfKeywordSearchOrder)) {
                    return true;
                } else if (Customer.getServiceName().toLowerCase().contains(tfKeywordSearchOrder)) {
                    return true;
                } else if (Customer.getServiceInfo().toLowerCase().contains(tfKeywordSearchOrder)) {
                    return true;
                } else {
                    return false;
                }
            });
        });
        SortedList<Customer> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tv.comparatorProperty());
        tv.setItems(sortedList);
    }

    public void handleMouseClick(TableView<Customer> tvOrder,
                                 TextField tfCustomerId,
                                 TextField tfCustomerName,
                                 TextField tfCustomerLastName,
                                 ChoiceBox<String>choiceBoxLake,
                                 TextField tfCustomerPhone,
                                 DatePicker datePickerCheckIn,
                                 DatePicker datePickerCheckOut)  {
        Customer customer = tvOrder.getSelectionModel().getSelectedItem();
        if (customer != null) {
            tfCustomerId.setText(Integer.toString(customer.getId()));
            tfCustomerName.setText(customer.getFirstName());
            tfCustomerLastName.setText(customer.getLastName());
            choiceBoxLake.setValue(customer.getLakeName());
            tfCustomerPhone.setText(customer.getPhone());
            datePickerCheckIn.setValue(customer.getCheckIn().toLocalDate());
            datePickerCheckOut.setValue(customer.getCheckOut().toLocalDate());
        }
    }

}
