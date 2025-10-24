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
            INSERT INTO tipo_ocorrencia (tipo_evento, pontuacao, gravidade)
            VALUES (?, ?, ?)""";

        Connection conn = null;
        try {
            conn = conexao.conectar();
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setString(1, tipoOcorrencia.getTipoEvento());
            pstmt.setInt(2, tipoOcorrencia.getPontuacao());
            pstmt.setString(3, tipoOcorrencia.getGravidade());
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

    public int apagar(int idOcorrencia) {
        String comando = "DELETE FROM tipo_ocorrencia WHERE id = ?";
        Connection conn = null;

        try {
            conn = conexao.conectar();
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setInt(1, idOcorrencia);
            int execucao = pstmt.executeUpdate();
            return execucao;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return -1;
        } finally {
            conexao.desconectar(conn);
        }
    }

    public List<TipoOcorrencia> buscarPorId(int idTipoOcorrencia) {
        ResultSet rs;
        List<TipoOcorrencia> listaRetorno = new ArrayList<>();
        String comando = "SELECT * FROM tipo_ocorrencia WHERE id = ?";
        Connection conn = null; // Adicionar esta linha

        try {
            conn = conexao.conectar(); // Inicializar a conexão
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setInt(1, idTipoOcorrencia);
            rs = pstmt.executeQuery(); // Corrigir: usar executeQuery() diretamente
            while (rs.next()) {
                TipoOcorrencia tipo = new TipoOcorrencia(
                        rs.getInt("id"),
                        rs.getString("tipo_evento"),
                        rs.getInt("pontuacao"),
                        rs.getString("gravidade")
                );
                listaRetorno.add(tipo);
            }
            return listaRetorno;
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
            return null;
        }
        finally {
            conexao.desconectar(conn); // Usar a variável local conn
        }
    }

    public int alterarTodos(int id, String tipoEvento, int pontuacao, String gravidade) {
        Connection conn = null;
        try {
            conn = conexao.conectar();
            PreparedStatement pstmt = conn.prepareStatement(
                    "UPDATE tipo_ocorrencia SET tipo_evento = ?, pontuacao = ?, gravidade = ? WHERE id = ?"
            );

            pstmt.setString(1, tipoEvento);
            pstmt.setInt(2, pontuacao);
            pstmt.setString(3, gravidade);
            pstmt.setInt(4, id);

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

    public int numeroRegistros() {
        String comando = "SELECT COUNT(*) AS total FROM tipo_ocorrencia";
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

    public List<TipoOcorrencia> filtrarTiposOcorrenciaMultiplos(String filtroId, String filtroTipoEvento,
                                                                String filtroPontuacao, String filtroGravidade) {
        ResultSet rs;
        List<TipoOcorrencia> listaRetorno = new ArrayList<>();
        Connection conn = null;

        try {
            conn = conexao.conectar();
            StringBuilder sql = new StringBuilder("SELECT * FROM tipo_ocorrencia WHERE 1=1");
            List<Object> parametros = new ArrayList<>();

            // Filtro por ID (busca parcial)
            if (filtroId != null && !filtroId.trim().isEmpty()) {
                sql.append(" AND id::text LIKE ?");
                parametros.add("%" + filtroId.trim() + "%");
            }

            // Filtro por Tipo de Evento (busca parcial case-insensitive)
            if (filtroTipoEvento != null && !filtroTipoEvento.trim().isEmpty()) {
                sql.append(" AND tipo_evento ILIKE ?");
                parametros.add("%" + filtroTipoEvento.trim() + "%");
            }

            // Filtro por Pontuação (busca parcial)
            if (filtroPontuacao != null && !filtroPontuacao.trim().isEmpty()) {
                // Tenta converter para número para busca exata, senão busca como texto
                try {
                    int pontuacao = Integer.parseInt(filtroPontuacao.trim());
                    sql.append(" AND pontuacao = ?");
                    parametros.add(pontuacao);
                } catch (NumberFormatException e) {
                    // Se não é número, busca como texto parcial
                    sql.append(" AND pontuacao::text LIKE ?");
                    parametros.add("%" + filtroPontuacao.trim() + "%");
                }
            }

            // Filtro por Gravidade (busca parcial case-insensitive)
            if (filtroGravidade != null && !filtroGravidade.trim().isEmpty()) {
                sql.append(" AND gravidade ILIKE ?");
                parametros.add("%" + filtroGravidade.trim() + "%");
            }

            sql.append(" ORDER BY id");

            PreparedStatement pstmt = conn.prepareStatement(sql.toString());

            // Preenche os parâmetros
            for (int i = 0; i < parametros.size(); i++) {
                pstmt.setObject(i + 1, parametros.get(i));
            }

            System.out.println("SQL Tipo Ocorrência: " + sql.toString());
            System.out.println("Parâmetros Tipo Ocorrência: " + parametros);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                TipoOcorrencia tipoOcorrencia = new TipoOcorrencia(
                        rs.getInt("id"),
                        rs.getString("tipo_evento"),
                        rs.getInt("pontuacao"),
                        rs.getString("gravidade")
                );
                listaRetorno.add(tipoOcorrencia);
            }
            return listaRetorno;

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return null;
        } finally {
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

    public int alterarPontuacao(TipoOcorrencia tipoOcorrencia, int novaPontuacao) { // Mudei para int
        String comando = "UPDATE tipo_ocorrencia SET pontuacao = ? WHERE id = ?";
        Connection conn = null;

        try {
            conn = conexao.conectar();
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setInt(1, novaPontuacao); // Mudei para setInt
            pstmt.setInt(2, tipoOcorrencia.getId());
            int execucao = pstmt.executeUpdate();
            if (execucao > 0) {
                tipoOcorrencia.setPontuacao(novaPontuacao); // Corrigido para setPontuacao
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