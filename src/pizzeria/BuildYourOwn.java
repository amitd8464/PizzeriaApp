package pizzeria;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Class to Build your own pizza
 * @author Andrew Ho, Amit Deshpande
 */
public class BuildYourOwn extends Pizza{

    /**
     * Constructor for BuildYourOwn
     * @param crust - instance of Crust, Crust on the pizza
     */
    public BuildYourOwn(Crust crust){
        super(crust);
    }

    /**
     * Gets the price of the pizza based on size and toppings
     * @return price of pizza double
     */
    @Override
    public double price(){
        double toppingsPrice = (1.69) * (this.getToppings().size());
        return switch (this.getSize()) {
            case Size.SMALL -> new BigDecimal(8.99 + toppingsPrice).setScale(2, RoundingMode.HALF_UP).doubleValue();
            case Size.MEDIUM -> new BigDecimal(10.99 + toppingsPrice).setScale(2, RoundingMode.HALF_UP).doubleValue();
            case Size.LARGE -> new BigDecimal(12.99 + toppingsPrice).setScale(2, RoundingMode.HALF_UP).doubleValue();
        };
    }

    /**
     * Gets type as string
     * @return String "Build-Your-Own"
     */
    @Override
    public String getSpecialty(){ return "Build-Your-Own"; }
}
