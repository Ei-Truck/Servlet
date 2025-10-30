package org.example.eitruck.model;

import org.example.eitruck.util.Regex;

import java.time.LocalDate;

public class Analista {
    // Atributos
    private int id;
    private int idUnidade;
    private String cpf;
    private String nomeCompleto;
    private LocalDate dtContratacao;
    private String email;
    private String senha;
    private String cargo;
    private String telefone;
    private String nomeUnidade; // Não é campo do banco (atributo auxiliar para mostrar na área restrita)

    // Métodos construtores
    public Analista(int id, int idUnidade, String cpf, String nomeCompleto, LocalDate dtContratacao, String email, String senha, String cargo, String telefone) {
        this.id = id;
        this.idUnidade = idUnidade;
        this.cpf = cpf;
        this.nomeCompleto = nomeCompleto;
        this.dtContratacao = dtContratacao;
        this.email = email;
        this.senha = senha;
        this.cargo = cargo;
        this.telefone = telefone;
    }
    public Analista(int idUnidade, String cpf, String nomeCompleto, LocalDate dtContratacao, String email, String senha, String cargo, String telefone) {
        this.idUnidade = idUnidade;
        this.cpf = cpf;
        this.nomeCompleto = nomeCompleto;
        this.dtContratacao = dtContratacao;
        this.email = email;
        this.senha = senha;
        this.cargo = cargo;
        this.telefone = telefone;
    }
    public Analista(int idUnidade, String cpf, String nome, String email, LocalDate dataContratacaoDate, String senha, String cargo, String telefone) {
        this.idUnidade = idUnidade;
        this.cpf = cpf;
        this.nomeCompleto = nome;
        this.dtContratacao = dataContratacaoDate;
        this.email = email;
        this.senha = senha;
        this.cargo = cargo;
        this.telefone = telefone;
    }

    // Getters e setters
    // Campo ID
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }

    // Campo ID da unidade
    public int getIdUnidade() {
        return this.idUnidade;
    }
    public void setIdUnidade(int idUnidade) {
        this.idUnidade = idUnidade;
    }

    // Campo CPF
    public String getCpf() {
        return this.cpf;
    }
    public String getCpfFormatado() {
        Regex re = new Regex();
        String cpfFormatado = re.formatarCpf(this.cpf);
        return cpfFormatado;
    } // Formatar o CPF para mostrar na área restrita
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    // Campo nome completo
    public String getNomeCompleto() {
        return this.nomeCompleto;
    }
    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    // Campo data de contratação
    public LocalDate getDtContratacao() {
        return this.dtContratacao;
    }
    public String getDtContratacaoFormatada() {
        if (this.dtContratacao == null) {
            return null;
        }
        Regex regex = new Regex();
        return regex.formatarData(this.dtContratacao);
    } // Formatar a data de contratação para mostrar na área restrita
    public void setDtContratacao(LocalDate dtContratacao) {
        this.dtContratacao = dtContratacao;
    }

    // Campo e-mail
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    // Campo senha
    public String getSenha() {
        return this.senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }

    // Campo cargo
    public String getCargo() {
        return this.cargo;
    }
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    // Campo telefone
    public String getTelefone() {
        return this.telefone;
    }
    public String getTelefoneFormatado() {
        Regex re = new Regex();
        String telefoneFormatado = re.formatarTelefone(this.telefone);
        return telefoneFormatado;
    } // Formatar o telefone para mostrar na área restrita
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    // Atritubo nome da unidade (auxiliar para mostrar na área restrita)
    public String getNomeUnidade() {
        return nomeUnidade;
    }
    public void setNomeUnidade(String nomeUnidade) {
        this.nomeUnidade = nomeUnidade;
    }

    // Método toString
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
