package com.realestate.dbabstract;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AbstractDAO<T> implements AbstractHandler<T> {

    private final Connection conn;
    private final String table;

    public AbstractDAO(Connection conn, String table) {
        this.conn = conn;
        this.table = table;
    }

    @Override
    public int add(T t) throws SQLException, IllegalAccessException {
            Field[] fields = t.getClass().getDeclaredFields();
            Field id = null;

            StringBuilder names = new StringBuilder();
            StringBuilder values = new StringBuilder();

            for (Field f : fields) {
                f.setAccessible(true);
                if (f.isAnnotationPresent(Id.class)) {id = f;}
                names.append(f.getName()).append(',');
                values.append('"').append(f.get(t)).append("\",");
            }
            if (id == null)
                throw new RuntimeException("Id field was not found");

            names.deleteCharAt(names.length() - 1); // last ','
            values.deleteCharAt(values.length() - 1); // last ','

            String sql = "INSERT INTO " + table + "(" + names.toString() +
                    ") VALUES(" + values.toString() + ")";

            try (Statement st = conn.createStatement()) {
                st.execute(sql);
                ResultSet rs = st.executeQuery("SELECT MAX("+id.getName()+") FROM " + table);
                if (rs.next()) {
                    return rs.getInt(1);
                }

            }
            return 0;
    }

    @Override
    public List<T> selectAll(Class<T> cls) throws SQLException, IllegalAccessException, InstantiationException, NoSuchFieldException {
        List<T> res = new ArrayList<>();

            try (Statement st = conn.createStatement()) {
                try (ResultSet rs = st.executeQuery("SELECT * FROM " + table)) {
                    ResultSetMetaData md = rs.getMetaData();
                    while (rs.next()) {
                        T client = (T) cls.newInstance();

                        for (int i = 1; i <= md.getColumnCount(); i++) {
                            String columnName = md.getColumnName(i);

                            Field field = cls.getDeclaredField(columnName);
                            field.setAccessible(true);

                            field.set(client, rs.getObject(columnName));
                        }
                        res.add(client);
                    }
                }
            }
            return res;
    }

    @Override
    public List<Object[]> selectAll(Class<T> cls, String... fields) throws SQLException, NoSuchFieldException {
        List<Object[]> res = new ArrayList<>();
            try (Statement st = conn.createStatement()) {
                StringBuilder fieldsNames = new StringBuilder();

                for (int i = 0; i < fields.length; i++) {
                    if (cls.getDeclaredField(fields[i])==null) {
                        throw new IllegalArgumentException("Wrong field name");
                    }
                    fieldsNames.append(fields[i]).append(",");
                }
                fieldsNames.deleteCharAt(fieldsNames.length() - 1); // last ','
                try (ResultSet rs = st.executeQuery("SELECT " +fieldsNames+" FROM " + table)) {
                    ResultSetMetaData md = rs.getMetaData();
                    Object[] row = fields;
                    res.add(row);
                    while (rs.next()) {
                        row = new Object[md.getColumnCount()];
                        for (int i = 1; i <= md.getColumnCount(); i++) {
                            String columnName = md.getColumnName(i);
                            row[i-1]=rs.getString(columnName);
                        }
                        res.add(row);
                    }
                }
            }
            return res;
    }

    @Override
    public List<Object[]> selectFiltered(Class<T> cls, String... conditions) throws SQLException, NoSuchFieldException {
        List<Object[]> res = new ArrayList<>();
        try (Statement st = conn.createStatement()) {
            StringBuilder where = new StringBuilder();

            for (int i = 0; i < conditions.length-1; i++) {
                where.append(conditions[i]).append(" AND ");
            }
            where.append(conditions[conditions.length - 1]); // last
            try (ResultSet rs = st.executeQuery("SELECT * FROM " + table + " WHERE " + where)) {
                ResultSetMetaData md = rs.getMetaData();
                Object[] row = new Object[md.getColumnCount()];
                for (int i = 1; i <= md.getColumnCount(); i++) {
                    row[i - 1] = md.getColumnName(i);
                }
                res.add(row);
                while (rs.next()) {
                    row = new Object[md.getColumnCount()];
                    for (int i = 1; i <= md.getColumnCount(); i++) {
                        String columnName = md.getColumnName(i);
                        row[i-1]=rs.getString(columnName);
                    }
                    res.add(row);
                }
            }
        }
        return res;

    }
}
