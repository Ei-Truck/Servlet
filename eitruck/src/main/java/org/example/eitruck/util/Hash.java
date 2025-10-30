package org.example.eitruck.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Hash {
    // Atributo
    private BCryptPasswordEncoder encoder;

    // Método construtor
    public Hash() {
        this.encoder = new BCryptPasswordEncoder();
    }

    // Método para criptografar a senha
    public String criptografar(String senha) {
        return encoder.encode(senha);
    }

    // Método para verificar a senha
    public boolean verificarSenha(String senhaDigitada, String senhaCriptografada) {
        return encoder.matches(senhaDigitada, senhaCriptografada);
    }
}
