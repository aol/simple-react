package com.aol.cyclops2.internal.stream.spliterators;

import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

import lombok.Getter;
import lombok.Setter;

public class ReversingRangeIntSpliterator implements Spliterator.OfInt, ReversableSpliterator<Integer>, Indexable<Integer> {

    private final int min;
    private final int max;
    private int index;
    private int start;

    @Getter
    @Setter
    private boolean reverse;

    public ReversingRangeIntSpliterator(final int min, final int max, final boolean reverse) {
        this.min = Math.min(min, max) - 1;
        this.max = Math.max(min, max);
        this.reverse = this.max >= this.min ? reverse : !reverse;
        start =index = Math.min(min, max);
    }

    @Override
    public ReversableSpliterator invert() {
        setReverse(!isReverse());
        start =index = max - 1;
        return this;
    }

    @Override
    public boolean tryAdvance(final IntConsumer consumer) {
        Objects.requireNonNull(consumer);
        //
        if (!reverse) {
            if (index < max && index > min) {
                consumer.accept(index++);
                return true;
            }
        }
        if (reverse) {
            if (index > min && index < max) {
                consumer.accept(index--);
                return true;
            }
        }
        return false;
    }

    @Override
    public long estimateSize() {
        return max;
    }

    @Override
    public int characteristics() {
        return IMMUTABLE;
    }

    @Override
    public Spliterator.OfInt trySplit() {
        return this;
    }

    @Override
    public ReversableSpliterator copy() {
        return new ReversingRangeIntSpliterator(
                                                start, max, reverse);
    }

    /* (non-Javadoc)
     * @see java.util.Spliterator.OfInt#forEachRemaining(java.util.function.IntConsumer)
     */
    @Override
    public void forEachRemaining(IntConsumer action) {
        int index = this.index; //use local index making spliterator reusable
        if (!reverse) {
            for( ;index < max && index > min;) {
                action.accept(index++);
                
            }
        }
        if (reverse) {
            for( ;index > min && index < max;) {
                action.accept(index--);
                
            }
            
        }
    }

    /* (non-Javadoc)
     * @see java.util.Spliterator.OfInt#forEachRemaining(java.util.function.Consumer)
     */
    @Override
    public void forEachRemaining(Consumer<? super Integer> action) {
        int index = this.index; //use local index making spliterator reusable
        if (!reverse) {
            for( ;index < max && index > min;) {
                action.accept(index++);
            }
        }
        if (reverse) {
            for( ;index > min && index < max;) {
                action.accept(index--);   
            }
            
        }
    }

    @Override
    public Spliterator<Integer> start(long start) {
        return new ReversingRangeIntSpliterator(
                (int)start, max, reverse);
    }

    @Override
    public Spliterator<Integer> end(long end) {
        return new ReversingRangeIntSpliterator(
                min, (int)end, reverse);
    }

    @Override
    public Spliterator<Integer> startAndEnd(long start, long end) {
        return new ReversingRangeIntSpliterator(
                (int)start, (int)end, reverse);
    }
}