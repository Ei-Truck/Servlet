package org.example.eitruck.model;

import org.example.eitruck.util.Uteis;

//Obs.: conversar com o Modolo sobre se a lógica de getters e setters é igual nos models
//ATENÇÃO, REVISAR O USO DE GET E DO TOSTRING NO CASO DE SENHAS (MANTER POR ENQUANTO)
public class Segmento {
    //atributos
    private int id;
    private String nome;
    private String descricao;

    //método construtor
    public Segmento(int id, String nome, String descricao) {
        setId(id);
        this.nome = nome;
        this.descricao = descricao;
    }

    //getters e setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = Uteis.validarId(id);
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
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