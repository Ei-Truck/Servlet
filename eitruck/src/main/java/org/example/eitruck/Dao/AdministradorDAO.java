package org.example.eitruck.Dao;


import org.example.eitruck.Dao.DAO;
import org.example.eitruck.Util.Hash;
import org.example.eitruck.model.Administrador;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdministradorDAO extends DAO {
    public AdministradorDAO() {
        super();
    }

    public boolean cadastrar(Administrador admin) {
        String comando = """
            INSERT INTO administrador (id, cpf, nome_completo, email, senha)
            VALUES (?, ?, ?, ?, ?)""";

        try {
            conn = conexao.conectar();
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setInt(1, admin.getId());
            pstmt.setString(2, admin.getCpf());
            pstmt.setString(3, admin.getNomeCompleto());
            pstmt.setString(4, admin.getEmail());
            pstmt.setString(5, admin.getSenha());
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
    public int alterarNomeCompleto(Administrador admin, String novoNomeCompleto) {
        String comando = """
            UPDATE administrador
            SET nome_completo = ?
            WHERE id = ?""";

        try {
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setString(1, novoNomeCompleto);
            pstmt.setInt(2, admin.getId());
            int execucao = pstmt.executeUpdate();
            if (execucao > 0) {
                admin.setNomeCompleto(novoNomeCompleto);
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
    public int alterarEmail(Administrador admin, String novoEmail) {
        String comando = """
            UPDATE administrador
            SET email = ?
            WHERE id = ?""";

        try {
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setString(1, novoEmail);
            pstmt.setInt(2, admin.getId());
            int execucao = pstmt.executeUpdate();
            if (execucao > 0) {
                admin.setEmail(novoEmail);
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
    public int alterarSenha(Administrador admin, String novaSenha) {
        String comando = """
            UPDATE administrador
            SET senha = ?
            WHERE id = ?""";

        try {
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setString(1, novaSenha);
            pstmt.setInt(2, admin.getId());
            int execucao = pstmt.executeUpdate();
            if (execucao > 0) {
                admin.setSenha(novaSenha);
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
    public int apagar(Administrador admin, int idAdmin) {
        String comando = """
            DELETE FROM administrador
            WHERE id = ?""";

        try {
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setInt(1, idAdmin);
            int execucao = pstmt.executeUpdate();
            if (execucao > 0) {
                admin = null;
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
    public List<Administrador> buscarPorId(int idAdmin) {
        ResultSet rs;
        List<Administrador> listaRetorno = new ArrayList<>();
        String comando = """
            SELECT * FROM administrador
            WHERE id = ?""";

        try {
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setInt(1, idAdmin);
            pstmt.executeQuery();
            rs = pstmt.getResultSet();
            while (rs.next()) {
                Administrador admin = new Administrador(rs.getInt("id"), rs.getString("cpf"), rs.getString("nome_completo"), rs.getString("email"), rs.getString("senha"));
                listaRetorno.add(admin);
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
    public List<Administrador> buscarPorCpf(String cpfAdmin) {
        ResultSet rs;
        List<Administrador> listaRetorno = new ArrayList<>();
        String comando = """
            SELECT * FROM administrador
            WHERE cpf = ?""";

        try {
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setString(1, cpfAdmin);
            pstmt.executeQuery();
            rs = pstmt.getResultSet();
            while (rs.next()) {
                Administrador admin = new Administrador(rs.getInt("id"), rs.getString("cpf"), rs.getString("nome_completo"), rs.getString("email"), rs.getString("senha"));
                listaRetorno.add(admin);
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
    public List<Administrador> buscarNomeCompleto(String nomeCompletoAdmin) {
        ResultSet rs;
        List<Administrador> listaRetorno = new ArrayList<>();
        String comando = """
            SELECT * FROM administrador
            WHERE nome_completo = ?""";

        try {
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setString(1, nomeCompletoAdmin);
            pstmt.executeQuery();
            rs = pstmt.getResultSet();
            while (rs.next()) {
                Administrador admin = new Administrador(rs.getInt("id"), rs.getString("cpf"), rs.getString("nome_completo"), rs.getString("email"), rs.getString("senha"));
                listaRetorno.add(admin);
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
    public List<Administrador> buscarPorEmail(String emailAdmin) {
        ResultSet rs;
        List<Administrador> listaRetorno = new ArrayList<>();
        String comando = """
            SELECT * FROM administrador
            WHERE id = ?""";

        try {
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setString(1, emailAdmin);
            pstmt.executeQuery();
            rs = pstmt.getResultSet();
            while (rs.next()) {
                Administrador admin = new Administrador(rs.getInt("id"), rs.getString("cpf"), rs.getString("nome_completo"), rs.getString("email"), rs.getString("senha"));
                listaRetorno.add(admin);
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
    public List<Administrador> buscarPorSenha(String senhaAdmin) {
        ResultSet rs;
        List<Administrador> listaRetorno = new ArrayList<>();
        String comando = """
            SELECT * FROM administrador
            WHERE id = ?""";

        try {
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setString(1, senhaAdmin);
            pstmt.executeQuery();
            rs = pstmt.getResultSet();
            while (rs.next()) {
                Administrador admin = new Administrador(rs.getInt("id"), rs.getString("cpf"), rs.getString("nome_completo"), rs.getString("email"), rs.getString("senha"));
                listaRetorno.add(admin);
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
    public String ehAdimin(String email, String senha){
        conn = conexao.conectar();
//        Hash hash = new Hash();
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        String sql = "SELECT senha, nome FROM ADMINISTRADOR WHERE EMAIL = ?";
        String senhaBanco, senhaCriptografada, nome;

        try {
            pstmt = conn.prepareStatement(sql);

//            Setando valores
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();
            if (rs.next()){
                senhaBanco = rs.getString("senha");
                nome = rs.getString("nome");
//                senhaCriptografada = hash.criptografarSenha(senha);

//                if (senhaCriptografada.equals(senhaBanco)){
//                    return nome;
//                }

                // Verificar se a senha é igual no banco -- Lucas
                if (senha.equals(senhaBanco)) {
                    return nome;
                }
            }
            return null;
        } catch (SQLException sqle){
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(conn);
        } return null;
    }

    // Métodos adicionados - Igor
    public int alterarCpf(Administrador admin, String novoCpf) {
        String comando = "UPDATE administrador SET cpf = ? WHERE id = ?";

        Connection conn = null;
        try {
            conn = conexao.conectar();
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setString(1, novoCpf);
            pstmt.setInt(2, admin.getId());
            int execucao = pstmt.executeUpdate();
            if (execucao > 0) {
                admin.setCpf(novoCpf);
                return 1;
            } else {
                return 0;
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return -1;
        } finally {
            conexao.desconectar(conn);
        }
    }

    public List<Administrador> buscarTodos() {
        ResultSet rs;
        List<Administrador> listaRetorno = new ArrayList<>();
        String comando = "SELECT * FROM administrador";

        Connection conn = null;
        try {
            conn = conexao.conectar();
            PreparedStatement pstmt = conn.prepareStatement(comando);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Administrador admin = new Administrador(
                        rs.getInt("id"),
                        rs.getString("cpf"),
                        rs.getString("nome_completo"),
                        rs.getString("email"),
                        rs.getString("senha")
                );
                listaRetorno.add(admin);
            }
            return listaRetorno;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return null;
        } finally {
            conexao.desconectar(conn);
        }
    }

    // Corrigindo o método buscarPorEmail
    public List<Administrador> buscarPorEmailCorreto(String emailAdmin) {
        ResultSet rs;
        List<Administrador> listaRetorno = new ArrayList<>();
        String comando = "SELECT * FROM administrador WHERE email = ?";

        Connection conn = null;
        try {
            conn = conexao.conectar();
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setString(1, emailAdmin);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Administrador admin = new Administrador(
                        rs.getInt("id"),
                        rs.getString("cpf"),
                        rs.getString("nome_completo"),
                        rs.getString("email"),
                        rs.getString("senha")
                );
                listaRetorno.add(admin);
            }
            return listaRetorno;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return null;
        } finally {
            conexao.desconectar(conn);
        }
    }
}

