package com.example.ecommercefluxodecaixaalberto_everaldina.models;

import java.util.Date;

public class RecebimentoPagamento {
    private int idRecPag;
    private int fkIdModalidade;
    private String pagamentoRecebimento;
    private Date data;
    private double valor;
    private int fkIdEmpresa;

    // Getters e Setters
    public int getIdRecPag() {
        return idRecPag;
    }

    public void setIdRecPag(int idRecPag) {
        this.idRecPag = idRecPag;
    }

    public int getFkIdModalidade() {
        return fkIdModalidade;
    }

    public void setFkIdModalidade(int fkIdModalidade) {
        this.fkIdModalidade = fkIdModalidade;
    }

    public String getPagamentoRecebimento() {
        return pagamentoRecebimento;
    }

    public void setPagamentoRecebimento(String pagamentoRecebimento) {
        this.pagamentoRecebimento = pagamentoRecebimento;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getFkIdEmpresa() {
        return fkIdEmpresa;
    }

    public void setFkIdEmpresa(int fkIdEmpresa) {
        this.fkIdEmpresa = fkIdEmpresa;
    }
}
