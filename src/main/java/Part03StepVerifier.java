/*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


//generic imports to help with simpler IDEs (ie tech.io)
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.*;
import java.util.function.*;
import java.time.*;
import reactor.test.StepVerifier;

import reactor.core.publisher.Flux;

import static org.assertj.core.api.Assertions.*;

/**
 * Learn how to use StepVerifier to test Mono, Flux or any other kind of Reactive Streams Publisher.
 *
 * @author Sebastien Deleuze
 * @see <a href="https://projectreactor.io/docs/test/release/api/reactor/test/StepVerifier.html">StepVerifier Javadoc</a>
 */
public class Part03StepVerifier {

//========================================================================================

    /**
     *
     * Until now, your solution for each exercise was checked by passing the Publisher you defined to a test using a StepVerifier.

     This class from the reactor-test artifact is capable of subscribing to any Publisher (eg. a Flux or an Akka Stream...) and then assert a set of user-defined expectations with regard to the sequence.

     If any event is triggered that doesn't match the current expectation, the StepVerifier will produce an AssertionError.

     You can obtain an instance of StepVerifier from the static factory create. It offers a DSL to set up expectations on the data part and finish with a single terminal expectation (completion, error, cancellation...).

     Note that you must always call the verify() method or one of the shortcuts that combine the terminal expectation and verify, like .verifyErrorMessage(String). Otherwise the StepVerifier won't subscribe to your sequence and nothing will be asserted.
     *
     * StepVerifier.create(T<Publisher>).{expectations...}.verify()
     *
     */
    // TODO Use StepVerifier to check that the flux parameter emits "foo" and "bar" elements then completes successfully.
    void expectFooBarComplete(Flux<String> flux) {
        StepVerifier.create(flux)
                .expectNext("foo")
                .expectNext("bar")
                .verifyComplete();
    }

//========================================================================================

    // TODO Use StepVerifier to check that the flux parameter emits "foo" and "bar" elements then a RuntimeException error.
    void expectFooBarError(Flux<String> flux) {
        StepVerifier.create(flux)
                .expectNext("foo")
                .expectNext("bar")
                .expectError(RuntimeException.class)
                .verify();
    }

//========================================================================================

    // TODO Use StepVerifier to check that the flux parameter emits a User with "swhite"username
    // and another one with "jpinkman" then completes successfully.
    void expectSkylerJesseComplete(Flux<User> flux) {
        StepVerifier.create(flux)
                .assertNext(user -> assertThat(user.getUsername()).isEqualTo("swhite"))
                .assertNext(user -> assertThat(user.getUsername()).isEqualTo("jpinkman"))
                .verifyComplete();

    }

//========================================================================================

    // TODO Expect 10 elements then complete and notice how long the test takes.
    void expect10Elements(Flux<Long> flux) {
        Duration verify = StepVerifier.create(flux)
                .expectNextCount(10)
                .expectComplete()
                .verify();
    }

//========================================================================================

    /**
     * Fortunately, StepVerifier comes with a virtual time option: by using
     * StepVerifier.withVirtualTime(Supplier<Publisher>), the verifier will temporarily replace default core
     * Schedulers (the component that define the execution context in Reactor).
     * All these default Scheduler are replaced by a single instance of a VirtualTimeScheduler,
     * which has a virtual clock that can be manipulated.
     *
     * In order for the operators to pick up that Scheduler, you should lazily build your operator chain inside
     * the lambda passed to withVirtualTime.
     *
     * You must then advance time as part of your test scenario, by calling either thenAwait(Duration)
     * or expectNoEvent(Duration). The former simply advances the clock, while the later additionally
     * fails if any unexpected event triggers during the provided duration
     * (note that almost all the time there will at least be a "subscription" event even though the clock hasn't
     * advanced, so you should usually put a expectSubscription() after .withVirtualTime()
     * if you're going to use expectNoEvent right after).
     * 
     * // StepVerifier.withVirtualTime(() -> Mono.delay(Duration.ofHours(3)))
     *             .expectSubscription()
     *             .expectNoEvent(Duration.ofHours(2))
     *             .thenAwait(Duration.ofHours(1))
     *             .expectNextCount(1)
     *             .expectComplete()
     *             .verify();
     */

    // TODO Expect 3600 elements at intervals of 1 second, and verify quicker than 3600s
    // by manipulating virtual time thanks to StepVerifier#withVirtualTime, notice how long the test takes
    void expect3600Elements(Supplier<Flux<Long>> supplier) {
        StepVerifier.withVirtualTime(supplier)
                .thenAwait(Duration.ofHours(1))
                .expectNextCount(3600)
                .verifyComplete();

    }

    private void fail() {
        throw new AssertionError("workshop not implemented");
    }

    class User {
        private String username;

        String getUsername() {
            return username;
        }
    }
}