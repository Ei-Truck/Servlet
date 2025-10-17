package org.example.eitruck.Dao;

import org.example.eitruck.model.TipoOcorrencia;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TipoOcorrenciaDAO {
    private Connection connection;

    public TipoOcorrenciaDAO(Connection connection) {
        this.connection = connection;
    }

    // CREATE
    public void inserir(TipoOcorrencia obj) throws SQLException {
        // Ajuste os campos conforme o banco de dados
        String sql = "INSERT INTO tipoocorrencia VALUES (...)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            // TODO: mapear atributos do objeto para stmt.setXXX
        }
    }

    // READ - buscar por id
    public TipoOcorrencia buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM tipoocorrencia WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // TODO: mapear ResultSet -> objeto TipoOcorrencia
                return new TipoOcorrencia(/* atributos */);
            }
        }
        return null;
    }

    // READ - listar todos
    public List<TipoOcorrencia> listarTodos() throws SQLException {
        List<TipoOcorrencia> lista = new ArrayList<>();
        String sql = "SELECT * FROM tipoocorrencia";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                // TODO: mapear ResultSet -> objeto TipoOcorrencia
                TipoOcorrencia obj = new TipoOcorrencia(/* atributos */);
                lista.add(obj);
            }
        }
        return lista;
    }

    // UPDATE
    public void atualizar(TipoOcorrencia obj) throws SQLException {
        String sql = "UPDATE tipoocorrencia SET ... WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            // TODO: mapear atributos
            stmt.executeUpdate();
        }
    }

    // DELETE
    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM tipoocorrencia WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}

