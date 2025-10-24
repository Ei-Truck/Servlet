package org.example.eitruck.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Hash {

        //    Comando para encriptar senha
        public static String criptografarSenha(String senha){
                try {
//            Instância de SHA-256
                        MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
//            Gera hash em bytes
                        byte[] hashBytes = sha256.digest(senha.getBytes());
//            Converte bytes[] para números
                        BigInteger numHash = new BigInteger(1, hashBytes);

//            Converte para String
                        return numHash.toString(16);

                } catch (NoSuchAlgorithmException nsae){
                        nsae.printStackTrace();
                        return null;
                }
        }

        public static boolean descriptografarSenha(String senhaStr, String senhaCriptografada){
//        transforma senhaNormal em hash
                String hash = criptografarSenha(senhaStr);
                return hash.equals(senhaCriptografada);
        }

        public static boolean compararComHash(String senhaTexto, String hashArmazenado) {
                String hashGerado = criptografarSenha(senhaTexto);
                if (hashGerado == null) return false;
                return hashGerado.equalsIgnoreCase(hashArmazenado);
        }

        public static void main(String[] args) {
                Scanner sc = new Scanner(System.in);
                String senhaEntrada;

                // Tentar usar Console para ocultar a senha (quando disponível)
                if (System.console() != null) {
                        char[] pwd = System.console().readPassword("Digite a senha que deseja testar: ");
                        senhaEntrada = new String(pwd);
                } else {
                        // Fallback para Scanner (em IDEs como Eclipse/IntelliJ)
                        System.out.print("Digite a senha que deseja testar: ");
                        senhaEntrada = sc.nextLine();
                }

                // Gera hash da senha digitada
                String hashGerado = criptografarSenha(senhaEntrada);

                // Pede o hash que está "no banco" (usuário cola/insere)
                System.out.print("Cole/insira a senha do banco (hash SHA-256) e pressione Enter: ");
                String hashBanco = sc.nextLine().trim();

                // Mostra os resultados
                System.out.println("\n---- Resultados ----");
                System.out.println("Hash gerado (da senha digitada): " + hashGerado);
                System.out.println("Hash do banco (inserido):       " + hashBanco);

                // Compara e mostra se bate
                boolean bate = compararComHash(senhaEntrada, hashBanco);
                System.out.println("\nA senha digitada corresponde ao hash do banco? " + (bate ? "SIM" : "NÃO"));

                sc.close();
        }
}