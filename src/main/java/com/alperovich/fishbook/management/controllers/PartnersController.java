package com.alperovich.fishbook.management.controllers;

import com.alperovich.fishbook.management.DAO.impl.PartnerDaoImpl;
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

    PartnerDaoImpl pdao = new PartnerDaoImpl();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showPartners();
    }

    public void saveButtonOnAction() {
        Partner partner = new Partner();
        partner.setCompanyName(tfCompanyName.getText());
        partner.setAddress(tfAddress.getText());
        partner.setPhone(tfPhone.getText());
        partner.setLakeName(tfLakeName.getText());
        partner.setServiceName(tfServiceName.getText());
        partner.setServiceInfo(tfServiceInfo.getText());
        partner.setPrice(Double.parseDouble(tfPrice.getText()));
        pdao.createPartner(partner);
        clearPartnerForm();
        showPartners();
    }

    public void updateButtonOnAction(ActionEvent event) {
        Partner partner = new Partner();
        partner.setCompanyName(tfCompanyName.getText());
        partner.setAddress(tfAddress.getText());
        partner.setPhone(tfPhone.getText());
        partner.setLakeName(tfLakeName.getText());
        partner.setServiceName(tfServiceName.getText());
        partner.setServiceInfo(tfServiceInfo.getText());
        partner.setPrice(Double.parseDouble(tfPrice.getText()));
        partner.setId(Integer.parseInt(tfPartnerId.getText()));
        pdao.updatePartner(partner);
        clearPartnerForm();
        showPartners();
    }

    public void deleteButtonOnAction(ActionEvent event) {
        int id = Integer.parseInt(tfPartnerId.getText());
        pdao.deletePartnerById(id);
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

    public void searchPartnersOnAction(ActionEvent event) {
        FilteredList<Partner> filteredList = new FilteredList<>(pdao.getAllPartners(), b -> true);
        textFieldSearching.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(Partner -> {
                if (newValue.isEmpty() || newValue.isBlank()) {
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();

                if (Partner.getCompanyName().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if (Partner.getAddress().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if (Partner.getPhone().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if (Partner.getLakeName().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if (Partner.getServiceName().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if (Partner.getServiceInfo().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else {
                    return false;
                }
            });
        });
        SortedList<Partner> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tableViewPartners.comparatorProperty());
        tableViewPartners.setItems(sortedList);
    }

    public void showPartners() {
        ObservableList<Partner> partnerList = pdao.getAllPartners();
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnCompany.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        columnAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        columnPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        columnLake.setCellValueFactory(new PropertyValueFactory<>("lakeName"));
        columnServiceName.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        columnServiceInfo.setCellValueFactory(new PropertyValueFactory<>("serviceInfo"));
        columnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        tableViewPartners.setItems(partnerList);
    }

    public void handleMouseClick() {
        Partner partner = tableViewPartners.getSelectionModel().getSelectedItem();
        if (partner != null) {
            tfPartnerId.setText(Integer.toString(partner.getId()));
            tfCompanyName.setText(partner.getCompanyName());
            tfAddress.setText(partner.getAddress());
            tfPhone.setText(partner.getPhone());
            tfLakeName.setText(partner.getLakeName());
            tfServiceName.setText(partner.getServiceName());
            tfServiceInfo.setText(partner.getServiceInfo());
            tfPrice.setText(Double.toString(partner.getPrice()));
        }
    }

    public void clearPartnerForm() {
        tfPartnerId.setText("");
        tfCompanyName.setText("");
        tfAddress.setText("");
        tfPhone.setText("");
        tfLakeName.setText("");
        tfServiceName.setText("");
        tfServiceInfo.setText("");
        tfPrice.setText("");
        tfPartnerId.requestFocus();
    }
}
