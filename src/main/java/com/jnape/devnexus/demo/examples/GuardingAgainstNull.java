package com.jnape.devnexus.demo.examples;

import com.jnape.devnexus.demo.functions.Function1;

import static com.jnape.devnexus.demo.option.Option.option;

public class GuardingAgainstNull {

    private static Integer simpleExample(Integer id) {
        return option(id).map(times(3)).getOrElse(0);
    }

    private static Function1<Integer, Integer> times(final int multiplier) {
        return new Function1<Integer, Integer>() {
            @Override
            public Integer apply(Integer multiplicand) {
                return multiplicand * multiplier;
            }
        };
    }

    public static void main(String[] args) {
        System.out.println(
                simpleExample(10)
        );
        System.out.println(
                simpleExample(null)
        );
    }
}
