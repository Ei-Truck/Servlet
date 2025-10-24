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

    public int apagar(int idSegmento) {
        String comando = "DELETE FROM segmento WHERE id = ?";
        Connection conn = null;

        try {
            conn = conexao.conectar();

            // Primeiro, verifica se existe alguma unidade usando este segmento
            String verificaUnidade = "SELECT COUNT(*) FROM unidade WHERE id_segmento = ?";
            PreparedStatement pstmtVerifica = conn.prepareStatement(verificaUnidade);
            pstmtVerifica.setInt(1, idSegmento);
            ResultSet rs = pstmtVerifica.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                // Existem unidades usando este segmento, não pode excluir
                return -2; // Código especial para "em uso por unidade"
            }

            // Se não há unidades usando, procede com a exclusão
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setInt(1, idSegmento);
            int execucao = pstmt.executeUpdate();
            return execucao;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            // Verifica se é erro de restrição de chave estrangeira
            if (sqle.getSQLState().equals("23503")) { // Código para violação de chave estrangeira no PostgreSQL
                return -2; // Também retorna -2 se houver violação de FK
            }
            return -1;
        } finally {
            conexao.desconectar(conn);
        }
    }

    public List<Segmento> buscarPorId(int idSegmento) {
        ResultSet rs;
        List<Segmento> listaRetorno = new ArrayList<>();
        String comando = "SELECT * FROM segmento WHERE id = ?";
        Connection conn = null; // Adicionar esta linha

        try {
            conn = conexao.conectar(); // Inicializar a conexão
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setInt(1, idSegmento);
            rs = pstmt.executeQuery(); // Corrigir: usar executeQuery() diretamente
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
            conexao.desconectar(conn); // Usar a variável local conn
        }
    }

    public int alterarTodos(int id, String nome, String descricao) {
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

        } catch (SQLException e) {
            e.printStackTrace();
            return -1; // Erro
        } finally {
            conexao.desconectar(conn);
        }
        return 0; // Nenhum registro alterado
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

    public List<Segmento> filtrarSegmentosMultiplos(String filtroId, String filtroNome, String filtroDescricao) {
        ResultSet rs;
        List<Segmento> listaRetorno = new ArrayList<>();
        Connection conn = null;

        try {
            conn = conexao.conectar();
            StringBuilder sql = new StringBuilder("SELECT * FROM segmento WHERE 1=1");
            List<Object> parametros = new ArrayList<>();

            // Filtro por ID (busca exata ou parcial)
            if (filtroId != null && !filtroId.trim().isEmpty()) {
                // Tenta converter para número para busca exata, senão busca como texto
                try {
                    int id = Integer.parseInt(filtroId.trim());
                    sql.append(" AND id = ?");
                    parametros.add(id);
                } catch (NumberFormatException e) {
                    // Se não é número, busca como texto parcial
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

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return null;
        } finally {
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