package com.custom.hermes.mode;

/**
 * Created by: Ysw on 2020/2/7.
 */
public class HermesTestMode implements HermesTestInterface {
    private String name;
    private String age;

    private static volatile HermesTestMode singleton = null;

    private HermesTestMode() {
    }

    public static HermesTestMode getInstance() {
        if (singleton == null) {
            synchronized (HermesTestMode.class) {
                if (singleton == null) {
                    singleton = new HermesTestMode();
                }
            }
        }
        return singleton;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getAge() {
        return age;
    }
}
