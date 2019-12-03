
//generic imports to help with simpler IDEs (ie tech.io)

import java.util.*;
import java.util.function.*;
import java.time.*;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Learn how to use various other operators.
 *
 * @author Sebastien Deleuze
 */
public class Part09OtherOperations {

//========================================================================================

    /**
     * In the first exercise we'll receive 3 Flux<String>. Their elements
     * could arrive with latency, yet each time the three sequences have all
     * emitted an element, we want to combine these 3 elements and create a new
     * User. This concatenate-and-transform operation is called zip:
     */
    // TODO Create a Flux of user from Flux of username, firstname and lastname.and
    Flux<User> userFluxFromStringFlux(Flux<String> usernameFlux,
                                      Flux<String> firstnameFlux, Flux<String> lastnameFlux) {
        return Flux.zip(usernameFlux, firstnameFlux, lastnameFlux)
                .map(it -> new User(it.getT1(), it.getT2(), it.getT3()));
    }

//========================================================================================

    // TODO Return the mono which returns its value faster
    Mono<User> useFastestMono(Mono<User> mono1, Mono<User> mono2) {
        return Mono.first(mono1, mono2);
    }

//========================================================================================

    // TODO Return the flux which returns the first value faster
    Flux<User> useFastestFlux(Flux<User> flux1, Flux<User> flux2) {
        return Flux.first(flux1, flux2);
    }

//========================================================================================

    // TODO Convert the input Flux<User> to a Mono<Void> that represents the complete signal of the flux

    /**
     * Sometimes you're not interested in elements of a Flux<T>. If you
     * want to still keep a Flux<T> type, you can use ignoreElements(). But if
     * you really just want the completion, represented as a Mono<Void>, you
     * can use then() instead:
     */
    Mono<Void> fluxCompletion(Flux<User> flux) {
        return flux.then();
    }

//========================================================================================

    // TODO Return a valid Mono of user for null input and non null
    // input user(hint:Reactive Streams do not accept null values)

    Mono<User> nullAwareUserToMono(User user) {
        return Mono.justOrEmpty(user);
    }

//========================================================================================

    // TODO Return the same mono passed as input parameter, expect that
    // it will emit User. JESSE when empty

    Mono<User> emptyToSkyler(Mono<User> mono) {
        return mono.switchIfEmpty(Mono.just(User.JESSE));
    }

}