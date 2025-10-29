package org.example.eitruck.model;
import org.example.eitruck.util.Regex;

public class Administrador {
    // atributos
    private int id;
    private String cpf;
    private String nomeCompleto;
    private String email;
    private String senha;
    private String telefone;

    // método construtor
    public Administrador(int id, String cpf, String nomeCompleto, String email, String senha, String telefone) {
        this.id = id;
        this.cpf = cpf;
        this.nomeCompleto = nomeCompleto;
        this.email = email;
        this.senha = senha;
        this.telefone = telefone;
    }

    public Administrador(String telefone, String cpf, String nomeCompleto, String email, String senha) {
        this.telefone = telefone;
        this.cpf = cpf;
        this.nomeCompleto = nomeCompleto;
        this.email = email;
        this.senha = senha;
    }

    public Administrador(int id, String cpf, String nomeCompleto, String email, String senha) {
        this.id = id;
        this.cpf = cpf;
        this.nomeCompleto = nomeCompleto;
        this.email = email;
        this.senha = senha;
    }

    // getters e setters
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getCpf() {
        return this.cpf;
    }
    public String getCpfFormatado() {
        Regex re = new Regex();
        String cpfFormatado = re.formatarCpf(this.cpf);
        return cpfFormatado;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNomeCompleto() {
        return this.nomeCompleto;
    }
    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return this.senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTelefone() {
        return this.telefone;
    }
    public String getTelefoneFormatado() {
        Regex re = new Regex();
        String telefoneFormatado = re.formatarTelefone(this.telefone);
        return telefoneFormatado;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    // toString
    @Override
    public String toString() {
        return String.format("""
            Administrador:
                Id = %d
                Cpf = %s
                Nome = %s
                Email = %s
                Senha = %s
                Telefone = %s""", this.id, this.cpf, this.nomeCompleto, this.email, this.senha, this.telefone);
    }
}
