package org.example.eitruck.Dao;

import org.example.eitruck.model.Analista;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AnalistaDAO extends DAO {
    public AnalistaDAO() {
        super();
    }

    public boolean cadastrar(Analista analista) {
        String comando = """
            INSERT INTO analista (id, id_unidade, cpf, nome, email, dt_contratacao, senha, cargo)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)""";

        Connection conn = null;
        try {
            conn = conexao.conectar();
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setInt(1, analista.getId());
            pstmt.setInt(2, analista.getIdUnidade());
            pstmt.setString(3, analista.getCpf());
            pstmt.setString(4, analista.getNomeCompleto());
            pstmt.setString(5, analista.getEmail());
            pstmt.setDate(6, Date.valueOf(analista.getDtContratacao()));
            pstmt.setString(7, analista.getSenha());
            pstmt.setString(8, analista.getCargo());
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

    // Método para buscar todos os analistas
    public List<Analista> buscarTodos() {
        ResultSet rs;
        List<Analista> listaRetorno = new ArrayList<>();
        String comando = "SELECT * FROM analista";

        Connection conn = null;
        try {
            conn = conexao.conectar();
            PreparedStatement pstmt = conn.prepareStatement(comando);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Analista analista = new Analista(
                        rs.getInt("id"),
                        rs.getInt("id_unidade"),
                        rs.getString("cpf"),
                        rs.getString("nome"),
                        rs.getDate("dt_contratacao").toLocalDate(),
                        rs.getString("email"),
                        rs.getString("senha"),
                        rs.getString("cargo"),
                        rs.getString("telefone")
                );
                listaRetorno.add(analista);
            }
            return listaRetorno;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return null;
        } finally {
            conexao.desconectar(conn);
        }
    }

    // Método de atualização consolidado
    public boolean atualizar(Analista analista, String novaSenha) {
        String comando;
        if (novaSenha != null && !novaSenha.isEmpty()) {
            comando = """
                UPDATE analista 
                SET id_unidade = ?, cpf = ?, nome = ?, email = ?, dt_contratacao = ?, senha = ?, cargo = ?
                WHERE id = ?""";
        } else {
            comando = """
                UPDATE analista 
                SET id_unidade = ?, cpf = ?, nome = ?, email = ?, dt_contratacao = ?, cargo = ?
                WHERE id = ?""";
        }

        Connection conn = null;
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
            } else {
                pstmt.setString(6, analista.getCargo());
                pstmt.setInt(7, analista.getId());
            }

            int execucao = pstmt.executeUpdate();
            return execucao > 0;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return false;
        } finally {
            conexao.desconectar(conn);
        }
    }

    // Método apagar simplificado
    public int apagar(int idAnalista) {
        String comando = "DELETE FROM analista WHERE id = ?";
        Connection conn = null;

        try {
            conn = conexao.conectar();
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setInt(1, idAnalista);
            int execucao = pstmt.executeUpdate();
            return execucao;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return -1;
        } finally {
            conexao.desconectar(conn);
        }
    }

    // Métodos de busca
    public List<Analista> buscarPorId(int idAnalista) {
        ResultSet rs;
        List<Analista> listaRetorno = new ArrayList<>();
        String comando = "SELECT * FROM analista WHERE id = ?";

        Connection conn = null;
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
                        rs.getString("nome"),
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

    public List<Analista> buscarPorIdUnidade(int idUnidade) {
        ResultSet rs;
        List<Analista> listaRetorno = new ArrayList<>();
        String comando = "SELECT * FROM analista WHERE id_unidade = ?";

        Connection conn = null;
        try {
            conn = conexao.conectar();
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setInt(1, idUnidade);
            rs = pstmt.executeQuery();
            while (rs.next()){
                Analista analista = new Analista(
                        rs.getInt("id"),
                        rs.getInt("id_unidade"),
                        rs.getString("cpf"),
                        rs.getString("nome"),
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

    public List<Analista> buscarPorCpf(String cpf) {
        ResultSet rs;
        List<Analista> listaRetorno = new ArrayList<>();
        String comando = "SELECT * FROM analista WHERE cpf = ?";

        Connection conn = null;
        try {
            conn = conexao.conectar();
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setString(1, cpf);
            rs = pstmt.executeQuery();
            while(rs.next()){
                Analista analista = new Analista(
                        rs.getInt("id"),
                        rs.getInt("id_unidade"),
                        rs.getString("cpf"),
                        rs.getString("nome"),
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

    public List<Analista> buscarPorNome(String nome) {
        ResultSet rs;
        List<Analista> listaRetorno = new ArrayList<>();
        String comando = "SELECT * FROM analista WHERE nome = ?";

        Connection conn = null;
        try {
            conn = conexao.conectar();
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setString(1, nome);
            rs = pstmt.executeQuery();
            while (rs.next()){
                Analista analista = new Analista(
                        rs.getInt("id"),
                        rs.getInt("id_unidade"),
                        rs.getString("cpf"),
                        rs.getString("nome"),
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

    public List<Analista> buscarPorEmail(String email) {
        ResultSet rs;
        List<Analista> listaRetorno = new ArrayList<>();
        String comando = "SELECT * FROM analista WHERE email = ?";

        Connection conn = null;
        try {
            conn = conexao.conectar();
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();
            while (rs.next()){
                Analista analista = new Analista(
                        rs.getInt("id"),
                        rs.getInt("id_unidade"),
                        rs.getString("cpf"),
                        rs.getString("nome"),
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

    public List<Analista> buscarPorCargo(String cargo) {
        ResultSet rs;
        List<Analista> listaRetorno = new ArrayList<>();
        String comando = "SELECT * FROM analista WHERE cargo = ?";

        Connection conn = null;
        try {
            conn = conexao.conectar();
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setString(1, cargo);
            rs = pstmt.executeQuery();
            while (rs.next()){
                Analista analista = new Analista(
                        rs.getInt("id"),
                        rs.getInt("id_unidade"),
                        rs.getString("cpf"),
                        rs.getString("nome"),
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

    // Método para autenticação
    public String ehAnalista(String email, String senha){
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        String sql = "SELECT senha, nome FROM analista WHERE email = ?";
        String senhaBanco, nome;

        try {
            conn = conexao.conectar();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();
            if (rs.next()){
                senhaBanco = rs.getString("senha");
                nome = rs.getString("nome");
                if (senha.equals(senhaBanco)){
                    return nome;
                }
            } return null;
        } catch (SQLException sqle){
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(conn);
        } return null;
    }
}