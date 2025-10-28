package org.example.eitruck.model;
import org.example.eitruck.util.Regex;
import org.example.eitruck.util.Uteis;

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
        setId(id);
        setCpf(cpf);
        this.nomeCompleto = nomeCompleto;
        setEmail(email);
        setSenha(senha);
        this.telefone = telefone;
    }

    public Administrador(String telefone, String cpf, String nomeCompleto, String email, String senha) {
        setCpf(cpf);
        this.nomeCompleto = nomeCompleto;
        setEmail(email);
        setSenha(senha);
        this.telefone = telefone;
    }

    public Administrador(int id, String cpf, String nomeCompleto, String email, String senha) {
        setId(id);
        setCpf(cpf);
        this.nomeCompleto = nomeCompleto;
        setEmail(email);
        setSenha(senha);
    }

    // getters e setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = Uteis.validarId(id);
    }

    public String getCpf() {
        Regex re = new Regex();
        String cpfFormatado = re.formatarCpf(this.cpf);
        return cpfFormatado;
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

    public String getTelefone() {
        Regex re = new Regex();
        String telefoneFormatado = re.formatarTelefone(this.telefone);
        return telefoneFormatado;
    }
    public void setTelefone(String telefone) {
        this.telefone = Uteis.validarTelefone(telefone);
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
                Senha = %s""", this.id, this.cpf, this.nomeCompleto, this.email, this.senha);
    }
}

