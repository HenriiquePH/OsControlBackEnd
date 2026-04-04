package br.unipar.devbackend.oscontrol.Controller;

import br.unipar.devbackend.oscontrol.DTO.OrdemDeServicoDTO;
import br.unipar.devbackend.oscontrol.Service.OrdemDeServicoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/os")
public class OrdemDeServicoController {

    private final OrdemDeServicoService service;

    public OrdemDeServicoController(OrdemDeServicoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<OrdemDeServicoDTO> salvar(@RequestBody OrdemDeServicoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrdemDeServicoDTO> editar(@PathVariable Integer id,
                                                    @RequestBody OrdemDeServicoDTO dto) {
        return ResponseEntity.ok(service.editar(id, dto));
    }

    @GetMapping
    public ResponseEntity<List<OrdemDeServicoDTO>> buscarTodas() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdemDeServicoDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.buscarDetalhePorId(id));
    }

    @PutMapping("/{id}/em-andamento")
    public ResponseEntity<OrdemDeServicoDTO> colocarEmAndamento(@PathVariable Integer id) {
        return ResponseEntity.ok(service.colocarEmAndamento(id));
    }

    @PutMapping("/{id}/fechar")
    public ResponseEntity<OrdemDeServicoDTO> fecharOrdem(@PathVariable Integer id) {
        return ResponseEntity.ok(service.fecharOrdem(id));
    }

    @GetMapping("/importar-orcamento/{orcamentoId}")
    public ResponseEntity<OrdemDeServicoDTO> importarOrcamento(@PathVariable Integer orcamentoId) {
        return ResponseEntity.ok(service.prepararOSApartirDeOrcamento(orcamentoId));
    }
}