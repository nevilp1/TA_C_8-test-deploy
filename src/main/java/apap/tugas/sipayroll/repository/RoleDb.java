package apap.tugas.sipayroll.repository;

import apap.tugas.sipayroll.model.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleDb extends JpaRepository<RoleModel, Integer> {
    List<RoleModel> findAll();
    Optional<RoleModel> findByNama(String nama);
    Optional<RoleModel> findById(Integer id);
}