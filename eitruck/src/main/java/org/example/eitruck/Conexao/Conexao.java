package org.example.eitruck.Conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//dependencias do .env estão no pom.xml na raiz do projeto
import io.github.cdimascio.dotenv.Dotenv;

public class Conexao {
    //carrega o .env
    Dotenv dotenv = Dotenv.configure().load();

    //variáveis do .env
    String hostBd = dotenv.get("HOST_BD");
    String nomeBd = dotenv.get("NOME_BD");
    String usuarioBd = dotenv.get("USUARIO_BD");
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
}
