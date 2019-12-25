
//generic imports to help with simpler IDEs (ie tech.io)
import java.util.*;
import java.util.function.*;
import java.time.*;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Learn how to turn Reactive API to blocking one.
 *
 * @author Sebastien Deleuze
 */
public class Part10ReactiveToBlocking {

//========================================================================================

    /**
     * Thus if you need to block until the value from a Mono is available, use Mono#block() method.
     * It will throw an Exception if the onError event is triggered.
     *
     * Note that you should avoid this by favoring having reactive code end-to-end, as much as possible.
     * You MUST avoid this at all cost in the middle of other reactive code, as this has the potential to lock your whole reactive pipeline.
     * @param mono
     * @return
     */
    // TODO Return the user contained in that Mono
    User monoToValue(Mono<User> mono) {
        return mono.block();
    }

//========================================================================================

    /**
     * Similarly, you can block for the first or last value in a Flux with blockFirst()/blockLast(). You can also transform a Flux to an Iterable with toIterable.
     * Same restrictions as above still apply.
     * @param flux
     * @return
     */
    // TODO Return the users contained in that Flux
    Iterable<User> fluxToValues(Flux<User> flux) {
        return flux.toIterable();
    }

}