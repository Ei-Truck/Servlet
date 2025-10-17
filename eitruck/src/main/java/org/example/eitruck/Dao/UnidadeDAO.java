package org.example.eitruck.Dao;

import org.example.eitruck.model.Unidade;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UnidadeDAO extends DAO {
    public UnidadeDAO() throws SQLException, ClassNotFoundException{
        super();
    }

    public boolean cadastrar(Unidade unidade) {
        String comando = """
            INSERT INTO unidade (id, id_segmento, id_endereco, nome)
            VALUES (?, ?, ?, ?)""";

        try {
            conn = conexao.conectar();
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setInt(1, unidade.getId());
            pstmt.setInt(2, unidade.getIdSegmento());
            pstmt.setInt(3, unidade.getIdEndereco());
            pstmt.setString(4, unidade.getNome());
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

    public int alterarIdSegmento(Unidade unidade, int novoIdSegmento) {
        String comando = "UPDATE unidade SET id_segmento = ? WHERE id = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setInt(1, novoIdSegmento);
            pstmt.setInt(2, unidade.getId());
            int execucao = pstmt.executeUpdate();
            if (execucao > 0){
                unidade.setIdSegmento(novoIdSegmento);
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

    public int alterarIdEndereco(Unidade unidade, int novoIdEndereco) {
        String comando = "UPDATE unidade SET id_endereco = ? WHERE id = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setInt(1, novoIdEndereco);
            pstmt.setInt(2, unidade.getId());
            int execucao = pstmt.executeUpdate();
            if(execucao > 0){
                unidade.setIdEndereco(novoIdEndereco);
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

    public int alterarNome(Unidade unidade, String novoNome) {
        String comando = "UPDATE unidade SET nome = ? WHERE id = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setString(1, novoNome);
            pstmt.setInt(2, unidade.getId());
            int execucao = pstmt.executeUpdate();
            if (execucao > 0){
                unidade.setNome(novoNome);
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

    public int apagar(Unidade unidade, int idUnidade) {
        String comando = "DELETE FROM unidade WHERE id = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setInt(1, idUnidade);
            int execucao = pstmt.executeUpdate();
            if (execucao > 0){
                unidade = null;
                return 1;
            }
            else {
                return 0;
            }
        }
        catch(SQLException sqle){
            sqle.printStackTrace();
            return -1;
        }
        finally {
            conexao.desconectar(conn);
        }
    }

    public List<Unidade> buscarPorId(int idUnidade) {
        ResultSet rs;
        List<Unidade> listaRetorno = new ArrayList<>();
        String comando = "SELECT * FROM unidade WHERE id = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setInt(1, idUnidade);
            pstmt.executeQuery();
            rs = pstmt.getResultSet();
            while (rs.next()){
                Unidade unidade = new Unidade(rs.getInt("id"), rs.getInt("id_segmento"),
                        rs.getInt("id_endereco"), rs.getString("nome"));
                listaRetorno.add(unidade);
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

    public List<Unidade> buscarPorIdSegmento(int idSegmento) {
        ResultSet rs;
        List<Unidade> listaRetorno = new ArrayList<>();
        String comando = "SELECT * FROM unidade WHERE id_segmento = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setInt(1, idSegmento);
            pstmt.executeQuery();
            rs = pstmt.getResultSet();
            while (rs.next()){
                Unidade unidade = new Unidade(rs.getInt("id"), rs.getInt("id_segmento"),
                        rs.getInt("id_endereco"), rs.getString("nome"));
                listaRetorno.add(unidade);
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

    public List<Unidade> buscarPorIdEndereco(int idEndereco) {
        ResultSet rs;
        List<Unidade> listaRetorno = new ArrayList<>();
        String comando = "SELECT * FROM unidade WHERE id_endereco = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setInt(1, idEndereco);
            pstmt.executeQuery();
            rs = pstmt.getResultSet();
            while (rs.next()){
                Unidade unidade = new Unidade(rs.getInt("id"), rs.getInt("id_segmento"),
                        rs.getInt("id_endereco"), rs.getString("nome"));
                listaRetorno.add(unidade);
            }
            return listaRetorno;
        }
        catch(SQLException sqle){
            sqle.printStackTrace();
            return null;
        }
        finally {
            conexao.desconectar(conn);
        }
    }

    public List<Unidade> buscarPorNome(String nome) {
        ResultSet rs;
        List<Unidade> listaRetorno = new ArrayList<>();
        String comando = "SELECT * FROM unidade WHERE nome = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setString(1, nome);
            pstmt.executeQuery();
            rs = pstmt.getResultSet();
            while (rs.next()){
                Unidade unidade = new Unidade(rs.getInt("id"), rs.getInt("id_segmento"),
                        rs.getInt("id_endereco"), rs.getString("nome"));
                listaRetorno.add(unidade);
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
