package org.example.eitruck.util;

import java.util.regex.Pattern;

public class Regex {
    //    ======================================================================================
    private String apenasNumeros(String valor) {
        return valor.replaceAll("\\D", "");
    }

    //    ======================================================================================
    public boolean checarCpf(String cpf){
        String regexCpf = "^[0-9]{11}$";
        return Pattern.matches(regexCpf, apenasNumeros(cpf));
    }


    public boolean checarCep(String cep){
        String regexCep = "^[0-9]{8}$";
        return Pattern.matches(regexCep, apenasNumeros(cep));
    }

    public boolean checarTelefone(String telefone){
        String regexTelefone = "^[0-9]{10,11}$";
        return Pattern.matches(regexTelefone, apenasNumeros(telefone));
    }

    public boolean checarEmail(String email){
        String regexEmail = "[a-z0-9][a-z0-9_]@[a-z0-9-]\\.{1}[a-z]\\.?[a-z]";
        return Pattern.matches(regexEmail, email);
    }

//    ======================================================================================

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

    public String formatarCnpj(String cnpj) {
        cnpj = apenasNumeros(cnpj);
        if (cnpj.length() == 14) {
            return cnpj.substring(0,2) + "." +
                    cnpj.substring(2,5) + "." +
                    cnpj.substring(5,8) + "/" +
                    cnpj.substring(8,12) + "-" +
                    cnpj.substring(12,14);
        }
        return cnpj;
    }

    public String formatarTelefone(String tel) {
        tel = apenasNumeros(tel);
        if (tel.length() == 11) {
            return "(" + tel.substring(0,2) + ") " + tel.substring(2,7) + "-" + tel.substring(7,11);
        } else if (tel.length() == 10) {
            return "(" + tel.substring(0,2) + ") " + tel.substring(2,6) + "-" + tel.substring(6,10);
        }
        return tel;
    }

    public String formatarCep(String cep) {
        cep = apenasNumeros(cep);
        if (cep.length() == 8) {
            return cep.substring(0,5) + "-" + cep.substring(5,8);
        }
        return cep;
    }
}