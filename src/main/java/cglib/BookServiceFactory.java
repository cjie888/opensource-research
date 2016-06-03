package cglib;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

/**
 * Created by hucj on 2016/6/3.
 */
public class BookServiceFactory {
    private static BookServiceBean service = new BookServiceBean();
    private BookServiceFactory() {
    }
    public static BookServiceBean getInstance() {
        return service;
    }

    public static BookServiceBean getProxyInstance(MyCglibProxy myProxy){
        Enhancer en = new Enhancer();
        //进行代理
        en.setSuperclass(BookServiceBean.class);
        en.setCallback(myProxy);
        //生成代理实例
        return (BookServiceBean)en.create();
    }

    public static BookServiceBean getProxyInstanceByFilter(MyCglibProxy myProxy){
        Enhancer en = new Enhancer();
        en.setSuperclass(BookServiceBean.class);
        en.setCallbacks(new Callback[]{myProxy, NoOp.INSTANCE});
        en.setCallbackFilter(new MyProxyFilter());
        return (BookServiceBean)en.create();
    }
}
