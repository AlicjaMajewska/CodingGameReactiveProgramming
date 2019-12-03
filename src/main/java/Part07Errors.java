/*
 * Copyright (c) 2011-2017 Pivotal Software Inc, All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


//generic imports to help with simpler IDEs (ie tech.io)

import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Learn how to deal with errors.
 *
 * @author Sebastien Deleuze
 * @see Exceptions#propagate(Throwable)
 */
public class Part07Errors {

//========================================================================================

    // TODO Return a Mono<User> containing User.SAUL when an error
//    occurs in the input Mono, else do not change the input Mono.
    Mono<User> betterCallSaulForBogusMono(Mono<User> mono) {
        return mono.onErrorReturn(IllegalStateException.class, User.SAUL);
    }

//========================================================================================

    /**
     * Let's try the same thing with Flux. In this case, we don't just want
     * a single fallback value, but a totally separate sequence (think getting
     * stale data from a cache). This can be achieved with onErrorResume, which
     * falls back to a Publisher<T>.
     */

    // TODO Return a Flux<User> containing User.SAUL and User.JESSE
//    when an error occurs in the input Flux, else do not change the input Flux.
    Flux<User> betterCallSaulAndJesseForBogusFlux(Flux<User> flux) {
        return flux.onErrorResume(IllegalStateException.class,
                (throwable) -> Flux.just(User.SAUL, User.JESSE));
    }

//========================================================================================

    /**
     * Dealing with checked exceptions is a bit more complicated. Whenever
     * some code that throws checked exceptions is used in an operator (eg. the
     * transformation function of a map), you will need to deal with it. The
     * most straightforward way is to make a more complex lambda with a
     * try-catch block that will transform the checked exception into a
     * RuntimeException that can be signalled downstream.
     * <p>
     * There is a Exceptions#propagate utility that will wrap a checked
     * exception into a special runtime exception that can be automatically
     * unwrapped by Reactor subscribers and StepVerifier: this avoid seeing an
     * irrelevant RuntimeException in the stacktrace.
     */

    // TODO Implement a method that capitalizes each user of the
//    incoming flux using the
    // #capitalizeUser method and emits an error containing a
//    GetOutOfHereException error

    Flux<User> capitalizeMany(Flux<User> flux) {
        return flux.map(user -> {
            try {
                return capitalizeUser(user);
            } catch (GetOutOfHereException e) {
                throw Exceptions.propagate(e);
            }
        });
    }

    User capitalizeUser(User user) throws GetOutOfHereException {
        if (user.equals(User.SAUL)) {
            throw new GetOutOfHereException();
        }
        return new User(user.getUsername(), user.getFirstname(),
                user.getLastname());
    }

    protected final class GetOutOfHereException extends Exception {
        private static final long serialVersionUID = 0L;
    }

}