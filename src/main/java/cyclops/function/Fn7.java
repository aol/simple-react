package cyclops.function;

import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.function.Function;

import cyclops.control.Eval;
import cyclops.async.Future;
import cyclops.control.Maybe;
import cyclops.control.Try;

public interface Fn7<T1, T2, T3, T4, T5, T6, T7, R> extends Fn1<T1, Fn1<T2, Fn1<T3,Fn1<T4,Fn1<T5,Fn1<T6, Fn1<T7,R>>>>>>>{
    /**
     * Create a curried function with arity of 7
     * 
     * e.g. with Lombok val 
     * 
     * <pre>{@code
     *      val fn  = λ((Integer a)-> (Integer b)-> a+b+)
     * }</pre>
     * @param quadFunc
     * @return supplied function
     */
    public static <T1, T2, T3, T4, T5, T6,T7,R> Fn7<T1,T2,T3, T4, T5,T6,T7,R> λ(final Fn7<T1,T2,T3,T4,T5,T6,T7, R> func7) {
        return func7;
    }
    public static <T1, T2, T3, T4, T5, T6,T7,R> Fn7<? super T1,? super T2,? super T3, ? super T4, ? super T5,? super T6,? super T7,? extends R> v(final Fn7<? super T1,? super T2,? super T3,? super T4,? super T5,? super T6,? super T7, ? extends R> func7) {
        return func7;
    }
    

    public R apply(T1 a, T2 b, T3 c, T4 d, T5 e, T6 f, T7 g);

    default Fn1<T2, Fn1<T3, Fn1<T4, Fn1<T5, Fn1<T6, Fn1<T7, R>>>>>> apply(final T1 s) {
        return Curry.curry7(this)
                    .apply(s);
    }

    default Fn1<T3, Fn1<T4, Fn1<T5, Fn1<T6, Fn1<T7, R>>>>> apply(final T1 s, final T2 s2) {
        return Curry.curry7(this)
                    .apply(s)
                    .apply(s2);
    }

    default Fn1<T4, Fn1<T5, Fn1<T6, Fn1<T7, R>>>> apply(final T1 s, final T2 s2, final T3 s3) {
        return Curry.curry7(this)
                    .apply(s)
                    .apply(s2)
                    .apply(s3);
    }

    default Fn1<T5, Fn1<T6, Fn1<T7, R>>> apply(final T1 s, final T2 s2, final T3 s3, final T4 s4) {
        return Curry.curry7(this)
                    .apply(s)
                    .apply(s2)
                    .apply(s3)
                    .apply(s4);
    }

    default Fn1<T6, Fn1<T7, R>> apply(final T1 s, final T2 s2, final T3 s3, final T4 s4, final T5 s5) {
        return Curry.curry7(this)
                    .apply(s)
                    .apply(s2)
                    .apply(s3)
                    .apply(s4)
                    .apply(s5);
    }

    default Fn1<T7, R> apply(final T1 s, final T2 s2, final T3 s3, final T4 s4, final T5 s5, final T6 s6) {
        return Curry.curry7(this)
                    .apply(s)
                    .apply(s2)
                    .apply(s3)
                    .apply(s4)
                    .apply(s5)
                    .apply(s6);
    }

    default <V> Fn7<T1, T2, T3, T4, T5, T6,T7,V> andThen7(Function<? super R, ? extends V> after) {
        return (t1,t2,t3,t4,t5,t6,t7)-> after.apply(apply(t1,t2,t3,t4,t5,t6,t7));
    }

    default Fn7<T1, T2, T3, T4, T5, T6, T7, Maybe<R>> lift7() {
        return (s1, s2, s3, s4, s5,s6,s7) -> Maybe.fromLazy(Eval.later(()->Maybe.ofNullable(apply(s1,s2,s3,s4,s5,s6,s7))));
    }
    default Fn7<T1, T2, T3, T4, T5, T6, T7,Future<R>> lift7(Executor ex) {

        return (s1, s2, s3, s4, s5,s6,s7) -> Future.ofSupplier(() -> apply(s1, s2, s3, s4, s5,s6,s7), ex);
    }

    default Fn7<T1, T2, T3, T4, T5, T6, T7, Try<R, Throwable>> liftTry7() {
        return (s1, s2, s3, s4, s5,s6,s7) -> Try.withCatch(() -> apply(s1, s2, s3, s4, s5,s6,s7), Throwable.class);
    }

    default Fn7<T1, T2, T3, T4, T5, T6, T7, Optional<R>> liftOpt7() {

        return (s1, s2, s3, s4, s5, s6,s7) -> Optional.ofNullable(apply(s1, s2, s3, s4, s5, s6,s7));
    }

    default Fn1<? super T1, Fn1<? super T2, Fn1<? super T3, Fn1<? super T4, Fn1<? super T5,Fn1<? super T6,Fn1<? super T7, ? extends R>>>>>>> curry() {
        return CurryVariance.curry7(this);
    }
   
}
