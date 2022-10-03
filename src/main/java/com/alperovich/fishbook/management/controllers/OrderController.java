package com.alperovich.fishbook.management.controllers;

import com.alperovich.fishbook.management.models.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Files;
import java.sql.Date;
import java.util.Collection;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class OrderController implements Initializable {

    @FXML
    public TextField tfOrderFirstName;
    @FXML
    public TextField tfOrderLastName;
    @FXML
    public TextField tfOrderPhone;
    @FXML
    public Button orderSaveButton;
    @FXML
    public Button OrderBackButton;
    @FXML
    public TextField tfLakeName;
    @FXML
    public TextField tfOrderServiceName;
    @FXML
    public TextField tfOrderServiceInfo;
    @FXML
    public TextField tfOrderPrice;
    @FXML
    public TextField tfOrderCheckIn;
    @FXML
    public TextField tfOrderCheckOut;
    @FXML
    public TextField tfOrderTotal;
    @FXML
    public TableView<Order> tvLoadOrder;
    @FXML
    public TableColumn<Order, String> columnOrderId;
    @FXML
    public TableColumn<Order, String> columnOrderFirstName;
    @FXML
    public TableColumn<Order, String> columnOrderLastName;
    @FXML
    public TableColumn<Order, String> columnOrderPhone;
    @FXML
    public TableColumn<Order, String> columnOrderLake;
    @FXML
    public TableColumn<Order, String> columnOrderServiceName;
    @FXML
    public TableColumn<Order, String> columnOrderServiceInfo;
    @FXML
    public TableColumn<Order, String> columnOrderPrice;
    @FXML
    public TableColumn<Order, String> columnOrderCheckIn;
    @FXML
    public TableColumn<Order, String> columnOrderCheckOut;
    @FXML
    public Button orderLoadButton;
    @FXML
    public TableColumn<Order, String> columnOrderTotal;

    private Stage stage;
    private Scene scene;
    private Parent root;

    FileChooser fileChooser = new FileChooser();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fileChooser.setInitialDirectory(new File("C:\\ProjectTemp\\"));
    }


    public void showOrders() throws IOException {
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
                })
                .collect(Collectors.toList());

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
        Double getSum = orderList.stream().mapToDouble(order -> Double.parseDouble(order.getPrice())).sum();
        tfOrderTotal.setText(Double.toString(days * getSum));
    }


    public void onLoadButtonClick(MouseEvent mouseEvent) throws IOException {
        showOrders();
    }

    public void OnBackButtonClick(ActionEvent event) throws IOException {
        clearForm();
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/alperovich/fishbook/management/manager-view.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void onSaveButtonClick(MouseEvent mouseEvent) throws FileNotFoundException {
        File file = fileChooser.showSaveDialog(new Stage());
        if (tvLoadOrder != null) {
            ObservableList<Order> getOrders = tvLoadOrder.getItems();
            saveOrderToFile(file, getOrders);
        }
    }

    public void handleMouseClick() {
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

    public void clearForm() {
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






