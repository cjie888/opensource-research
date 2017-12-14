package jmx;

/**
 * Created by hucj on 2017/1/19.
 */
public interface CarMBean {
    public String getColor();
    public void setColor(String color);
    public void drive();
}