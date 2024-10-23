package project.dao;

import project.domain.Ovchipkaart;
import project.domain.Reiziger;

import java.util.List;

public interface OvchipkaartDAO {
    // CRUD methods
    List<Ovchipkaart> findByReiziger(Reiziger reiziger);

    Ovchipkaart findById(int id);

    boolean save(Ovchipkaart harryOvkaart1);

    boolean update(Ovchipkaart harryOvkaart1);

    boolean delete(Ovchipkaart harryOvkaart1);

}
