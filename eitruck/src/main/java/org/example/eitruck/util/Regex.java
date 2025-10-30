package org.example.eitruck.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Regex {
    // Metódo para verificar apenas números no campo
    private String apenasNumeros(String valor) {
        return valor.replaceAll("\\D", "");
    }

    // Metódo para formatar o CPF
    public String formatarCpf(String cpf) {
        cpf = apenasNumeros(cpf);
        if (cpf.length() == 11) {
            return cpf.substring(0,3) + "." +
                    cpf.substring(3,6) + "." +
                    cpf.substring(6,9) + "-" +
                    cpf.substring(9,11);
        }
        return cpf;
    }

    // Metódo para formatar o telefone
    public String formatarTelefone(String tel) {
        tel = apenasNumeros(tel);
        if (tel.length() == 11) {
            return "(" + tel.substring(0,2) + ") " + tel.substring(2,7) + "-" + tel.substring(7,11);
        } else if (tel.length() == 10) {
            return "(" + tel.substring(0,2) + ") " + tel.substring(2,6) + "-" + tel.substring(6,10);
        }
        return tel;
    }

    // Metódo para formatar o CEP
    public String formatarCep(String cep) {
        cep = apenasNumeros(cep);
        if (cep.length() == 8) {
            return cep.substring(0,5) + "-" + cep.substring(5,8);
        }
        return cep;
    }

    // Método para formatar a data no formato DD-MM-YYYY
    public String formatarData(LocalDate data) {
        if (data == null) {
            return null;
        }
        return data.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }
}
