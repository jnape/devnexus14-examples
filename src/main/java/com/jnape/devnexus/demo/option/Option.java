package com.jnape.devnexus.demo.option;

import com.jnape.devnexus.demo.functions.Function1;

import static com.jnape.devnexus.demo.option.None.none;
import static com.jnape.devnexus.demo.option.Some.some;

public abstract class Option<T> {

    public abstract <Output> Option<Output> map(Function1<T, Output> mapper);

    public abstract T getOrElse(T t);

    public static <T> Option<T> option(T t) {
        if (t == null)
            return none();

        return some(t);
    }
}
