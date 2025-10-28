package org.example.eitruck.Dao;

import org.example.eitruck.Conexao.Conexao;
import org.example.eitruck.model.Unidade;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UnidadeDAO {
    public boolean cadastrar(Unidade unidade) {
        Conexao conexao = new Conexao();
        Connection conn = null;

        String comando = """
            INSERT INTO unidade (id_segmento, id_endereco, nome)
            VALUES (?, ?, ?)""";

        try {
            conn = conexao.conectar();
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setInt(1, unidade.getIdSegmento());
            pstmt.setInt(2, unidade.getIdEndereco());
            pstmt.setString(3, unidade.getNome());
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

    public int apagar(int idUnidade) {
        Conexao conexao = new Conexao();
        Connection conn = null;

        String comando = "DELETE FROM unidade WHERE id = ?";

        try {
            conn = conexao.conectar();

            // Primeiro, verifica se existe algum analista usando esta unidade
            String verificaAnalista = "SELECT COUNT(*) FROM analista WHERE id_unidade = ?";
            PreparedStatement pstmtVerifica = conn.prepareStatement(verificaAnalista);
            pstmtVerifica.setInt(1, idUnidade);
            ResultSet rs = pstmtVerifica.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                // Existem analistas usando esta unidade, não pode excluir
                return -2; // Código especial para "em uso por analista"
            }

            // Se não há analistas usando, procede com a exclusão
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setInt(1, idUnidade);
            int execucao = pstmt.executeUpdate();
            return execucao;
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
            // Verifica se é erro de restrição de chave estrangeira
            if (sqle.getSQLState().equals("23503")) { // Código para violação de chave estrangeira no PostgreSQL
                return -2; // Também retorna -2 se houver violação de FK
            }
            return -1;
        }
        finally {
            conexao.desconectar(conn);
        }
    }

    public List<Unidade> buscarPorId(int idUnidade) {
        Conexao conexao = new Conexao();
        Connection conn = null;

        ResultSet rs;
        List<Unidade> listaRetorno = new ArrayList<>();
        String comando = "SELECT * FROM unidade WHERE id = ?";

        try {
            conn = conexao.conectar(); // Inicializar a conexão
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setInt(1, idUnidade);
            rs = pstmt.executeQuery(); // Corrigir: usar executeQuery() diretamente
            while (rs.next()){
                Unidade unidade = new Unidade(
                        rs.getInt("id"),
                        rs.getInt("id_segmento"),
                        rs.getInt("id_endereco"),
                        rs.getString("nome")
                );
                listaRetorno.add(unidade);
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

    public int alterarTodos(int id, String nome, int idSegmento, int idEndereco) {
        Conexao conexao = new Conexao();
        Connection conn = null;

        try {
            conn = conexao.conectar();
            PreparedStatement pstmt = conn.prepareStatement(
                    "UPDATE unidade SET nome = ?, id_segmento = ?, id_endereco = ? WHERE id = ?"
            );

            pstmt.setString(1, nome);
            pstmt.setInt(2, idSegmento);
            pstmt.setInt(3, idEndereco);
            pstmt.setInt(4, id);

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

    public List<Unidade> buscarTodos() {
        Conexao conexao = new Conexao();
        Connection conn = null;

        String comando = """
        SELECT 
            u.*,
            s.nome as nome_segmento,
            e.cidade as nome_endereco
        FROM unidade u
        INNER JOIN segmento s ON u.id_segmento = s.id
        INNER JOIN endereco e ON u.id_endereco = e.id
        ORDER BY id
        """;

        try {
            conn = conexao.conectar();
            PreparedStatement pstmt = conn.prepareStatement(comando);
            ResultSet rs = pstmt.executeQuery();

            List<Unidade> listaRetorno = new ArrayList<>();
            while (rs.next()) {
                Unidade unidade = new Unidade(
                        rs.getInt("id"),
                        rs.getInt("id_segmento"),
                        rs.getInt("id_endereco"),
                        rs.getString("nome"),
                        rs.getString("nome_segmento"),
                        rs.getString("nome_endereco")
                );
                listaRetorno.add(unidade);
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
        Conexao conexao = new Conexao();
        Connection conn = null;

        String comando = "SELECT COUNT(*) AS total FROM unidade";

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

    public List<Unidade> filtrarUnidadesMultiplos(String filtroId, String filtroNome, String filtroNomeSegmento, String filtroNomeEndereco) {
        Conexao conexao = new Conexao();
        Connection conn = null;

        ResultSet rs;
        List<Unidade> listaRetorno = new ArrayList<>();

        try {
            conn = conexao.conectar();
            // Usando JOIN para buscar o nome do segmento e o nome do endereço (cidade)
            StringBuilder sql = new StringBuilder("""
            SELECT 
                u.*,
                s.nome as nome_segmento,
                e.cidade as nome_endereco
            FROM unidade u
            INNER JOIN segmento s ON u.id_segmento = s.id
            INNER JOIN endereco e ON u.id_endereco = e.id
            WHERE 1=1
        """);

            List<Object> parametros = new ArrayList<>();

            // Filtro por ID (busca parcial)
            if (filtroId != null && !filtroId.trim().isEmpty()) {
                sql.append(" AND u.id::text LIKE ?");
                parametros.add("%" + filtroId.trim() + "%");
            }

            // Filtro por Nome (busca parcial case-insensitive)
            if (filtroNome != null && !filtroNome.trim().isEmpty()) {
                sql.append(" AND u.nome ILIKE ?");
                parametros.add("%" + filtroNome.trim() + "%");
            }

            // Filtro por Nome do Segmento (busca parcial case-insensitive)
            if (filtroNomeSegmento != null && !filtroNomeSegmento.trim().isEmpty()) {
                sql.append(" AND s.nome ILIKE ?");
                parametros.add("%" + filtroNomeSegmento.trim() + "%");
            }

            // Filtro por Nome do Endereço (cidade) (busca parcial case-insensitive)
            if (filtroNomeEndereco != null && !filtroNomeEndereco.trim().isEmpty()) {
                sql.append(" AND e.cidade ILIKE ?");
                parametros.add("%" + filtroNomeEndereco.trim() + "%");
            }

            sql.append(" ORDER BY u.id");

            PreparedStatement pstmt = conn.prepareStatement(sql.toString());

            // Preenche os parâmetros
            for (int i = 0; i < parametros.size(); i++) {
                pstmt.setObject(i + 1, parametros.get(i));
            }

            System.out.println("SQL Unidade: " + sql.toString());
            System.out.println("Parâmetros Unidade: " + parametros);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                Unidade unidade = new Unidade(
                        rs.getInt("id"),
                        rs.getInt("id_segmento"),
                        rs.getInt("id_endereco"),
                        rs.getString("nome"),
                        rs.getString("nome_segmento"),
                        rs.getString("nome_endereco")
                );
                listaRetorno.add(unidade);
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

    public int alterarIdSegmento(Unidade unidade, int novoIdSegmento) {
        Conexao conexao = new Conexao();
        Connection conn = null;

        String comando = "UPDATE unidade SET id_segmento = ? WHERE id = ?";

        try {
            conn = conexao.conectar();
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
        Conexao conexao = new Conexao();
        Connection conn = null;

        String comando = "UPDATE unidade SET id_endereco = ? WHERE id = ?";

        try {
            conn = conexao.conectar();
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
        Conexao conexao = new Conexao();
        Connection conn = null;

        String comando = "UPDATE unidade SET nome = ? WHERE id = ?";

        try {
            conn = conexao.conectar();
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

    public List<Unidade> buscarPorIdSegmento(int idSegmento) {
        Conexao conexao = new Conexao();
        Connection conn = null;

        ResultSet rs;
        List<Unidade> listaRetorno = new ArrayList<>();
        String comando = "SELECT * FROM unidade WHERE id_segmento = ?";

        try {
            conn = conexao.conectar();
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setInt(1, idSegmento);
            pstmt.executeQuery();
            rs = pstmt.getResultSet();
            while (rs.next()){
                Unidade unidade = new Unidade(rs.getInt("id"), rs.getInt("id_segmento"), rs.getInt("id_endereco"), rs.getString("nome"));
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
        Conexao conexao = new Conexao();
        Connection conn = null;

        ResultSet rs;
        List<Unidade> listaRetorno = new ArrayList<>();
        String comando = "SELECT * FROM unidade WHERE id_endereco = ?";

        try {
            conn = conexao.conectar();
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setInt(1, idEndereco);
            pstmt.executeQuery();
            rs = pstmt.getResultSet();
            while (rs.next()){
                Unidade unidade = new Unidade(rs.getInt("id"),
                        rs.getInt("id_segmento"),
                        rs.getInt("id_endereco"),
                        rs.getString("nome"));
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
        Conexao conexao = new Conexao();
        Connection conn = null;

        ResultSet rs;
        List<Unidade> listaRetorno = new ArrayList<>();
        String comando = "SELECT * FROM unidade WHERE nome = ?";

        try {
            conn = conexao.conectar();
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setString(1, nome);
            pstmt.executeQuery();
            rs = pstmt.getResultSet();
            while (rs.next()){
                Unidade unidade = new Unidade(rs.getInt("id"), rs.getInt("id_segmento"), rs.getInt("id_endereco"), rs.getString("nome"));
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