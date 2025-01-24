package pizzeria;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

/**
 * Class to handle the list of orders
 * @author Andrew Ho, Amit Deshpande
 */
public class Order {
    /**
     * previous Serial number
     */
    private static int previousSerial = 0;
    /**
     * the number of pizzas in the list
     */
    private int number;
    /**
     * The array list of pizzas in the order
     */
    private ArrayList<Pizza> pizzas;
    /**
     * the set tax rate for the bill
     */
    private static final double TAX_RATE = 0.06625;

    /**
     * Constructor for a new order
     */
    public Order(){
        this.pizzas = new ArrayList<>();
        this.number = previousSerial + 1;
        previousSerial = this.number;
    }

    /**
     * Adds an instance of pizza to the list
     * @param pizza - instance of pizza to be added
     */
    public void addPizza(Pizza pizza){ pizzas.add(pizza); }

    /**
     * Getter for the list of pizzas in the order
     * @return ArrayList of pizzas in the order
     */
    public ArrayList<Pizza> getPizzas(){return pizzas; }

    /**
     * Getter for the pizzas list to string
     * @return String of the pizzas in the order
     */
    public String pizzasToString(){
        StringBuilder ret = new StringBuilder();
        for (Pizza pizza : pizzas){
            ret.append(pizza.toString()).append("\n");
        }
        return ret.toString();
    }

    /**
     * Getter for the order number of the pizza
     * @return String of the order number for the pizza
     */
    public String orderNumToString(){ return String.format("%03d", number); }

    /**
     * Cancels the pizzas in the order
     */
    public void cancel(){ this.pizzas = new ArrayList<>(); }

    /**
     * Calculates the cost of the order of pizzas
     * @return double of the cost of the order
     */
    public double total(){
        double subTotal = 0.0;
        for (Pizza pizza : pizzas){
            subTotal += pizza.price();
        }
        subTotal = new BigDecimal(subTotal).setScale(2, RoundingMode.HALF_UP).doubleValue();
        double tax = new BigDecimal(TAX_RATE * subTotal).setScale(2, RoundingMode.HALF_UP).doubleValue();
        return new BigDecimal(subTotal + tax).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * String getter for the order with the order number
     * @return String with order number and pizzas
     */
    public String toString(){
        StringBuilder ret = new StringBuilder();
        ret.append("Order ").append(String.format("%03d", number)).append(": \n");
        for (Pizza pizza : pizzas){
            ret.append(pizza.toString());
        }
        ret.append("\nOrder total: $").append(total());
        return ret.toString();
    }
}
