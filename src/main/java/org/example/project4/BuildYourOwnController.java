package org.example.project4;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import pizzeria.Topping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Build your own controller class
 * @author Andrew Ho, Amit Deshpande
 */
public class BuildYourOwnController {
    /**
     * back button
     */
    @FXML
    private Button goBackBtn;
    /**
     * add build your own pizza button
     */
    @FXML
    private Button addBYOPizzaBtn;
    /**
     * small size button
     */
    @FXML
    private RadioButton smallRadioBtn;
    /**
     * medium size button
     */
    @FXML
    private RadioButton medRadioBtn;
    /**
     * large size button
     */
    @FXML
    private RadioButton largeRadioBtn;
    /**
     * size button toggle group
     */
    @FXML
    private ToggleGroup sizeToggleGroup = new ToggleGroup();
    /**
     * small price text box
     */
    @FXML
    private Text smallPriceText;
    /**
     * medium price text box
     */
    @FXML
    private Text medPriceText;
    /**
     * large price text box
     */
    @FXML
    private Text largePriceText;
    /**
     * sub total text box
     */
    @FXML
    private Text subTotalText;
    /**
     * topping selection pane
     */
    @FXML
    private Pane toppingSelectorPane;
    /**
     * size selector pane
     */
    @FXML
    private Pane sizeSelectorPane;

    // instance variables to hold values:
    /**
     * array list of toppings
     */
    private ArrayList<Topping> BYOToppings = new ArrayList<>();
    /**
     * build your own pizza size
     */
    private String BYOSize;
    /**
     * sub total of the order
     */
    private double subTotal = 0.0;
    /**
     * if the order has been placed
     */
    boolean ordered = false;

    // Constants:
    /**
     * max number of toppings on a single pizza
     */
    private static final int MAX_NUM_OF_TOPPINGS = 7;
    /**
     * build your own small size price
     */
    private static final double BYO_SMALL_PRICE = 8.99;
    /**
     * build your own medium size price
     */
    private static final double BYO_MED_PRICE = 10.99;
    /**
     * build your own large size price
     */
    private static final double BYO_LARGE_PRICE = 12.99;


    /**
     * alert for button
     * @param type alert type
     * @param title title/name of the alert
     * @param message message to be sent with the alert
     * @return button type
     */
    @FXML
    private Optional<ButtonType> sendAlert(Alert.AlertType type, String title, String message){
        Alert alert = new Alert(type);
        if (type.equals(Alert.AlertType.WARNING)) alert.getButtonTypes().add(ButtonType.YES); alert.getButtonTypes().remove(ButtonType.OK);
        alert.setTitle(title);
        alert.setContentText(message);
        return alert.showAndWait();
    }

    /**
     * checks if size is choosen
     * @return true if filled, false otherwise
     */
    private boolean sizeFilled(){ return sizeToggleGroup.getSelectedToggle() != null; }

    /**
     * action event to add build your own pizza
     * @param event - action event that occurs
     * @throws IOException input exception
     */
    @FXML
    void addBYOPizza(ActionEvent event) throws IOException {
        if (sizeFilled()){
            this.BYOSize = ((RadioButton) sizeToggleGroup.getSelectedToggle()).getText();
            ordered = true;
            navigateAddOrderView(event);
        }
    }

    /**
     * navigation action event
     * @param event action event that occurs
     * @throws IOException - input exception
     */
    @FXML
    void navigateAddOrderView(ActionEvent event) throws IOException {
        Optional<ButtonType> response = null;
        if (!ordered) {
            response = sendAlert(Alert.AlertType.WARNING, "Warning", "The selected options will not be saved\nAre you sure you want to continue?");
        }

        if ((ordered) || (response.isPresent() && response.get().equals(ButtonType.YES))){
            Stage currentStage = (Stage)((Node) event.getSource()).getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/project4/add-order-view.fxml"));

            Scene scene = new Scene(loader.load()); // 759,440
            Stage stage = new Stage();

            if (ordered) ((AddOrderViewController)loader.getController()).addBYOPizza(this.BYOToppings, this.BYOSize);

            stage.setTitle("RUPizzeria - Add New Orders");
            stage.setResizable(false);
            stage.setScene(scene);

            currentStage.close();
            stage.show();
        }
    }

    /**
     * disable topping pane
     */
    private void disableToppings(){
        for (Node child : toppingSelectorPane.getChildren()){
            if (child instanceof CheckBox c){
                Topping topping = Topping.valueOf(c.getText().toUpperCase().replace(" ","_"));
                if (!BYOToppings.contains(topping)){
                    c.setDisable(true);
                }
            }
        }
    }

    /**
     * enable topping pane
     */
    private void enableToppings(){
        for (Node child : toppingSelectorPane.getChildren()){
            if (child instanceof CheckBox c){
                c.setDisable(false);
            }
        }
    }

    /**
     * disable container
     * @param parent parent to be disabled
     */
    void disableContainer(Parent parent){
        for (Node child : parent.getChildrenUnmodifiable()){
            if (child instanceof Text t) t.setOpacity(0.5);
            if (child instanceof RadioButton r) r.setSelected(false);
            else child.setDisable(true);
            if (child instanceof Parent p) disableContainer(p);
        }
    }

    /**
     * enable container
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
     * updates subtotal
     * @param amount current amount
     * @param dir operation 1 to add, 0 to set, -1 to subtract
     */
    private void updateSubTotal(double amount, int dir){
        switch(dir){
            case (1):
                subTotal += amount;
                break;
            case (0):
                subTotal = amount;
                break;
            case (-1):
                subTotal -= amount;
                break;
        }
        subTotalText.setText("$" + String.format("%.2f", subTotal));
    }

    /**
     * sets listner for the toppings
     * @param c check box to listen for
     */
    private void setToppingListener(CheckBox c){
        c.selectedProperty().addListener((observable, oldValue, newValue) -> {
            Topping topping = Topping.valueOf(c.getText().toUpperCase().replace(" ","_"));
            if (newValue) { // topping added
                BYOToppings.add(topping);
                updateSubTotal(1.69, 1);
                addBYOPizzaBtn.setDisable(false);
                if (BYOToppings.size() == MAX_NUM_OF_TOPPINGS){
                    disableToppings();
                }
            } else { // topping removed
                BYOToppings.remove(topping);
                updateSubTotal(1.69, -1);
                if (BYOToppings.size() < MAX_NUM_OF_TOPPINGS){
                    enableToppings();
                }
                if (BYOToppings.isEmpty()){
                    addBYOPizzaBtn.setDisable(true);
                }
            }
        });
    }

    /**
     * sets topping selector pane
     */
    private void setListeners(){
        for (Node child : toppingSelectorPane.getChildren()){
            if (child instanceof CheckBox c){
                setToppingListener(c);
            }
        }
        sizeToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null){
                enableContainer(toppingSelectorPane);
                switch ((((RadioButton)newValue).getText())){
                    case "Small":
                        updateSubTotal(BYO_SMALL_PRICE, 0);
                        break;
                    case "Medium":
                        updateSubTotal(BYO_MED_PRICE, 0);
                        break;
                    case "Large":
                        updateSubTotal(BYO_LARGE_PRICE, 0);
                        break;
                }
            }

        } );
    }

    /**
     * sets the initial values
     */
    private void setValues(){
        smallPriceText.setText("$" + BYO_SMALL_PRICE);
        medPriceText.setText("$" + BYO_MED_PRICE);
        largePriceText.setText("$" + BYO_LARGE_PRICE);

        smallRadioBtn.setToggleGroup(sizeToggleGroup);
        medRadioBtn.setToggleGroup(sizeToggleGroup);
        largeRadioBtn.setToggleGroup(sizeToggleGroup);

        disableContainer(toppingSelectorPane);
        addBYOPizzaBtn.setDisable(true);
    }

    /**
     * initializes the values and listeners
     */
    public void initialize(){
        setValues();
        setListeners();
    }
}
