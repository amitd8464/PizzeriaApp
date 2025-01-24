package pizzeria;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * class for Meatzza
 * @author Andrew Ho, Amit Deshpande
 */
public class Meatzza extends Pizza{
    public Meatzza(Crust crust){
        super(crust);
        this.setToppings(new ArrayList<>(Arrays.asList(Topping.SAUSAGE, Topping.PEPPERONI, Topping.BEEF, Topping.HAM)));
    }

    /**
     * gives the price based on size
     * @return price of the pizza
     */
    @Override
    public double price(){
        return switch (this.getSize()) {
            case Size.SMALL -> 17.99;
            case Size.MEDIUM -> 19.99;
            case Size.LARGE -> 21.99;
        };
    }

    /**
     * Getter for the name as a string
     * @return name of the pizza
     */
    @Override
    public String getSpecialty(){ return "Meatzza"; }

}
