package com.alperovich.fishbook.management.services;

import com.alperovich.fishbook.management.models.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.sql.Date;
import java.util.Collection;

public class OrderService {

    public Collection<Order> fileToOrder(FileChooser fileChooser) throws IOException {
        File file = fileChooser.showOpenDialog(new Stage());
        Collection<Order> orderList = Files.readAllLines(file.toPath())
                .stream()
                .map(line -> {
                    String[] details = line.split(",");
                    Order loadOrder = new Order();
                    loadOrder.setFirstName(details[0]);
                    loadOrder.setLastName(details[1]);
                    loadOrder.setPhone(details[2]);
                    loadOrder.setLakeName(details[3]);
                    loadOrder.setServiceName(details[4]);
                    loadOrder.setServiceInfo(details[5]);
                    loadOrder.setPrice(details[6]);
                    loadOrder.setCheckIn(details[7]);
                    loadOrder.setCheckOut(details[8]);
                    return loadOrder;
                }).toList();
        return orderList;
    }

    public void showOrders(Collection<Order> orderList,
                           TableColumn<Order, String> columnOrderFirstName,
                           TableColumn<Order, String> columnOrderLastName,
                           TableColumn<Order, String> columnOrderPhone,
                           TableColumn<Order, String> columnOrderLake,
                           TableColumn<Order, String> columnOrderServiceName,
                           TableColumn<Order, String> columnOrderServiceInfo,
                           TableColumn<Order, String> columnOrderPrice,
                           TableColumn<Order, String> columnOrderCheckIn,
                           TableColumn<Order, String> columnOrderCheckOut,
                           TableView<Order> tvLoadOrder,
                           TextField tfOrderTotal) {
        ObservableList<Order> orderDetails = FXCollections.observableArrayList(orderList);
        columnOrderFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        columnOrderLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        columnOrderPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        columnOrderLake.setCellValueFactory(new PropertyValueFactory<>("lakeName"));
        columnOrderServiceName.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        columnOrderServiceInfo.setCellValueFactory(new PropertyValueFactory<>("serviceInfo"));
        columnOrderPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        columnOrderCheckIn.setCellValueFactory(new PropertyValueFactory<>("checkIn"));
        columnOrderCheckOut.setCellValueFactory(new PropertyValueFactory<>("checkOut"));
        tvLoadOrder.setItems(orderDetails);
        int checkOut = Date.valueOf(orderList.stream().findAny().get().getCheckOut()).toLocalDate().getDayOfYear();
        int checkIn = Date.valueOf(orderList.stream().findAny().get().getCheckIn()).toLocalDate().getDayOfYear();
        int days = (checkOut - checkIn) + 1;
        double getSum = orderList.stream().mapToDouble(order -> Double.parseDouble(order.getPrice())).sum();
        tfOrderTotal.setText(Double.toString(days * getSum));
    }

    public void fillForm(TableView<Order> tvLoadOrder,
                         TextField tfOrderFirstName,
                         TextField tfOrderLastName,
                         TextField tfOrderPhone,
                         TextField tfLakeName,
                         TextField tfOrderServiceName,
                         TextField tfOrderServiceInfo,
                         TextField tfOrderPrice,
                         TextField tfOrderCheckIn,
                         TextField tfOrderCheckOut) {
        Order order = tvLoadOrder.getSelectionModel().getSelectedItem();
        if (order != null) {
            tfOrderFirstName.setText(order.getFirstName());
            tfOrderLastName.setText(order.getLastName());
            tfOrderPhone.setText(order.getPhone());
            tfLakeName.setText(order.getLakeName());
            tfOrderServiceName.setText(order.getServiceName());
            tfOrderServiceInfo.setText(order.getServiceInfo());
            tfOrderPrice.setText(order.getPrice());
            tfOrderCheckIn.setText(order.getCheckIn());
            tfOrderCheckOut.setText(order.getCheckOut());
        }
    }

    public void clearForm(TextField tfOrderFirstName,
                          TextField tfOrderLastName,
                          TextField tfOrderPhone,
                          TextField tfLakeName,
                          TextField tfOrderServiceName,
                          TextField tfOrderServiceInfo,
                          TextField tfOrderPrice,
                          TextField tfOrderCheckIn,
                          TextField tfOrderCheckOut) {
        tfOrderFirstName.setText("");
        tfOrderLastName.setText("");
        tfOrderPhone.setText("");
        tfLakeName.setText("");
        tfOrderServiceName.setText("");
        tfOrderServiceInfo.setText("");
        tfOrderPrice.setText("");
        tfOrderCheckIn.setText("");
        tfOrderCheckOut.setText("");
    }

    public void saveOrderToFile(File file, ObservableList<Order> saveList) throws FileNotFoundException {
        PrintWriter printWriter = new PrintWriter(file);
        for (Order order : saveList) {
            printWriter.write(order.getFirstName().trim().
                    concat("," + order.getLastName().trim()).
                    concat("," + order.getPhone().trim()).
                    concat("," + order.getLakeName().trim()).
                    concat("," + order.getServiceName().trim()).
                    concat("," + order.getServiceInfo().trim()).
                    concat("," + order.getPrice()).
                    concat("," + order.getCheckIn()).
                    concat("," + order.getCheckOut() + "\n"));
        }
        printWriter.close();
    }


}
