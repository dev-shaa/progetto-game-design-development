package com.badlogic.androidgames.framework;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class Pool<T> implements Supplier<T> {

    public interface PoolObjectFactory<T> {
        T createObject();
    }

    private final List<T> freeObjects;
    private final PoolObjectFactory<T> factory;
    private final int maxSize;

    public Pool(PoolObjectFactory<T> factory, int maxSize) {
        this.factory = factory;
        this.maxSize = maxSize;
        this.freeObjects = new ArrayList<>(maxSize);
    }

    @Override
    public T get() {
        return freeObjects.isEmpty()
                ? factory.createObject()
                : freeObjects.remove(freeObjects.size() - 1);
    }

    public void free(T object) {
        if (freeObjects.size() < maxSize)
            freeObjects.add(object);
    }

}
