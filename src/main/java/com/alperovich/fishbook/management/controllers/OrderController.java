package com.alperovich.fishbook.management.controllers;

import com.alperovich.fishbook.management.models.Order;
import com.alperovich.fishbook.management.services.OrderService;
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
    OrderService oserv = new OrderService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fileChooser.setInitialDirectory(new File("C:\\ProjectTemp\\"));
    }


    public void showOrders() throws IOException {
        oserv.showOrders(oserv.fileToOrder(fileChooser), columnOrderFirstName, columnOrderLastName, columnOrderPhone,
                columnOrderLake, columnOrderServiceName, columnOrderServiceInfo, columnOrderPrice, columnOrderCheckIn,
                columnOrderCheckOut, tvLoadOrder, tfOrderTotal);
    }

    public void onLoadButtonClick() throws IOException {
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

    public void onSaveButtonClick() throws FileNotFoundException {
        File file = fileChooser.showSaveDialog(new Stage());
        if (tvLoadOrder != null) {
            ObservableList<Order> getOrders = tvLoadOrder.getItems();
            oserv.saveOrderToFile(file, getOrders);
        }
    }

    public void handleMouseClick() {
        oserv.fillForm(tvLoadOrder, tfOrderFirstName, tfOrderLastName, tfOrderPhone, tfLakeName,
                tfOrderServiceName, tfOrderServiceInfo, tfOrderPrice, tfOrderCheckIn, tfOrderCheckOut);
    }

    public void clearForm() {
        oserv.clearForm(tfOrderFirstName, tfOrderLastName, tfOrderPhone, tfLakeName, tfOrderServiceName,
                tfOrderServiceInfo, tfOrderPrice, tfOrderCheckIn, tfOrderCheckOut);
    }
}






