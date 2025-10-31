package org.example.eitruck.model;
import org.example.eitruck.util.Regex;

public class Administrador {
    // Atributos
    private int id;
    private String cpf;
    private String nomeCompleto;
    private String email;
    private String senha;
    private String telefone;

    // Métodos construtores
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

    // Getters e setters
    // Campo ID
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
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

    // Método toString
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
