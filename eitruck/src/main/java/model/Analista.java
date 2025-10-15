package model;

import java.time.LocalDate;

//Obs.: conversar com o Modolo sobre se a lógica de getters e setters é igual nos models
//ATENÇÃO, REVISAR O USO DE GET E DO TOSTRING NO CASO DE SENHAS (MANTER POR ENQUANTO)
public class Analista {
    //atributos
    private int id;
    private String nome;
    private int idUnidade;
    private String cpf;
    private String email;
    private String senha;
    private String cargo;
    private String telefone;
    private LocalDate dtcontratacao;

    //método construtor
    public Analista(int id, String nome, String cpf, String email, String senha, String cargo, String telefone, LocalDate dtcontratacao, int idUnidade) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        this.cargo = cargo;
        this.telefone = telefone;
        this.dtcontratacao = dtcontratacao;
        this.idUnidade=idUnidade;
    }

    //getters e setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
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
        this.telefone = telefone;
    }

    public LocalDate getDtcontratacao() {
        return dtcontratacao;
    }

    public void setDtcontratacao(LocalDate dtcontratacao) {
        this.dtcontratacao = dtcontratacao;
    }

    public int getIdUnidade() {
        return idUnidade;
    }

    public void setIdUnidade(int idUnidade) {
        this.idUnidade = idUnidade;
    }

    //toString
    public String toString() {
        return String.format("""
                Analista:
                    Id = %d
                    Nome = %s
                    CPF = %s
                    Email = %s
                    Cargo = %s
                    Telefone = %s
                    Data de Contratação = %s""", this.id, this.nome, this.cpf, this.email, this.cargo, this.telefone, this.dtcontratacao);
    }
}
