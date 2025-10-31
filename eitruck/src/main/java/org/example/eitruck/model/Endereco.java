package org.example.eitruck.model;

import org.example.eitruck.util.Regex;

public class Endereco {
    // Atributos
    private int id;
    private String cep;
    private String rua;
    private int numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String pais;

    // Métodos construtores
    public Endereco(int id, String cep, String rua, int numero, String bairro, String cidade, String estado, String pais) {
        this.id = id;
        this.cep = cep;
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.pais = pais;
    }
    public Endereco(String cep, String rua, int numero, String bairro, String cidade, String estado, String pais) {
        this.cep = cep;
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.pais = pais;
    }

    // Getters e setters
    // Campo ID
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }

    // Campo CEP
    public String getCep() {
        return this.cep;
    }
    public String getCepFormatado() {
        Regex re = new Regex();
        String cepFormatado = re.formatarCep(this.cep);
        return cepFormatado;
    } // Formatar o CEP para mostrar na área restrita
    public void setCep(String cep) {
        this.cep = cep;
    }

    // Campo rua
    public String getRua() {
        return this.rua;
    }
    public void setRua(String rua) {
        this.rua = rua;
    }

    // Campo número
    public int getNumero() {
        return this.numero;
    }
    public void setNumero(int numero) {
        this.numero = numero;
    }

    // Campo bairro
    public String getBairro() {
        return this.bairro;
    }
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    // Campo cidade
    public String getCidade() {
        return this.cidade;
    }
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    // Campo estado
    public String getEstado() {
        return this.estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }

    // Campo país
    public String getPais() {
        return this.pais;
    }
    public void setPais(String pais) {
        this.pais = pais;
    }

    // Método toString
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
