package org.example.eitruck.Dao;

import org.example.eitruck.model.Segmento;
import org.example.eitruck.model.TipoOcorrencia;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TipoOcorrenciaDAO extends DAO {
    public TipoOcorrenciaDAO() {
        super();
    }

    public boolean cadastrar(TipoOcorrencia tipoOcorrencia) {
        String comando = """
            INSERT INTO tipo_ocorrencia (id, tipo_evento, pontuacao, gravidade)
            VALUES (?, ?, ?, ?)""";

        try {
            conn = conexao.conectar();
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setInt(1, tipoOcorrencia.getId());
            pstmt.setString(2, tipoOcorrencia.getTipoEvento());
            pstmt.setInt(3, tipoOcorrencia.getPontuacao());
            pstmt.setString(4, tipoOcorrencia.getGravidade());
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

    public int alterarTipoEvento(TipoOcorrencia tipoOcorrencia, String novoTipoEvento) {
        String comando = "UPDATE tipo_ocorrencia SET tipo_evento = ? WHERE id = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setString(1, novoTipoEvento);
            pstmt.setInt(2, tipoOcorrencia.getId());
            int execucao = pstmt.executeUpdate();
            if (execucao > 0) {
                tipoOcorrencia.setTipoEvento(novoTipoEvento);
                return 1;
            }
            else {
                return 0;
            }
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
            return -1;
        }
        finally {
            conexao.desconectar(conn);
        }
    }

    public int alterarPontuacao(TipoOcorrencia tipoOcorrencia, String novaPontuacao) {
        String comando = "UPDATE tipo_ocorrencia SET pontuacao = ? WHERE id = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setString(1, novaPontuacao);
            pstmt.setInt(2, tipoOcorrencia.getId());
            int execucao = pstmt.executeUpdate();
            if (execucao > 0) {
                tipoOcorrencia.setGravidade(novaPontuacao);
                return 1;
            }
            else {
                return 0;
            }
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
            return -1;
        }
        finally {
            conexao.desconectar(conn);
        }
    }

    public int alterarGravidade(TipoOcorrencia tipoOcorrencia, String novaGravidade) {
        String comando = "UPDATE tipo_ocorrencia SET gravidade = ? WHERE id = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setString(1, novaGravidade);
            pstmt.setInt(2, tipoOcorrencia.getId());
            int execucao = pstmt.executeUpdate();
            if (execucao > 0) {
                tipoOcorrencia.setGravidade(novaGravidade);
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

    public int apagar(TipoOcorrencia tipoOcorrencia, int idTipoOcorrencia) {
        String comando = "DELETE FROM tipo_ocorrencia WHERE id = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setInt(1, idTipoOcorrencia);
            int execucao = pstmt.executeUpdate();
            if (execucao > 0) {
                tipoOcorrencia = null;
                return 1;
            } else return 0;
        }
        catch (SQLException sqle){
            sqle.printStackTrace();
            return -1;
        }
        finally {
            conexao.desconectar(conn);
        }
    }

    public List<TipoOcorrencia> buscarTodos() {
        ResultSet rs;
        List<TipoOcorrencia> listaRetorno = new ArrayList<>();
        String comando = "SELECT * FROM tipo_ocorrencia";

        Connection conn = null;
        try {
            conn = conexao.conectar();
            PreparedStatement pstmt = conn.prepareStatement(comando);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                TipoOcorrencia tipo = new TipoOcorrencia(rs.getInt("id"), rs.getString("tipo_evento"), rs.getInt("pontuacao"), rs.getString("gravidade"));
                listaRetorno.add(tipo);
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

    public List<TipoOcorrencia> buscarPorId(int idTipoOcorrencia) {
        ResultSet rs;
        List<TipoOcorrencia> listaRetorno = new ArrayList<>();
        String comando = "SELECT * FROM tipo_ocorrencia WHERE id = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setInt(1, idTipoOcorrencia);
            pstmt.executeQuery();
            rs = pstmt.getResultSet();
            while (rs.next()) {
                TipoOcorrencia tipo = new TipoOcorrencia(rs.getInt("id"), rs.getString("tipo_evento"), rs.getInt("pontuacao"), rs.getString("gravidade"));
                listaRetorno.add(tipo);
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

    public List<TipoOcorrencia> buscarPorTipoEvento(String tipoEvento) {
        ResultSet rs;
        List<TipoOcorrencia> listaRetorno = new ArrayList<>();
        String comando = "SELECT * FROM tipo_ocorrencia WHERE tipo_evento = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setString(1, tipoEvento);
            pstmt.executeQuery();
            rs = pstmt.getResultSet();
            while (rs.next()) {
                TipoOcorrencia tipo = new TipoOcorrencia(rs.getInt("id"), rs.getString("tipo_evento"), rs.getInt("pontuacao"), rs.getString("gravidade"));
                listaRetorno.add(tipo);
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

    public List<TipoOcorrencia> buscarPorPontuacao(int pontuacaoTipoOcorrencia) {
        ResultSet rs;
        List<TipoOcorrencia> listaRetorno = new ArrayList<>();
        String comando = "SELECT * FROM tipo_ocorrencia WHERE pontuacao = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setInt(1, pontuacaoTipoOcorrencia);
            pstmt.executeQuery();
            rs = pstmt.getResultSet();
            while (rs.next()) {
                TipoOcorrencia tipo = new TipoOcorrencia(rs.getInt("id"), rs.getString("tipo_evento"), rs.getInt("pontuacao"), rs.getString("gravidade"));
                listaRetorno.add(tipo);
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

    public List<TipoOcorrencia> buscarPorGravidade(String gravidade) {
        ResultSet rs;
        List<TipoOcorrencia> listaRetorno = new ArrayList<>();
        String comando = "SELECT * FROM tipo_ocorrencia WHERE gravidade = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setString(1, gravidade);
            pstmt.executeQuery();
            rs = pstmt.getResultSet();
            while (rs.next()) {
                TipoOcorrencia tipo = new TipoOcorrencia(rs.getInt("id"), rs.getString("tipo_evento"), rs.getInt("pontuacao"), rs.getString("gravidade"));
                listaRetorno.add(tipo);
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
}
