package opdracht.dao;

import opdracht.domain.Reiziger;

import java.util.Date;
import java.util.List;

public interface ReizigerDAO {
    // All CRUD operations
    Reiziger findById(int id);
    List<Reiziger> findAll();
    List<Reiziger> findByGbdatum(Date datum);
    boolean save(Reiziger reiziger);
    boolean update(Reiziger reiziger);
    boolean delete(Reiziger reiziger);
}

