package pizzeria;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class for BBQChicken
 * @author Andrew Ho, Amit Deshpande
 */
public class BBQChicken extends Pizza{
    /**
     * Constructor for BBQChicken and sets the right toppings
     * @param crust - instance of Crust
     */
    public BBQChicken(Crust crust){
        super(crust);
        this.setToppings(new ArrayList<>(Arrays.asList(Topping.BBQ_CHICKEN, Topping.GREEN_PEPPER, Topping.PROVOLONE, Topping.CHEDDAR)));
    }

    /**
     * Gives the price of the BBQChicken pizza based on it's size
     * @return double - price of the pizza
     */
    @Override
    public double price(){
        return switch (this.getSize()) {
            case Size.SMALL -> 14.99;
            case Size.MEDIUM -> 16.99;
            case Size.LARGE -> 19.99;
        };
    };

    /**
     * Returns string
     * @return String "BBQ Chicken"
     */
    @Override
    public String getSpecialty(){ return "BBQ Chicken"; }
}
