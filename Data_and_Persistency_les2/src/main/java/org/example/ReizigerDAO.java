package org.example;

import java.sql.SQLException;
import java.util.List;

public interface ReizigerDAO {


    public void save(Reiziger reiziger);
    public boolean existsById(int id) throws SQLException;
    public void update(Reiziger reiziger);
    public void delete(Reiziger reiziger);
    public Reiziger findById(int id) throws SQLException;
    public List<Reiziger> findByGbdatum(String datum) throws SQLException;
    public List<Reiziger> findAll() throws SQLException;
}
