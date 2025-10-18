package org.example.eitruck.Conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//dependencias do .env estão no pom.xml na raiz do projeto
import io.github.cdimascio.dotenv.Dotenv;

public class Conexao {
    //carrega o .env
    Dotenv dotenv = Dotenv.load();

    //variáveis do .env
    String hostBd = dotenv.get("HOST_BD");
    String nomeBd = dotenv.get("NOME_BD");
    String usuarioBd = dotenv.get("NOME_USUARIO_BD");
    String portaBd = dotenv.get("PORTA_BD");
    String senhaBd = dotenv.get("SENHA_BD");

    //método para conectar o código com o banco de dados
    public Connection conectar() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://" + hostBd + ":" + portaBd + "/" + nomeBd, usuarioBd, senhaBd);
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return conn;
    }

    //método para desconectar o código com o banco de dados
    public boolean desconectar(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                return true;
            }
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return false;
    }
//
//    // Método auxiliar para testar a conexão com feedback
//    public void testarConexaoComFeedback() {
//        Connection conn = null;
//        try {
//            conn = conectar();
//            if (conn != null) {
//                System.out.println("\n*** CONEXÃO COM O BANCO DE DADOS TESTADA E BEM-SUCEDIDA! *** ✅");
//            } else {
//                System.out.println("\n--- FALHA NA CONEXÃO --- ❌");
//            }
//        } finally {
//            // Garante que a conexão de teste será fechada
//            desconectar(conn);
//            System.out.println("--- TESTE DE CONEXÃO FINALIZADO ---");
//        }
//    }
//
//    // MÉTODO MAIN (PSVM) PARA TESTE
//    public static void main(String[] args) {
//        System.out.println("\n--- INICIANDO TESTE DA CONEXÃO ---");
//
//        Conexao teste = new Conexao();
//
//        // 1. Imprime o Diretório de Trabalho (Ajuda a depurar o problema do .env)
//        System.out.println("Diretório de Trabalho Atual: " + new java.io.File(".").getAbsolutePath());
//
//        // 2. Imprime Variáveis Carregadas (Checa o .env)
//        System.out.println("\n--- Variáveis Carregadas ---");
//        System.out.println("HOST_BD: " + teste.hostBd);
//        System.out.println("NOME_BD: " + teste.nomeBd);
//        System.out.println("NOME_USUARIO_BD: " + teste.usuarioBd);
//        System.out.println("PORTA_BD: " + teste.portaBd);
//        System.out.println("SENHA_BD: " + (teste.senhaBd != null ? "[Censurada]" : "null")); // Evita expor a senha
//
//        // 3. Verifica se as Variáveis Falharam
//        if (teste.hostBd == null || teste.nomeBd == null || teste.usuarioBd == null) {
//            System.err.println("\nERRO: Alguma variável de ambiente retornou 'null'. 🚨");
//            System.err.println("Verifique se o arquivo .env está na raiz do projeto onde o código está sendo EXECUTADO (Veja o 'Diretório de Trabalho Atual' acima).");
//            return; // Para o teste se as variáveis falharem
//        }
//
//        // 4. Se as variáveis carregaram, tenta a conexão
//        System.out.println("\nVariáveis carregadas. Tentando conectar ao banco de dados...");
//        teste.testarConexaoComFeedback();
//    }
}
