package org.example.eitruck.dao;

import org.example.eitruck.conexao.Conexao;
import org.example.eitruck.model.Analista;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AnalistaDAO {
    // Método inserir
    public boolean cadastrar(Analista analista) {
        Conexao conexao = new Conexao();
        Connection conn = null;

        String comando = """
            INSERT INTO analista (id_unidade, cpf, nome_completo, email, dt_contratacao, senha, cargo, telefone)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)""";

        try {
            conn = conexao.conectar();
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setInt(1, analista.getIdUnidade());
            pstmt.setString(2, analista.getCpf());
            pstmt.setString(3, analista.getNomeCompleto());
            pstmt.setString(4, analista.getEmail());
            pstmt.setDate(5, Date.valueOf(analista.getDtContratacao()));
            pstmt.setString(6, analista.getSenha());
            pstmt.setString(7, analista.getCargo());
            pstmt.setString(8, analista.getTelefone());
            int execucao = pstmt.executeUpdate();
            return execucao > 0;
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
            return false;
        }
        finally {
            conexao.desconectar(conn);
        }
    }

    // Método deletar
    public int apagar(int idAnalista) {
        Conexao conexao = new Conexao();
        Connection conn = null;

        String comando = "DELETE FROM analista WHERE id = ?";

        try {
            conn = conexao.conectar();
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setInt(1, idAnalista);
            int execucao = pstmt.executeUpdate();
            return execucao;
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
            return -1;
        }
        finally {
            conexao.desconectar(conn);
        }
    }

    // Método alterar
    public int alterarTodos(int id, int idUnidade, String cpf, String nomeCompleto, LocalDate dtContratacao, String email, String senha, String cargo, String telefone) {
        Conexao conexao = new Conexao();
        Connection conn = null;

        try {
            conn = conexao.conectar();
            PreparedStatement pstmt = conn.prepareStatement(
                    "UPDATE analista SET id_unidade = ?, cpf = ?, nome_completo = ?, dt_contratacao = ?, email = ?, senha = ?, cargo = ?, telefone = ? WHERE id = ?"
            );

            pstmt.setInt(1, idUnidade);
            pstmt.setString(2, cpf);
            pstmt.setString(3, nomeCompleto);
            pstmt.setDate(4, java.sql.Date.valueOf(dtContratacao));
            pstmt.setString(5, email);
            pstmt.setString(6, senha);
            pstmt.setString(7, cargo);
            pstmt.setString(8, telefone);
            pstmt.setInt(9, id);

            int linhasAfetadas = pstmt.executeUpdate();

            if (linhasAfetadas > 0) {
                return 1; // Sucesso - registro alterado
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return -1; // Erro
        } finally {
            conexao.desconectar(conn);
        }
        return 0; // Nenhum registro alterado
    }

    public boolean atualizar(Analista analista, String novaSenha) {
        Conexao conexao = new Conexao();
        Connection conn = null;

        String comando;
        if (novaSenha != null && !novaSenha.isEmpty()) {
            comando = """
                UPDATE analista 
                SET id_unidade = ?, cpf = ?, nome = ?, email = ?, dt_contratacao = ?, senha = ?, cargo = ?
                WHERE id = ?""";
        }
        else {
            comando = """
                UPDATE analista 
                SET id_unidade = ?, cpf = ?, nome = ?, email = ?, dt_contratacao = ?, cargo = ?
                WHERE id = ?""";
        }

        try {
            conn = conexao.conectar();
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setInt(1, analista.getIdUnidade());
            pstmt.setString(2, analista.getCpf());
            pstmt.setString(3, analista.getNomeCompleto());
            pstmt.setString(4, analista.getEmail());
            pstmt.setDate(5, Date.valueOf(analista.getDtContratacao()));
            if (novaSenha != null && !novaSenha.isEmpty()) {
                pstmt.setString(6, novaSenha);
                pstmt.setString(7, analista.getCargo());
                pstmt.setInt(8, analista.getId());
            }
            else {
                pstmt.setString(6, analista.getCargo());
                pstmt.setInt(7, analista.getId());
            }

            int execucao = pstmt.executeUpdate();
            return execucao > 0;
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
            return false;
        }
        finally {
            conexao.desconectar(conn);
        }
    }

    // Método mostrar os registros
    public List<Analista> buscarTodos() {
        Conexao conexao = new Conexao();
        Connection conn = null;

        ResultSet rs;
        List<Analista> listaRetorno = new ArrayList<>();
        String comando = """
        SELECT 
            a.*,
            u.nome as nome_unidade
        FROM analista a
        INNER JOIN unidade u ON a.id_unidade = u.id
        ORDER BY id
        """;

        try {
            conn = conexao.conectar();
            PreparedStatement pstmt = conn.prepareStatement(comando);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Analista analista = new Analista(
                        rs.getInt("id"),
                        rs.getInt("id_unidade"),
                        rs.getString("cpf"),
                        rs.getString("nome_completo"),
                        rs.getDate("dt_contratacao").toLocalDate(),
                        rs.getString("email"),
                        rs.getString("senha"),
                        rs.getString("cargo"),
                        rs.getString("telefone")
                );
                analista.setNomeUnidade(rs.getString("nome_unidade"));
                listaRetorno.add(analista);
            }
            return listaRetorno;
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
            return null;
        }
        finally {
            conexao.desconectar(conn);
        }
    }

    // Método quantidade de registros
    public int numeroRegistros() {
        Conexao conexao = new Conexao();
        Connection conn = null;

        String comando = "SELECT COUNT(*) AS total FROM analista";

        try {
            conn = conexao.conectar();
            PreparedStatement pstmt = conn.prepareStatement(comando);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("total");
            }
            return 0;
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
            return -1;
        }
        finally {
            conexao.desconectar(conn);
        }
    }

    // Método filtrar
    public List<Analista> filtrarAnalistasMultiplos(String filtroId, String filtroNomeUnidade, String filtroNomeCompleto, String filtroCpf, String filtroEmail, String filtroCargo) {
        Conexao conexao = new Conexao();
        Connection conn = null;

        ResultSet rs;
        List<Analista> listaRetorno = new ArrayList<>();

        try {
            conn = conexao.conectar();

            StringBuilder sql = new StringBuilder("""
            SELECT a.*, u.nome as nome_unidade 
            FROM analista a 
            LEFT JOIN unidade u ON a.id_unidade = u.id 
            WHERE 1=1
        """);

            List<Object> parametros = new ArrayList<>();

            // Filtro por ID (busca parcial)
            if (filtroId != null && !filtroId.trim().isEmpty()) {
                sql.append(" AND a.id::text LIKE ?");
                parametros.add("%" + filtroId.trim() + "%");
            }

            // Filtro por Nome da Unidade (busca parcial case-insensitive)
            if (filtroNomeUnidade != null && !filtroNomeUnidade.trim().isEmpty()) {
                sql.append(" AND u.nome ILIKE ?");
                parametros.add("%" + filtroNomeUnidade.trim() + "%");
            }

            // Filtro por Nome Completo (busca parcial case-insensitive)
            if (filtroNomeCompleto != null && !filtroNomeCompleto.trim().isEmpty()) {
                sql.append(" AND a.nome_completo ILIKE ?");
                parametros.add("%" + filtroNomeCompleto.trim() + "%");
            }

            // Filtro por CPF (busca parcial)
            if (filtroCpf != null && !filtroCpf.trim().isEmpty()) {
                sql.append(" AND a.cpf LIKE ?");
                parametros.add("%" + filtroCpf.trim() + "%");
            }

            // Filtro por Email (busca parcial case-insensitive)
            if (filtroEmail != null && !filtroEmail.trim().isEmpty()) {
                sql.append(" AND a.email ILIKE ?");
                parametros.add("%" + filtroEmail.trim() + "%");
            }

            // Filtro por Cargo (busca parcial case-insensitive)
            if (filtroCargo != null && !filtroCargo.trim().isEmpty()) {
                sql.append(" AND a.cargo ILIKE ?");
                parametros.add("%" + filtroCargo.trim() + "%");
            }

            sql.append(" ORDER BY a.id");

            PreparedStatement pstmt = conn.prepareStatement(sql.toString());

            // Preenche os parâmetros
            for (int i = 0; i < parametros.size(); i++) {
                pstmt.setObject(i + 1, parametros.get(i));
            }

            System.out.println("SQL Analista: " + sql.toString());
            System.out.println("Parâmetros Analista: " + parametros);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                Analista analista = new Analista(
                        rs.getInt("id"),
                        rs.getInt("id_unidade"),
                        rs.getString("cpf"),
                        rs.getString("nome_completo"),
                        rs.getDate("dt_contratacao").toLocalDate(),
                        rs.getString("email"),
                        rs.getString("senha"),
                        rs.getString("cargo"),
                        rs.getString("telefone")
                );
                analista.setNomeUnidade(rs.getString("nome_unidade"));
                listaRetorno.add(analista);
            }
            return listaRetorno;
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
            return null;
        }
        finally {
            conexao.desconectar(conn);
        }
    }

    // Métodos buscar por ID
    public List<Analista> buscarPorId(int idAnalista) {
        Conexao conexao = new Conexao();
        Connection conn = null;

        ResultSet rs;
        List<Analista> listaRetorno = new ArrayList<>();
        String comando = "SELECT * FROM analista WHERE id = ?";

        try {
            conn = conexao.conectar();
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setInt(1, idAnalista);
            rs = pstmt.executeQuery();
            while (rs.next()){
                Analista analista = new Analista(
                        rs.getInt("id"),
                        rs.getInt("id_unidade"),
                        rs.getString("cpf"),
                        rs.getString("nome_completo"),
                        rs.getDate("dt_contratacao").toLocalDate(),
                        rs.getString("email"),
                        rs.getString("senha"),
                        rs.getString("cargo"),
                        rs.getString("telefone")
                );
                listaRetorno.add(analista);
            }
            return listaRetorno;
        }
        catch (SQLException sqle){
            sqle.printStackTrace();
            return null;
        }
        finally {
            conexao.desconectar(conn);
        }
    }
}