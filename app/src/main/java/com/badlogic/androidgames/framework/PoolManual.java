package com.badlogic.androidgames.framework;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class PoolManual<T> implements Supplier<T> {

    private final List<T> freeObjects;
    private final int maxSize;

    public PoolManual(int maxSize) {
        this.maxSize = maxSize;
        this.freeObjects = new ArrayList<>(maxSize);
    }

    @Override
    public T get() {
        if (freeObjects.isEmpty())
            throw new RuntimeException();

        return freeObjects.remove(freeObjects.size() - 1);
    }

    public void free(T object) {
        if (freeObjects.size() < maxSize)
            freeObjects.add(object);
    }

    public boolean isEmpty() {
        return freeObjects.isEmpty();
    }

}
