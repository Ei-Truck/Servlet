package org.example.eitruck.model;


import org.example.eitruck.util.Uteis;

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
        setId(id);
        this.nome = nome;
        setIdSegmento(idSegmento);
        setIdEndereco(idEndereco);
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
        setIdSegmento(idSegmento);
        setIdEndereco(idEndereco);
    }

    // getters e setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = Uteis.validarId(id);
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nomeCompleto) {
        this.nome = nomeCompleto;
    }

    public int getIdSegmento() {
        return idSegmento;
    }
    public void setIdSegmento(int idSegmento) {
        this.idSegmento = Uteis.validarId(idSegmento);
    }

    public int getIdEndereco() {
        return idEndereco;
    }
    public void setIdEndereco(int idEndereco) {
        this.idEndereco = Uteis.validarId(idEndereco);
    }

    public String getNomeSegmento() {
        return nomeSegmento;
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

