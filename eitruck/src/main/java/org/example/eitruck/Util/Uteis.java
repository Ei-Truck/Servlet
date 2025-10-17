package org.example.eitruck.Util;
public class Uteis {

    public static String validarCpf(String cpf) {
        if (cpf == null || !cpf.matches("[0-9]{3}\\.?[0-9]{3}\\.?[0-9]{3}\\-?[0-9]{2}")) {
            throw new IllegalArgumentException("CPF inválido. Deve conter 11 dígitos numéricos.");
        }
        String numeros = cpf.replaceAll("\\D", ""); // remove tudo que não é número
        if (numeros.chars().distinct().count() == 1 || numeros.endsWith("00")) {
            throw new IllegalArgumentException("CPF inválido.");
        }
        return numeros; // retorna só os números, pronto para banco
    }

    public static String validarEmail(String email) {
        if (email == null || !email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            throw new IllegalArgumentException("Email inválido.");
        }
        return email; // email não precisa de limpeza de caracteres
    }

    public static String validarSenha(String senha) {
        if (senha == null || !senha.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")) {
            throw new IllegalArgumentException("Senha inválida. Mínimo 8 caracteres, incluindo letras e números.");
        }
        return senha; // senha não precisa de limpeza
    }

    public static String validarTelefone(String telefone) {
        if (telefone == null || !telefone.matches("\\([0-9]{2}\\) ?[0-9]{5}-[0-9]{4}")) {
            throw new IllegalArgumentException("Telefone inválido. Formato esperado: (XX) 9XXXX-XXXX");
        }
        return telefone.replaceAll("\\D", ""); // só números
    }

    public static String validarCep(String cep) {
        if (cep == null || !cep.matches("\\d{8}")) {
            throw new IllegalArgumentException("CEP inválido. Deve conter 8 dígitos numéricos.");
        }
        return cep.replaceAll("\\D", ""); // só números, mesmo que venha com "-"
    }

    public static int validarPontuacao(int pontuacao) {
        if (pontuacao < 1 || pontuacao > 10) {
            throw new IllegalArgumentException("Gravidade inválida. Deve estar entre 1 e 10.");
        }
        return pontuacao;
    }

    public static int validarId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID inválido. Deve ser positivo.");
        }
        return id;
    }
}