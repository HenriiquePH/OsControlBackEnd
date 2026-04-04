package br.unipar.devbackend.oscontrol.Repository;

import br.unipar.devbackend.oscontrol.Entity.OrcamentoServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrcamentoServicoRepository extends JpaRepository<OrcamentoServico, Integer> {
}