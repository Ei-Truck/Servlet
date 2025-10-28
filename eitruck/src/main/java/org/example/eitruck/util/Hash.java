package org.example.eitruck.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Hash {
    private BCryptPasswordEncoder encoder;

    public Hash() {
        this.encoder = new BCryptPasswordEncoder();
    }

    public String criptografar(String senha) {
        return encoder.encode(senha);
    }

    public boolean verificarSenha(String senhaDigitada, String senhaCriptografada) {
        return encoder.matches(senhaDigitada, senhaCriptografada);
    }
}