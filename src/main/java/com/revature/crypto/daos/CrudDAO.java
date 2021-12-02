package com.revature.crypto.daos;
import java.util.List;

public interface CrudDAO<T> {

    T findById(String id);
    boolean save(T newObj);
    List<T> findAll();
    boolean update(T updatedObj);
    boolean removeById(String id);

}
