package br.unipar.devbackend.oscontrol.Service;

import br.unipar.devbackend.oscontrol.Entity.EnderecoCliente;
import br.unipar.devbackend.oscontrol.Repository.EnderecoClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnderecoClienteService {
    @Autowired
    private EnderecoClienteRepository repository;

    public EnderecoCliente salvar(EnderecoCliente endereco) {
        return repository.save(endereco);
    }
}
