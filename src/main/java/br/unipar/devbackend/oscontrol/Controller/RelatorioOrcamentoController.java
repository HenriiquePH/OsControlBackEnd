package br.unipar.devbackend.oscontrol.Controller;

import br.unipar.devbackend.oscontrol.Service.RelatorioOrcamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/relatorio-orcamento")
@CrossOrigin(origins = "http://localhost:4200")
public class RelatorioOrcamentoController {

    @Autowired
    private RelatorioOrcamentoService relatorioOrcamentoService;

    @GetMapping("/pdf/{id}")
    public ResponseEntity<byte[]> gerarPdf(
            @PathVariable Integer id) throws Exception {

        byte[] pdf =
                relatorioOrcamentoService
                        .gerarPdfOrcamento(id);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=orcamento.pdf"
                )
                .body(pdf);
    }
}