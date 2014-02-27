package com.jnape.devnexus.demo.option;

import com.jnape.devnexus.demo.functions.Function1;

public final class None<T> extends Option<T> {

    private static final None INSTANCE = new None();

    @Override
    public final <Output> Option<Output> map(Function1<T, Output> mapper) {
        return new None<Output>();
    }

    @Override
    public final T getOrElse(T t) {
        return t;
    }

    @SuppressWarnings("unchecked")
    public static <T> None<T> none() {
        return (None<T>) INSTANCE;
    }
}
