package annotation;

/**
 * @description 胡成杰(chengjiehu@creditease.cn)
 * 2016/11/16 20:19
 */
@Factory(
        id = "Margherita",
        type = Meal.class
)
public class MargheritaPizza implements Meal {

    @Override public float getPrice() {
        return 6.0f;
    }
}