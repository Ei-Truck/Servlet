package org.example.eitruck.model;

public class Segmento {
    //atributos
    private int id;
    private String nome;
    private String descricao;

    //método construtor
    public Segmento(int id, String nome, String descricao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
    }

    public Segmento(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    //getters e setters
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return this.descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    //toString
    public String toString() {
        return String.format("""
            Segmento:
                Id = %d
                Nome = %s
                Descricao = %s""", this.id, this.nome, this.descricao);
    }
}
