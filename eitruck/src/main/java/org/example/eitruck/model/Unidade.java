package org.example.eitruck.model;

public class Unidade {
    // atributos
    private int id;
    private String nome;
    private int idSegmento;
    private int idEndereco;
    private String nomeSegmento;
    private String nomeEndereco;


    // construtor
    public Unidade(int id, int idSegmento, int idEndereco, String nome) {
        this.id = id;
        this.idSegmento = idSegmento;
        this.idEndereco = idEndereco;
        this.nome = nome;
    }

    public Unidade(int id, int idSegmento, int idEndereco, String nome, String nomeSegmento, String nomeEndereco) {
        this.id = id;
        this.idSegmento = idSegmento;
        this.idEndereco = idEndereco;
        this.nome = nome;
        this.nomeSegmento = nomeSegmento;
        this.nomeEndereco = nomeEndereco;
    }

    public Unidade(int idSegmento, int idEndereco, String nome) {
        this.nome = nome;
        this.idSegmento = idSegmento;
        this.idEndereco = idEndereco;
    }

    // getters e setters
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }
    public void setNome(String nomeCompleto) {
        this.nome = nomeCompleto;
    }

    public int getIdSegmento() {
        return this.idSegmento;
    }
    public void setIdSegmento(int idSegmento) {
        this.idSegmento = idSegmento;
    }

    public int getIdEndereco() {
        return this.idEndereco;
    }
    public void setIdEndereco(int idEndereco) {
        this.idEndereco = idEndereco;
    }

    public String getNomeSegmento() {
        return this.nomeSegmento;
    }

    public void setNomeSegmento(String nomeSegmento) {
        this.nomeSegmento = nomeSegmento;
    }

    public String getNomeEndereco() {
        return nomeEndereco;
    }

    public void setNomeEndereco(String nomeEndereco) {
        this.nomeEndereco = nomeEndereco;
    }

    // toString
    @Override
    public String toString() {
        return String.format("""
            Unidade:
                Id = %d
                Nome = %s
                ID Segmento = %d
                ID Endereco = %d""",
                this.id, this.nome, this.idSegmento, this.idEndereco);
    }
}
