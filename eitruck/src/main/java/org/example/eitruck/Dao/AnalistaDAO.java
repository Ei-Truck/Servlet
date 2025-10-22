package org.example.eitruck.Dao;

import org.example.eitruck.model.Analista;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AnalistaDAO extends DAO {
    public AnalistaDAO() {
        super();
    }

    public boolean cadastrar(Analista analista) {
        String comando = """
            INSERT INTO analista (id_unidade, cpf, nome_completo, email, dt_contratacao, senha, cargo, telefone)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)""";

        Connection conn = null;
        try {
            conn = conexao.conectar();
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setInt(1, analista.getIdUnidade());
            pstmt.setString(2, analista.getCpf());
            pstmt.setString(3, analista.getNomeCompleto());
            pstmt.setString(4, analista.getEmail());
            pstmt.setDate(5, Date.valueOf(analista.getDtContratacao()));
            pstmt.setString(6, analista.getSenha());
            pstmt.setString(7, analista.getCargo());
            pstmt.setString(8, analista.getTelefone());
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

    // Método para buscar todos os analistas
    public List<Analista> buscarTodos() {
        ResultSet rs;
        List<Analista> listaRetorno = new ArrayList<>();
        String comando = "SELECT * FROM analista";

        Connection conn = null;
        try {
            conn = conexao.conectar();
            PreparedStatement pstmt = conn.prepareStatement(comando);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Analista analista = new Analista(
                        rs.getInt("id"),
                        rs.getInt("id_unidade"),
                        rs.getString("cpf"),
                        rs.getString("nome_completo"),
                        rs.getDate("dt_contratacao").toLocalDate(),
                        rs.getString("email"),
                        rs.getString("senha"),
                        rs.getString("cargo"),
                        rs.getString("telefone")
                );
                listaRetorno.add(analista);
            }
            return listaRetorno;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return null;
        } finally {
            conexao.desconectar(conn);
        }
    }

    // Método de atualização consolidado
    public boolean atualizar(Analista analista, String novaSenha) {
        String comando;
        if (novaSenha != null && !novaSenha.isEmpty()) {
            comando = """
                UPDATE analista 
                SET id_unidade = ?, cpf = ?, nome = ?, email = ?, dt_contratacao = ?, senha = ?, cargo = ?
                WHERE id = ?""";
        } else {
            comando = """
                UPDATE analista 
                SET id_unidade = ?, cpf = ?, nome = ?, email = ?, dt_contratacao = ?, cargo = ?
                WHERE id = ?""";
        }

        Connection conn = null;
        try {
            conn = conexao.conectar();
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setInt(1, analista.getIdUnidade());
            pstmt.setString(2, analista.getCpf());
            pstmt.setString(3, analista.getNomeCompleto());
            pstmt.setString(4, analista.getEmail());
            pstmt.setDate(5, Date.valueOf(analista.getDtContratacao()));
            if (novaSenha != null && !novaSenha.isEmpty()) {
                pstmt.setString(6, novaSenha);
                pstmt.setString(7, analista.getCargo());
                pstmt.setInt(8, analista.getId());
            } else {
                pstmt.setString(6, analista.getCargo());
                pstmt.setInt(7, analista.getId());
            }

            int execucao = pstmt.executeUpdate();
            return execucao > 0;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return false;
        } finally {
            conexao.desconectar(conn);
        }
    }

    // Método apagar simplificado
    public int apagar(int idAnalista) {
        String comando = "DELETE FROM analista WHERE id = ?";
        Connection conn = null;

        try {
            conn = conexao.conectar();
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setInt(1, idAnalista);
            int execucao = pstmt.executeUpdate();
            return execucao;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return -1;
        } finally {
            conexao.desconectar(conn);
        }
    }

    // Métodos de busca
    public List<Analista> buscarPorId(int idAnalista) {
        ResultSet rs;
        List<Analista> listaRetorno = new ArrayList<>();
        String comando = "SELECT * FROM analista WHERE id = ?";

        Connection conn = null;
        try {
            conn = conexao.conectar();
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setInt(1, idAnalista);
            rs = pstmt.executeQuery();
            while (rs.next()){
                Analista analista = new Analista(
                        rs.getInt("id"),
                        rs.getInt("id_unidade"),
                        rs.getString("cpf"),
                        rs.getString("nome"),
                        rs.getDate("dt_contratacao").toLocalDate(),
                        rs.getString("email"),
                        rs.getString("senha"),
                        rs.getString("cargo"),
                        rs.getString("telefone")
                );
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

        Connection conn = null;
        try {
            conn = conexao.conectar();
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setInt(1, idUnidade);
            rs = pstmt.executeQuery();
            while (rs.next()){
                Analista analista = new Analista(
                        rs.getInt("id"),
                        rs.getInt("id_unidade"),
                        rs.getString("cpf"),
                        rs.getString("nome"),
                        rs.getDate("dt_contratacao").toLocalDate(),
                        rs.getString("email"),
                        rs.getString("senha"),
                        rs.getString("cargo"),
                        rs.getString("telefone")
                );
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

        Connection conn = null;
        try {
            conn = conexao.conectar();
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setString(1, cpf);
            rs = pstmt.executeQuery();
            while(rs.next()){
                Analista analista = new Analista(
                        rs.getInt("id"),
                        rs.getInt("id_unidade"),
                        rs.getString("cpf"),
                        rs.getString("nome"),
                        rs.getDate("dt_contratacao").toLocalDate(),
                        rs.getString("email"),
                        rs.getString("senha"),
                        rs.getString("cargo"),
                        rs.getString("telefone")
                );
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

        Connection conn = null;
        try {
            conn = conexao.conectar();
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setString(1, nome);
            rs = pstmt.executeQuery();
            while (rs.next()){
                Analista analista = new Analista(
                        rs.getInt("id"),
                        rs.getInt("id_unidade"),
                        rs.getString("cpf"),
                        rs.getString("nome"),
                        rs.getDate("dt_contratacao").toLocalDate(),
                        rs.getString("email"),
                        rs.getString("senha"),
                        rs.getString("cargo"),
                        rs.getString("telefone")
                );
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

    public List<Analista> buscarPorEmail(String email) {
        ResultSet rs;
        List<Analista> listaRetorno = new ArrayList<>();
        String comando = "SELECT * FROM analista WHERE email = ?";

        Connection conn = null;
        try {
            conn = conexao.conectar();
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();
            while (rs.next()){
                Analista analista = new Analista(
                        rs.getInt("id"),
                        rs.getInt("id_unidade"),
                        rs.getString("cpf"),
                        rs.getString("nome"),
                        rs.getDate("dt_contratacao").toLocalDate(),
                        rs.getString("email"),
                        rs.getString("senha"),
                        rs.getString("cargo"),
                        rs.getString("telefone")
                );
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

        Connection conn = null;
        try {
            conn = conexao.conectar();
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setString(1, cargo);
            rs = pstmt.executeQuery();
            while (rs.next()){
                Analista analista = new Analista(
                        rs.getInt("id"),
                        rs.getInt("id_unidade"),
                        rs.getString("cpf"),
                        rs.getString("nome_completo"),
                        rs.getDate("dt_contratacao").toLocalDate(),
                        rs.getString("email"),
                        rs.getString("senha"),
                        rs.getString("cargo"),
                        rs.getString("telefone")
                );
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

    // Método para autenticação
    public String ehAnalista(String email, String senha){
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        String sql = "SELECT senha, nome FROM analista WHERE email = ?";
        String senhaBanco, nome;

        try {
            conn = conexao.conectar();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();
            if (rs.next()){
                senhaBanco = rs.getString("senha");
                nome = rs.getString("nome");
                if (senha.equals(senhaBanco)){
                    return nome;
                }
            } return null;
        } catch (SQLException sqle){
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(conn);
        } return null;
    }
    public boolean verificarEmailExistente(String email) {
        String comando = "SELECT COUNT(*) FROM analista WHERE email = ?";
        Connection conn = null;

        try {
            conn = conexao.conectar();
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return false;
        } finally {
            conexao.desconectar(conn);
        }
    }

    public boolean verificarCpfExistente(String cpf) {
        String comando = "SELECT COUNT(*) FROM analista WHERE cpf = ?";
        Connection conn = null;

        try {
            conn = conexao.conectar();
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setString(1, cpf);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return false;
        } finally {
            conexao.desconectar(conn);
        }
    }
//// Método para buscar todos os analistas
//    public List<Analista> buscarTodos() {
//        ResultSet rs;
//        List<Analista> listaRetorno = new ArrayList<>();
//        String comando = "SELECT * FROM analista";
//
//        Connection conn = null;
//        try {
//            conn = conexao.conectar();
//            PreparedStatement pstmt = conn.prepareStatement(comando);
//            rs = pstmt.executeQuery();
//            while (rs.next()) {
//                Analista analista = new Analista(
//                        rs.getInt("id"),
//                        rs.getInt("id_unidade"),
//                        rs.getString("cpf"),
//                        rs.getString("nome_completo"),
//                        rs.getDate("dt_contratacao").toLocalDate(),
//                        rs.getString("email"),
//                        rs.getString("senha"),
//                        rs.getString("cargo"),
//                        rs.getString("telefone")
//                );
//                listaRetorno.add(analista);
//            }
//            return listaRetorno;
//        } catch (SQLException sqle) {
//            sqle.printStackTrace();
//            return null;
//        } finally {
//            conexao.desconectar(conn);
//        }
//    }
//    public int alterarTodos(int id, int idUnidade, String cpf, String nomeCompleto, LocalDate dtContratacao, String email, String senha, String cargo, String telefone) {
//        Connection conn = null;
//        try {
//            conn = conexao.conectar();
//            PreparedStatement pstmt = conn.prepareStatement(
//                    "UPDATE analista SET id_unidade = ?, cpf = ?, nome_completo = ?, dt_contratacao = ?, email = ?, senha = ?, cargo = ?, telefone = ? WHERE id = ?"
//            );
//
//            pstmt.setInt(1, idUnidade);
//            pstmt.setString(2, cpf);
//            pstmt.setString(3, nomeCompleto);
//            pstmt.setDate(4, java.sql.Date.valueOf(dtContratacao));
//            pstmt.setString(5, email);
//            pstmt.setString(6, senha);
//            pstmt.setString(7, cargo);
//            pstmt.setString(8, telefone);
//            pstmt.setInt(9, id);
//
//            int linhasAfetadas = pstmt.executeUpdate();
//
//            if (linhasAfetadas > 0) {
//                return 1; // Sucesso - registro alterado
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return -1; // Erro
//        } finally {
//            conexao.desconectar(conn);
//        }
//        return 0; // Nenhum registro alterado
//    }
//    // Método de atualização consolidado
//    public boolean atualizar(Analista analista, String novaSenha) {
//        String comando;
//        if (novaSenha != null && !novaSenha.isEmpty()) {
//            comando = """
//                UPDATE analista
//                SET id_unidade = ?, cpf = ?, nome = ?, email = ?, dt_contratacao = ?, senha = ?, cargo = ?
//                WHERE id = ?""";
//        } else {
//            comando = """
//                UPDATE analista
//                SET id_unidade = ?, cpf = ?, nome = ?, email = ?, dt_contratacao = ?, cargo = ?
//                WHERE id = ?""";
//        }
//
//        Connection conn = null;
//        try {
//            conn = conexao.conectar();
//            PreparedStatement pstmt = conn.prepareStatement(comando);
//            pstmt.setInt(1, analista.getIdUnidade());
//            pstmt.setString(2, analista.getCpf());
//            pstmt.setString(3, analista.getNomeCompleto());
//            pstmt.setString(4, analista.getEmail());
//            pstmt.setDate(5, Date.valueOf(analista.getDtContratacao()));
//            if (novaSenha != null && !novaSenha.isEmpty()) {
//                pstmt.setString(6, novaSenha);
//                pstmt.setString(7, analista.getCargo());
//                pstmt.setInt(8, analista.getId());
//            } else {
//                pstmt.setString(6, analista.getCargo());
//                pstmt.setInt(7, analista.getId());
//            }
//
//            int execucao = pstmt.executeUpdate();
//            return execucao > 0;
//        } catch (SQLException sqle) {
//            sqle.printStackTrace();
//            return false;
//        } finally {
//            conexao.desconectar(conn);
//        }
//    }
//
//    // Método apagar simplificado
//    public int apagar(int idAnalista) {
//        String comando = "DELETE FROM analista WHERE id = ?";
//        Connection conn = null;
//
//        try {
//            conn = conexao.conectar();
//            PreparedStatement pstmt = conn.prepareStatement(comando);
//            pstmt.setInt(1, idAnalista);
//            int execucao = pstmt.executeUpdate();
//            return execucao;
//        } catch (SQLException sqle) {
//            sqle.printStackTrace();
//            return -1;
//        } finally {
//            conexao.desconectar(conn);
//        }
//    }
//
//    // Métodos de busca
//    public List<Analista> buscarPorId(int idAnalista) {
//        ResultSet rs;
//        List<Analista> listaRetorno = new ArrayList<>();
//        String comando = "SELECT * FROM analista WHERE id = ?";
//
//        Connection conn = null;
//        try {
//            conn = conexao.conectar();
//            PreparedStatement pstmt = conn.prepareStatement(comando);
//            pstmt.setInt(1, idAnalista);
//            rs = pstmt.executeQuery();
//            while (rs.next()){
//                Analista analista = new Analista(
//                        rs.getInt("id"),
//                        rs.getInt("id_unidade"),
//                        rs.getString("cpf"),
//                        rs.getString("nome"),
//                        rs.getDate("dt_contratacao").toLocalDate(),
//                        rs.getString("email"),
//                        rs.getString("senha"),
//                        rs.getString("cargo"),
//                        rs.getString("telefone")
//                );
//                listaRetorno.add(analista);
//            }
//            return listaRetorno;
//        }
//        catch (SQLException sqle){
//            sqle.printStackTrace();
//            return null;
//        }
//        finally {
//            conexao.desconectar(conn);
//        }
//    }
//
//    public List<Analista> buscarPorIdUnidade(int idUnidade) {
//        ResultSet rs;
//        List<Analista> listaRetorno = new ArrayList<>();
//        String comando = "SELECT * FROM analista WHERE id_unidade = ?";
//
//        Connection conn = null;
//        try {
//            conn = conexao.conectar();
//            PreparedStatement pstmt = conn.prepareStatement(comando);
//            pstmt.setInt(1, idUnidade);
//            rs = pstmt.executeQuery();
//            while (rs.next()){
//                Analista analista = new Analista(
//                        rs.getInt("id"),
//                        rs.getInt("id_unidade"),
//                        rs.getString("cpf"),
//                        rs.getString("nome"),
//                        rs.getDate("dt_contratacao").toLocalDate(),
//                        rs.getString("email"),
//                        rs.getString("senha"),
//                        rs.getString("cargo"),
//                        rs.getString("telefone")
//                );
//                listaRetorno.add(analista);
//            }
//            return listaRetorno;
//        }
//        catch (SQLException sqle){
//            sqle.printStackTrace();
//            return null;
//        }
//        finally {
//            conexao.desconectar(conn);
//        }
//    }
//
//    public List<Analista> buscarPorCpf(String cpf) {
//        ResultSet rs;
//        List<Analista> listaRetorno = new ArrayList<>();
//        String comando = "SELECT * FROM analista WHERE cpf = ?";
//
//        Connection conn = null;
//        try {
//            conn = conexao.conectar();
//            PreparedStatement pstmt = conn.prepareStatement(comando);
//            pstmt.setString(1, cpf);
//            rs = pstmt.executeQuery();
//            while(rs.next()){
//                Analista analista = new Analista(
//                        rs.getInt("id"),
//                        rs.getInt("id_unidade"),
//                        rs.getString("cpf"),
//                        rs.getString("nome"),
//                        rs.getDate("dt_contratacao").toLocalDate(),
//                        rs.getString("email"),
//                        rs.getString("senha"),
//                        rs.getString("cargo"),
//                        rs.getString("telefone")
//                );
//                listaRetorno.add(analista);
//            }
//            return listaRetorno;
//        }
//        catch (SQLException sqle){
//            sqle.printStackTrace();
//            return null;
//        }
//        finally {
//            conexao.desconectar(conn);
//        }
//    }
//
//    public List<Analista> buscarPorNome(String nome) {
//        ResultSet rs;
//        List<Analista> listaRetorno = new ArrayList<>();
//        String comando = "SELECT * FROM analista WHERE nome = ?";
//
//        Connection conn = null;
//        try {
//            conn = conexao.conectar();
//            PreparedStatement pstmt = conn.prepareStatement(comando);
//            pstmt.setString(1, nome);
//            rs = pstmt.executeQuery();
//            while (rs.next()){
//                Analista analista = new Analista(
//                        rs.getInt("id"),
//                        rs.getInt("id_unidade"),
//                        rs.getString("cpf"),
//                        rs.getString("nome"),
//                        rs.getDate("dt_contratacao").toLocalDate(),
//                        rs.getString("email"),
//                        rs.getString("senha"),
//                        rs.getString("cargo"),
//                        rs.getString("telefone")
//                );
//                listaRetorno.add(analista);
//            }
//            return listaRetorno;
//        }
//        catch (SQLException sqle){
//            sqle.printStackTrace();
//            return null;
//        }
//        finally {
//            conexao.desconectar(conn);
//        }
//    }
//
//    public List<Analista> buscarPorEmail(String email) {
//        ResultSet rs;
//        List<Analista> listaRetorno = new ArrayList<>();
//        String comando = "SELECT * FROM analista WHERE email = ?";
//
//        Connection conn = null;
//        try {
//            conn = conexao.conectar();
//            PreparedStatement pstmt = conn.prepareStatement(comando);
//            pstmt.setString(1, email);
//            rs = pstmt.executeQuery();
//            while (rs.next()){
//                Analista analista = new Analista(
//                        rs.getInt("id"),
//                        rs.getInt("id_unidade"),
//                        rs.getString("cpf"),
//                        rs.getString("nome"),
//                        rs.getDate("dt_contratacao").toLocalDate(),
//                        rs.getString("email"),
//                        rs.getString("senha"),
//                        rs.getString("cargo"),
//                        rs.getString("telefone")
//                );
//                listaRetorno.add(analista);
//            }
//            return listaRetorno;
//        }
//        catch (SQLException sqle){
//            sqle.printStackTrace();
//            return null;
//        }
//        finally {
//            conexao.desconectar(conn);
//        }
//    }
//
//    public List<Analista> buscarPorCargo(String cargo) {
//        ResultSet rs;
//        List<Analista> listaRetorno = new ArrayList<>();
//        String comando = "SELECT * FROM analista WHERE cargo = ?";
//
//        Connection conn = null;
//        try {
//            conn = conexao.conectar();
//            PreparedStatement pstmt = conn.prepareStatement(comando);
//            pstmt.setString(1, cargo);
//            rs = pstmt.executeQuery();
//            while (rs.next()){
//                Analista analista = new Analista(
//                        rs.getInt("id"),
//                        rs.getInt("id_unidade"),
//                        rs.getString("cpf"),
//                        rs.getString("nome_completo"),
//                        rs.getDate("dt_contratacao").toLocalDate(),
//                        rs.getString("email"),
//                        rs.getString("senha"),
//                        rs.getString("cargo"),
//                        rs.getString("telefone")
//                );
//                listaRetorno.add(analista);
//            }
//            return listaRetorno;
//        }
//        catch (SQLException sqle){
//            sqle.printStackTrace();
//            return null;
//        }
//        finally {
//            conexao.desconectar(conn);
//        }
//    }
//
//    // Método para autenticação
//    public String ehAnalista(String email, String senha){
//        Connection conn = null;
//        PreparedStatement pstmt;
//        ResultSet rs;
//        String sql = "SELECT senha, nome FROM analista WHERE email = ?";
//        String senhaBanco, nome;
//
//        try {
//            conn = conexao.conectar();
//            pstmt = conn.prepareStatement(sql);
//            pstmt.setString(1, email);
//            rs = pstmt.executeQuery();
//            if (rs.next()){
//                senhaBanco = rs.getString("senha");
//                nome = rs.getString("nome");
//                if (senha.equals(senhaBanco)){
//                    return nome;
//                }
//            } return null;
//        } catch (SQLException sqle){
//            sqle.printStackTrace();
//        } finally {
//            conexao.desconectar(conn);
//        } return null;
//    }
//    public boolean verificarEmailExistente(String email) {
//        String comando = "SELECT COUNT(*) FROM analista WHERE email = ?";
//        Connection conn = null;
//
//        try {
//            conn = conexao.conectar();
//            PreparedStatement pstmt = conn.prepareStatement(comando);
//            pstmt.setString(1, email);
//            ResultSet rs = pstmt.executeQuery();
//
//            if (rs.next()) {
//                return rs.getInt(1) > 0;
//            }
//            return false;
//        } catch (SQLException sqle) {
//            sqle.printStackTrace();
//            return false;
//        } finally {
//            conexao.desconectar(conn);
//        }
//    }
//
//    public boolean verificarCpfExistente(String cpf) {
//        String comando = "SELECT COUNT(*) FROM analista WHERE cpf = ?";
//        Connection conn = null;
//
//        try {
//            conn = conexao.conectar();
//            PreparedStatement pstmt = conn.prepareStatement(comando);
//            pstmt.setString(1, cpf);
//            ResultSet rs = pstmt.executeQuery();
//
//            if (rs.next()) {
//                return rs.getInt(1) > 0;
//            }
//            return false;
//        } catch (SQLException sqle) {
//            sqle.printStackTrace();
//            return false;
//        } finally {
//            conexao.desconectar(conn);
//        }
//    }
//}
//public static void main(String[] args) {
//    // Teste do método buscarTodos()
//    AnalistaDAO dao = new AnalistaDAO(); // Supondo que o método está em uma classe AnalistaDAO
//
//    System.out.println("=== TESTE DO MÉTODO buscarTodos() ===");
//
//    List<Analista> analistas = dao.buscarTodos();
//
//    // Validação dos resultados
//    if (analistas != null) {
//        System.out.println("✅ Busca realizada com sucesso!");
//        System.out.println("📊 Quantidade de analistas encontrados: " + analistas.size());
//
//        // Exibir os analistas encontrados
//        if (analistas.isEmpty()) {
//            System.out.println("ℹ  Nenhum analista encontrado na base de dados.");
//        } else {
//            System.out.println("\n--- LISTA DE ANALISTAS ---");
//            for (Analista analista : analistas) {
//                System.out.println("ID: " + analista.getId());
//                System.out.println("Nome: " + analista.getNomeCompleto());
//                System.out.println("CPF: " + analista.getCpf());
//                System.out.println("Email: " + analista.getEmail());
//                System.out.println("Cargo: " + analista.getCargo());
//                System.out.println("Telefone: " + analista.getTelefone());
//                System.out.println("Data Contratação: " + analista.getDtContratacao());
//                System.out.println("ID Unidade: " + analista.getIdUnidade());
//                System.out.println("---");
//            }
//        }
//    } else {
//        System.out.println("❌ Erro ao buscar analistas! Retornou null.");
//    }
//}
//
//    //TESTANDO ALTERAR GERAL
//
//    public static void main(String[] args) {
//        AnalistaDAO dao = new AnalistaDAO(); // Supondo que o método está em AnalistaDAO
//
//        System.out.println("=== TESTE ALTERAR ANALISTA ===");
//
//        // Teste de alteração
//        int resultado = dao.alterarTodos(
//                1,                          // id
//                1,                          // idUnidade
//                "12345678900",           // cpf
//                "Ana Clara Silva",          // nomeCompleto
//                LocalDate.of(2023, 1, 15),  // dtContratacao
//                "ana.clara@empresa.com",    // email
//                "novaSenha123",             // senha
//                "Analista Sênior",          // cargo
//                "11999999999"          // telefone
//        );
//
//        switch (resultado) {
//            case 1:
//                System.out.println("✅ Analista alterado com sucesso!");
//                break;
//            case 0:
//                System.out.println("ℹ  Nenhum analista encontrado com o ID informado");
//                break;
//            case -1:
//                System.out.println("❌ Erro ao alterar analista");
//                break;
//        }
//
//        // Teste com verificação antes e depois
//        System.out.println("\n=== TESTE COMPLETO ===");
//
//        // Buscar analistas existentes primeiro
//        List<Analista> analistas = dao.buscarTodos();
//        if (analistas != null && !analistas.isEmpty()) {
//            Analista primeiroAnalista = analistas.get(0);
//            System.out.println("Analista antes da alteração:");
//            System.out.println("ID: " + primeiroAnalista.getId());
//            System.out.println("Nome: " + primeiroAnalista.getNomeCompleto());
//            System.out.println("CPF: " + primeiroAnalista.getCpf());
//            System.out.println("Email: " + primeiroAnalista.getEmail());
//
//            // Alterar
//            int resultado2 = dao.alterarTodos(
//                    primeiroAnalista.getId(),
//                    primeiroAnalista.getIdUnidade(),
//                    primeiroAnalista.getCpf(),
//                    "Nome Alterado " + System.currentTimeMillis(),
//                    primeiroAnalista.getDtContratacao(),
//                    "email.alterado" + System.currentTimeMillis() + "@empresa.com",
//                    primeiroAnalista.getSenha(),
//                    primeiroAnalista.getCargo(),
//                    primeiroAnalista.getTelefone()
//            );
//
//            System.out.println("Resultado: " + (resultado2 == 1 ? "Sucesso" : "Falha"));
//        }
//    }
//
//}
}