package pizzeria;

/**
 * Interface to create pizzas
 * @author Andrew Ho, Amit Deshpande
 */
public interface PizzaFactory {
    /**
     * creates deluxe
     * @return pizza created
     */
    Pizza createDeluxe();

    /**
     * creates Meatzza
     * @return pizza created
     */
    Pizza createMeatzza();

    /**
     * creates BBQChicken
     * @return pizza created
     */
    Pizza createBBQChicken();

    /**
     * creates build your own
     * @return pizza created
     */
    Pizza createBuildYourOwn();
}
