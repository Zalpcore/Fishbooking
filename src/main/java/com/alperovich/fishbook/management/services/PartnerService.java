package com.alperovich.fishbook.management.services;

import com.alperovich.fishbook.management.DAO.impl.PartnerDaoImpl;
import com.alperovich.fishbook.management.models.Partner;
import com.alperovich.fishbook.management.models.User;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class PartnerService {

    PartnerDaoImpl pdao = new PartnerDaoImpl();

    public Partner savePartner(TextField tfCompanyName,
                               TextField tfAddress,
                               TextField tfPhone,
                               TextField tfLakeName,
                               TextField tfServiceName,
                               TextField tfServiceInfo,
                               TextField tfPrice) {
        Partner partner = new Partner();
        partner.setCompanyName(tfCompanyName.getText());
        partner.setAddress(tfAddress.getText());
        partner.setPhone(tfPhone.getText());
        partner.setLakeName(tfLakeName.getText());
        partner.setServiceName(tfServiceName.getText());
        partner.setServiceInfo(tfServiceInfo.getText());
        partner.setPrice(Double.parseDouble(tfPrice.getText()));
        pdao.createPartner(partner);
        return partner;
    }

    public Partner updatePartner(TextField tfCompanyName,
                                 TextField tfAddress,
                                 TextField tfPhone,
                                 TextField tfLakeName,
                                 TextField tfServiceName,
                                 TextField tfServiceInfo,
                                 TextField tfPrice,
                                 TextField tfPartnerId) {
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
        return partner;
    }

    public void deletePartner(TextField tfPartnerId) {
        int id = Integer.parseInt(tfPartnerId.getText());
        pdao.deletePartnerById(id);
    }

    public void searchPartner(TextField textFieldSearching, TableView<Partner> tv) {
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
        sortedList.comparatorProperty().bind(tv.comparatorProperty());
        tv.setItems(sortedList);
    }

    public void showPartners(TableColumn<Partner, Integer> columnId,
                             TableColumn<Partner, String> columnCompany,
                             TableColumn<Partner, String> columnAddress,
                             TableColumn<Partner, String> columnPhone,
                             TableColumn<Partner, String> columnLake,
                             TableColumn<Partner, String> columnServiceName,
                             TableColumn<Partner, String> columnServiceInfo,
                             TableColumn<Partner, Double> columnPrice,
                             TableView<Partner> tableViewPartners) {
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

    public void fillForm(TableView<Partner> tableViewPartners,
                         TextField tfPartnerId,
                         TextField tfCompanyName,
                         TextField tfAddress,
                         TextField tfPhone,
                         TextField tfLakeName,
                         TextField tfServiceName,
                         TextField tfServiceInfo,
                         TextField tfPrice) {
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

    public void clearForm(TextField tfPartnerId,
                          TextField tfCompanyName,
                          TextField tfAddress,
                          TextField tfPhone,
                          TextField tfLakeName,
                          TextField tfServiceName,
                          TextField tfServiceInfo,
                          TextField tfPrice){
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
