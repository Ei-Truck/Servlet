package Dao;



import model.Endereco;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnderecoDAO extends DAO {
    public EnderecoDAO() throws SQLException, ClassNotFoundException{
        super();
    }

    public boolean cadastrar(Endereco endereco) {
        String comando = """
            INSERT INTO endereco (id, cep, rua, numero, bairro, cidade, estado, pais)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)""";

        try {
            conn = conexao.conectar();
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setInt(1, endereco.getId());
            pstmt.setString(2, endereco.getCep());
            pstmt.setString(3, endereco.getRua());
            pstmt.setString(4, endereco.getNumero());
            pstmt.setString(5, endereco.getBairro());
            pstmt.setString(6, endereco.getCidade());
            pstmt.setString(7, endereco.getEstado());
            pstmt.setString(8, endereco.getPais());
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
    public int alterarCep(Endereco endereco, String novoCep) {
        String comando = "UPDATE endereco SET cep = ? WHERE id = ?";

        try {
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

    public int alterarNumero(Endereco endereco, String novoNumero) {
        String comando = "UPDATE endereco SET numero = ? WHERE id = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setString(1, novoNumero);
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

    public int apagar(Endereco endereco, int idEndereco) {
        String comando = "DELETE FROM endereco WHERE id = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setInt(1, idEndereco);
            int execucao = pstmt.executeUpdate();
            if (execucao > 0) {
                endereco = null;
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
                        rs.getString("numero"), rs.getString("bairro"), rs.getString("cidade"),
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
                        rs.getString("numero"), rs.getString("bairro"), rs.getString("cidade"),
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
                        rs.getString("numero"), rs.getString("bairro"), rs.getString("cidade"),
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
                        rs.getString("numero"), rs.getString("bairro"), rs.getString("cidade"),
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
                        rs.getString("numero"), rs.getString("bairro"), rs.getString("cidade"),
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
                        rs.getString("numero"), rs.getString("bairro"), rs.getString("cidade"),
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

