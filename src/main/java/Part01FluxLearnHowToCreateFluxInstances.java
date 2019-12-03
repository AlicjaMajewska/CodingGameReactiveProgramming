//generic imports to help with simpler IDEs (ie tech.io)

import java.util.*;
import java.util.function.*;
import java.time.*;
import java.time.temporal.ChronoUnit;

import reactor.core.publisher.Flux;

/**
 * Learn how to create Flux instances.
 *
 * @author Sebastien Deleuze
 * @see <a href="https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Flux.html">Flux Javadoc</a>
 */
public class Part01FluxLearnHowToCreateFluxInstances {


/**
 Flux.fromIterable(getSomeLongList())
 .delayElements(Duration.ofMillis(100))
 .doOnNext(serviceA::someObserver)
 .map(d -> d * 2)
 .take(3)
 .onErrorResumeWith(errorHandler::fallback)
 .doAfterTerminate(serviceM::incrementTerminate)
 .subscribe(System.out::println);
 */
//========================================================================================


    /**
     * TIP: If you want some insight as to what is going on inside a Flux or Mono you are about to return
     * during one of these exercises, you can always append .log() to the flux just before returning it.
     * Part 6 makes use of that.
     */

    // TODO Return an empty Flux
    Flux<String> emptyFlux() {
        // Create a Flux that completes without emitting any item.
        return Flux.empty();
    }

//========================================================================================

    // TODO Return a Flux that contains 2 values "foo" and "bar" without using an array or a collection
    // Create a new Flux that emits the specified item(s) and then complete.
    Flux<String> fooBarFluxFromValues() {
        return Flux.just("foo", "bar");
    }

//===========================================


    // TODO Create a Flux from a List that contains 2 values "foo" and "bar"
    // Create a Flux that emits the items contained in the provided Iterable.
    Flux<String> fooBarFluxFromList() {
        return Flux.fromIterable(Arrays.asList("foo", "bar"));
    }

    //========================================================================================
    // Reactive Streams defines the onError signal to deal with exceptions. Note that such an event is
    // terminal: this is the last event the Flux will produce.

    // Flux#error produces a Flux that simply emits this signal, terminating immediately:
    // Create a Flux that completes with the specified error.
    // TODO Create a Flux that emits an IllegalStateException
    Flux<String> errorFlux() {
        return Flux.error(new IllegalStateException("error"));
    }

//========================================================================================

    /**
     * To finish with Flux, let's try to create a Flux that produces ten elements, at a regular pace.
     * In order to do that regular publishing, we can use interval. But it produces an infinite stream
     * (like ticks of a clock), and we want to take only 10 elements, so don't forget to precise it.
     */
    // TODO Create a Flux that emits increasing values from 0 to 9 each 100ms

    Flux<Long> counter() {
        return Flux.interval(Duration.of(100, ChronoUnit.MILLIS))
                .take(10);
    }

}