package it.unibo.inner;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import it.unibo.inner.api.IterableWithPolicy;
import it.unibo.inner.api.Predicate;

public class ImplIterableWithPolicy<T> implements IterableWithPolicy<T>{

    private List<T> elements;
    private Predicate<T> filter;
    
    public ImplIterableWithPolicy(final T[] elements) {
        this(elements, new Predicate<T>() { // Interfaccia funzionale, posso usare lambda

            @Override
            public boolean test(T elem) {
                return true;
            }
            
        });
    }

    public ImplIterableWithPolicy(final T[] elements, final Predicate<T> filter) {
        this.elements = List.of(Objects.requireNonNull(elements));
        this.filter = filter;
    }

    @Override
    public Iterator<T> iterator() {
        return new MyIterator();
    }

    @Override
    public void setIterationPolicy(Predicate<T> filter) {
        this.filter = filter;
    }

    public class MyIterator implements Iterator<T> {

        private int i = 0;

        @Override
        public boolean hasNext() {
            while (i < elements.size()) {
                T elem = elements.get(i);
                if (filter.test(elem)) {
                    return true;
                }
                i++;
            }
            return false;
        }

        @Override
        public T next() {
            return ImplIterableWithPolicy.this.elements.get(i++);
        }

    }
}
