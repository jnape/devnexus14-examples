package com.jnape.devnexus.demo.functions;

import com.jnape.devnexus.demo.tuples.Tuple2;

public abstract class Function2<X, Y, Z> extends Function1<Tuple2<X, Y>, Z> {

    public abstract Z apply(X x, Y y);

    @Override
    public final Z apply(Tuple2<X, Y> args) {
        return apply(args._1, args._2);
    }

    public Function1<Y, Z> partial(final X x) {
        return new Function1<Y, Z>() {
            @Override
            public Z apply(Y y) {
                return Function2.this.apply(x, y);
            }
        };
    }

    public final Function2<Y, X, Z> flip() {
        return new Function2<Y, X, Z>() {
            @Override
            public Z apply(Y y, X x) {
                return Function2.this.apply(x, y);
            }
        };
    }
}
