package org.example.project4;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pizzeria.Order;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Class to manage the state of the application
 * @author Andrew Ho, Amit Deshpande
 */
public class StateManager {
    /**
     * state manager instance
     */
    private static StateManager instance;

    /**
     * current pizza style
     */
    private String currentPizzaStyle;
    /**
     * current order being used
     */
    private Order currentOrder;
    /**
     * list of orders
     */
    private ArrayList<Order> orders;

    /**
     * private constructor to start the state manager
     */
    private StateManager() {
        // Initialize variables if needed
        this.currentPizzaStyle = "";
        this.currentOrder = new Order();
        this.orders = new ArrayList<>();
    }

    /**
     * creator of instance of a new manager
     * @return instance of new manager
     */
    public static StateManager getInstance(){
        if (instance == null){
            instance = new StateManager();
        }
        return instance;
    }

    /**
     * saves the state of the applicaiton
     * @param currentPizzaStyle - style of pizza
     * @param currentOrder - the order being worked on
     * @param orders - Array list of orders
     */
    public void saveState(String currentPizzaStyle, Order currentOrder, ArrayList<Order> orders){
        this.currentPizzaStyle = currentPizzaStyle;
        this.currentOrder = currentOrder;
        this.orders = orders;
    }

    /**
     * loads the pizza style
     * @return String of the pizza style
     */
    public String loadStyle(){ return this.currentPizzaStyle == null ? "" : this.currentPizzaStyle; }

    /**
     * loads the order
     * @return order to be used
     */
    public Order loadOrder(){ return this.currentOrder == null ? new Order() : this.currentOrder; }

    /**
     * loads the order list
     * @return array list of orders
     */
    public ArrayList<Order> loadOrderList(){ return this.orders == null ? new ArrayList<>() : this.orders; }
}
