package org.example.eitruck.dao;

import org.example.eitruck.conexao.Conexao;
import org.example.eitruck.model.Endereco;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnderecoDAO {
    // Método inserir
    public boolean cadastrar(Endereco endereco) {
        Conexao conexao = new Conexao();
        Connection conn = null;

        String comando = """
            INSERT INTO endereco (cep, rua, numero, bairro, cidade, estado, pais)
            VALUES (?, ?, ?, ?, ?, ?, ?)""";

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

    // Método deletar
    public int apagar(int idEndereco) {
        Conexao conexao = new Conexao();
        Connection conn = null;

        String comando = "DELETE FROM endereco WHERE id = ?";

        try {
            conn = conexao.conectar();

            String verificaUnidade = "SELECT COUNT(*) FROM unidade WHERE id_endereco = ?";
            PreparedStatement pstmtVerifica = conn.prepareStatement(verificaUnidade);
            pstmtVerifica.setInt(1, idEndereco);
            ResultSet rs = pstmtVerifica.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                return -2;
            }

            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setInt(1, idEndereco);
            int execucao = pstmt.executeUpdate();
            return execucao;
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
            if (sqle.getSQLState().equals("23503")) {
                return -2;
            }
            return -1;
        }
        finally {
            conexao.desconectar(conn);
        }
    }

    // Método alterar
    public int alterarEndereco(int id, String cep, String rua, int numero, String bairro, String cidade, String estado, String pais) {
        Conexao conexao = new Conexao();
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
    public List<Endereco> buscarTodos() {
        Conexao conexao = new Conexao();
        Connection conn = null;

        ResultSet rs;
        List<Endereco> listaRetorno = new ArrayList<>();
        String comando = "SELECT * FROM endereco ORDER BY id";

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

        String comando = "SELECT COUNT(*) AS total FROM endereco";

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
    public List<Endereco> filtrarEnderecosMultiplos(String filtroId, String filtroCep, String filtroRua, String filtroNumero, String filtroBairro, String filtroCidade, String filtroEstado, String filtroPais) {
        Conexao conexao = new Conexao();
        Connection conn = null;

        ResultSet rs;
        List<Endereco> listaRetorno = new ArrayList<>();

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

        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
            return null;
        }
        finally {
            conexao.desconectar(conn);
        }
    }

    // Métodos individuais de alteração (mantidos para compatibilidade)
    public int alterarCep(Endereco endereco, String novoCep) {
        Conexao conexao = new Conexao();
        Connection conn = null;

        String comando = "UPDATE endereco SET cep = ? WHERE id = ?";

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

    public int alterarRua(Endereco endereco, String novaRua) {
        Conexao conexao = new Conexao();
        Connection conn = null;

        String comando = "UPDATE endereco SET rua = ? WHERE id = ?";

        try {
            conn = conexao.conectar();
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
        Conexao conexao = new Conexao();
        Connection conn = null;

        String comando = "UPDATE endereco SET numero = ? WHERE id = ?";

        try {
            conn = conexao.conectar();
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
        Conexao conexao = new Conexao();
        Connection conn = null;

        String comando = "UPDATE endereco SET bairro = ? WHERE id = ?";

        try {
            conn = conexao.conectar();
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
        Conexao conexao = new Conexao();
        Connection conn = null;

        String comando = "UPDATE endereco SET cidade = ? WHERE id = ?";

        try {
            conn = conexao.conectar();
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

    // Métodos individuais de buscar (mantidos para compatibilidade)
    public List<Endereco> buscarPorId(int idEndereco) {
        Conexao conexao = new Conexao();
        Connection conn = null;

        ResultSet rs = null;
        List<Endereco> listaRetorno = new ArrayList<>();
        String comando = "SELECT * FROM endereco WHERE id = ?";
        PreparedStatement pstmt = null;

        try {
            conn = conexao.conectar();
            pstmt = conn.prepareStatement(comando);
            pstmt.setInt(1, idEndereco);
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
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
            return null;
        }
        finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Endereco> buscarPorCep(String cep) {
        Conexao conexao = new Conexao();
        Connection conn = null;

        ResultSet rs;
        List<Endereco> listaRetorno = new ArrayList<>();
        String comando = "SELECT * FROM endereco WHERE cep = ?";

        try {
            conn = conexao.conectar();
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
        Conexao conexao = new Conexao();
        Connection conn = null;

        ResultSet rs;
        List<Endereco> listaRetorno = new ArrayList<>();
        String comando = "SELECT * FROM endereco WHERE rua = ?";

        try {
            conn = conexao.conectar();
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
        Conexao conexao = new Conexao();
        Connection conn = null;

        ResultSet rs;
        List<Endereco> listaRetorno = new ArrayList<>();
        String comando = "SELECT * FROM endereco WHERE cidade = ?";

        try {
            conn = conexao.conectar();
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
        Conexao conexao = new Conexao();
        Connection conn = null;

        ResultSet rs;
        List<Endereco> listaRetorno = new ArrayList<>();
        String comando = "SELECT * FROM endereco WHERE estado = ?";

        try {
            conn = conexao.conectar();
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
        Conexao conexao = new Conexao();
        Connection conn = null;

        ResultSet rs;
        List<Endereco> listaRetorno = new ArrayList<>();
        String comando = "SELECT * FROM endereco WHERE pais = ?";

        try {
            conn = conexao.conectar();
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