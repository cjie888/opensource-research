/**
 * Created with IntelliJ IDEA.
 * User: hucj
 * Date: 14-5-19
 * Time: 下午4:28
 * To change this template use File | Settings | File Templates.
 */
public class Title {
    public String generate(String name, String gender) {
        String title ="";
        switch (gender) {
            case "男":
                title = name + "先生";
                break;
            case "女":
                title = name + "女士";
            default:
                title = name;
        }
        return title;
    }
}
