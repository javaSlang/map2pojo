package com.javaslang.map2pojo.core.filling.impl.fillings;

import com.javaslang.map2pojo.core.filling.Filling;
import com.javaslang.map2pojo.core.filling.Fillings;
import com.javaslang.map2pojo.core.filling.impl.baking.NoBaking;
import com.javaslang.map2pojo.core.filling.impl.filling.BasicFilling;

import java.util.Map;

public class BasicFillings implements Fillings {

    private final Map<Class, Filling> fillings;

    public BasicFillings(Map<Class, Filling> fillings) {
        this.fillings = fillings;
    }

    @Override
    public <T> Filling appropriate(Class<T> tClass) {
        return fillings.containsKey(tClass)
                ?
                fillings.get(tClass)
                :
                new BasicFilling<>(
                        new NoBaking<T>()
                );
    }

}