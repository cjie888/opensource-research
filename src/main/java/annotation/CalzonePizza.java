package annotation;

/**
 * @description 胡成杰(chengjiehu@creditease.cn)
 * 2016/11/16 20:19
 */
@Factory(
        id = "Calzone",
        type = Meal.class
)
public class CalzonePizza implements Meal {

    @Override public float getPrice() {
        return 8.5f;
    }
}