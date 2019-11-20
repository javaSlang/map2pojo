package com.javaslang.map2pojo.core.filling.iface;

public interface Fillings {

    <T> Filling appropriate(Class<T> tClass);

}
