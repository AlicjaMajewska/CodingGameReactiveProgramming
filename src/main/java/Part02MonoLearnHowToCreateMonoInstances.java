//generic imports to help with simpler IDEs (ie tech.io)

import java.util.*;
import java.util.function.*;
import java.time.*;

import reactor.core.publisher.Mono;

/**
 * Learn how to create Mono instances.
 *
 * @author Sebastien Deleuze
 * @see <a href="https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Mono.html">Mono Javadoc</a>
 */
class Part02MonoLearnHowToCreateMonoInstances {

/**
 * A Mono<Void> can be used in cases where only the completion signal is interesting (the Reactive Streams equivalent of a Runnable task completing).
 * */

    /**
     * Mono.just(1)
     * .map(integer -> "foo" + integer)
     * .or(Mono.delay(Duration.ofMillis(100)))
     * .subscribe(System.out::println);
     */


//========================================================================================

    // Create a Mono that completes without emitting any item.
    // TODO Return an empty Mono
    Mono<String> emptyMono() {
        return Mono.empty();
    }

//========================================================================================

    // Now, we will try to create a Mono which never emits anything. Unlike empty(), it won't even emit an onComplete event.
    // TODO Return a Mono that never emits any signal
    Mono<String> monoWithNoSignal() {
        return Mono.never();
    }

//========================================================================================

    // Like Flux, you can create a Mono from an available (unique) value.
    // TODO Return a Mono that contains a "foo" value
    Mono<String> fooMono() {
        return Mono.just("foo");
    }

//========================================================================================

    // TODO Create a Mono that emits an IllegalStateException
    Mono<String> errorMono() {
        return Mono.error(new IllegalStateException(""));
    }

}