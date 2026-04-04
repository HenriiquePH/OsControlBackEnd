package br.unipar.devbackend.oscontrol.Controller;

import br.unipar.devbackend.oscontrol.Entity.Peca;
import br.unipar.devbackend.oscontrol.Service.PecaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/peca")
public class PecaController {

    @Autowired
    private PecaService service;

    @GetMapping
    public ResponseEntity<List<Peca>> listarTodas() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Peca> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Peca> cadastrar(@RequestBody Peca peca) {
        return ResponseEntity.ok(service.cadastrarPeca(peca));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Peca> atualizar(@PathVariable Integer id,
                                          @RequestBody Peca peca) {
        return ResponseEntity.ok(service.atualizar(id, peca));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        service.excluirPeca(id);
        return ResponseEntity.noContent().build();
    }
}