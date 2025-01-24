package pizzeria;

/**
 * enum for toppings
 * @author Andrew Ho, Amit Deshpande
 */
public enum Topping {
    /**
     * sausage
     */
    SAUSAGE,
    /**
     * pepperoni
     */
    PEPPERONI,
    /**
     * Beef
     */
    BEEF,
    /**
     * ham
     */
    HAM,
    /**
     * bbq chicken
     */
    BBQ_CHICKEN,
    /**
     * buffalo chicken
     */
    BUFFALO_CHICKEN,
    /**
     * provolone
     */
    PROVOLONE,
    /**
     * cheddar
     */
    CHEDDAR,
    /**
     * green pepper
     */
    GREEN_PEPPER,
    /**
     * onion
     */
    ONION,
    /**
     * pineapple
     */
    PINEAPPLE,
    /**
     * black olive
     */
    BLACK_OLIVE,
    /**
     * mushroom
     */
    MUSHROOM,
    /**
     * jalapeno
     */
    JALAPENO,
    /**
     * basil
     */
    BASIL;

    /**
     * replaces "_" to " "
     * @return replaced String
     */
    public String toString(){
        return this.name().replace("_", " ");
    }
}
