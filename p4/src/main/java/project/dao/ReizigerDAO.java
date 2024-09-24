package project.dao;

import project.domain.Reiziger;

import java.util.List;

public interface ReizigerDAO {
    // CRUD methods
    Reiziger findById(int id);
    List<Reiziger> findAll();
    boolean save(Reiziger reiziger);
    boolean update(Reiziger reiziger);
    boolean delete(Reiziger reiziger);
}

