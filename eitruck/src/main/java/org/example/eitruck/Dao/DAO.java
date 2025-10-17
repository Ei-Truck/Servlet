package org.example.eitruck.Dao;
import org.example.eitruck.Conexao.Conexao;

import java.sql.Connection;

public abstract class DAO {
    protected final Conexao conexao = new Conexao();
    protected Connection conn;

    public DAO() {
        this.conn = conexao.conectar();
        if (conn == null) {
            throw new RuntimeException();
        }
    }
}