package com.realestate.dbabstract;

import java.sql.SQLException;
import java.util.List;

public interface AbstractHandler<T> {
    public abstract int add(T t) throws SQLException, IllegalAccessException;
    public abstract List<T> selectAll(Class<T> cls) throws SQLException, IllegalAccessException, InstantiationException, NoSuchFieldException;
    public abstract List<Object[]> selectAll(Class<T> cls, String... fields) throws SQLException, NoSuchFieldException;
}
