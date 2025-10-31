package org.example.eitruck.model;

public class TipoOcorrencia {
    // Atributos
    private int id;
    private String tipoEvento;
    private int pontuacao;
    private String gravidade;

    // Métodos construtores
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

    // Getters e setters
    // Campo ID
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }

    // Campo tipo de evento
    public String getTipoEvento() {
        return this.tipoEvento;
    }
    public void setTipoEvento(String tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    // Campo pontuação
    public int getPontuacao() {
        return this.pontuacao;
    }
    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }

    // Campo gravidade
    public String getGravidade() {
        return this.gravidade;
    }
    public void setGravidade(String gravidade) {
        this.gravidade = gravidade;
    }

    // Método toString
    @Override
    public String toString() {
        return String.format("""
            Tipo de Ocorrencia:
                Id = %d
                Tipo de Evento = %s
                Pontuação = %d
                Gravidade = %s""", this.id, this.tipoEvento, this.pontuacao, this.gravidade);
    }
}
