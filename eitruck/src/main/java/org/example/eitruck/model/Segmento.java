package org.example.eitruck.model;

public class Segmento {
    // Atributos
    private int id;
    private String nome;
    private String descricao;

    // Método construtores
    public Segmento(int id, String nome, String descricao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
    }
    public Segmento(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    // Getters e setters
    // Campo ID
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }

    // Campo nome
    public String getNome() {
        return this.nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    // Campo descrição
    public String getDescricao() {
        return this.descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    // Método toString
    public String toString() {
        return String.format("""
            Segmento:
                Id = %d
                Nome = %s
                Descricao = %s""", this.id, this.nome, this.descricao);
    }
}
