import reactor.core.publisher.Flux;

interface ReactiveRepository<T> {
    Flux<T> findAll();
}
