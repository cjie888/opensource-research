package guava;

/**
 * Created with IntelliJ IDEA.
 * User: hucj
 * Date: 14-4-9
 * Time: 上午11:22
 * To change this template use File | Settings | File Templates.
 */

import com.google.common.base.Objects;
import org.junit.Test;

public class ObjectTest {
    @Test
    public void toStringTest() {
        System.out.println(Objects.toStringHelper(this).add("x", 1).toString());
        System.out.println(Objects.toStringHelper(Person.class).add("x", 1).toString());

        Person person=new Person("peida",23);
        String result = Objects.toStringHelper(Person.class)
                .add("name", person.name)
                .add("age", person.age).toString();
        System.out.print(result);
    }
    @Test
    public void equalTest() {
        System.out.println(Objects.equal("a", "a"));
        System.out.println(Objects.equal(null, "a"));
        System.out.println(Objects.equal("a", null));
        System.out.println(Objects.equal(null, null));
    }
    @Test
    public void hashcodeTest() {
        System.out.println(Objects.hashCode("a"));
        System.out.println(Objects.hashCode("a"));
        System.out.println(Objects.hashCode("a", "b"));
        System.out.println(Objects.hashCode("b", "a"));
        System.out.println(Objects.hashCode("a", "b", "c"));

        Person person=new Person("peida",23);
        System.out.println(Objects.hashCode(person));
        System.out.println(Objects.hashCode(person));
    }
    @Test
    public void equalPersonTest() {
        System.out.println(Objects.equal(new Person("peida", 23), new Person("peida", 23)));
        Person person=new Person("peida",23);
        System.out.println(Objects.equal(person, person));
    }
}

class Person {
    public String name;
    public int age;

    Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
