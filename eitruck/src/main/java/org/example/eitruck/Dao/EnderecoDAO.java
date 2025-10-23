package org.example.eitruck.Dao;

import org.example.eitruck.model.Endereco;
import org.example.eitruck.model.Unidade;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnderecoDAO extends DAO {
    public EnderecoDAO() {
        super();
    }

    public boolean cadastrar(Endereco endereco) {
        String comando = """
            INSERT INTO endereco (cep, rua, numero, bairro, cidade, estado, pais)
            VALUES (?, ?, ?, ?, ?, ?, ?)""";

        Connection conn = null;
        try {
            conn = conexao.conectar();
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setString(1, endereco.getCep());
            pstmt.setString(2, endereco.getRua());
            pstmt.setInt(3, endereco.getNumero());
            pstmt.setString(4, endereco.getBairro());
            pstmt.setString(5, endereco.getCidade());
            pstmt.setString(6, endereco.getEstado());
            pstmt.setString(7, endereco.getPais());
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

    public int apagar(int idEndereco) {
        String comando = "DELETE FROM endereco WHERE id = ?";
        Connection conn = null;

        try {
            conn = conexao.conectar();

            // Primeiro, verifica se existe alguma unidade usando este endereço
            String verificaUnidade = "SELECT COUNT(*) FROM unidade WHERE id_endereco = ?";
            PreparedStatement pstmtVerifica = conn.prepareStatement(verificaUnidade);
            pstmtVerifica.setInt(1, idEndereco);
            ResultSet rs = pstmtVerifica.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                // Existem unidades usando este endereço, não pode excluir
                return -2; // Código especial para "em uso por unidade"
            }

            // Se não há unidades usando, procede com a exclusão
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setInt(1, idEndereco);
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

    public List<Endereco> buscarTodos() {
        ResultSet rs;
        List<Endereco> listaRetorno = new ArrayList<>();
        String comando = "SELECT * FROM endereco";

        Connection conn = null;
        try {
            conn = conexao.conectar();
            PreparedStatement pstmt = conn.prepareStatement(comando);
            rs = pstmt.executeQuery();
            while (rs.next()){
                Endereco endereco = new Endereco(rs.getInt("id"), rs.getString("cep"), rs.getString("rua"),
                        rs.getInt("numero"), rs.getString("bairro"), rs.getString("cidade"),
                        rs.getString("estado"), rs.getString("pais"));
                listaRetorno.add(endereco);
            }
            return listaRetorno;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return null;
        } finally {
            conexao.desconectar(conn);
        }
    }

    public int numeroRegistros() {
        String comando = "SELECT COUNT(*) AS total FROM endereco";
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

    public List<Endereco> filtrarEnderecosMultiplos(String filtroId, String filtroCep, String filtroRua,
                                                    String filtroNumero, String filtroBairro, String filtroCidade,
                                                    String filtroEstado, String filtroPais) {
        ResultSet rs;
        List<Endereco> listaRetorno = new ArrayList<>();
        Connection conn = null;

        try {
            conn = conexao.conectar();
            StringBuilder sql = new StringBuilder("SELECT * FROM endereco WHERE 1=1");
            List<Object> parametros = new ArrayList<>();

            // Filtro por ID (busca parcial)
            if (filtroId != null && !filtroId.trim().isEmpty()) {
                sql.append(" AND id::text LIKE ?");
                parametros.add("%" + filtroId.trim() + "%");
            }

            // Filtro por CEP (busca parcial)
            if (filtroCep != null && !filtroCep.trim().isEmpty()) {
                sql.append(" AND cep LIKE ?");
                parametros.add("%" + filtroCep.trim() + "%");
            }

            // Filtro por Rua (busca parcial case-insensitive)
            if (filtroRua != null && !filtroRua.trim().isEmpty()) {
                sql.append(" AND rua ILIKE ?");
                parametros.add("%" + filtroRua.trim() + "%");
            }

            // Filtro por Número (busca parcial)
            if (filtroNumero != null && !filtroNumero.trim().isEmpty()) {
                sql.append(" AND numero::text LIKE ?");
                parametros.add("%" + filtroNumero.trim() + "%");
            }

            // Filtro por Bairro (busca parcial case-insensitive)
            if (filtroBairro != null && !filtroBairro.trim().isEmpty()) {
                sql.append(" AND bairro ILIKE ?");
                parametros.add("%" + filtroBairro.trim() + "%");
            }

            // Filtro por Cidade (busca parcial case-insensitive)
            if (filtroCidade != null && !filtroCidade.trim().isEmpty()) {
                sql.append(" AND cidade ILIKE ?");
                parametros.add("%" + filtroCidade.trim() + "%");
            }

            // Filtro por Estado (busca parcial case-insensitive)
            if (filtroEstado != null && !filtroEstado.trim().isEmpty()) {
                sql.append(" AND estado ILIKE ?");
                parametros.add("%" + filtroEstado.trim() + "%");
            }

            // Filtro por País (busca parcial case-insensitive)
            if (filtroPais != null && !filtroPais.trim().isEmpty()) {
                sql.append(" AND pais ILIKE ?");
                parametros.add("%" + filtroPais.trim() + "%");
            }

            sql.append(" ORDER BY id");

            PreparedStatement pstmt = conn.prepareStatement(sql.toString());

            // Preenche os parâmetros
            for (int i = 0; i < parametros.size(); i++) {
                pstmt.setObject(i + 1, parametros.get(i));
            }

            System.out.println("SQL Endereço: " + sql.toString());
            System.out.println("Parâmetros Endereço: " + parametros);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                Endereco endereco = new Endereco(
                        rs.getInt("id"),
                        rs.getString("cep"),
                        rs.getString("rua"),
                        rs.getInt("numero"),
                        rs.getString("bairro"),
                        rs.getString("cidade"),
                        rs.getString("estado"),
                        rs.getString("pais")
                );
                listaRetorno.add(endereco);
            }
            return listaRetorno;

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return null;
        } finally {
            conexao.desconectar(conn);
        }
    }

    public int alterarCep(Endereco endereco, String novoCep) {
        String comando = "UPDATE endereco SET cep = ? WHERE id = ?";
        Connection conn = null;

        try {
            conn = conexao.conectar();
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setString(1, novoCep);
            pstmt.setInt(2, endereco.getId());
            int execucao = pstmt.executeUpdate();
            if (execucao > 0) {
                endereco.setRua(novoCep);
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
    public int alterarEndereco(int id, String cep, String rua, int numero, String bairro, String cidade, String estado, String pais) {
        Connection conn = null;

        try {
            conn = conexao.conectar();
            PreparedStatement pstmt = conn.prepareStatement(
                    "UPDATE endereco SET cep = ?, rua = ?, numero = ?, bairro = ?, cidade = ?, estado = ?, pais = ? WHERE id = ?"
            );

            pstmt.setString(1, cep);
            pstmt.setString(2, rua);
            pstmt.setInt(3, numero);
            pstmt.setString(4, bairro);
            pstmt.setString(5, cidade);
            pstmt.setString(6, estado);
            pstmt.setString(7, pais);
            pstmt.setInt(8, id);

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
    public int alterarRua(Endereco endereco, String novaRua) {
        String comando = "UPDATE endereco SET rua = ? WHERE id = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setString(1, novaRua);
            pstmt.setInt(2, endereco.getId());
            int execucao = pstmt.executeUpdate();
            if (execucao > 0) {
                endereco.setRua(novaRua);
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

    public int alterarNumero(Endereco endereco, int novoNumero) {
        String comando = "UPDATE endereco SET numero = ? WHERE id = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setInt(1, novoNumero);
            pstmt.setInt(2, endereco.getId());
            int execucao = pstmt.executeUpdate();
            if (execucao > 0){
                endereco.setNumero(novoNumero);
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
    public int alterarBairro(Endereco endereco, String novoBairro) {
        String comando = "UPDATE endereco SET bairro = ? WHERE id = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setString(1, novoBairro);
            pstmt.setInt(2, endereco.getId());
            int execucao = pstmt.executeUpdate();
            if (execucao > 0){
                endereco.setRua(novoBairro);
                return 1;
            } else {
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
    public int alterarCidade(Endereco endereco, String novaCidade) {
        String comando = "UPDATE endereco SET cidade = ? WHERE id = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setString(1, novaCidade);
            pstmt.setInt(2, endereco.getId());
            int execucao = pstmt.executeUpdate();
            if (execucao > 0){
                endereco.setRua(novaCidade);
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

    public List<Endereco> buscarPorId(int idEndereco) {
        ResultSet rs;
        List<Endereco> listaRetorno = new ArrayList<>();
        String comando = "SELECT * FROM endereco WHERE id = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setInt(1, idEndereco);
            pstmt.executeQuery();
            rs = pstmt.getResultSet();
            while (rs.next()){
                Endereco endereco = new Endereco(rs.getInt("id"), rs.getString("cep"), rs.getString("rua"),
                        rs.getInt("numero"), rs.getString("bairro"), rs.getString("cidade"),
                        rs.getString("estado"), rs.getString("pais"));
                listaRetorno.add(endereco);
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

    public List<Endereco> buscarPorCep(String cep) {
        ResultSet rs;
        List<Endereco> listaRetorno = new ArrayList<>();
        String comando = "SELECT * FROM endereco WHERE cep = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setString(1, cep);
            pstmt.executeQuery();
            rs = pstmt.getResultSet();
            while (rs.next()){
                Endereco endereco = new Endereco(rs.getInt("id"), rs.getString("cep"), rs.getString("rua"),
                        rs.getInt("numero"), rs.getString("bairro"), rs.getString("cidade"),
                        rs.getString("estado"), rs.getString("pais"));
                listaRetorno.add(endereco);
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

    public List<Endereco> buscarPorRua(String rua) {
        ResultSet rs;
        List<Endereco> listaRetorno = new ArrayList<>();
        String comando = "SELECT * FROM endereco WHERE rua = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setString(1, rua);
            pstmt.executeQuery();
            rs = pstmt.getResultSet();
            while (rs.next()){
                Endereco endereco = new Endereco(rs.getInt("id"), rs.getString("cep"), rs.getString("rua"),
                        rs.getInt("numero"), rs.getString("bairro"), rs.getString("cidade"),
                        rs.getString("estado"), rs.getString("pais"));
                listaRetorno.add(endereco);
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
    public List<Endereco> buscarPorCidade(String cidade) {
        ResultSet rs;
        List<Endereco> listaRetorno = new ArrayList<>();
        String comando = "SELECT * FROM endereco WHERE cidade = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setString(1, cidade);
            pstmt.executeQuery();
            rs = pstmt.getResultSet();
            while (rs.next()){
                Endereco endereco = new Endereco(rs.getInt("id"), rs.getString("cep"), rs.getString("rua"),
                        rs.getInt("numero"), rs.getString("bairro"), rs.getString("cidade"),
                        rs.getString("estado"), rs.getString("pais"));
                listaRetorno.add(endereco);
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
    public List<Endereco> buscarPorEstado(String estado) {
        ResultSet rs;
        List<Endereco> listaRetorno = new ArrayList<>();
        String comando = "SELECT * FROM endereco WHERE estado = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setString(1, estado);
            pstmt.executeQuery();
            rs = pstmt.getResultSet();
            while (rs.next()){
                Endereco endereco = new Endereco(rs.getInt("id"), rs.getString("cep"), rs.getString("rua"),
                        rs.getInt("numero"), rs.getString("bairro"), rs.getString("cidade"),
                        rs.getString("estado"), rs.getString("pais"));
                listaRetorno.add(endereco);
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
    public List<Endereco> buscarPorPais(String pais) {
        ResultSet rs;
        List<Endereco> listaRetorno = new ArrayList<>();
        String comando = "SELECT * FROM endereco WHERE pais = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setString(1, pais);
            pstmt.executeQuery();
            rs = pstmt.getResultSet();
            while (rs.next()){
                Endereco endereco = new Endereco(rs.getInt("id"), rs.getString("cep"), rs.getString("rua"),
                        rs.getInt("numero"), rs.getString("bairro"), rs.getString("cidade"),
                        rs.getString("estado"), rs.getString("pais"));
                listaRetorno.add(endereco);
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