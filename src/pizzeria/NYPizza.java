package pizzeria;

/**
 * Class for NYPizza that implements PizzaFactory
 */
public class NYPizza implements PizzaFactory{

    /**
     * Calls for the creation of the deluxe pizza
     * @return instance of pizza that is a Deluxe pizza with the brooklyn crust
     */
    @Override
    public Pizza createDeluxe(){
        return new Deluxe(Crust.BROOKLYN);
    }

    /**
     * Calls for the creation of the Meatzza pizza
     * @return instance of pizza that is a Meatzza pizza with the Hand tossed crust
     */
    @Override
    public Pizza createMeatzza(){
        return new Meatzza(Crust.HAND_TOSSED);
    }

    /**
     * Calls for the creation of the BBQChicken pizza
     * @return instance of pizza that is a BBQChicken pizza with Thin crust
     */
    @Override
    public Pizza createBBQChicken(){
        return new BBQChicken(Crust.THIN);
    }

    /**
     * Calls for the creation of the Build your own piza
     * @return instance of pizza that is a build your own with a hand tossed crust
     */
    @Override
    public Pizza createBuildYourOwn(){
        return new BuildYourOwn(Crust.HAND_TOSSED);
    }
}
