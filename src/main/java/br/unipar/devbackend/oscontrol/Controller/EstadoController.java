package br.unipar.devbackend.oscontrol.Controller;

import br.unipar.devbackend.oscontrol.Entity.Estado;
import br.unipar.devbackend.oscontrol.Service.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estado")
public class EstadoController {

    @Autowired
    private EstadoService service;

    @GetMapping
    public List<Estado> buscarTodos() {
        return service.buscarTodos();
    }

    @GetMapping("/{id}")
    public Estado buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id);
    }
}