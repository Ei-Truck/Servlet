package model;

//Obs.: conversar com o Modolo sobre se a lógica de getters e setters é igual nos models
//ATENÇÃO, REVISAR O USO DE GET E DO TOSTRING NO CASO DE SENHAS (MANTER POR ENQUANTO)
public class Administrador {
    //atributos
    private int id;
    private String cpf;
    private String NomeCompleto;
    private String email;
    private String senha;

    //método construtor
    public Administrador(int id, String cpf, String NomeCompleto, String email, String senha){
        this.id = id;
        this.cpf = cpf;
        this.NomeCompleto = NomeCompleto;
        this.email = email;
        this.senha = senha;
    }

    //getters e setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNomeCompleto() {
        return NomeCompleto;
    }
    public void setNomeCompleto(String NomeCompleto) {
        this.NomeCompleto = NomeCompleto;
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

    //toString
    public String toSring() {
        return String.format("""
            Administrador:
                Id = %d
                Nome = %s
                Email = %s
                Senha = %s""", this.id, this.NomeCompleto, this.email, this.senha);
    }

}
