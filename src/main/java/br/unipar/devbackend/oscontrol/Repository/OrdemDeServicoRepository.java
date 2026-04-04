package br.unipar.devbackend.oscontrol.Repository;

import br.unipar.devbackend.oscontrol.Entity.OrdemDeServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdemDeServicoRepository extends JpaRepository<OrdemDeServico, Integer> {
    boolean existsByClienteId(Integer clienteId);
}