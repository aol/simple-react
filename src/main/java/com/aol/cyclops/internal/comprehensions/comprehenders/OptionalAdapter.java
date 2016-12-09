package com.aol.cyclops.internal.comprehensions.comprehenders;

import static com.aol.cyclops.control.AnyM.fromOptional;
import static com.aol.cyclops.types.anyM.Witness.optional;
import static com.aol.cyclops.util.Optionals.combine;

import java.util.Iterator;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import com.aol.cyclops.control.AnyM;
import com.aol.cyclops.control.Maybe;
import com.aol.cyclops.types.anyM.Witness;
import com.aol.cyclops.types.extensability.AbstractFunctionalAdapter;
import com.aol.cyclops.util.Optionals;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OptionalAdapter extends AbstractFunctionalAdapter<Witness.optional> {
    
    private final Supplier<Optional<?>> empty;
    private final Function<?,Optional<?>> unit;
    
    
    public final static OptionalAdapter optional = new OptionalAdapter(()->Optional.empty(),t->Optional.of(t));
    
    private <U> Supplier<Optional<U>> getEmpty(){
        return (Supplier)empty;
    }
    private <U> Function<U,Optional<U>>  getUnit(){
        return (Function)unit;
    }
    private <U> Function<Iterator<U>,Optional<U>>  getUnitIterator(){
        return  it->it.hasNext() ? this.<U>getUnit().apply(it.next()) : this.<U>getEmpty().get();
    }
    
    @Override
    public <T> Iterable<T> toIterable(AnyM<optional, T> t) {
        return Maybe.fromOptional(optional(t));
    }
    

    @Override
    public <T> AnyM<optional, T> filter(AnyM<optional, T> t, Predicate<? super T> fn) {
        return fromOptional(optional(t).filter(fn));
    }


    @Override
    public <T> AnyM<optional, T> empty() {
        return fromOptional(this.<T>getEmpty().get());
    }


    @Override
    public <T, R> AnyM<optional, R> ap(AnyM<optional, ? extends Function<T, R>> fn, AnyM<optional, T> apply) {
         return fromOptional(combine(optional(apply), optional(fn),(a,b)->b.apply(a)));
    }

    @Override
    public <T, R> AnyM<optional, R> flatMap(AnyM<optional, T> t,
            Function<? super T, ? extends AnyM<optional, ? extends R>> fn) {
        return fromOptional(optional(t).<R>flatMap(fn.andThen(Witness::optional).andThen(Optionals::narrow)));
    }

    @Override
    public <T> AnyM<optional, T> unitIterator(Iterator<T> it) {
       return fromOptional(this.<T>getUnitIterator().apply(it));
    }
   
    @Override
    public <T> AnyM<com.aol.cyclops.types.anyM.Witness.optional, T> unit(T o) {
        return fromOptional(this.<T>getUnit().apply(o));
    }

   
   
}