import java.util.List;

interface BlockingRepository<T> {
    List<User> findAll();

    void save(User user);
}
