package br.unipar.devbackend.oscontrol.Controller;

import br.unipar.devbackend.oscontrol.Entity.Cidade;
import br.unipar.devbackend.oscontrol.Service.CidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cidade")
public class CidadeController {

    @Autowired
    private CidadeService service;

    @GetMapping
    public List<Cidade> buscarTodos() {
        return service.buscarTodos();
    }

    @GetMapping("/{id}")
    public Cidade buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id);
    }
}