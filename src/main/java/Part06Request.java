
//generic imports to help with simpler IDEs (ie tech.io)

import java.util.*;
import java.util.function.*;
import java.time.*;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * Learn how to control the demand.
 *
 * @author Sebastien Deleuze
 */
public class Part06Request {

    /**
     * There's one aspect to it that we didn't cover: the volume
     * control. In Reactive Streams terms this is called backpressure. It is a
     * feedback mechanism that allows a Subscriber to signal to its Publisher
     * how much data it is prepared to process, limiting the rate at which the
     * Publisher produces data.
     * <p>
     * This control of the demand is done at the Subscription level: a
     * Subscription is created for each subscribe() call and it can be
     * manipulated to either cancel() the flow of data or tune demand with
     * request(long).
     * <p>
     * Making a request(Long.MAX_VALUE) means an unbounded demand, so the
     * Publisher will emit data at its fastest pace.
     */

    ReactiveRepository<User> repository = new ReactiveUserRepository();

//========================================================================================


    /**
     * The demand can be tuned in the StepVerifier as well, by using the
     * relevant parameter to create and withVirtualTime for the initial
     * request, then chaining in thenRequest(long) in your expectations for
     * further requests.
     * <p>
     * In this first example, create a StepVerifier that produces an initial
     * unbounded demand and verifies 4 values to be received, before
     * completion. This is equivalent to the way you've been using StepVerifier
     * so far.
     */
    // TODO Create a StepVerifier that initially requests all values and expect 4 values to be received
    StepVerifier requestAllExpectFour(Flux<User> flux) {
        return StepVerifier.create(flux)
                .thenRequest(Long.MAX_VALUE)
                .expectNextCount(4)
                .expectComplete(); // no verify because of return type

    }

//========================================================================================

    /**
     * Next we will request values one by one: for that you need an initial
     * request, but also a second single request after you've received and
     * asserted the first element.
     * <p>
     * Without more request, the source will never complete unless you cancel
     * it. This can be done instead of the terminal expectations by using
     * .thenCancel().
     */
    // TODO Create a StepVerifier that initially requests 1 value and
//    expects User.
//    SKYLER then
//    requests another
//    value and
//    expects User.
//
//    JESSE.
    StepVerifier
    requestOneExpectSkylerThenRequestOneExpectJesse(Flux<User> flux) {
        return StepVerifier.create(flux)
                .thenRequest(1)
                .expectNextCount(1)
                .thenRequest(1)
                .thenCancel();

    }

//========================================================================================

    /**
     * It's important to be able to debug reactive APIs, so in the next
     * example we will make use of the log operator to know exactly what
     * happens in term of signals and events.
     */
    // TODO Return a Flux with all users stored in the repository that
//    prints automatically
//    logs for
//    all Reactive
//    Streams signals

    Flux<User> fluxWithLog() {
        return repository.findAll()
                .log();
    }

//========================================================================================

    /**
     * If you want to perform custom actions without really modifying the
     * elements in the sequence, you can use the "side effect" methods that
     * start with doOn.
     * <p>
     * For example, if you want to print "Starting:" upon subscription, use
     * doOnSubscribe.
     * <p>
     * Each doOn method takes a relevant callback representing the custom
     * action for the corresponding event.
     * <p>
     * Note that you should not block or invoke operations with latency in
     * these callbacks (which is also true of other operator callbacks like
     * map): it's more for quick operations.
     */

    // TODO Return a Flux with all users stored in the repository that
//    prints "Starring:"
//    on subscribe, "firstname lastname"for
//    all values
//    and
//"The end!"
//    on complete

    Flux<User> fluxWithDoOnPrintln() {
        return repository.findAll()
                .doOnSubscribe(subscription -> System.out.print("Starring:"))
                .doOnNext(next -> System.out.print(next.getFirstname() + " " +
                        next.getLastname()))
                .doOnComplete(() -> System.out.print("The end!"));
    }

}