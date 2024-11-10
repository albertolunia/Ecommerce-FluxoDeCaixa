package com.example.ecommercefluxodecaixaalberto_everaldina.models;

public class Funcionario {
    private int idFuncionario;
    private String telefone;
    private String nomeFuncionario;
    private String cpf;
    private double comissao;
    private int fkIdCargo;
    private int fkIdEmpresa;

    // Getters e Setters
    public int getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getNomeFuncionario() {
        return nomeFuncionario;
    }

    public void setNomeFuncionario(String nomeFuncionario) {
        this.nomeFuncionario = nomeFuncionario;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public double getComissao() {
        return comissao;
    }

    public void setComissao(double comissao) {
        this.comissao = comissao;
    }

    public int getFkIdCargo() {
        return fkIdCargo;
    }

    public void setFkIdCargo(int fkIdCargo) {
        this.fkIdCargo = fkIdCargo;
    }

    public int getFkIdEmpresa() {
        return fkIdEmpresa;
    }

    public void setFkIdEmpresa(int fkIdEmpresa) {
        this.fkIdEmpresa = fkIdEmpresa;
    }
}
