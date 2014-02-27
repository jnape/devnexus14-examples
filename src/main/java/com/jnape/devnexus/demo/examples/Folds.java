package com.jnape.devnexus.demo.examples;

import com.jnape.devnexus.demo.functions.Function1;
import com.jnape.devnexus.demo.functions.Function2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static java.util.Arrays.asList;

public class Folds {

    private static final Function2<String, String, String> EXPLAIN_FOLD = new Function2<String, String, String>() {
        @Override
        public String apply(String acc, String x) {
            return "f(" + acc + ", " + x + ")";
        }
    };
    private static final Function1<String, String>         EXPLAIN_MAP  = new Function1<String, String>() {
        @Override
        public String apply(String x) {
            return "f(" + x + ")";
        }
    };

    public static void main(String[] args) {
        List<String> numbers = asList("1", "2", "3", "4", "5");

        System.out.println("foldLeft: " + foldLeft(EXPLAIN_FOLD, "0", numbers));
        System.out.println("foldRight: " + foldRight(EXPLAIN_FOLD, "0", numbers));
        System.out.println("reduceLeft: " + reduceLeft(EXPLAIN_FOLD, numbers));
        System.out.println("reduceRight: " + reduceRight(EXPLAIN_FOLD, numbers));
        System.out.println("map: " + map(EXPLAIN_MAP, numbers));
    }

    public static <A, B> Iterable<B> map(final Function1<A, B> fn, Iterable<A> xs) {
        return foldLeft(new Function2<List<B>, A, List<B>>() {
            @Override
            public List<B> apply(List<B> bs, A a) {
                bs.add(fn.apply(a));
                return bs;
            }
        }, new ArrayList<B>(), xs);
    }

    public static <Acc, Element> Acc foldLeft(Function2<Acc, Element, Acc> fn, Acc acc, Iterable<Element> elements) {
        Iterator<Element> iterator = elements.iterator();
        Acc result = acc;
        while (iterator.hasNext())
            result = fn.apply(result, iterator.next());
        return result;
    }

    public static <Element> Element reduceLeft(Function2<Element, Element, Element> fn,
                                               Iterable<Element> elements) {
        final Iterator<Element> iterator = elements.iterator();
        return foldLeft(fn, iterator.next(), new Iterable<Element>() {
            @Override
            public Iterator<Element> iterator() {
                return iterator;
            }
        });
    }

    public static <Acc, Element> Acc foldRight(final Function2<Element, Acc, Acc> fn, final Acc acc,
                                               Iterable<Element> elements) {
        return foldLeft(fn.flip(), acc, reverse(elements));
    }

    public static <Element> Element reduceRight(final Function2<Element, Element, Element> fn,
                                                Iterable<Element> elements) {
        return reduceLeft(fn.flip(), reverse(elements));
    }

    private static <Element> Iterable<Element> reverse(final Iterable<Element> elements) {
        return new Iterable<Element>() {
            @Override
            public Iterator<Element> iterator() {
                ArrayList<Element> listCopy = new ArrayList<Element>() {{
                    for (Element element : elements) {
                        add(element);
                    }
                }};
                final ListIterator<Element> reversingIterator = listCopy.listIterator(listCopy.size());

                return new Iterator<Element>() {
                    @Override
                    public boolean hasNext() {
                        return reversingIterator.hasPrevious();
                    }

                    @Override
                    public Element next() {
                        return reversingIterator.previous();
                    }

                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                };
            }
        };
    }
}
