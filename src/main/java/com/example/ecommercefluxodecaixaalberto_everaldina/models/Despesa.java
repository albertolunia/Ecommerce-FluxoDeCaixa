package com.example.ecommercefluxodecaixaalberto_everaldina.models;

import java.util.Date;

public class Despesa {
    private int idDespesa;
    private Date dataVencimento;
    private double valor;
    private String descricao;
    private int fkIdEmpresa;

    // Getters e Setters
    public int getIdDespesa() {
        return idDespesa;
    }

    public void setIdDespesa(int idDespesa) {
        this.idDespesa = idDespesa;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getFkIdEmpresa() {
        return fkIdEmpresa;
    }

    public void setFkIdEmpresa(int fkIdEmpresa) {
        this.fkIdEmpresa = fkIdEmpresa;
    }
}