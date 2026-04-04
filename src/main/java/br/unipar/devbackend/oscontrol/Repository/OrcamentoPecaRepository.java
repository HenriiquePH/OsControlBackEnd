package br.unipar.devbackend.oscontrol.Repository;

import br.unipar.devbackend.oscontrol.Entity.OrcamentoPeca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrcamentoPecaRepository extends JpaRepository<OrcamentoPeca, Integer> {
}