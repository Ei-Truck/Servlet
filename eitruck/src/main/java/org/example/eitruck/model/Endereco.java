package org.example.eitruck.model;

import org.example.eitruck.util.Regex;
import org.example.eitruck.util.Uteis;

public class Endereco {
    // atributos
    private int id;
    private String cep;
    private String rua;
    private int numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String pais;

    // construtor
    public Endereco(int id, String cep, String rua, int numero, String bairro, String cidade, String estado, String pais) {
        setId(id);
        setCep(cep);
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.pais = pais;
    }

    public Endereco(String cep, String rua, int numero, String bairro, String cidade, String estado, String pais) {
        setCep(cep);
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.pais = pais;
    }

    // getters e setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = Uteis.validarId(id);
    }

    public String getCep() {
        Regex re = new Regex();
        String cepFormatado = re.formatarCep(this.cep);
        return cepFormatado;
    }
    public void setCep(String cep) {
        this.cep = Uteis.validarCep(cep);
    }

    public String getRua() {
        return rua;
    }
    public void setRua(String rua) {
        this.rua = rua;
    }

    public int getNumero() {
        return numero;
    }
    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPais() {
        return pais;
    }
    public void setPais(String pais) {
        this.pais = pais;
    }

    // toString
    @Override
    public String toString() {
        return String.format("""
            Endereco:
                Id = %d
                Cep = %s
                Rua = %s
                Numero = %d
                Bairro = %s
                Cidade = %s
                Estado = %s
                Pais = %s""", this.id, this.cep, this.rua, this.numero, this.bairro, this.cidade, this.estado, this.pais);
    }
}


