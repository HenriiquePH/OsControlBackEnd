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

    // Cadastra um novo cliente
    public Cliente cadastrar(Cliente cliente) {

        // Valida os campos obrigatórios
        validarCliente(cliente);

        // Verifica se já existe cliente com o mesmo CPF
        if (clienteRepository.existsByCpf(cliente.getCpf())) {
            throw new RuntimeException("CPF já cadastrado.");
        }

        // Busca a cidade real no banco antes de salvar
        ajustarCidadeDoEndereco(cliente);

        // Vincula os veículos ao cliente antes de salvar
        vincularVeiculosAoCliente(cliente);

        // Salva no banco
        return clienteRepository.save(cliente);
    }

    // Edita um cliente já existente
    public Cliente editar(Integer id, Cliente clienteAtualizado) {

        // Busca o cliente atual
        Cliente cliente = buscarPorId(id);

        // Valida os novos dados
        validarCliente(clienteAtualizado);

        // Verifica se o CPF foi alterado e se já existe outro com esse CPF
        if (!cliente.getCpf().equals(clienteAtualizado.getCpf())
                && clienteRepository.existsByCpf(clienteAtualizado.getCpf())) {
            throw new RuntimeException("CPF já cadastrado.");
        }

        // Busca a cidade real no banco antes de atualizar
        ajustarCidadeDoEndereco(clienteAtualizado);

        // Atualiza os campos principais
        cliente.setNome(clienteAtualizado.getNome());
        cliente.setCpf(clienteAtualizado.getCpf());
        cliente.setTelefone(clienteAtualizado.getTelefone());
        cliente.setEmail(clienteAtualizado.getEmail());
        cliente.setEndereco(clienteAtualizado.getEndereco());
        cliente.setVeiculos(clienteAtualizado.getVeiculos());

        // Garante que os veículos atualizados fiquem vinculados ao cliente
        vincularVeiculosAoCliente(cliente);

        // Salva as alterações
        return clienteRepository.save(cliente);
    }

    // Lista todos os clientes
    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    // Busca um cliente pelo ID
    public Cliente buscarPorId(Integer id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado."));
    }

    // Exclui um cliente
    public void excluir(Integer id) {

        // Busca o cliente primeiro
        Cliente cliente = buscarPorId(id);

        // Verifica se existe OS vinculada a ele
        if (ordemDeServicoRepository.existsByClienteId(id)) {
            throw new RuntimeException("Cliente não pode ser excluído porque possui ordem de serviço.");
        }

        // Exclui do banco
        clienteRepository.delete(cliente);
    }

    // Método privado para validar os campos obrigatórios
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

    // Busca a cidade real no banco e coloca no endereço
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