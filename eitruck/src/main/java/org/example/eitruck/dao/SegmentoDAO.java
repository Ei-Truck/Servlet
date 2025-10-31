package org.example.eitruck.dao;

import org.example.eitruck.conexao.Conexao;
import org.example.eitruck.model.Segmento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SegmentoDAO {
    // Método inserir
    public boolean cadastrar(Segmento segmento) {
        Conexao conexao = new Conexao();
        Connection conn = null;

        String comando = """
            INSERT INTO segmento (nome, descricao)
            VALUES (?, ?)""";

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

    // Método deletar
    public int apagar(int idSegmento) {
        Conexao conexao = new Conexao();
        Connection conn = null;
        String comando = "DELETE FROM segmento WHERE id = ?";

        try {
            conn = conexao.conectar();

            String verificaUnidade = "SELECT COUNT(*) FROM unidade WHERE id_segmento = ?";
            PreparedStatement pstmtVerifica = conn.prepareStatement(verificaUnidade);
            pstmtVerifica.setInt(1, idSegmento);
            ResultSet rs = pstmtVerifica.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                return -2;
            }

            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setInt(1, idSegmento);
            int execucao = pstmt.executeUpdate();
            return execucao;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            if (sqle.getSQLState().equals("23503")) {
                return -2;
            }
            return -1;
        } finally {
            conexao.desconectar(conn);
        }
    }

    // Método alterar
    public int alterarTodos(int id, String nome, String descricao) {

        Conexao conexao = new Conexao();
        Connection conn = null;

        try {
            conn = conexao.conectar();
            PreparedStatement pstmt = conn.prepareStatement(
                    "UPDATE segmento SET nome = ?, descricao = ? WHERE id = ?"
            );

            pstmt.setString(1, nome);
            pstmt.setString(2, descricao);
            pstmt.setInt(3, id);

            int linhasAfetadas = pstmt.executeUpdate();

            if (linhasAfetadas > 0) {
                return 1; // Sucesso - registro alterado
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
            return -1; // Erro
        }
        finally {
            conexao.desconectar(conn);
        }
        return 0; // Nenhum registro alterado
    }

    // Método mostrar os registros
    public List<Segmento> buscarTodos() {
        Conexao conexao = new Conexao();
        Connection conn = null;

        ResultSet rs;
        List<Segmento> listaRetorno = new ArrayList<>();
        String comando = "SELECT * FROM segmento ORDER BY id";

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

    // Método quantidade de registros
    public int numeroRegistros() {
        Conexao conexao = new Conexao();
        Connection conn = null;

        String comando = "SELECT COUNT(*) AS total FROM segmento";

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
    public List<Segmento> filtrarSegmentosMultiplos(String filtroId, String filtroNome, String filtroDescricao) {
        Conexao conexao = new Conexao();
        Connection conn = null;
        ResultSet rs;
        List<Segmento> listaRetorno = new ArrayList<>();

        try {
            conn = conexao.conectar();
            StringBuilder sql = new StringBuilder("SELECT * FROM segmento WHERE 1=1");
            List<Object> parametros = new ArrayList<>();

            if (filtroId != null && !filtroId.trim().isEmpty()) {
                try {
                    int id = Integer.parseInt(filtroId.trim());
                    sql.append(" AND id = ?");
                    parametros.add(id);
                } catch (NumberFormatException e) {
                    sql.append(" AND id::text LIKE ?");
                    parametros.add("%" + filtroId.trim() + "%");
                }
            }

            // Filtro por Nome (busca parcial case-insensitive)
            if (filtroNome != null && !filtroNome.trim().isEmpty()) {
                sql.append(" AND nome ILIKE ?");
                parametros.add("%" + filtroNome.trim() + "%");
            }

            // Filtro por Descrição (busca parcial case-insensitive)
            if (filtroDescricao != null && !filtroDescricao.trim().isEmpty()) {
                sql.append(" AND descricao ILIKE ?");
                parametros.add("%" + filtroDescricao.trim() + "%");
            }

            sql.append(" ORDER BY id");

            PreparedStatement pstmt = conn.prepareStatement(sql.toString());

            // Preenche os parâmetros
            for (int i = 0; i < parametros.size(); i++) {
                pstmt.setObject(i + 1, parametros.get(i));
            }

            System.out.println("SQL: " + sql.toString());
            System.out.println("Parâmetros: " + parametros);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                Segmento segmento = new Segmento(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("descricao")
                );
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

    // Métodos buscar por ID
    public List<Segmento> buscarPorId(int idSegmento) {
        Conexao conexao = new Conexao();
        Connection conn = null;

        ResultSet rs;
        List<Segmento> listaRetorno = new ArrayList<>();
        String comando = "SELECT * FROM segmento WHERE id = ?";

        try {
            conn = conexao.conectar();
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setInt(1, idSegmento);
            rs = pstmt.executeQuery();
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

    // Métodos alterar individuais
    public int alterarNome(Segmento segmento, String novoNome) {
        Conexao conexao = new Conexao();
        Connection conn = null;

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
        Conexao conexao = new Conexao();
        Connection conn = null;

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

    // Métodos buscar individuais
    public List<Segmento> buscarPorNome(String nome) {
        Conexao conexao = new Conexao();
        Connection conn = null;

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

    public List<Segmento> buscarPorDescricao(String descricao) {
        Conexao conexao = new Conexao();
        Connection conn = null;

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