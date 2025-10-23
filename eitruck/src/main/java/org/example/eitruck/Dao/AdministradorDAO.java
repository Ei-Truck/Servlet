package org.example.eitruck.Dao;

import org.example.eitruck.model.Administrador;
import org.example.eitruck.model.Analista;
import org.example.eitruck.util.Hash;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdministradorDAO extends DAO {
    public AdministradorDAO() {
        super();
    }

    public boolean cadastrar(Administrador admin) {
        String comando = """
            INSERT INTO administrador (cpf, nome_completo, email, senha, telefone)
            VALUES (?, ?, ?, ?, ?)""";

        Connection conn = null;
        try {
            conn = conexao.conectar();
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setString(1, admin.getCpf());
            pstmt.setString(2, admin.getNomeCompleto());
            pstmt.setString(3, admin.getEmail());
            pstmt.setString(4, admin.getSenha());
            pstmt.setString(5, admin.getTelefone());
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

    // Método apagar simplificado (CORREÇÃO)
    public int apagar(int idAdmin) {
        String comando = "DELETE FROM administrador WHERE id = ?";
        Connection conn = null;

        try {
            conn = conexao.conectar();
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setInt(1, idAdmin);
            int execucao = pstmt.executeUpdate();
            return execucao;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return -1;
        } finally {
            conexao.desconectar(conn);
        }
    }

    // Método para buscar todos os administradores (FALTANTE)
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
                        rs.getString("senha"),
                        rs.getString("telefone")
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

    public int numeroRegistros() {
        String comando = "SELECT COUNT(*) AS total FROM administrador";
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

    // Método de atualização consolidado (FALTANTE)

    public int alterarTodos(int id, String cpf, String nomeCompleto, String email, String senha, String telefone) {
        Connection conn = null;
        try {
            conn = conexao.conectar();
            PreparedStatement pstmt = conn.prepareStatement(
                    "UPDATE administrador SET cpf = ?, nome_completo = ?, email = ?, senha = ?, telefone = ? WHERE id = ?"
            );

            pstmt.setString(1, cpf);
            pstmt.setString(2, nomeCompleto);
            pstmt.setString(3, email);
            pstmt.setString(4, senha);
            pstmt.setString(5, telefone);
            pstmt.setInt(6, id);

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


    public boolean atualizar(Administrador admin, String novaSenha) {
        String comando;
        if (novaSenha != null && !novaSenha.isEmpty()) {
            comando = """
                UPDATE administrador 
                SET cpf = ?, nome_completo = ?, email = ?, senha = ?
                WHERE id = ?""";
        } else {
            comando = """
                UPDATE administrador 
                SET cpf = ?, nome_completo = ?, email = ?
                WHERE id = ?""";
        }

        Connection conn = null;
        try {
            conn = conexao.conectar();
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setString(1, admin.getCpf());
            pstmt.setString(2, admin.getNomeCompleto());
            pstmt.setString(3, admin.getEmail());

            if (novaSenha != null && !novaSenha.isEmpty()) {
                pstmt.setString(4, novaSenha);
                pstmt.setInt(5, admin.getId());
            } else {
                pstmt.setInt(4, admin.getId());
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

    // Métodos individuais de alteração (mantidos para compatibilidade)
    public int alterarNomeCompleto(Administrador admin, String novoNomeCompleto) {
        String comando = """
            UPDATE administrador
            SET nome_completo = ?
            WHERE id = ?""";

        Connection conn = null;
        try {
            conn = conexao.conectar();
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

        Connection conn = null;
        try {
            conn = conexao.conectar();
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

        Connection conn = null;
        try {
            conn = conexao.conectar();
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

    // Método apagar antigo (mantido para compatibilidade)
    public int apagar(Administrador admin, int idAdmin) {
        return apagar(idAdmin);
    }

    // CORREÇÃO nos métodos de busca - adicionar conexão
    public List<Administrador> buscarPorId(int idAdmin) {
        ResultSet rs;
        List<Administrador> listaRetorno = new ArrayList<>();
        String comando = "SELECT * FROM administrador WHERE id = ?";

        Connection conn = null;
        try {
            conn = conexao.conectar();
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setInt(1, idAdmin);
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

    public List<Administrador> buscarPorCpf(String cpfAdmin) {
        ResultSet rs;
        List<Administrador> listaRetorno = new ArrayList<>();
        String comando = "SELECT * FROM administrador WHERE cpf = ?";

        Connection conn = null;
        try {
            conn = conexao.conectar();
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setString(1, cpfAdmin);
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

    public List<Administrador> buscarNomeCompleto(String nomeCompletoAdmin) {
        ResultSet rs;
        List<Administrador> listaRetorno = new ArrayList<>();
        String comando = "SELECT * FROM administrador WHERE nome_completo = ?";

        Connection conn = null;
        try {
            conn = conexao.conectar();
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setString(1, nomeCompletoAdmin);
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

    // CORREÇÃO no método buscarPorEmail
    public List<Administrador> buscarPorEmail(String emailAdmin) {
        ResultSet rs;
        List<Administrador> listaRetorno = new ArrayList<>();
        String comando = "SELECT * FROM administrador WHERE email = ?"; // CORRIGIDO

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

    // Método para autenticação
//    public String ehAdimin(String email, String senha){
//        Connection conn = null;
//        PreparedStatement pstmt;
//        ResultSet rs;
//        String sql = "SELECT senha, nome_completo FROM administrador WHERE email = ?";
//        String senhaBanco, nome;
//
//        try {
//            conn = conexao.conectar();
//            pstmt = conn.prepareStatement(sql);
//            pstmt.setString(1, email);
//            rs = pstmt.executeQuery();
//            if (rs.next()){
//                senhaBanco = rs.getString("senha");
//                nome = rs.getString("nome_completo");
//                // Comparação de senha (considerando que a senha no banco está em texto plano, mas o ideal é usar hash)
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
    public String ehAdmin(String email, String senha){
        Connection conn = null;
        Hash hash = new Hash();
        PreparedStatement pstmt;
        ResultSet rs;
        String sql = "SELECT senha, nome FROM ADMINISTRADOR WHERE EMAIL = ?";
        String senhaBanco, senhaCriptografada, nome;

        try {
            conn = conexao.conectar();
            pstmt = conn.prepareStatement(sql);

//            Setando valores
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();
            if (rs.next()){
                senhaBanco = rs.getString("senha");
                nome = rs.getString("nome");
                senhaCriptografada = hash.criptografarSenha(senha);

                if (senhaCriptografada.equals(senhaBanco)){
                    return nome;
                }
            } return null;
        } catch (SQLException sqle){
            sqle.printStackTrace();
        } finally {
            conexao.desconectar(conn);
        } return null;
    }
}