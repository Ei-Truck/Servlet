package Util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class Hash {
        private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

public static String criptografar(String senha) {
        return encoder.encode(senha);
        }

// Verifica se a senha em texto puro corresponde ao hash armazenado
public static boolean verificarSenha(String senhaDigitada, String senhaCriptografada) {
        return encoder.matches(senhaDigitada, senhaCriptografada);
        }

public static void main(String[] args) {
        String senhaOriginal = "123456";
        String hash = criptografar(senhaOriginal);

        System.out.println("Hash: " + hash);
        System.out.println("Verificação correta: " + verificarSenha("123456", hash)); // true
        System.out.println("Verificação incorreta: " + verificarSenha("654321", hash)); // false
        }}
