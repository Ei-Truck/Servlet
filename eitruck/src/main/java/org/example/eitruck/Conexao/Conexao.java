package org.example.eitruck.Conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//dependencias do .env estão no pom.xml na raiz do projeto
import io.github.cdimascio.dotenv.Dotenv;

public class Conexao {
    // Carregar o .env
    Dotenv dotenv = Dotenv.load();

    // Variáveis do .env
    String hostBd = dotenv.get("HOST_BD");
    String nomeBd = dotenv.get("NOME_BD");
    String usuarioBd = dotenv.get("NOME_USUARIO_BD");
    String portaBd = dotenv.get("PORTA_BD");
    String senhaBd = dotenv.get("SENHA_BD");

    // Método para conectar o código com o banco de dados
    public Connection conectar() {
        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");

            conn = DriverManager.getConnection("jdbc:postgresql://" + hostBd + ":" + portaBd + "/" + nomeBd + "?sslmode=require", usuarioBd, senhaBd);
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            System.err.println("Driver JDBC não encontrado: " + e.getMessage());
        }
        return conn;
    }

    // Método para desconectar o código com o banco de dados
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
}
