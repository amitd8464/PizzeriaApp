package pizzeria;

/**
 * Enum for the types of crust
 */
public enum
Crust {
    /**
     * Deep Dish
     */
    DEEP_DISH,
    /**
     * Brooklyn
     */
    BROOKLYN,
    /**
     * Pan
     */
    PAN,
    /**
     * Thin
     */
    THIN,
    /**
     * Stuffed
     */
    STUFFED,
    /**
     * hand tossed
     */
    HAND_TOSSED;

    /**
     * Getter for the string of the crust
     * @return instance of a pizza crust as a string
     */
    public String toString(){
        return this.name().replace("_"," ");
    }
}
