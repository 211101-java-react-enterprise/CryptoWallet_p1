package com.revature.crypto.daos;
import java.util.List;

public interface CrudDAO<T> {

    T findById(String id);

}
