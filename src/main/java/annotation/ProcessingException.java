package annotation;

import javax.lang.model.element.Element;

/**
 * @description 胡成杰(chengjiehu@creditease.cn)
 * 2016/11/16 20:29
 */
public class ProcessingException extends Exception {

    Element element;

    public ProcessingException(Element element, String msg, Object... args) {
        super(String.format(msg, args));
        this.element = element;
    }

    public Element getElement() {
        return element;
    }
}
