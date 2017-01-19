package annotation;

/**
 * @description 胡成杰(chengjiehu@creditease.cn)
 * 2016/11/16 20:20
 */
@Factory(
        id = "Tiramisu",
        type = Meal.class
)
public class Tiramisu implements Meal {

    @Override public float getPrice() {
        return 4.5f;
    }
}