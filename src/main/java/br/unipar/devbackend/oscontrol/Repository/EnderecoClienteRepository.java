package br.unipar.devbackend.oscontrol.Repository;

import br.unipar.devbackend.oscontrol.Entity.EnderecoCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoClienteRepository extends JpaRepository<EnderecoCliente, Integer> {
}