package project.dao;

import project.domain.Adres;
import project.domain.Reiziger;

import java.util.List;

public interface AdresDAO {
    // CRUD methods
    List<Adres> findAll();
    Adres findByReiziger(Reiziger reiziger);
    boolean save(Adres adres);
    boolean update(Adres adres);
    boolean delete(Adres adres);
}
