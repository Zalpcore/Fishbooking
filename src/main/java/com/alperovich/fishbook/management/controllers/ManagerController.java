package com.alperovich.fishbook.management.controllers;

import com.alperovich.fishbook.management.DAO.impl.CustomerDaoImpl;
import com.alperovich.fishbook.management.DAO.impl.PartnerDaoImpl;
import com.alperovich.fishbook.management.models.Customer;
import com.alperovich.fishbook.management.models.Partner;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class ManagerController implements Initializable {

    @FXML
    public TextField tfCustomerId;
    @FXML
    public TextField tfKeywordSearchOrder;
    @FXML
    public Button loadFromFileButton;
    @FXML
    public Button saveToFileButton;
    @FXML
    public TextField tfTotalPrice;
    @FXML
    private TextField tfCustomerName;
    @FXML
    private TextField tfCustomerLastName;
    @FXML
    private TextField tfCustomerPhone;
    @FXML
    private ChoiceBox<String> choiceBoxLake;
    @FXML
    public TableView<Partner> tvServices;
    @FXML
    private TableColumn<Partner, String> columnChooseServiceName;
    @FXML
    private TableColumn<Partner, String> columnChooseServiceInfo;
    @FXML
    private TableColumn<Partner, Double> columnChoosePrice;
    @FXML
    private DatePicker datePickerCheckIn;
    @FXML
    private DatePicker datePickerCheckOut;
    @FXML
    private TableView<Customer> tvOrder;
    @FXML
    private TableColumn<Customer, Integer> columnOrderId;
    @FXML
    private TableColumn<Customer, String> columnOrderFirstName;
    @FXML
    private TableColumn<Customer, String> columnOrderLastName;
    @FXML
    private TableColumn<Customer, String> columnOrderPhone;
    @FXML
    private TableColumn<Customer, String> columnOrderLake;
    @FXML
    private TableColumn<Customer, String> columnOrderServiceName;
    @FXML
    private TableColumn<Customer, String> columnOrderServiceInfo;
    @FXML
    private TableColumn<Customer, Double> columnOrderPrice;
    @FXML
    private TableColumn<Customer, Date> columnOrderCheckIn;
    @FXML
    private TableColumn<Customer, Date> columnOrderCheckOut;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button prepareCheckButton;
    @FXML
    private Button clearButton;
    @FXML
    private Button exitButton;
    @FXML
    private Button addOrderButton;

    private Stage stage;
    private Scene scene;
    private Parent root;

    CustomerDaoImpl cdao = new CustomerDaoImpl();
    PartnerDaoImpl pdao = new PartnerDaoImpl();
    FileChooser fileChooser = new FileChooser();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fileChooser.setInitialDirectory(new File("C:\\ProjectTemp\\"));
        choiceBoxLake.getItems().addAll(pdao.getAllLakes());
        choiceBoxLake.setOnAction(this::showServiceByLake);
        showCustomers();
    }

    public void showServiceByLake(ActionEvent event) {
        String lakeName = choiceBoxLake.getValue();
        ObservableList<Partner> serviceByLake = pdao.getServiceByLake(lakeName);
        columnChooseServiceName.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        columnChooseServiceInfo.setCellValueFactory(new PropertyValueFactory<>("serviceInfo"));
        columnChoosePrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        tvServices.setItems(serviceByLake);
    }

    List<Customer> customerList = new ArrayList<>();

    public void saveButtonOnAction() {
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
        customerList.add(customer);
        showCustomersByName();
    }

    public void editButtonOnAction() {
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
        clearCustomerForm();
        showCustomersByName();
    }

    public void clearButtonOnAction() {
        clearCustomerForm();
        showCustomers();
    }

    public void deleteButtonOnAction() {
        int id = tvOrder.getSelectionModel().getSelectedItem().getId();
        cdao.deleteCustomerById(id);
        showCustomersByName();
    }

    public void exitButtonOnAction(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/alperovich/fishbook/management/signup-view.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void showCustomersByName() {
        String firstName = tfCustomerName.getText();
        String lastName = tfCustomerLastName.getText();
        ObservableList<Customer> customerList = cdao.getCustomersByName(firstName, lastName);
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

    public void showCustomers() {
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

    public void handleMouseClick() {
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

    public void clearCustomerForm() {
        tfCustomerId.setText("");
        tfCustomerName.setText("");
        tfCustomerLastName.setText("");
        tfCustomerPhone.setText("");
        choiceBoxLake.setValue("");
        datePickerCheckIn.setValue(null);
        datePickerCheckOut.setValue(null);
        tfCustomerId.requestFocus();
    }

    public void searchCustomerServiceOnAction(ActionEvent event) {
        FilteredList<Customer> filteredList = new FilteredList<>(cdao.getAllCustomers(), b -> true);
        tfKeywordSearchOrder.textProperty().addListener((observable, oldValue, newValue) -> {
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
        sortedList.comparatorProperty().bind(tvOrder.comparatorProperty());
        tvOrder.setItems(sortedList);
    }

    public void saveToFileButton(MouseEvent mouseEvent) throws FileNotFoundException {
        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            saveOrderToFile(file, customerList);
        }
    }

    public void saveOrderToFile(File file, List<Customer> saveList) throws FileNotFoundException {
        PrintWriter printWriter = new PrintWriter(file);
        for (Customer customer : saveList) {
            printWriter.write(customer.getFirstName().trim().
                    concat("," + customer.getLastName().trim()).
                    concat("," + customer.getPhone().trim()).
                    concat("," + customer.getLakeName().trim()).
                    concat("," + customer.getServiceName().trim()).
                    concat("," + customer.getServiceInfo().trim()).
                    concat("," + customer.getPrice()).
                    concat("," + customer.getCheckIn()).
                    concat("," + customer.getCheckOut() + "\n"));
        }
        printWriter.close();
    }

    public void loadFromFileButton(MouseEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/alperovich/fishbook/management/order-view.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
