package br.unipar.devbackend.oscontrol.Repository;

import br.unipar.devbackend.oscontrol.Entity.Orcamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrcamentoRepository extends JpaRepository<Orcamento, Integer> {

    // O "ContainingIgnoreCase" permite buscar apenas uma parte do nome (ex: "Revisão")
    // e ignora se está em maiúsculo ou minúsculo.
    List<Orcamento> findByNomeOrcamentoContainingIgnoreCase(String nome);
}