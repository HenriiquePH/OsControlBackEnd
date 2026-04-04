package br.unipar.devbackend.oscontrol.Controller;

import br.unipar.devbackend.oscontrol.Entity.Servico;
import br.unipar.devbackend.oscontrol.Service.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servico")
public class ServicoController {

    @Autowired
    private ServicoService service;

    @GetMapping
    public List<Servico> listarTodos() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public Servico buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    public Servico cadastrar(@RequestBody Servico servico) {
        return service.cadastrarServico(servico);
    }

    @PutMapping("/{id}")
    public Servico editar(@PathVariable Integer id, @RequestBody Servico servico) {
        servico.setId(id);
        return service.editarServico(servico);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
        service.excluirServico(id);
    }
}