package com.jnape.devnexus.demo.functions;

public abstract class Function1<X, Y> {

    public abstract Y apply(X x);

    public final <Z> Function1<X, Z> compose(final Function1<Y, Z> f) {
        return new Function1<X, Z>() {
            @Override
            public Z apply(X x) {
                Function1<X, Y> g = Function1.this;
                return f.apply(g.apply(x));
            }
        };
    }
}
