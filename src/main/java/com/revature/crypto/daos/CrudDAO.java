package com.revature.crypto.daos;
import com.revature.CryptoORM_P1.exception.InvalidClassException;
import com.revature.CryptoORM_P1.exception.MethodInvocationException;

import java.sql.SQLException;
import java.util.List;

public interface CrudDAO<T> {

    T findById(T newObj) throws InvalidClassException, MethodInvocationException, SQLException;
    boolean save(T newObj) throws InvalidClassException, MethodInvocationException, SQLException;
    List<T> findAll() throws InvalidClassException, MethodInvocationException, SQLException;
    boolean update(T updatedObj) throws InvalidClassException, MethodInvocationException, SQLException;
    boolean removeById(T removedObj) throws InvalidClassException, MethodInvocationException, SQLException;

}
