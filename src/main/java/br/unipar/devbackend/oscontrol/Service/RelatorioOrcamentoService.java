package br.unipar.devbackend.oscontrol.Service;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

@Service
public class RelatorioOrcamentoService {

    @Autowired
    private DataSource dataSource;

    public byte[] gerarPdfOrcamento(Integer idOrcamento) throws Exception {

        InputStream arquivo = getClass()
                .getResourceAsStream("/jasper/orcamento.jasper");

        Map<String, Object> parametros = new HashMap<>();

        parametros.put("orcamento_id", idOrcamento);

        Connection conexao = dataSource.getConnection();

        JasperPrint jasperPrint = JasperFillManager.fillReport(
                arquivo,
                parametros,
                conexao
        );

        conexao.close();

        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
}