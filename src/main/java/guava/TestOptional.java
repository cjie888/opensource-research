package guava;

import com.google.common.base.Optional;

import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: hucj
 * Date: 14-4-2
 * Time: 下午6:21
 * To change this template use File | Settings | File Templates.
 */
public class TestOptional {

    public static void testOptional() throws Exception {
        Optional<Integer> possible= Optional.of(6);
        Optional<Integer> absentOpt= Optional.absent();
        Optional<Integer> NullableOpt= Optional.fromNullable(null);
        Optional<Integer> NoNullableOpt= Optional.fromNullable(10);
        if(possible.isPresent()){
            System.out.println("possible isPresent:"+possible.isPresent());
            System.out.println("possible value:"+possible.get());
        }
        if(absentOpt.isPresent()){
            System.out.println("absentOpt isPresent:"+absentOpt.isPresent()); ;
        }
        if(NullableOpt.isPresent()){
            System.out.println("fromNullableOpt isPresent:"+NullableOpt.isPresent()); ;
        }
        if(NoNullableOpt.isPresent()){
            System.out.println("NoNullableOpt isPresent:"+NoNullableOpt.isPresent()); ;
        }
        possible= Optional.absent();
        System.out.println("possible isPresent:"+possible.isPresent());
        //System.out.println("possible value:"+possible.get());
    }
    public static void testMethodReturn() {
        Optional<Long> value = method();
        if(value.isPresent()==true){
            System.out.println("获得返回值: " + value.get());
        }else{

            System.out.println("获得返回值: " + value.or(-12L));
        }

        System.out.println("获得返回值 orNull: " + value.orNull());

        Optional<Long> valueNoNull = methodNoNull();
        if(valueNoNull.isPresent()==true){
            Set<Long> set=valueNoNull.asSet();
            System.out.println("获得返回值 set 的 size : " + set.size());
            System.out.println("获得返回值: " + valueNoNull.get());
        }else{
            System.out.println("获得返回值: " + valueNoNull.or(-12L));
        }

        System.out.println("获得返回值 orNull: " + valueNoNull.orNull());
    }
    private static Optional<Long> method() {
        return Optional.fromNullable(null);
    }
    private static Optional<Long> methodNoNull() {
        return Optional.fromNullable(15L);
    }
    public static  void main(String[] args) throws Exception {
        //testOptional();
        testMethodReturn();
    }
}
