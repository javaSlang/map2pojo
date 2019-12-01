package com.slang.map2pojo.core.filling;

public interface Fillings {

    <T> Filling appropriate(Class<T> tClass);

}
