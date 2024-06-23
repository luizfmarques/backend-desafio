package com.example.demo.services;

import com.example.demo.constants.TipoCliente;
import com.example.demo.entities.Pedido;
import com.example.demo.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;

    public List<Pedido> listarPedidos() {
        return pedidoRepository.findAll();
    }

    public Pedido criarPedido(Pedido pedido) {
        return salvar(pedido);
    }

    public Pedido atualizarPedido(Long id, Pedido pedidoAtualizado) {
        if (pedidoRepository.existsById(id)) {
            pedidoAtualizado.setId(id);
            return salvar(pedidoAtualizado);
        }
        throw new RuntimeException(String.format("Nenhum pedido encontrado para o Id %d", id));
    }

    public void deletarPedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        pedidoRepository.delete(pedido);
    }

    public Pedido buscarPedidoPorId(Long id) {
        Optional<Pedido> pedido = pedidoRepository.findById(id);
        return pedido.orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
    }

    public Pedido calcularImposto(Pedido pedido) {
        TipoCliente tipoCliente = pedido.getTipo();
        double valorItens = pedido.getItens().stream().mapToDouble(m -> m.getValor() * m.getQuantidade()).sum();
        double imposto = 0.0;

        if (TipoCliente.CPF.equals(tipoCliente)) {
            imposto = valorItens * 0.052;
        } else if ((TipoCliente.CNPJ.equals(tipoCliente))) {
            imposto = 1 + (valorItens * 0.032);
        }
        pedido.setImposto(imposto);
        pedido.setValorItens(valorItens);
        pedido.setValorTotal(valorItens + imposto);
        return pedido;
    }

    private Pedido salvar(Pedido pedido) {
        validarPedido(pedido);
        pedido.getItens().forEach(i -> i.setPedido(pedido));
        return pedidoRepository.save(pedido);
    }

    private void validarPedido(Pedido pedido) {
        if (pedido.getClientName() == null || pedido.getClientName().isEmpty()) {
            throw new RuntimeException("Nome cliente é obrigatório");
        }

        if (pedido.getTipo() == null) {
            throw new RuntimeException("Tipo cliente é obrigatório");
        }

        if (pedido.getCnpjOuCpf() == null || pedido.getCnpjOuCpf().isEmpty()) {
            throw new RuntimeException("Nome cliente é obrigatório");
        }

        if (pedido.getDataCompra() == null) {
            throw new RuntimeException("Data pedido é obrigatório");
        }

        if (pedido.getImposto() == null || pedido.getImposto() <= 0) {
            throw new RuntimeException("Imposto deve ser calculado");
        }

        if (pedido.getItens() == null || pedido.getItens().isEmpty()) {
            throw new RuntimeException("Pedido deve ter ao menos um item");
        } else {
            pedido.getItens().forEach(i -> {
                if (i.getDescricao() == null || i.getDescricao().isEmpty()) {
                    throw new RuntimeException("Nome do produto é obrigatório");
                }

                if (i.getQuantidade() == null || i.getQuantidade() <= 0) {
                    throw new RuntimeException("Quantidade do produto é obrigatória");
                }

                if (i.getValor() == null || i.getValor() <= 0) {
                    throw new RuntimeException("Valor do produto é obrigatório");
                }
            });
        }


    }

}

