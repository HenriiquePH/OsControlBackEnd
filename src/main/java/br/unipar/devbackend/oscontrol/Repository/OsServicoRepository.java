package br.unipar.devbackend.oscontrol.Repository;

import br.unipar.devbackend.oscontrol.Entity.OsServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OsServicoRepository extends JpaRepository<OsServico, Integer> {

    boolean existsByServicoId(Integer servicoId);

    List<OsServico> findByOsId(Integer osId);

    void deleteByOsId(Integer osId);
}