package br.unipar.devbackend.oscontrol.Repository;

import br.unipar.devbackend.oscontrol.Entity.OsPeca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OsPecaRepository extends JpaRepository<OsPeca, Integer> {

    boolean existsByPecaId(Integer pecaId);

    List<OsPeca> findByOsId(Integer osId);

    void deleteByOsId(Integer osId);
}