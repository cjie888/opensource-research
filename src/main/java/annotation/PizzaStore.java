package annotation;

import java.io.IOException;

/**
 * @description 胡成杰(chengjiehu@creditease.cn)
 * 2016/11/16 20:20
 */
public class PizzaStore {

//    public Meal order(String mealName) {
//
//        if (mealName == null) {
//            throw new IllegalArgumentException("Name of the meal is null!");
//        }
//
//        if ("Margherita".equals(mealName)) {
//            return new MargheritaPizza();
//        }
//
//        if ("Calzone".equals(mealName)) {
//            return new CalzonePizza();
//        }
//
//        if ("Tiramisu".equals(mealName)) {
//            return new Tiramisu();
//        }
//
//        throw new IllegalArgumentException("Unknown meal '" + mealName + "'");
//    }
    private MealFactory factory = new MealFactory();

    public Meal order(String mealName) {
        return factory.create(mealName);
    }
    public static void main(String[] args) throws IOException {
        PizzaStore pizzaStore = new PizzaStore();
        Meal meal = pizzaStore.order("Tiramisu");
        System.out.println("Bill: $" + meal.getPrice());
    }
}