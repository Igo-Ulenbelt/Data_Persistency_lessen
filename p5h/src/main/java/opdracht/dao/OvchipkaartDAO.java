package opdracht.dao;

import opdracht.domain.Ovchipkaart;
import opdracht.domain.Product;
import opdracht.domain.Reiziger;

import java.util.List;

public interface OvchipkaartDAO {
    // CRUD methods
    List<Ovchipkaart> findByReiziger(Reiziger reiziger);
    Ovchipkaart findById(int id);

    List<Ovchipkaart> findAll();
}

