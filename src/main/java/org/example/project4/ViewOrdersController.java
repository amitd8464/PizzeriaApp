package org.example.project4;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import pizzeria.Order;
import pizzeria.Pizza;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

/**
 * public class for viewing order controller
 * @author Andrew Ho, Amit Deshpande
 */
public class ViewOrdersController {

    /**
     *Button to go back
     */
    @FXML
    private Button goBackBtn;

    /**
     * Button to export orders
     */
    @FXML
    private Button exportOrdersBtn;

    /**
     * button to remove order
     */
    @FXML
    private Button removeOrderBtn;

    /**
     * list for the order
     */
    @FXML
    private ListView<Order> orderListView;

    /**
     * pane for the order details
     */
    @FXML
    private Pane orderDetails;

    /**
     * order number as text
     */
    @FXML
    private Text orderNumText;

    /**
     * text for the order total
     */
    @FXML
    private Text orderTotalText;

    /**
     * scroll pane for the pizza details
     */
    @FXML
    private ScrollPane pizzaDetails;

    /**
     * array list of the orders
     */
    private ArrayList<Order> orders;

    /**
     * observable list of the orders
     */
    private ObservableList<Order> observableOrders = FXCollections.observableArrayList();

    /**
     * sends alert for button type
     * @param type tupe of alert
     * @param title name of the alert
     * @param message message that comes with the alert
     * @return Optional button type
     */
    private Optional<ButtonType> sendAlert(Alert.AlertType type, String title, String message){
        Alert alert = new Alert(type);
        if (type.equals(Alert.AlertType.WARNING)){
            alert.getButtonTypes().add(ButtonType.YES);
            alert.getButtonTypes().remove(ButtonType.OK);
        }
        alert.setTitle(title);
        alert.setContentText(message);
        return alert.showAndWait();
    }

    /**
     * sets an array list of orders
     * @param orders array list of orders to be set
     */
    public void setOrders(ArrayList<Order> orders){
        this.orders = new ArrayList<>(orders);
        observableOrders.setAll(this.orders);
        this.orderListView.setItems(observableOrders);
    }

    /**
     * sets the order
     * @param order Order to be ordered
     */
    private void setOrderView(Order order){
        orderNumText.setVisible(true);
        orderTotalText.setVisible(true);
        pizzaDetails.setVisible(true);
        removeOrderBtn.setVisible(true);

        orderNumText.setText("Order #" + order.orderNumToString());
        orderTotalText.setText("Order total: $" + String.format("%.2f", order.total()));
        pizzaDetails.setContent(new Text(order.pizzasToString()));
    }

    @FXML
    void exportOrders(){
        StringBuilder text = new StringBuilder();
        for (Order order : this.orders){
            text.append("Order #").append(order.orderNumToString()).append("\n");
            text.append("Order total: $").append(order.total()).append("\n");
            text.append(order.pizzasToString()).append("---------\n");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("orders.txt"))) {
            writer.write(text.toString());
        } catch (IOException e) {
            sendAlert(Alert.AlertType.ERROR, "Error", "Could not write to file");
        }

        sendAlert(Alert.AlertType.INFORMATION, "Orders exported!", "Orders were successfully exported");
    }


    /**
     * removes the order
     */
    @FXML
    void removeOrder(){
        Optional <javafx.scene.control.ButtonType> response = sendAlert(Alert.AlertType.WARNING, "Warning", "Are you sure you want to remove this order?");
        if (response.isPresent() && response.get().equals(ButtonType.YES)){
            Order selectedOrder = orderListView.getSelectionModel().selectedItemProperty().get();
            this.orders.remove(selectedOrder);
            observableOrders.remove(selectedOrder);
        }
    }

    /**
     * navigates to the add order view
     * @param event event that should occur
     * @throws IOException resource get error
     */
    @FXML
    void navigateAddOrderView(ActionEvent event) throws IOException {
        Stage currentStage = (Stage)((Node) event.getSource()).getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/project4/add-order-view.fxml"));

        Scene scene = new Scene(loader.load()); // 759,440
        Stage stage = new Stage();

        ((AddOrderViewController)loader.getController()).setOrders(this.orders);

        stage.setTitle("RUPizzeria");
        stage.setResizable(false);
        stage.setScene(scene);

        currentStage.close();
        stage.show();
    }

    /**
     * clears the order view
     */
    private void clearOrderView(){
        orderNumText.setVisible(false);
        orderTotalText.setVisible(false);
        pizzaDetails.setVisible(false);
        removeOrderBtn.setVisible(false);
    }

    /**
     * sets the listeners
     */
    private void setListeners(){
        orderListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                setOrderView(newValue);
            }
            else{
                clearOrderView();
            }
        });
    }

    /**
     * sets the values
     */
    private void setValues(){
        clearOrderView();
        pizzaDetails.setPadding(new Insets(10));

        orderListView.setCellFactory(listView -> new ListCell<Order>() {
            @Override
            protected void updateItem(Order order, boolean empty) {
                super.updateItem(order, empty);
                if (empty || order == null) {
                    setText(null);
                } else {
                    // Display the customer name instead of the default `toString()`
                    setText("Order #" + order.orderNumToString());
                }
            }
        });
    }

    /**
     * initializes the values and listeners
     */
    public void initialize(){
        setValues();
        setListeners();
    }
}
