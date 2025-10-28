package org.example.eitruck.model;

import org.example.eitruck.util.Uteis;

import java.sql.Date;
import java.time.LocalDate;

public class Analista {
    // atributos
    private int id;
    private int idUnidade;
    private String cpf;
    private String nomeCompleto;
    private LocalDate dtContratacao;
    private String email;
    private String senha;
    private String cargo;
    private String telefone;
    private String nomeUnidade;


    // construtor
    public Analista(int id, int idUnidade, String cpf, String nomeCompleto, LocalDate dtContratacao, String email, String senha, String cargo, String telefone) {
        setId(id);
        setIdUnidade(idUnidade);
        setCpf(cpf);
        this.nomeCompleto = nomeCompleto;
        this.dtContratacao = dtContratacao;
        setEmail(email);
        setSenha(senha);
        this.cargo = cargo;
        setTelefone(telefone);

    }

    public Analista(int idUnidade, String cpf, String nomeCompleto, LocalDate dtContratacao, String email, String senha, String cargo, String telefone) {
        setIdUnidade(idUnidade);
        setCpf(cpf);
        this.nomeCompleto = nomeCompleto;
        this.dtContratacao = dtContratacao;
        setEmail(email);
        setSenha(senha);
        this.cargo = cargo;
        setTelefone(telefone);

    }

    public Analista(int idUnidade, String cpf, String nome, String email, LocalDate dataContratacaoDate, String senha, String cargo, String telefone) {
        setIdUnidade(idUnidade);
        setCpf(cpf);
        this.nomeCompleto = nome;
        this.dtContratacao = dataContratacaoDate;
        setEmail(email);
        setSenha(senha);
        this.cargo = cargo;
        this.telefone = telefone;
    }

    // getters e setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = Uteis.validarId(id);
    }

    public int getIdUnidade() {
        return idUnidade;
    }
    public void setIdUnidade(int idUnidade) {
        this.idUnidade = Uteis.validarId(idUnidade);
    }

    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = Uteis.validarCpf(cpf);
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }
    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public LocalDate getDtContratacao() {
        return dtContratacao;
    }
    public void setDtContratacao(LocalDate dtContratacao) {
        this.dtContratacao = dtContratacao;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = Uteis.validarEmail(email);
    }

    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha; //Uteis.validarSenha(senha);
    }

    public String getCargo() {
        return cargo;
    }
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = Uteis.validarTelefone(telefone);
    }

    public String getNomeUnidade() {
        return nomeUnidade;
    }
    public void setNomeUnidade(String nomeUnidade) {
        this.nomeUnidade = nomeUnidade;
    }

    // toString
    @Override
    public String toString() {
        return String.format("""
            Analista:
                Id = %d
                Id da Unidade = %d
                Cpf = %s
                Nome = %s
                Data de Contratação = %s
                Email = %s
                Cargo = %s
                Telefone = %s""", this.id, this.idUnidade, this.cpf, this.nomeCompleto, this.dtContratacao, this.email, this.cargo, this.telefone);
    }
}
