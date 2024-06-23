package com.example.demo.entities;

import com.example.demo.constants.TipoCliente;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Pedido implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String clientName;

    @NotBlank
    private TipoCliente tipo;

    @NotBlank
    private String cnpjOuCpf;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dataCompra;

    @NotNull
    private Double valorItens = 0D;
    @NotNull
    private Double valorTotal = 0D;;

    private Double imposto = 0D;;

    @OneToMany(mappedBy = "pedido", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<Item> itens = new ArrayList<>();

    public Pedido() {
    }

    public Pedido(String clientName, TipoCliente tipo, String cnpjOuCpf, Date dataCompra, double valorTotal) {
        this.clientName = clientName;
        this.tipo = tipo;
        this.cnpjOuCpf = cnpjOuCpf;
        this.dataCompra = dataCompra;
        this.valorTotal = valorTotal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public TipoCliente getTipo() {
        return tipo;
    }

    public void setTipo(TipoCliente tipo) {
        this.tipo = tipo;
    }

    public String getCnpjOuCpf() {
        return cnpjOuCpf;
    }

    public void setCnpjOuCpf(String cnpjOuCpf) {
        this.cnpjOuCpf = cnpjOuCpf;
    }

    public Date getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(Date dataCompra) {
        this.dataCompra = dataCompra;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public List<Item> getItens() {
        return itens;
    }

    public Double getValorItens() {
        return valorItens;
    }

    public void setValorItens(Double valorItens) {
        this.valorItens = valorItens;
    }

    public Double getImposto() {
        return imposto;
    }

    public void setImposto(Double importo) {
        this.imposto = importo;
    }

    public void setItens(List<Item> itens) {
        this.itens = itens;
    }
}
