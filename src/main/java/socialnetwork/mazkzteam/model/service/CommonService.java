package socialnetwork.mazkzteam.model.service;

import java.util.List;

public interface CommonService<T> {
    List<T> getAll();

    T save(T t);

    T findById(int id);

    boolean deleteById( int id);
}
