package org.example.eitruck.model;

public class TipoOcorrencia {
    //atributos
    private int id;
    private String tipoEvento;
    private int pontuacao;
    private String gravidade;

    //método construtor
    public TipoOcorrencia(int id, String tipoEvento, int pontuacao, String gravidade) {
        this.id = id;
        this.tipoEvento = tipoEvento;
        this.pontuacao = pontuacao;
        this.gravidade = gravidade;
    }

    public TipoOcorrencia(String tipoEvento, int pontuacao, String gravidade) {
        this.tipoEvento = tipoEvento;
        this.pontuacao = pontuacao;
        this.gravidade = gravidade;
    }

    //getters e setters
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getTipoEvento() {
        return this.tipoEvento;
    }
    public void setTipoEvento(String tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public int getPontuacao() {
        return this.pontuacao;
    }
    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }

    public String getGravidade() {
        return this.gravidade;
    }
    public void setGravidade(String gravidade) {
        this.gravidade = gravidade;
    }

    //toString
    public String toString() {
        return String.format("""
            Tipo de Ocorrencia:
                Id = %d
                Tipo de Evento = %s
                Pontuação = %d
                Gravidade = %s""", this.id, this.tipoEvento, this.pontuacao, this.gravidade);
    }
}
