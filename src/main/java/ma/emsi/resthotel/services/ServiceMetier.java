package ma.emsi.resthotel.services;

import java.util.List;
import java.util.Optional;

public interface ServiceMetier<T,R,Q> {
    List<R> findAll();
    Optional<R> findById(Long id);
    Optional<R> save(Q t);
    Optional<R> delete(Long id);
    Optional<R> update(Q t,Long id);
}
