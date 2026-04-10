package br.unipar.devbackend.oscontrol.Service;

import br.unipar.devbackend.oscontrol.Entity.Cidade;
import br.unipar.devbackend.oscontrol.Entity.Cliente;
import br.unipar.devbackend.oscontrol.Entity.Veiculo;
import br.unipar.devbackend.oscontrol.Repository.ClienteRepository;
import br.unipar.devbackend.oscontrol.Repository.OrdemDeServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private OrdemDeServicoRepository ordemDeServicoRepository;

    @Autowired
    private CidadeService cidadeService;

    public Cliente cadastrar(Cliente cliente) {

        validarCliente(cliente);

        if (clienteRepository.existsByCpf(cliente.getCpf())) {
            throw new RuntimeException("CPF já cadastrado.");
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
            throw new RuntimeException("CPF já cadastrado.");
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
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado."));
    }

    public void excluir(Integer id) {

        Cliente cliente = buscarPorId(id);

        if (ordemDeServicoRepository.existsByClienteId(id)) {
            throw new RuntimeException("Cliente não pode ser excluído porque possui ordem de serviço.");
        }

        clienteRepository.delete(cliente);
    }

    private void validarCliente(Cliente cliente) {

        if (cliente.getNome() == null || cliente.getNome().isBlank()) {
            throw new RuntimeException("Nome do cliente é obrigatório.");
        }

        if (cliente.getCpf() == null || cliente.getCpf().isBlank()) {
            throw new RuntimeException("CPF do cliente é obrigatório.");
        }

        if (cliente.getTelefone() == null || cliente.getTelefone().isBlank()) {
            throw new RuntimeException("Telefone do cliente é obrigatório.");
        }
    }

    private void ajustarCidadeDoEndereco(Cliente cliente) {

        if (cliente.getEndereco() != null &&
                cliente.getEndereco().getCidade() != null &&
                cliente.getEndereco().getCidade().getId() != null) {

            Integer idCidade = cliente.getEndereco().getCidade().getId();

            Cidade cidade = cidadeService.buscarPorId(idCidade);

            cliente.getEndereco().setCidade(cidade);
        }
    }

    // Faz cada veículo apontar para o cliente
    private void vincularVeiculosAoCliente(Cliente cliente) {

        if (cliente.getVeiculos() != null) {
            for (Veiculo veiculo : cliente.getVeiculos()) {
                veiculo.setCliente(cliente);
            }
        }
    }
}