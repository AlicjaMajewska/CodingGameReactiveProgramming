
//generic imports to help with simpler IDEs (ie tech.io)
import java.util.*;
import java.util.function.*;
import java.time.*;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Learn how to transform values.
 *
 * @author Sebastien Deleuze
 */
public class Part04Transform {

//========================================================================================

    // TODO Capitalize the user username, firstname and lastname
    Mono<User> capitalizeOne(Mono<User> mono) {
        return mono.map(user -> new
                User(user.getUsername().toUpperCase(),
                user.getFirstname().toUpperCase(), user.getLastname().toUpperCase()));
    }

//========================================================================================

    // TODO Capitalize the users username, firstName and lastName
    Flux<User> capitalizeMany(Flux<User> flux) {
        return flux.map(user -> new
                User(user.getUsername().toUpperCase(),
                user.getFirstname().toUpperCase(), user.getLastname().toUpperCase()));
    }

//========================================================================================

/**
 * Now imagine that we have to call a webservice to capitalize our
 String. This new call can have latency so we cannot use the synchronous
 map anymore. Instead, we want to represent the asynchronous call as a
 Flux or Mono, and use a different operator: flatMap.
 * */

    /**
     * flatMap takes a transformation Function that returns a Publisher<U>
     instead of a U. This publisher represents the asynchronous
     transformation to apply to each element. If we were using it with map,
     we'd obtain a stream of Flux<Publisher<U>>. Not very useful.
     * */


    /**
     * But flatMap on the other hand knows how to deal with these inner
     publishers: it will subscribe to them then merge all of them into a
     single global output, a much more useful Flux<U>. Note that if values
     from inner publishers arrive at different times, they can interleave in
     the resulting Flux.
     * */

    // TODO Capitalize the users username, firstName and lastName using #asyncCapitalizeUser
    Flux<User> asyncCapitalizeMany(Flux<User> flux) {
        return flux.flatMap(user -> asyncCapitalizeUser(user));
    }

    Mono<User> asyncCapitalizeUser(User u) {
        return Mono.just(new User(u.getUsername().toUpperCase(),
                u.getFirstname().toUpperCase(), u.getLastname().toUpperCase()));
    }

}


