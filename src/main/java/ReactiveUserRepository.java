import reactor.core.publisher.Flux;

class ReactiveUserRepository implements ReactiveRepository<User> {
    @Override
    public Flux<User> findAll() {
        return null;
    }
}
