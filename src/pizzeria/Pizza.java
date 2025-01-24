package pizzeria;

import java.util.ArrayList;

/**
 * Abstract class for pizza
 * @author Andrew Ho, Amit Deshpande
 */
public abstract class Pizza {
    /**
     * List of toppings on the pizza
     */
    private ArrayList<Topping> toppings;
    /**
     * Crust on the pizza
     */
    private Crust crust;
    /**
     * size of the pizza
     */
    private Size size;

    /**
     * abstract method for price
     * @return double price
     */
    public abstract double price();

    /**
     * abstract method for the specialty
     * @return String of the specialty
     */
    public abstract String getSpecialty();

    /**
     * Setter for the pizza crust
     * @param crust crust to be set
     */
    public Pizza(Crust crust){
        this.crust = crust;
    }

    /**
     * Getter for the size of the pizza
     * @return instance of Size of the pizza
     */
    public Size getSize(){
        return this.size;
    }

    /**
     * Setter for the size of the pizza
     * @param size Size of the pizza to be set
     */
    public void setSize(Size size){
        this.size = size;
    }

    /**
     * Sets the toppings list with a new list
     * @param toppings toppings to be set in the list
     */
    public void setToppings(ArrayList<Topping> toppings){
        this.toppings = new ArrayList<>(toppings);
    }

    /**
     * Getter for the list of toppings
     * @return Array list of the toppings
     */
    public ArrayList<Topping> getToppings(){ return this.toppings; }

    /**
     * Getter for the crust of the pizza
     * @return instance of the Crust
     */
    public Crust getCrust(){ return crust; }

    /**
     * Lists the toppings on the pizza as a string
     * @return String list of the toppings on the pizza
     */
    public String toppingsToStr(){
        StringBuilder ret = new StringBuilder();
        for (Topping t : this.getToppings()){
            ret.append("\t").append(t.toString()).append("\n");
        }
        return ret.toString();
    }

    /**
     * Gets the style of pizza given the crust as a String
     * @return Style of the pizza as a String
     */
    public String getStyle(){
        switch(this.crust.toString()){
            case ("DEEP DISH"):
            case ("PAN"):
            case ("STUFFED"):
                return "Chicago";
            case ("BROOKLYN"):
            case ("THIN"):
            case ("HAND TOSSED"):
                return "New York";
            default:
                return "";
        }
    }

    /**
     * Getter for the pizza, size, style and speciality list
     * @return list of pizza with their parts
     */
    public String toString(){
        //return this.size + " " + this.getStyle();
        return "(1x) " + this.getSize() + " " + this.getStyle() + " " + getSpecialty() +
                " (" + this.getCrust().toString() + ")\n" + this.toppingsToStr() +
                "Subtotal:\n$" + this.price() + "\n";
    }
}
