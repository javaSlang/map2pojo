package com.javaslang.map2pojo.core.filling;

import com.javaslang.map2pojo.core.filling.baking.NoBaking;

import java.util.Map;

public class Fillings {

    private final Map<Class, Filling> fillings;

    public Fillings(Map<Class, Filling> fillings) {
        this.fillings = fillings;
    }

    public <T> Filling appropriate(Class<T> tClass) {
        return fillings.containsKey(tClass)
                ?
                fillings.get(tClass)
                :
                new Filling<>(
                        new NoBaking<T>()
                );
    }

}