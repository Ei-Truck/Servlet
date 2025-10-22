package org.example.eitruck.Dao;

import org.example.eitruck.model.Endereco;
import org.example.eitruck.model.Segmento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SegmentoDAO extends DAO {
    public SegmentoDAO() {
        super();
    }

    public boolean cadastrar(Segmento segmento) {
        String comando = """
            INSERT INTO segmento (nome, descricao)
            VALUES (?, ?)""";

        Connection conn = null;
        try {
            conn = conexao.conectar();
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setString(1, segmento.getNome());
            pstmt.setString(2, segmento.getDescricao());
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

    public int alterarNome(Segmento segmento, String novoNome) {
        String comando = "UPDATE segmento SET nome = ? WHERE id = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setString(1, novoNome);
            pstmt.setInt(2, segmento.getId());
            int execucao = pstmt.executeUpdate();
            if (execucao > 0){
                segmento.setNome(novoNome);
                return 1;
            }
            else {
                return 0;
            }
        }
        catch (SQLException sqle){
            sqle.printStackTrace();
            return -1;
        }
        finally {
            conexao.desconectar(conn);
        }
    }

    public int alterarDescricao(Segmento segmento, String novaDescricao) {
        String comando = "UPDATE segmento SET descricao = ? WHERE id = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setString(1, novaDescricao);
            pstmt.setInt(2, segmento.getId());
            int execucao = pstmt.executeUpdate();
            if (execucao > 0){
                segmento.setDescricao(novaDescricao);
                return 1;
            }
            else {
                return 0;
            }
        }
        catch (SQLException sqle){
            sqle.printStackTrace();
            return -1;
        }
        finally {
            conexao.desconectar(conn);
        }
    }

    public int apagar(Segmento segmento, int idSegmento) {
        String comando = "DELETE FROM segmento WHERE id = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setInt(1, idSegmento);
            int execucao = pstmt.executeUpdate();
            if (execucao > 0){
                segmento = null;
                return 1;
            }
            else {
                return 0;
            }
        }
        catch (SQLException sqle){
            sqle.printStackTrace();
            return -1;
        }
        finally {
            conexao.desconectar(conn);
        }
    }

    public List<Segmento> buscarPorId(int idSegmento) {
        ResultSet rs;
        List<Segmento> listaRetorno = new ArrayList<>();
        String comando = "SELECT * FROM segmento WHERE id = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setInt(1, idSegmento);
            pstmt.executeQuery();
            rs = pstmt.getResultSet();
            while (rs.next()){
                Segmento segmento = new Segmento(rs.getInt("id"), rs.getString("nome"), rs.getString("descricao"));
                listaRetorno.add(segmento);
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

    public List<Segmento> buscarPorNome(String nome) {
        ResultSet rs;
        List<Segmento> listaRetorno = new ArrayList<>();
        String comando = "SELECT * FROM segmento WHERE nome = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setString(1, nome);
            pstmt.executeQuery();
            rs = pstmt.getResultSet();
            while (rs.next()){
                Segmento segmento = new Segmento(rs.getInt("id"), rs.getString("nome"), rs.getString("descricao"));
                listaRetorno.add(segmento);
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

    public List<Segmento> buscarTodos() {
        ResultSet rs;
        List<Segmento> listaRetorno = new ArrayList<>();
        String comando = "SELECT * FROM segmento";

        Connection conn = null;
        try {
            conn = conexao.conectar();
            PreparedStatement pstmt = conn.prepareStatement(comando);
            rs = pstmt.executeQuery();
            while (rs.next()){
                Segmento segmento = new Segmento(rs.getInt("id"), rs.getString("nome"), rs.getString("descricao"));
                listaRetorno.add(segmento);
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

    public int numeroRegistros() {
        String comando = "SELECT COUNT(*) AS total FROM segmento";
        Connection conn = null;

        try {
            conn = conexao.conectar();
            PreparedStatement pstmt = conn.prepareStatement(comando);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("total");
            }
            return 0;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return -1;
        } finally {
            conexao.desconectar(conn);
        }
    }

    public List<Segmento> buscarPorDescricao(String descricao) {
        ResultSet rs;
        List<Segmento> listaRetorno = new ArrayList<>();
        String comando = "SELECT * FROM segmento WHERE descricao = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setString(1, descricao);
            pstmt.executeQuery();
            rs = pstmt.getResultSet();
            while (rs.next()){
                Segmento segmento = new Segmento(rs.getInt("id"), rs.getString("nome"), rs.getString("descricao"));
                listaRetorno.add(segmento);
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


