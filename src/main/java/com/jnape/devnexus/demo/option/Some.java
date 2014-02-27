package com.jnape.devnexus.demo.option;

import com.jnape.devnexus.demo.functions.Function1;

public class Some<T> extends Option<T> {

    private final T payload;

    public Some(T payload) {
        this.payload = payload;
    }

    @Override
    public final <Output> Option<Output> map(Function1<T, Output> mapper) {
        return new Some<Output>(mapper.apply(payload));
    }

    @Override
    public final T getOrElse(T t) {
        return payload;
    }

    public static <T> Some<T> some(T t) {
        return new Some<T>(t);
    }
}
