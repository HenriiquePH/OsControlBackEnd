package br.unipar.devbackend.oscontrol.Service;

import br.unipar.devbackend.oscontrol.Entity.Cidade;
import br.unipar.devbackend.oscontrol.Entity.Cliente;
import br.unipar.devbackend.oscontrol.Entity.EnderecoCliente;
import br.unipar.devbackend.oscontrol.Entity.Estado;
import br.unipar.devbackend.oscontrol.Entity.Veiculo;
import br.unipar.devbackend.oscontrol.Repository.ClienteRepository;
import br.unipar.devbackend.oscontrol.Repository.OrdemDeServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private OrdemDeServicoRepository ordemDeServicoRepository;

    @Autowired
    private CidadeService cidadeService;

    @Autowired
    private EstadoService estadoService;

    public Cliente cadastrar(Cliente cliente) {

        validarCliente(cliente);

        if (clienteRepository.existsByCpf(cliente.getCpf())) {
            throw new RuntimeException("CPF ja cadastrado.");
        }

        ajustarCidadeDoEndereco(cliente);

        vincularVeiculosAoCliente(cliente);

        return clienteRepository.save(cliente);
    }

    public Cliente editar(Integer id, Cliente clienteAtualizado) {

        Cliente cliente = buscarPorId(id);

        validarCliente(clienteAtualizado);

        if (!cliente.getCpf().equals(clienteAtualizado.getCpf())
                && clienteRepository.existsByCpf(clienteAtualizado.getCpf())) {
            throw new RuntimeException("CPF ja cadastrado.");
        }

        ajustarCidadeDoEndereco(clienteAtualizado);

        cliente.setNome(clienteAtualizado.getNome());
        cliente.setCpf(clienteAtualizado.getCpf());
        cliente.setTelefone(clienteAtualizado.getTelefone());
        cliente.setEmail(clienteAtualizado.getEmail());
        cliente.setEndereco(clienteAtualizado.getEndereco());
        cliente.setVeiculos(clienteAtualizado.getVeiculos());

        vincularVeiculosAoCliente(cliente);

        return clienteRepository.save(cliente);
    }

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    public Cliente buscarPorId(Integer id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente nao encontrado."));
    }

    public void excluir(Integer id) {

        Cliente cliente = buscarPorId(id);

        if (ordemDeServicoRepository.existsByClienteId(id)) {
            throw new RuntimeException("Cliente nao pode ser excluido porque possui ordem de servico.");
        }

        clienteRepository.delete(cliente);
    }

    private void validarCliente(Cliente cliente) {

        if (cliente.getNome() == null || cliente.getNome().isBlank()) {
            throw new RuntimeException("Nome do cliente e obrigatorio.");
        }

        if (cliente.getCpf() == null || cliente.getCpf().isBlank()) {
            throw new RuntimeException("CPF do cliente e obrigatorio.");
        }

        if (cliente.getTelefone() == null || cliente.getTelefone().isBlank()) {
            throw new RuntimeException("Telefone do cliente e obrigatorio.");
        }
    }

    private void ajustarCidadeDoEndereco(Cliente cliente) {

        EnderecoCliente endereco = cliente.getEndereco();

        if (endereco == null || endereco.getCidade() == null) {
            return;
        }

        Cidade cidadeInformada = endereco.getCidade();

        if (cidadeInformada.getId() != null) {
            endereco.setCidade(cidadeService.buscarPorId(cidadeInformada.getId()));
            return;
        }

        if (cidadeInformada.getNome() == null || cidadeInformada.getNome().isBlank()) {
            endereco.setCidade(null);
            return;
        }

        Estado estadoResolvido = resolverEstado(cidadeInformada.getEstado());
        Cidade cidadeResolvida = resolverCidade(cidadeInformada, estadoResolvido);
        endereco.setCidade(cidadeResolvida);
    }

    private Estado resolverEstado(Estado estadoInformado) {

        if (estadoInformado == null) {
            return null;
        }

        if (estadoInformado.getId() != null) {
            return estadoService.buscarPorId(estadoInformado.getId());
        }

        String uf = limparTexto(estadoInformado.getUf()).toUpperCase(Locale.ROOT);
        if (!uf.isBlank()) {
            Estado estadoPorUf = estadoService.buscarPorUf(uf).orElse(null);

            if (estadoPorUf != null) {
                return estadoPorUf;
            }

            Estado novoEstado = new Estado();
            novoEstado.setUf(uf);
            novoEstado.setNome(uf);
            return estadoService.salvar(novoEstado);
        }

        String nome = limparTexto(estadoInformado.getNome());
        if (nome.isBlank()) {
            return null;
        }

        Estado estadoPorNome = estadoService.buscarPorNome(nome).orElse(null);

        if (estadoPorNome != null) {
            return estadoPorNome;
        }

        Estado novoEstado = new Estado();
        novoEstado.setNome(nome);
        return estadoService.salvar(novoEstado);
    }

    private Cidade resolverCidade(Cidade cidadeInformada, Estado estado) {
        String nomeCidade = limparTexto(cidadeInformada.getNome());

        if (estado != null && estado.getId() != null) {
            return cidadeService.buscarPorNomeEEstado(nomeCidade, estado.getId())
                    .orElseGet(() -> salvarNovaCidade(nomeCidade, estado));
        }

        return salvarNovaCidade(nomeCidade, estado);
    }

    private Cidade salvarNovaCidade(String nomeCidade, Estado estado) {
        Cidade cidade = new Cidade();
        cidade.setNome(nomeCidade);
        cidade.setEstado(estado);
        return cidadeService.salvar(cidade);
    }

    private void vincularVeiculosAoCliente(Cliente cliente) {

        if (cliente.getVeiculos() != null) {
            for (Veiculo veiculo : cliente.getVeiculos()) {
                veiculo.setCliente(cliente);
            }
        }
    }

    private String limparTexto(String valor) {
        return valor == null ? "" : valor.trim();
    }
}
