package Dao;

import model.Analista;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnalistaDAO extends DAO {
    public AnalistaDAO() throws SQLException, ClassNotFoundException{
        super();
    }

    public boolean cadastrar(Analista analista) {
        String comando = """
            INSERT INTO analista (id, id_unidade, cpf, nome, email, dt_contratacao, senha, cargo)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)""";

        try {
            conn = conexao.conectar();
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setInt(1, analista.getId());
            pstmt.setInt(2, analista.getIdUnidade());
            pstmt.setString(3, analista.getCpf());
            pstmt.setString(4, analista.getNome());
            pstmt.setString(5, analista.getEmail());
            pstmt.setDate(6, analista.getDtcontratacao());
            pstmt.setString(7, analista.getSenha());
            pstmt.setString(8, analista.getCargo());
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

    public int alterarIdUnidade(Analista analista, int novoIdUnidade) {
        String comando = "UPDATE analista SET id_unidade = ? WHERE id = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setInt(1, novoIdUnidade);
            pstmt.setInt(2, analista.getId());
            int execucao = pstmt.executeUpdate();
            if (execucao > 0){
                analista.setIdUnidade(novoIdUnidade);
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

    public int alterarCpf(Analista analista, String novoCpf) {
        String comando = "UPDATE analista SET cpf = ? WHERE id = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setString(1, novoCpf);
            pstmt.setInt(2, analista.getId());
            int execucao = pstmt.executeUpdate();
            if (execucao > 0){
                analista.setCpf(novoCpf);
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

    public int alterarNome(Analista analista, String novoNome) {
        String comando = "UPDATE analista SET nome = ? WHERE id = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setString(1, novoNome);
            pstmt.setInt(2, analista.getId());
            int execucao = pstmt.executeUpdate();
            if(execucao > 0){
                analista.setNome(novoNome);
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

    public int alterarEmail(Analista analista, String novoEmail) {
        String comando = "UPDATE analista SET email_institucional = ? WHERE id = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setString(1, novoEmail);
            pstmt.setInt(2, analista.getId());
            int execucao = pstmt.executeUpdate();
            if(execucao > 0){
                analista.setEmail(novoEmail);
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

    public int alterarDtContratacao(Analista analista, Date novaData) {
        String comando = "UPDATE analista SET dt_contratacao = ? WHERE id = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setDate(1, novaData);
            pstmt.setInt(2, analista.getId());
            int execucao = pstmt.executeUpdate();
            if (execucao > 0){
                analista.setDtContratacao(novaData);
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

    public int alterarSenha(Analista analista, String novaSenha) {
        String comando = "UPDATE analista SET senha = ? WHERE id = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setString(1, novaSenha);
            pstmt.setInt(2, analista.getId());
            int execucao = pstmt.executeUpdate();
            if (execucao > 0){
                analista.setSenha(novaSenha);
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

    public int alterarCargo(Analista analista, String novoCargo) {
        String comando = "UPDATE analista SET cargo = ? WHERE id = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setString(1, novoCargo);
            pstmt.setInt(2, analista.getId());
            int execucao = pstmt.executeUpdate();
            if (execucao > 0){
                analista.setCargo(novoCargo);
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

    public int apagar(Analista analista, int idAnalista) {
        String comando = "DELETE FROM analista WHERE id = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setInt(1, idAnalista);
            int execucao = pstmt.executeUpdate();
            if (execucao > 0){
                analista = null;
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

    public List<Analista> buscarPorId(int idAnalista) {
        ResultSet rs;
        List<Analista> listaRetorno = new ArrayList<>();
        String comando = "SELECT * FROM analista WHERE id = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setInt(1, idAnalista);
            pstmt.executeQuery();
            rs = pstmt.getResultSet();
            while (rs.next()){
                Analista analista = new Analista(rs.getInt("id"), rs.getInt("id_unidade"),
                        rs.getString("cpf"), rs.getString("nome"), rs.getString("email"),
                        rs.getDate("dt_contratacao"), rs.getString("senha"), rs.getString("cargo"));
                listaRetorno.add(analista);
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

    public List<Analista> buscarPorIdUnidade(int idUnidade) {
        ResultSet rs;
        List<Analista> listaRetorno = new ArrayList<>();
        String comando = "SELECT * FROM analista WHERE id_unidade = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setInt(1, idUnidade);
            pstmt.executeQuery();
            rs = pstmt.getResultSet();
            while (rs.next()){
                Analista analista = new Analista(rs.getInt("id"), rs.getInt("id_unidade"),
                        rs.getString("cpf"), rs.getString("nome"), rs.getString("email"),
                        rs.getDate("dt_contratacao"), rs.getString("senha"), rs.getString("cargo"));
                listaRetorno.add(analista);
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

    public List<Analista> buscarPorCpf(String cpf) {
        ResultSet rs;
        List<Analista> listaRetorno = new ArrayList<>();
        String comando = "SELECT * FROM analista WHERE cpf = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setString(1, cpf);
            pstmt.executeQuery();
            rs = pstmt.getResultSet();
            while(rs.next()){
                Analista analista = new Analista(rs.getInt("id"), rs.getInt("id_unidade"),
                        rs.getString("cpf"), rs.getString("nome"), rs.getString("email"),
                        rs.getDate("dt_contratacao"), rs.getString("senha"), rs.getString("cargo"));
                listaRetorno.add(analista);
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

    public List<Analista> buscarPorNome(String nome) {
        ResultSet rs;
        List<Analista> listaRetorno = new ArrayList<>();
        String comando = "SELECT * FROM analista WHERE nome = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setString(1, nome);
            pstmt.executeQuery();
            rs = pstmt.getResultSet();
            while (rs.next()){
                Analista analista = new Analista(rs.getInt("id"), rs.getInt("id_unidade"),
                        rs.getString("cpf"), rs.getString("nome"), rs.getString("email"),
                        rs.getDate("dt_contratacao"), rs.getString("senha"), rs.getString("cargo"));
                listaRetorno.add(analista);
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

    public List<Analista> buscarPorEmailInstitucional(String email) {
        ResultSet rs;
        List<Analista> listaRetorno = new ArrayList<>();
        String comando = "SELECT * FROM analista WHERE email_institucional = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setString(1, email);
            pstmt.executeQuery();
            rs = pstmt.getResultSet();
            while (rs.next()){
                Analista analista = new Analista(rs.getInt("id"), rs.getInt("id_unidade"),
                        rs.getString("cpf"), rs.getString("nome"), rs.getString("email"),
                        rs.getDate("dt_contratacao"), rs.getString("senha"), rs.getString("cargo"));
                listaRetorno.add(analista);
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

    public List<Analista> buscarPorDtContratacao(Date data) {
        ResultSet rs;
        List<Analista> listaRetorno = new ArrayList<>();
        String comando = "SELECT * FROM analista WHERE dt_contratacao = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setDate(1, data);
            pstmt.executeQuery();
            rs = pstmt.getResultSet();
            while (rs.next()){
                Analista analista = new Analista(rs.getInt("id"), rs.getInt("id_unidade"),
                        rs.getString("cpf"), rs.getString("nome"), rs.getString("email"),
                        rs.getDate("dt_contratacao"), rs.getString("senha"), rs.getString("cargo"));
                listaRetorno.add(analista);
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

    public List<Analista> buscarPorSenha(String senha) {
        ResultSet rs;
        List<Analista> listaRetorno = new ArrayList<>();
        String comando = "SELECT * FROM analista WHERE senha = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setString(1, senha);
            pstmt.executeQuery();
            rs = pstmt.getResultSet();
            while (rs.next()){
                Analista analista = new Analista(rs.getInt("id"), rs.getInt("id_unidade"),
                        rs.getString("cpf"), rs.getString("nome"), rs.getString("email"),
                        rs.getDate("dt_contratacao"), rs.getString("senha"), rs.getString("cargo"));
                listaRetorno.add(analista);
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

    public List<Analista> buscarPorCargo(String cargo) {
        ResultSet rs;
        List<Analista> listaRetorno = new ArrayList<>();
        String comando = "SELECT * FROM analista WHERE cargo = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setString(1, cargo);
            pstmt.executeQuery();
            rs = pstmt.getResultSet();
            while (rs.next()){
                Analista analista = new Analista(rs.getInt("id"), rs.getInt("id_unidade"),
                        rs.getString("cpf"), rs.getString("nome"), rs.getString("email"),
                        rs.getDate("dt_contratacao"), rs.getString("senha"), rs.getString("cargo"));
                listaRetorno.add(analista);
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

