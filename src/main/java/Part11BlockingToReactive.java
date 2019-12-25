
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

/**
 * Learn how to call blocking code from Reactive one with adapted concurrency strategy for
 * blocking code that produces or receives data.
 * <p>
 * For those who know RxJava:
 * - RxJava subscribeOn = Reactor subscribeOn
 * - RxJava observeOn = Reactor publishOn
 * - RxJava Schedulers.io <==> Reactor Schedulers.elastic
 *
 * @author Sebastien Deleuze
 * @see Flux#subscribeOn(Scheduler)
 * @see Flux#publishOn(Scheduler)
 * @see Schedulers
 */
class Part11BlockingToReactive {

//========================================================================================

    /**
     * Say you have blocking code (eg. a JDBC connection to a database), and you want to integrate that into your reactive pipelines while avoiding too much of an impact on performance.
     * <p>
     * The best course of action is to isolate such intrinsically blocking parts of your code into their own execution context via a Scheduler, keeping the efficiency of the rest of the pipeline high and only creating extra threads when absolutely needed.
     * <p>
     * In the JDBC example you could use the fromIterable factory method. But how do you prevent the call to block the rest of the pipeline?
     */
    // TODO Create a Flux for reading all users from the blocking repository deferred until the flux is subscribed, and run it with an elastic scheduler
    Flux<User> blockingRepositoryToFlux(BlockingRepository<User> repository) {
        return Flux.defer(() -> Flux.fromIterable(repository.findAll())).subscribeOn(Schedulers.elastic());
    }

//========================================================================================

    /**
     * For slow subscribers (eg. saving to a database), you can isolate a smaller section of the sequence with the publishOn operator. Unlike subscribeOn, it only affects the part of the chain below it, switching it to a new Scheduler.
     * <p>
     * As an example, you can use doOnNext to perform a save on the repository, but first use the trick above to isolate that save into its own execution context. You can make it more explicit that you're only interested in knowing if the save succeeded or failed by chaining the then() operator at the end, which returns a Mono<Void>
     */
    // TODO Insert users contained in the Flux parameter in the blocking repository using an elastic scheduler and return a Mono<Void> that signal the end of the operation
    Mono<Void> fluxToBlockingRepository(Flux<User> flux, BlockingRepository<User> repository) {
        return flux.publishOn(Schedulers.elastic()).doOnNext(repository::save).then();
    }

}