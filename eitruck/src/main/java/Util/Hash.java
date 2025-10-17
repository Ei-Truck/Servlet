package Util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {

    //    Comando para encriptar senha
    public String criptografarSenha(String senha){
        try {
//            Instância de SHA-256
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
//            Gera hash em bytes
            byte[] hashBytes = sha256.digest(senha.getBytes());
//            Converte bytes[] para números
            BigInteger numHash = new BigInteger(1, hashBytes);

//            Converte para String
            return numHash.toString(16);

        } catch (NoSuchAlgorithmException nsae) {
            nsae.printStackTrace();
            return null;
        }
    }

    public boolean descriptografarSenha(String senhaStr, String senhaCriptografada){
//        transforma senhaNormal em hash
        String hash = criptografarSenha(senhaStr);
        return hash.equals(senhaCriptografada);
    }
}
