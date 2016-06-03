package cglib;

/**
 * Created by hucj on 2016/6/3.
 */
public class Client {

    public static void main(String[] args) {
        BookServiceBean service = BookServiceFactory.getInstance();
        doMethod(service);
        doMethod1();
    }
    public static void doMethod(BookServiceBean service){
        service.create();
        service.update();
        service.query();
        service.delete();
    }
    public static void doMethod1(){
        BookServiceBean service = BookServiceFactory.getProxyInstance(new MyCglibProxy("boss"));
        service.create();
        BookServiceBean service2 = BookServiceFactory.getProxyInstance(new MyCglibProxy("john"));
        service2.create();
        service2.query();

        BookServiceBean service3 = BookServiceFactory.getProxyInstanceByFilter(new MyCglibProxy("jhon"));
        service3.create();
        BookServiceBean service4 = BookServiceFactory.getProxyInstanceByFilter(new MyCglibProxy("jhon"));
        service4.query();
    }

}
