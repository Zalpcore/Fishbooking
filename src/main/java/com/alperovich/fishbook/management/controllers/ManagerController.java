package com.alperovich.fishbook.management.controllers;

import com.alperovich.fishbook.management.DAO.impl.CustomerDaoImpl;
import com.alperovich.fishbook.management.DAO.impl.PartnerDaoImpl;
import com.alperovich.fishbook.management.models.Customer;
import com.alperovich.fishbook.management.models.Partner;
import com.alperovich.fishbook.management.services.ManagerService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    ManagerService mutils = new ManagerService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fileChooser.setInitialDirectory(new File("C:\\ProjectTemp\\"));
        choiceBoxLake.getItems().addAll(pdao.getAllLakes());
        choiceBoxLake.setOnAction(this::showServiceByLake);
        showCustomers();
    }

    public void showServiceByLake(ActionEvent event) {
        mutils.serviceByLake(choiceBoxLake, columnChooseServiceName, columnChooseServiceInfo, columnChoosePrice, tvServices);
    }

    List<Customer> customerList = new ArrayList<>();

    public void saveButtonOnAction() {
        Customer customer = mutils.insertCustomer(tfCustomerName, tfCustomerLastName, tfCustomerPhone,
                choiceBoxLake, tvServices, datePickerCheckIn, datePickerCheckOut);
        customerList.add(customer);
        showCustomersByName();
    }

    public void editButtonOnAction() {
        mutils.updateCustomer(tfCustomerName, tfCustomerLastName, tfCustomerPhone,
                choiceBoxLake, tvOrder, datePickerCheckIn, datePickerCheckOut);
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
        mutils.customerByName(tfCustomerName, tfCustomerLastName, columnOrderId,
                columnOrderFirstName, columnOrderLastName, columnOrderPhone,
                columnOrderLake, columnOrderServiceName, columnOrderServiceInfo,
                columnOrderPrice, columnOrderCheckIn, columnOrderCheckOut,
                tvOrder, datePickerCheckIn, datePickerCheckOut, tfTotalPrice);
    }

    public void showCustomers() {
        mutils.showCustomers(columnOrderId, columnOrderFirstName,
                columnOrderLastName,columnOrderPhone, columnOrderLake,
                columnOrderServiceName, columnOrderServiceInfo, columnOrderPrice,
                columnOrderCheckIn, columnOrderCheckOut, tvOrder);
    }

    public void handleMouseClick() {
        mutils.handleMouseClick(tvOrder, tfCustomerId, tfCustomerName,
                 tfCustomerLastName, choiceBoxLake, tfCustomerPhone,
                 datePickerCheckIn, datePickerCheckOut);
    }

    public void clearCustomerForm() {
        mutils.clearCustomer(tfCustomerId, tfCustomerName, tfCustomerLastName, tfCustomerPhone,
                 choiceBoxLake, datePickerCheckIn, datePickerCheckOut);
    }

    public void searchCustomerServiceOnAction() {
        mutils.searchCustomerService(tfKeywordSearchOrder, tvOrder);
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
