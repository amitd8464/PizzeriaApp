package test;

import org.junit.jupiter.api.Test;
import pizzeria.BuildYourOwn;
import pizzeria.Crust;
import pizzeria.Size;
import pizzeria.Topping;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BuildYourOwnTest {

    //small pizza no topping
    @Test
    void priceTest1(){
        BuildYourOwn pizza1 = new BuildYourOwn(Crust.HAND_TOSSED);
        pizza1.setSize(Size.SMALL);
        pizza1.setToppings(new ArrayList<>());
        assertEquals(pizza1.price(),8.99);
    }
    //medium pizza no topping
    @Test
    void priceTest2(){
        BuildYourOwn pizza1 = new BuildYourOwn(Crust.HAND_TOSSED);
        pizza1.setSize(Size.MEDIUM);
        pizza1.setToppings(new ArrayList<>());
        assertEquals(pizza1.price(),10.99);
    }
    //large pizza no topping
    @Test
    void priceTest3() {
        BuildYourOwn pizza1 = new BuildYourOwn(Crust.HAND_TOSSED);
        pizza1.setSize(Size.LARGE);
        pizza1.setToppings(new ArrayList<>());
        assertEquals(pizza1.price(),12.99);
    }
    //small pizza 2 topping
    @Test
    void priceTest4() {
        BuildYourOwn pizza1 = new BuildYourOwn(Crust.HAND_TOSSED);
        pizza1.setSize(Size.SMALL);
        ArrayList<Topping> pizzaTopping = new ArrayList<Topping>();
        pizzaTopping.add(Topping.SAUSAGE);
        pizzaTopping.add(Topping.PINEAPPLE);
        pizza1.setToppings(pizzaTopping);
        assertEquals(pizza1.price(),12.37);
    }
    //large pizza 3 topping
    @Test
    void priceTest5() {
        BuildYourOwn pizza1 = new BuildYourOwn(Crust.HAND_TOSSED);
        pizza1.setSize(Size.SMALL);
        ArrayList<Topping> pizzaTopping = new ArrayList<Topping>();
        pizzaTopping.add(Topping.SAUSAGE);
        pizzaTopping.add(Topping.PINEAPPLE);
        pizzaTopping.add(Topping.BLACK_OLIVE);
        pizza1.setToppings(pizzaTopping);
        assertEquals(pizza1.price(),14.06);
    }
}