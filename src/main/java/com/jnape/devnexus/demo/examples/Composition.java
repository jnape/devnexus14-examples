package com.jnape.devnexus.demo.examples;

import com.jnape.devnexus.demo.functions.Function1;
import com.jnape.devnexus.demo.functions.Function2;

import static java.util.Arrays.asList;
import static com.jnape.devnexus.demo.examples.Folds.foldLeft;
import static com.jnape.devnexus.demo.examples.Folds.map;

public class Composition {

    private static final Function2<Integer, Integer, Integer> ADD = new Function2<Integer, Integer, Integer>() {
        @Override
        public Integer apply(Integer augend, Integer addend) {
            return augend + addend;
        }
    };

    public static void main(String[] args) {
        System.out.println(simpleExample());
        System.out.println(foldRightUsingComposition(ADD, 0, asList(1, 2, 3, 4, 5)));
    }

    private static Character simpleExample() {
        Function1<String, String> toUpperCase = new Function1<String, String>() {
            @Override
            public String apply(String string) {
                return string.toUpperCase();
            }
        };
        Function1<String, Character> firstCharacter = new Function1<String, Character>() {
            @Override
            public Character apply(String string) {
                return string.charAt(0);
            }
        };
        return toUpperCase.compose(firstCharacter).apply("bob smith");
    }

    private static <Acc, Element> Acc foldRightUsingComposition(
            final Function2<Acc, Element, Acc> fn, final Acc acc,
            Iterable<Element> elements
    ) {
        /*
            This is just an academic example of how to implement foldRight in terms of foldLeft. This
            can totally blow the stack, depending on the number of elements in the Iterable, due to
            the number of Functions that defer returning values until other functions down the line
            return.
        */
        Function2<Function1<Acc, Acc>, Function1<Acc, Acc>, Function1<Acc, Acc>> compose = new Function2<Function1<Acc, Acc>, Function1<Acc, Acc>, Function1<Acc, Acc>>() {
            @Override
            public Function1<Acc, Acc> apply(Function1<Acc, Acc> f,
                                             Function1<Acc, Acc> g) {
                return g.compose(f);
            }
        };
        Function1<Element, Function1<Acc, Acc>> partiallyApplyFlippedAccumulationFunction = new Function1<Element, Function1<Acc, Acc>>() {
            @Override
            public Function1<Acc, Acc> apply(Element element) {
                return fn.flip().partial(element);
            }
        };
        Function1<Acc, Acc> expandedFunctionChain = foldLeft(
                compose,
                Composition.<Acc>identity(),
                map(partiallyApplyFlippedAccumulationFunction, elements)
        );
        return expandedFunctionChain.apply(acc);
    }

    public static <T> Function1<T, T> identity() {
        return new Function1<T, T>() {
            @Override
            public T apply(T t) {
                return t;
            }
        };
    }
}
