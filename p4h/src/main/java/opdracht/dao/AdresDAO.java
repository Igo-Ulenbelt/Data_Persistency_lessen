package opdracht.dao;

import opdracht.domain.Reiziger;
import project.domain.Adres;

import java.util.Date;
import java.util.List;

public interface AdresDAO {
    // All CRUD operations
    List<Adres> findAll();
    Adres findByReiziger(Reiziger reiziger);
    boolean save(Adres adres);
    boolean update(Adres adres);
    boolean delete(Adres adres);
}
