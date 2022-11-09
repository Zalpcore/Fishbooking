package com.alperovich.fishbook.management.controllers;

import com.alperovich.fishbook.management.DAO.impl.PartnerDaoImpl;
import com.alperovich.fishbook.management.models.Partner;
import com.alperovich.fishbook.management.services.PartnerService;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class PartnersController implements Initializable {

    @FXML
    private TextField tfPartnerId;
    @FXML
    private TextField tfCompanyName;
    @FXML
    private TextField tfAddress;
    @FXML
    private TextField tfPhone;
    @FXML
    private TextField tfLakeName;
    @FXML
    private TextField tfServiceName;
    @FXML
    private TextField tfServiceInfo;
    @FXML
    private TextField tfPrice;
    @FXML
    private TableView<Partner> tableViewPartners;
    @FXML
    private TableColumn<Partner, Integer> columnId;
    @FXML
    private TableColumn<Partner, String> columnCompany;
    @FXML
    private TableColumn<Partner, String> columnAddress;
    @FXML
    private TableColumn<Partner, String> columnPhone;
    @FXML
    private TableColumn<Partner, String> columnLake;
    @FXML
    private TableColumn<Partner, String> columnServiceName;
    @FXML
    private TableColumn<Partner, String> columnServiceInfo;
    @FXML
    public TableColumn<Partner, Double> columnPrice;
    @FXML
    private TextField textFieldSearching;

    private Stage stage;
    private Scene scene;
    private Parent root;

    PartnerService pser = new PartnerService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showPartners();
    }

    public void saveButtonOnAction() {
        pser.savePartner(tfCompanyName, tfAddress, tfPhone, tfLakeName, tfServiceName, tfServiceInfo, tfPrice);
        clearPartnerForm();
        showPartners();
    }

    public void updateButtonOnAction() {
        pser.updatePartner(tfCompanyName, tfAddress, tfPhone, tfLakeName,
                tfServiceName, tfServiceInfo, tfPrice, tfPartnerId);
        clearPartnerForm();
        showPartners();
    }

    public void deleteButtonOnAction() {
        pser.deletePartner(tfPartnerId);
        clearPartnerForm();
        showPartners();
    }

    public void exitButtonOnAction(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/alperovich/fishbook/management/admin-view.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void searchPartnersOnAction() {
        pser.searchPartner(textFieldSearching, tableViewPartners);
    }

    public void showPartners() {
        pser.showPartners(columnId, columnCompany, columnAddress, columnPhone,
                columnLake, columnServiceName, columnServiceInfo, columnPrice, tableViewPartners);
    }

    public void handleMouseClick() {
        pser.fillForm(tableViewPartners, tfPartnerId, tfCompanyName, tfAddress, tfPhone,
                tfLakeName, tfServiceName, tfServiceInfo, tfPrice);
    }

    public void clearPartnerForm() {
        pser.clearForm(tfPartnerId, tfCompanyName, tfAddress, tfPhone, tfLakeName,
                tfServiceName, tfServiceInfo, tfPrice);
    }
}
