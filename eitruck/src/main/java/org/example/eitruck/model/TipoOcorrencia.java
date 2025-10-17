package org.example.eitruck.model;

import org.example.eitruck.Util.Uteis;

//Obs.: conversar com o Modolo sobre se a lógica de getters e setters é igual nos models
//ATENÇÃO, REVISAR O USO DE GET E DO TOSTRING NO CASO DE SENHAS (MANTER POR ENQUANTO)
public class TipoOcorrencia {
    //atributos
    private int id;
    private String tipoEvento;
    private int pontuacao;
    private String gravidade;

    //método construtor
    public TipoOcorrencia(int id, String tipoEvento, int pontuacao, String gravidade) {
        setId(id);
        this.tipoEvento = tipoEvento;
        setPontuacao(pontuacao);
        this.gravidade = gravidade;
    }

    //getters e setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = Uteis.validarId(id);
    }

    public String getTipoEvento() {
        return tipoEvento;
    }
    public void setTipoEvento(String tipoEvento) {
        this.tipoEvento = this.tipoEvento;
    }

    public int getPontuacao() {
        return pontuacao;
    }
    public void setPontuacao(int pontuacao) {
        this.pontuacao = Uteis.validarPontuacao(pontuacao);
    }

    public String getGravidade() {
        return gravidade;
    }
    public void setGravidade(String gravidade) {
        this.gravidade = this.gravidade;
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
