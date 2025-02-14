package com.badlogic.androidgames.framework;

import java.util.function.Supplier;

public class Pool<T> extends PoolManual<T> {

    private final Supplier<T> factory;

    public Pool(Supplier<T> factory, int maxSize) {
        super(maxSize);
        this.factory = factory;
    }

    @Override
    public T get() {
        return isEmpty() ? factory.get() : super.get();
    }

}
