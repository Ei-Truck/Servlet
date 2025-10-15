package model;

//Obs.: conversar com o Modolo sobre se a lógica de getters e setters é igual nos models
//ATENÇÃO, REVISAR O USO DE GET E DO TOSTRING NO CASO DE SENHAS (MANTER POR ENQUANTO)
public class Unidade {
    //atributos
    private int id;
    private String nome;
    private int idSegmento;
    private int idEndereco;

    //método construtor
    public Unidade(int id, String nome, int idSegmento, int idEndereco) {
        this.id = id;
        this.nome = nome;
        this.idSegmento = idSegmento;
        this.idEndereco = idEndereco;
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

    public int getIdSegmento() {
        return idSegmento;
    }
    public void setIdSegmento(int idSegmento) {
        this.idSegmento = idSegmento;
    }

    public int getIdEndereco() {
        return idEndereco;
    }
    public void setIdEndereco(int idEndereco) {
        this.idEndereco = idEndereco;
    }

    //toString
    public String toString() {
        return String.format("""
            Unidade:
                Id = %d
                Nome = %s
                ID Segmento = %d
                ID Endereco = %d""", this.id, this.nome, this.idSegmento, this.idEndereco);
    }
}

