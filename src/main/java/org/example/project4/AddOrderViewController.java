package org.example.project4;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import pizzeria.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * Add order view controller class
 * @author Andrew Ho, Amit Deshpande
 */
public class AddOrderViewController {
    /**
     * pane for new pizza
     */
    @FXML
    private Pane addNewPizzaPane;
    /**
     * pane fo sub total pane
     */
    @FXML
    private Pane subtotalPane;
    /**
     * pane for choosing pizza style
     */
    @FXML
    private Pane chooseStylePane;
    /**
     * pane for choosing specialty pane
     */
    @FXML
    private Pane selectSpecialtyPane;
    /**
     * pane for selecting size
     */
    @FXML
    private Pane selectSizePane;
    /**
     * pane for selecting pizza type
     */
    @FXML
    private Pane selectPizzaTypePane;
    /**
     * pane for side
     */
    @FXML
    private Pane sidePane;
    /**
     * pane for pizza topping text
     */
    @FXML
    private TextArea showPizzaToppingsTextArea;
    /**
     * pane for pizza image
     */
    @FXML
    private ImageView pizzaImageView;

    /**
     * ComboBox of strings to styleCmb
     */
    @FXML
    private ComboBox<String> chooseStyleCmb;

    /**
     * button to choose specialty
     */
    @FXML
    private Button chooseSpecialtyBtn;
    /**
     * button to choose build your own
     */
    @FXML
    private Button chooseBYOBtn;

    /**
     * button to do deluxe
     */
    @FXML
    private RadioButton deluxeRadioBtn;
    /**
     * button to choose Meatzza
     */
    @FXML
    private RadioButton meatzzaRadioBtn;
    /**
     * button to choose bbq chicken
     */
    @FXML
    private RadioButton bbqChickenRadioBtn;

    /**
     * button to choose small
     */
    @FXML
    private RadioButton smallRadioBtn;
    /**
     * button to choose medium
     */
    @FXML
    private RadioButton medRadioBtn;
    /**
     * button to choose large
     */
    @FXML
    private RadioButton largeRadioBtn;

    /**
     * small price text
     */
    @FXML
    private Text smallPriceText;

    /**
     * medium price text
     */
    @FXML
    private Text medPriceText;

    /**
     * large price text
     */
    @FXML
    private Text largePriceText;

    /**
     * total cost text
     */
    @FXML
    private Text orderTotalText;

    /**
     * sub total text
     */
    @FXML
    private Text subTotalText;

    /**
     * tax text
     */
    @FXML
    private Text taxText;

    /**
     * add pizza button
     */
    @FXML
    private Button addPizzaBtn;

    /**
     * view all orders button
     */
    @FXML
    private Button viewAllOrdersBtn;

    /**
     * cancel order button
     */
    @FXML
    private Button cancelOrderBtn;

    /**
     * place order button
     */
    @FXML
    private Button placeOrderBtn;

    /**
     * scroll pane for order details
     */
    @FXML
    private ScrollPane orderDetailsScrollPane;

    /**
     * specially toggle
     */
    @FXML
    ToggleGroup specialtyToggleGroup = new ToggleGroup();

    /**
     * size toggle group
     */
    @FXML
    ToggleGroup sizeToggleGroup = new ToggleGroup();

    // Backend variables:
    /**
     * chicago pizza creation
     */
    private PizzaFactory ChicagoPizzaFactory = new ChicagoPizza();

    /**
     * NY pizza factory
     */
    private PizzaFactory NYPizzaFactory = new NYPizza();

    /**
     * Hash map for prices
     */
    private Map<String, double[]> prices = new HashMap<>();

    /**
     * Hash map for specialty toppings
     */
    private Map<String, String> specialtyToppings = new HashMap<>();

    /**
     * current pizza style holder
     */
    private String currentPizzaStyle = "";

    /**
     * current order being displayed
     */
    private Order currentOrder;

    /**
     * list of orders
     */
    private ArrayList<Order> orders = new ArrayList<>();

    // BYO variables:
    /**
     * sie of the build your own
     */
    private Size BYOSize;

    /**
     * list of toppings
     */
    private ArrayList<Topping> BYOToppings;

    /**
     * state manager instance
     */
    StateManager sm = StateManager.getInstance();

    // Constants:
    /**
     * static tax rate
     */
    private final double TAX_RATE = 0.06625;
    /**
     * price format
     */
    private final String PRICE_PLACEHOLDER = "$0.00";

    /**
     * disables the container
     * @param parent parent to be disabled
     */
    void disableContainer(Parent parent){
        clearFields(addNewPizzaPane);
        for (Node child : parent.getChildrenUnmodifiable()){
            if (child.equals(subtotalPane) || child.equals(chooseStylePane) || child.equals(showPizzaToppingsTextArea)) continue;
            if (child instanceof Text t) t.setOpacity(0.5);
            else child.setDisable(true);
            if (child instanceof Parent p) disableContainer(p);
        }
    }

    /**
     * enables the container
     * @param parent parent to be enabled
     */
    void enableContainer(Parent parent){
        parent.setDisable(false);
        for (Node child : parent.getChildrenUnmodifiable()){
            if (child instanceof Text t) t.setOpacity(1);
            else child.setDisable(false);
            if (child instanceof Parent p) enableContainer(p);
        }
    }

    /**
     * Sends an alert dialog to GUI
     * @param type - type of alert to send
     * @param title - title of alert window
     * @param message - message to show in main body
     * @return optional button type
     */
    @FXML
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
     * resets the price list text box
     */
    private void resetPricesTexts(){
        taxText.setText(PRICE_PLACEHOLDER);
        orderTotalText.setText(PRICE_PLACEHOLDER);
        subTotalText.setText(PRICE_PLACEHOLDER);

        smallPriceText.setText(PRICE_PLACEHOLDER);
        medPriceText.setText(PRICE_PLACEHOLDER);
        largePriceText.setText(PRICE_PLACEHOLDER);
    }

    /**
     * places the order for the pizza
     */
    @FXML
    void placeOrder(){
        if (!currentOrder.getPizzas().isEmpty()){
            orders.add(currentOrder);
            sendAlert(Alert.AlertType.INFORMATION, "Order placed!", "Your order was placed!");
            postOrderReset();
        }
    }

    /**
     * cancels the order
     */
    @FXML
    void cancelOrder(){
        if (!currentOrder.getPizzas().isEmpty()){
            Optional <javafx.scene.control.ButtonType> response = sendAlert(Alert.AlertType.WARNING, "Warning", "Are you sure you want to cancel this order?");
            if (response.isPresent() && response.get().equals(ButtonType.YES)){
                currentOrder.cancel();
                postOrderReset();
            }
        }
    }

    /**
     * resets the fields after an order
     */
    private void postOrderReset(){
        currentOrder = new Order();
        setOrderDetail();
        resetPricesTexts();
        pizzaImageView.setImage(null);
        enableContainer(addNewPizzaPane);
        disableContainer(addNewPizzaPane);
    }

    /**
     * clears all fields
     * @param parent parenbt to be cleared
     */
    private void clearFields(Parent parent) {
        for (Node child : parent.getChildrenUnmodifiable()) {
            if (child instanceof TextArea t) t.clear();
            else if (child instanceof RadioButton r) r.setSelected(false);
            else if (child instanceof ComboBox<?> c) c.getSelectionModel().clearSelection();
            if (child instanceof Parent p) clearFields(p);
        }
    }

    /**
     * adds specialty Pizza to the list
     */
    @FXML
    void addSpecialtyPizza(){
        if (sizeFilled()){
            String specialty = ((RadioButton) specialtyToggleGroup.getSelectedToggle()).getText();
            String size = ((RadioButton) sizeToggleGroup.getSelectedToggle()).getText();
            Pizza pizza = null;
            switch(currentPizzaStyle){
                case "Chicago":
                    switch(specialty){
                        case "Deluxe":
                            pizza = ChicagoPizzaFactory.createDeluxe();
                            break;
                        case "Meatzza":
                            pizza = ChicagoPizzaFactory.createMeatzza();
                            break;
                        case "BBQ Chicken":
                            pizza = ChicagoPizzaFactory.createBBQChicken();
                            break;
                        default:
                            throw new NoSuchElementException("Nonexistent specialty type was sent to addSpecialtyPizza()"); // should never occur
                    }
                    pizza.setSize(Size.getValue(size));
                    currentOrder.addPizza(pizza);
                    break;
                case "New York":
                    switch(specialty){
                        case "Deluxe":
                            pizza = NYPizzaFactory.createDeluxe();
                            break;
                        case "Meatzza":
                            pizza = NYPizzaFactory.createMeatzza();
                            break;
                        case "BBQ Chicken":
                            pizza = NYPizzaFactory.createBBQChicken();
                            break;
                    }
                    pizza.setSize(Size.getValue(size));
                    currentOrder.addPizza(pizza);
                    break;
            } setOrderDetail();
        }
    }

    /**
     * removes the pizza and refreshes the stage page
     * @param pizza pizza to be removed
     */
    private void removeAndRefresh(Pizza pizza){
        currentOrder.getPizzas().remove(pizza);
        setOrderDetail();
    }

    /**
     * sets the order details
     */
    private void setOrderDetail(){
        double subTotal = 0.0;
        VBox result = new VBox(5);
        if (currentOrder.getPizzas().isEmpty()){
            orderDetailsScrollPane.setContent(new Text("Order is empty."));
            subTotalText.setText("$0.00");
            taxText.setText("$0.00");
            orderTotalText.setText("$0.00");
            return;
        }
        for (Pizza pizza : currentOrder.getPizzas()){
            TextArea orderText = new TextArea();
            // set detail text and add to orderText
            orderText.setText(pizza.toString());
            Platform.runLater(() -> {
                orderText.setPrefWidth(orderDetailsScrollPane.getWidth() - 13);
                orderText.setPrefHeight(170);
                orderText.setWrapText(true);
                orderText.setEditable(false);
            });
            // create remove order button
            Button b = new Button();
            b.setOnAction((observable) -> removeAndRefresh(pizza));
            b.setText("Remove pizza");
            // add to resulting content of pane
            VBox vbox = new VBox(5);
            vbox.getChildren().addAll(orderText, b);
            result.getChildren().add(vbox);
            // update order total:
            subTotal += pizza.price();
        }
        // calculate tax and total, and format values (two decimal places):
        subTotal = new BigDecimal(subTotal).setScale(2, RoundingMode.HALF_UP).doubleValue();
        double tax = new BigDecimal(TAX_RATE * subTotal).setScale(2, RoundingMode.HALF_UP).doubleValue();
        double orderTotal = new BigDecimal(subTotal + tax).setScale(2, RoundingMode.HALF_UP).doubleValue();
        // set values
        orderDetailsScrollPane.setContent(result);
        subTotalText.setText("$" + String.format("%.2f", subTotal));
        taxText.setText("$" + String.format("%.2f", tax));
        orderTotalText.setText("$" + String.format("%.2f", orderTotal));
    }

    /**
     * size filled
     * @return selected toggle
     */
    private boolean sizeFilled(){ return sizeToggleGroup.getSelectedToggle() != null; }

    /**
     * sets the listeners
     */
    private void setListeners(){
        // disable listeners:
        // ensure that next step in ordering process is disabled until previous option is selected:
        chooseStyleCmb.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null){ enableContainer(selectPizzaTypePane); currentPizzaStyle = newValue;}
        });
        chooseSpecialtyBtn.setOnAction((observable) -> enableContainer(selectSpecialtyPane));
        specialtyToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null){
                enableContainer(selectSizePane);
                String imagePath = "";
                switch(((RadioButton)newValue).getText()){
                    case "Deluxe":
                        imagePath = "/images/Deluxe.png";
                        break;
                    case "Meatzza":
                        imagePath = "/images/Meatzza.png";
                        break;
                    case "BBQ Chicken":
                        imagePath = "/images/BBQChicken.png";
                        break;
                }
                pizzaImageView.setImage(new Image(getClass().getResource(imagePath).toExternalForm()));
            }
            if (newValue instanceof RadioButton r) showPizzaToppingsTextArea.setText("\tToppings:\n" + specialtyToppings.get(r.getText()));
        });
        sizeToggleGroup.selectedToggleProperty().addListener((observable) -> { if (observable != null) enableContainer(addNewPizzaPane); });
        // setting a listener to change value of pizza sizes depending on specialty
        specialtyToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue instanceof RadioButton r){
                smallPriceText.setText("$" + prices.get(r.getText())[0]);
                medPriceText.setText("$" + prices.get(r.getText())[1]);
                largePriceText.setText("$" + prices.get(r.getText())[2]);
            }
        });
    }

    /**
     * sets the values for the stage
     */
    private void setValues(){
        // set values for chooseStyleCmb
        ObservableList<String> styles = FXCollections.observableArrayList("New York", "Chicago");
        if (chooseStyleCmb != null){
            chooseStyleCmb.setItems(styles);
        }
        // set radio buttons into toggle groups

        deluxeRadioBtn.setToggleGroup(specialtyToggleGroup);
        meatzzaRadioBtn.setToggleGroup(specialtyToggleGroup);
        bbqChickenRadioBtn.setToggleGroup(specialtyToggleGroup);

        smallRadioBtn.setToggleGroup(sizeToggleGroup);
        medRadioBtn.setToggleGroup(sizeToggleGroup);
        largeRadioBtn.setToggleGroup(sizeToggleGroup);

        disableContainer(addNewPizzaPane);
        orderDetailsScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        prices.put("Deluxe", new double[]{16.99,18.99,20.99});
        prices.put("BBQ Chicken", new double[]{14.99,16.99,19.99});
        prices.put("Meatzza", new double[]{17.99,19.99,21.99});
        prices.put("Build Your Own", new double[]{8.99,10.99,12.99});

        specialtyToppings.put("Deluxe", ChicagoPizzaFactory.createDeluxe().toppingsToStr());
        specialtyToppings.put("Meatzza", ChicagoPizzaFactory.createMeatzza().toppingsToStr());
        specialtyToppings.put("BBQ Chicken", ChicagoPizzaFactory.createBBQChicken().toppingsToStr());

        currentPizzaStyle = sm.loadStyle();
        currentOrder = sm.loadOrder();
        orders = sm.loadOrderList();

        setOrderDetail();
    }

    /**
     * add toppings for the build your own
     * @param toppings - toppings to be added
     * @param size - size of the pizza
     */
    public void addBYOPizza(ArrayList<Topping> toppings, String size){
        Pizza pizza = switch (currentPizzaStyle) {
            case "Chicago" -> ChicagoPizzaFactory.createBuildYourOwn();
            case "New York" -> NYPizzaFactory.createBuildYourOwn();
            default -> throw new NoSuchElementException("Nonexistent style was sent to addSpecialtyPizza()"); // should never occur
        };

        pizza.setSize(Size.getValue(size.toUpperCase()));

        if (toppings.size() > 7){
            throw new IllegalArgumentException("Maximum of seven toppings allowed, given: " + toppings.size()); // should never occur
        }
        pizza.setToppings(toppings);
        currentOrder.addPizza(pizza);
        setOrderDetail();
    }

    // navigation methods:

    /**
     * navigation for the build your own
     * @param event action event to change the view
     * @throws IOException - exception should the view not load
     */
    @FXML
    void navigateBYOView(ActionEvent event) throws IOException {
        Optional <javafx.scene.control.ButtonType> response = sendAlert(Alert.AlertType.WARNING, "Warning", "Any specialty options that are selected will not be saved\nAre you sure you want to continue?");
        if (response.isPresent() && response.get().equals(ButtonType.YES)){
            sm.saveState(currentPizzaStyle, currentOrder, orders);
            Stage currentStage = (Stage)((Node) event.getSource()).getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/project4/build-your-own-view.fxml"));
            Scene scene = new Scene(loader.load()); // 759,440
            Stage stage = new Stage();

            stage.setTitle("RUPizzeria - Add Build-Your-Own");
            stage.setResizable(false);
            stage.setScene(scene);

            currentStage.close();
            stage.show();
        }
    }

    /**
     * sets the orders for the list
     * @param orders list of orders for to be set
     */
    @FXML
    public void setOrders(ArrayList<Order> orders){
        this.orders = orders;
    }

    /**
     * navigator for the orders
     * @param event - action event to change the order
     * @throws IOException exception if the loader can't set
     */
    @FXML
    void navigateAllOrdersView(ActionEvent event) throws IOException{
        sm.saveState(currentPizzaStyle, currentOrder, orders);
        Stage currentStage = (Stage)((Node) event.getSource()).getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/project4/view-orders-view.fxml"));

        Scene scene = new Scene(loader.load()); // 759,440
        Stage stage = new Stage();

        ((ViewOrdersController)loader.getController()).setOrders(this.orders);

        stage.setTitle("RUPizzeria - View Placed Orders");
        stage.setResizable(false);
        stage.setScene(scene);

        currentStage.close();
        stage.show();
    }

    /**
     * initializes the values and listeners
     */
    public void initialize(){
        setValues();
        setListeners();
    }
}