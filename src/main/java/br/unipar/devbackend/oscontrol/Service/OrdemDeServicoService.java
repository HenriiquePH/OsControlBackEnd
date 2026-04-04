package br.unipar.devbackend.oscontrol.Service;

import br.unipar.devbackend.oscontrol.DTO.OrdemDeServicoDTO;
import br.unipar.devbackend.oscontrol.DTO.OsPecaDTO;
import br.unipar.devbackend.oscontrol.DTO.OsServicoDTO;
import br.unipar.devbackend.oscontrol.Entity.Cliente;
import br.unipar.devbackend.oscontrol.Entity.Orcamento;
import br.unipar.devbackend.oscontrol.Entity.OrcamentoPeca;
import br.unipar.devbackend.oscontrol.Entity.OrcamentoServico;
import br.unipar.devbackend.oscontrol.Entity.OrdemDeServico;
import br.unipar.devbackend.oscontrol.Entity.OsPeca;
import br.unipar.devbackend.oscontrol.Entity.OsServico;
import br.unipar.devbackend.oscontrol.Entity.Peca;
import br.unipar.devbackend.oscontrol.Entity.Servico;
import br.unipar.devbackend.oscontrol.Entity.StatusOsEnum;
import br.unipar.devbackend.oscontrol.Entity.Usuario;
import br.unipar.devbackend.oscontrol.Entity.Veiculo;
import br.unipar.devbackend.oscontrol.Repository.ClienteRepository;
import br.unipar.devbackend.oscontrol.Repository.OrdemDeServicoRepository;
import br.unipar.devbackend.oscontrol.Repository.OsPecaRepository;
import br.unipar.devbackend.oscontrol.Repository.OsServicoRepository;
import br.unipar.devbackend.oscontrol.Repository.PecaRepository;
import br.unipar.devbackend.oscontrol.Repository.ServicoRepository;
import br.unipar.devbackend.oscontrol.Repository.UsuarioRepository;
import br.unipar.devbackend.oscontrol.Repository.VeiculoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrdemDeServicoService {

    private final OrdemDeServicoRepository ordemDeServicoRepository;
    private final OsPecaRepository osPecaRepository;
    private final OsServicoRepository osServicoRepository;
    private final OrcamentoService orcamentoService;
    private final ClienteRepository clienteRepository;
    private final VeiculoRepository veiculoRepository;
    private final UsuarioRepository usuarioRepository;
    private final PecaRepository pecaRepository;
    private final ServicoRepository servicoRepository;

    public OrdemDeServicoService(OrdemDeServicoRepository ordemDeServicoRepository,
                                 OsPecaRepository osPecaRepository,
                                 OsServicoRepository osServicoRepository,
                                 OrcamentoService orcamentoService,
                                 ClienteRepository clienteRepository,
                                 VeiculoRepository veiculoRepository,
                                 UsuarioRepository usuarioRepository,
                                 PecaRepository pecaRepository,
                                 ServicoRepository servicoRepository) {
        this.ordemDeServicoRepository = ordemDeServicoRepository;
        this.osPecaRepository = osPecaRepository;
        this.osServicoRepository = osServicoRepository;
        this.orcamentoService = orcamentoService;
        this.clienteRepository = clienteRepository;
        this.veiculoRepository = veiculoRepository;
        this.usuarioRepository = usuarioRepository;
        this.pecaRepository = pecaRepository;
        this.servicoRepository = servicoRepository;
    }

    public OrdemDeServicoDTO prepararOSApartirDeOrcamento(Integer idOrcamento) {
        Orcamento orcamento = orcamentoService.buscarPorId(idOrcamento);

        OrdemDeServicoDTO dto = new OrdemDeServicoDTO();
        dto.setObservacoes(orcamento.getObservacao());
        dto.setValorTotalPecas(orcamento.getValorTotalPecas());
        dto.setValorTotalServico(orcamento.getValorTotalServico());
        dto.setValorTotal(orcamento.getValorTotal());

        List<OsPecaDTO> listaPecas = new ArrayList<>();
        if (orcamento.getItensPecas() != null) {
            for (OrcamentoPeca itemPeca : orcamento.getItensPecas()) {
                OsPecaDTO pecaDTO = new OsPecaDTO();
                pecaDTO.setPecaId(itemPeca.getPeca() != null ? itemPeca.getPeca().getId() : null);
                pecaDTO.setDescricaoPeca(itemPeca.getPeca() != null ? itemPeca.getPeca().getDescricao() : null);
                pecaDTO.setQuantidade(itemPeca.getQuantidade());
                pecaDTO.setValorUnitario(itemPeca.getValorUnitario());
                pecaDTO.setValorTotal(itemPeca.getValorTotal());
                listaPecas.add(pecaDTO);
            }
        }

        List<OsServicoDTO> listaServicos = new ArrayList<>();
        if (orcamento.getItensServicos() != null) {
            for (OrcamentoServico itemServico : orcamento.getItensServicos()) {
                OsServicoDTO servicoDTO = new OsServicoDTO();
                servicoDTO.setServicoId(itemServico.getServico() != null ? itemServico.getServico().getId() : null);
                servicoDTO.setDescricaoServico(itemServico.getServico() != null ? itemServico.getServico().getDescricao() : null);
                servicoDTO.setQuantidade(itemServico.getQuantidade());
                servicoDTO.setValorUnitario(itemServico.getValorUnitario());
                servicoDTO.setValorTotal(itemServico.getValorTotal());
                listaServicos.add(servicoDTO);
            }
        }

        dto.setPecas(listaPecas);
        dto.setServicos(listaServicos);

        return dto;
    }

    @Transactional
    public OrdemDeServicoDTO salvar(OrdemDeServicoDTO dto) {
        validarDtoDeEntrada(dto);

        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado."));
        Veiculo veiculo = veiculoRepository.findById(dto.getVeiculoId())
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado."));
        Usuario tecnico = usuarioRepository.findById(dto.getTecnicoResponsavelId())
                .orElseThrow(() -> new RuntimeException("Técnico responsável não encontrado."));

        validarClienteEVeiculo(cliente, veiculo);

        OrdemDeServico novaOs = new OrdemDeServico();
        novaOs.setObservacoes(dto.getObservacoes());
        novaOs.setCliente(cliente);
        novaOs.setVeiculo(veiculo);
        novaOs.setTecnicoResponsavel(tecnico);
        novaOs.criarOs();

        OrdemDeServico osSalva = ordemDeServicoRepository.save(novaOs);

        double totalPecas = salvarPecasDaOs(osSalva, dto.getPecas());
        double totalServicos = salvarServicosDaOs(osSalva, dto.getServicos());

        osSalva.setValorTotalPecas(totalPecas);
        osSalva.setValorTotalServico(totalServicos);
        osSalva.calcularValorTotal();

        OrdemDeServico osAtualizada = ordemDeServicoRepository.save(osSalva);
        return buscarDetalhePorId(osAtualizada.getId());
    }

    @Transactional
    public OrdemDeServicoDTO editar(Integer id, OrdemDeServicoDTO dto) {
        validarDtoDeEntrada(dto);

        OrdemDeServico os = buscarPorId(id);

        if (os.getStatusOs() == StatusOsEnum.FECHADA) {
            throw new RuntimeException("Não é possível editar uma OS fechada.");
        }

        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado."));
        Veiculo veiculo = veiculoRepository.findById(dto.getVeiculoId())
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado."));
        Usuario tecnico = usuarioRepository.findById(dto.getTecnicoResponsavelId())
                .orElseThrow(() -> new RuntimeException("Técnico responsável não encontrado."));

        validarClienteEVeiculo(cliente, veiculo);

        os.setObservacoes(dto.getObservacoes());
        os.setCliente(cliente);
        os.setVeiculo(veiculo);
        os.setTecnicoResponsavel(tecnico);

        double totalPecas = sincronizarPecasDaOs(os, dto.getPecas());
        double totalServicos = sincronizarServicosDaOs(os, dto.getServicos());

        os.setValorTotalPecas(totalPecas);
        os.setValorTotalServico(totalServicos);
        os.calcularValorTotal();

        ordemDeServicoRepository.save(os);

        return buscarDetalhePorId(os.getId());
    }

    public List<OrdemDeServicoDTO> listarTodas() {
        List<OrdemDeServico> ordens = ordemDeServicoRepository.findAll();
        List<OrdemDeServicoDTO> lista = new ArrayList<>();

        for (OrdemDeServico os : ordens) {
            lista.add(toResumoDTO(os));
        }

        return lista;
    }

    public OrdemDeServicoDTO buscarDetalhePorId(Integer id) {
        OrdemDeServico os = buscarPorId(id);

        List<OsPeca> pecasEntity = osPecaRepository.findByOsId(id);
        List<OsServico> servicosEntity = osServicoRepository.findByOsId(id);

        return toDetalheDTO(os, pecasEntity, servicosEntity);
    }

    public OrdemDeServicoDTO colocarEmAndamento(Integer id) {
        OrdemDeServico os = buscarPorId(id);

        os.setStatusOs(StatusOsEnum.EM_ANDAMENTO);

        if (os.getDataFechamento() != null) {
            os.setDataFechamento(null);
        }

        ordemDeServicoRepository.save(os);

        return buscarDetalhePorId(id);
    }

    public OrdemDeServicoDTO fecharOrdem(Integer id) {
        OrdemDeServico os = buscarPorId(id);

        if (os.getStatusOs() == StatusOsEnum.FECHADA) {
            throw new RuntimeException("Esta Ordem de Serviço já está fechada.");
        }

        os.fecharOS();
        ordemDeServicoRepository.save(os);

        return buscarDetalhePorId(id);
    }

    public OrdemDeServico buscarPorId(Integer id) {
        return ordemDeServicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ordem de Serviço não encontrada."));
    }

    private void validarClienteEVeiculo(Cliente cliente, Veiculo veiculo) {
        if (veiculo.getCliente() != null
                && veiculo.getCliente().getId() != null
                && !veiculo.getCliente().getId().equals(cliente.getId())) {
            throw new RuntimeException("O veículo informado não pertence ao cliente selecionado.");
        }
    }

    private double salvarPecasDaOs(OrdemDeServico os, List<OsPecaDTO> pecasDto) {
        double totalPecas = 0.0;

        if (pecasDto == null) {
            return totalPecas;
        }

        for (OsPecaDTO itemDto : pecasDto) {
            if (itemDto.getPecaId() == null) {
                throw new RuntimeException("Peça sem id informado.");
            }
            if (itemDto.getQuantidade() == null || itemDto.getQuantidade() <= 0) {
                throw new RuntimeException("Quantidade da peça deve ser maior que zero.");
            }

            Peca peca = pecaRepository.findById(itemDto.getPecaId())
                    .orElseThrow(() -> new RuntimeException("Peça não encontrada."));

            OsPeca item = new OsPeca();
            item.setOs(os);
            item.setPeca(peca);
            item.setQuantidade(itemDto.getQuantidade());

            Double valorUnitario = itemDto.getValorUnitario() != null
                    ? itemDto.getValorUnitario()
                    : peca.getValorUnitario();

            if (valorUnitario == null || valorUnitario < 0) {
                throw new RuntimeException("Valor unitário da peça inválido.");
            }

            item.setValorUnitario(valorUnitario);
            item.calcularSubtotal();

            osPecaRepository.save(item);
            totalPecas += item.getValorTotal();
        }

        return totalPecas;
    }

    private double salvarServicosDaOs(OrdemDeServico os, List<OsServicoDTO> servicosDto) {
        double totalServicos = 0.0;

        if (servicosDto == null) {
            return totalServicos;
        }

        for (OsServicoDTO itemDto : servicosDto) {
            if (itemDto.getServicoId() == null) {
                throw new RuntimeException("Serviço sem id informado.");
            }
            if (itemDto.getQuantidade() == null || itemDto.getQuantidade() <= 0) {
                throw new RuntimeException("Quantidade do serviço deve ser maior que zero.");
            }

            Servico servico = servicoRepository.findById(itemDto.getServicoId())
                    .orElseThrow(() -> new RuntimeException("Serviço não encontrado."));

            OsServico item = new OsServico();
            item.setOs(os);
            item.setServico(servico);
            item.setQuantidade(itemDto.getQuantidade());

            Double valorUnitario = itemDto.getValorUnitario() != null
                    ? itemDto.getValorUnitario()
                    : servico.getValor();

            if (valorUnitario == null || valorUnitario < 0) {
                throw new RuntimeException("Valor unitário do serviço inválido.");
            }

            item.setValorUnitario(valorUnitario);
            item.calcularSubtotal();

            osServicoRepository.save(item);
            totalServicos += item.getValorTotal();
        }

        return totalServicos;
    }

    private double sincronizarPecasDaOs(OrdemDeServico os, List<OsPecaDTO> pecasDto) {
        List<OsPeca> itensAtuais = osPecaRepository.findByOsId(os.getId());
        List<Integer> idsRecebidos = new ArrayList<>();
        double totalPecas = 0.0;

        if (pecasDto != null) {
            for (OsPecaDTO itemDto : pecasDto) {
                if (itemDto.getPecaId() == null) {
                    throw new RuntimeException("Peça sem id informado.");
                }
                if (itemDto.getQuantidade() == null || itemDto.getQuantidade() <= 0) {
                    throw new RuntimeException("Quantidade da peça deve ser maior que zero.");
                }

                Peca peca = pecaRepository.findById(itemDto.getPecaId())
                        .orElseThrow(() -> new RuntimeException("Peça não encontrada."));

                OsPeca item;

                if (itemDto.getId() != null) {
                    item = osPecaRepository.findById(itemDto.getId())
                            .orElseThrow(() -> new RuntimeException("Item de peça não encontrado."));

                    if (item.getOs() == null || !item.getOs().getId().equals(os.getId())) {
                        throw new RuntimeException("O item de peça informado não pertence a esta OS.");
                    }

                    idsRecebidos.add(item.getId());
                } else {
                    item = new OsPeca();
                    item.setOs(os);
                }

                item.setPeca(peca);
                item.setQuantidade(itemDto.getQuantidade());

                Double valorUnitario = itemDto.getValorUnitario() != null
                        ? itemDto.getValorUnitario()
                        : peca.getValorUnitario();

                if (valorUnitario == null || valorUnitario < 0) {
                    throw new RuntimeException("Valor unitário da peça inválido.");
                }

                item.setValorUnitario(valorUnitario);
                item.calcularSubtotal();

                OsPeca salvo = osPecaRepository.save(item);

                if (salvo.getId() != null && !idsRecebidos.contains(salvo.getId())) {
                    idsRecebidos.add(salvo.getId());
                }

                totalPecas += salvo.getValorTotal();
            }
        }

        for (OsPeca itemAtual : itensAtuais) {
            if (!idsRecebidos.contains(itemAtual.getId())) {
                osPecaRepository.delete(itemAtual);
            }
        }

        return totalPecas;
    }

    private double sincronizarServicosDaOs(OrdemDeServico os, List<OsServicoDTO> servicosDto) {
        List<OsServico> itensAtuais = osServicoRepository.findByOsId(os.getId());
        List<Integer> idsRecebidos = new ArrayList<>();
        double totalServicos = 0.0;

        if (servicosDto != null) {
            for (OsServicoDTO itemDto : servicosDto) {
                if (itemDto.getServicoId() == null) {
                    throw new RuntimeException("Serviço sem id informado.");
                }
                if (itemDto.getQuantidade() == null || itemDto.getQuantidade() <= 0) {
                    throw new RuntimeException("Quantidade do serviço deve ser maior que zero.");
                }

                Servico servico = servicoRepository.findById(itemDto.getServicoId())
                        .orElseThrow(() -> new RuntimeException("Serviço não encontrado."));

                OsServico item;

                if (itemDto.getId() != null) {
                    item = osServicoRepository.findById(itemDto.getId())
                            .orElseThrow(() -> new RuntimeException("Item de serviço não encontrado."));

                    if (item.getOs() == null || !item.getOs().getId().equals(os.getId())) {
                        throw new RuntimeException("O item de serviço informado não pertence a esta OS.");
                    }

                    idsRecebidos.add(item.getId());
                } else {
                    item = new OsServico();
                    item.setOs(os);
                }

                item.setServico(servico);
                item.setQuantidade(itemDto.getQuantidade());

                Double valorUnitario = itemDto.getValorUnitario() != null
                        ? itemDto.getValorUnitario()
                        : servico.getValor();

                if (valorUnitario == null || valorUnitario < 0) {
                    throw new RuntimeException("Valor unitário do serviço inválido.");
                }

                item.setValorUnitario(valorUnitario);
                item.calcularSubtotal();

                OsServico salvo = osServicoRepository.save(item);

                if (salvo.getId() != null && !idsRecebidos.contains(salvo.getId())) {
                    idsRecebidos.add(salvo.getId());
                }

                totalServicos += salvo.getValorTotal();
            }
        }

        for (OsServico itemAtual : itensAtuais) {
            if (!idsRecebidos.contains(itemAtual.getId())) {
                osServicoRepository.delete(itemAtual);
            }
        }

        return totalServicos;
    }

    private void validarDtoDeEntrada(OrdemDeServicoDTO dto) {
        if (dto == null) {
            throw new RuntimeException("Os dados da ordem de serviço não foram informados.");
        }

        if (dto.getClienteId() == null) {
            throw new RuntimeException("Cliente é obrigatório.");
        }

        if (dto.getVeiculoId() == null) {
            throw new RuntimeException("Veículo é obrigatório.");
        }

        if (dto.getTecnicoResponsavelId() == null) {
            throw new RuntimeException("Técnico responsável é obrigatório.");
        }

        boolean semPecas = dto.getPecas() == null || dto.getPecas().isEmpty();
        boolean semServicos = dto.getServicos() == null || dto.getServicos().isEmpty();

        if (semPecas && semServicos) {
            throw new RuntimeException("A OS deve ter pelo menos uma peça ou um serviço.");
        }
    }

    private OrdemDeServicoDTO toResumoDTO(OrdemDeServico os) {
        OrdemDeServicoDTO dto = new OrdemDeServicoDTO();

        dto.setId(os.getId());
        dto.setDataAbertura(os.getDataAbertura());
        dto.setDataFechamento(os.getDataFechamento());
        dto.setStatusOs(os.getStatusOs() != null ? os.getStatusOs().name() : null);
        dto.setObservacoes(os.getObservacoes());
        dto.setValorTotalPecas(os.getValorTotalPecas());
        dto.setValorTotalServico(os.getValorTotalServico());
        dto.setValorTotal(os.getValorTotal());

        if (os.getCliente() != null) {
            dto.setClienteId(os.getCliente().getId());
            dto.setClienteNome(os.getCliente().getNome());
        }

        if (os.getVeiculo() != null) {
            dto.setVeiculoId(os.getVeiculo().getId());
            dto.setVeiculoPlaca(os.getVeiculo().getPlaca());
            dto.setVeiculoModelo(os.getVeiculo().getModelo());
        }

        if (os.getTecnicoResponsavel() != null) {
            dto.setTecnicoResponsavelId(os.getTecnicoResponsavel().getId());
            dto.setTecnicoResponsavelNome(os.getTecnicoResponsavel().getNome());
        }

        return dto;
    }

    private OrdemDeServicoDTO toDetalheDTO(OrdemDeServico os,
                                           List<OsPeca> pecas,
                                           List<OsServico> servicos) {
        OrdemDeServicoDTO dto = toResumoDTO(os);

        List<OsPecaDTO> pecasDto = new ArrayList<>();
        for (OsPeca item : pecas) {
            OsPecaDTO itemDto = new OsPecaDTO();
            itemDto.setId(item.getId());
            itemDto.setPecaId(item.getPeca() != null ? item.getPeca().getId() : null);
            itemDto.setDescricaoPeca(item.getPeca() != null ? item.getPeca().getDescricao() : null);
            itemDto.setQuantidade(item.getQuantidade());
            itemDto.setValorUnitario(item.getValorUnitario());
            itemDto.setValorTotal(item.getValorTotal());
            pecasDto.add(itemDto);
        }

        List<OsServicoDTO> servicosDto = new ArrayList<>();
        for (OsServico item : servicos) {
            OsServicoDTO itemDto = new OsServicoDTO();
            itemDto.setId(item.getId());
            itemDto.setServicoId(item.getServico() != null ? item.getServico().getId() : null);
            itemDto.setDescricaoServico(item.getServico() != null ? item.getServico().getDescricao() : null);
            itemDto.setQuantidade(item.getQuantidade());
            itemDto.setValorUnitario(item.getValorUnitario());
            itemDto.setValorTotal(item.getValorTotal());
            servicosDto.add(itemDto);
        }

        dto.setPecas(pecasDto);
        dto.setServicos(servicosDto);

        return dto;
    }
}