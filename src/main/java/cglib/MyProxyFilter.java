package cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.CallbackFilter;

/**
 * Created by hucj on 2016/6/3.
 */
public class MyProxyFilter implements CallbackFilter {
    @Override
    public int accept(Method arg0) {
        if(!"query".equalsIgnoreCase(arg0.getName()))
            return 0;
        return 1;
    }
}
