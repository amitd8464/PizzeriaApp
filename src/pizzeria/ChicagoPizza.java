package pizzeria;

/**
 * Class for ChicagoPizza
 * @author Andrew Ho, Amit Deshpande
 */
public class ChicagoPizza implements PizzaFactory {
    /**
     * Calls for the creation of a deluxe
     * @return instance of a pizza that is deluxe
     */
    @Override
    public Pizza createDeluxe(){
        return new Deluxe(Crust.DEEP_DISH);
    }

    /**
     * Calls for the creation of a Meatzza
     * @return instance of a pizza that is Meatzza
     */
    @Override
    public Pizza createMeatzza(){
        return new Meatzza(Crust.STUFFED);
    }

    /**
     * Calls for the creation of a BBQChicken
     * @return instance of a pizza that is BBQChicken
     */
    @Override
    public Pizza createBBQChicken(){
        return new BBQChicken(Crust.PAN);
    }

    /**
     * Calls for the creation of a build your own
     * @return instance of a pizza that is build your own
     */
    @Override
    public Pizza createBuildYourOwn(){
        return new BuildYourOwn(Crust.PAN);
    }
}
