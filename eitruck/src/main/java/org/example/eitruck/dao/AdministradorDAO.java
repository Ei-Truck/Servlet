package org.example.eitruck.dao;

import org.example.eitruck.conexao.Conexao;
import org.example.eitruck.model.Administrador;
import org.example.eitruck.util.Hash;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdministradorDAO {
    // Método inserir
    public boolean cadastrar(Administrador admin) {
        Conexao conexao = new Conexao();
        Connection conn = null;
        String comando = """
            INSERT INTO administrador (cpf, nome_completo, email, senha, telefone)
            VALUES (?, ?, ?, ?, ?)""";

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

    // Método deletar
    public int apagar(int idAdmin) {
        Conexao conexao = new Conexao();
        Connection conn = null;
        String comando = "DELETE FROM administrador WHERE id = ?";

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

    // Método alterar
    public int alterarTodos(int id, String cpf, String nomeCompleto, String email, String senha, String telefone) {
        Conexao conexao = new Conexao();
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
        Conexao conexao = new Conexao();
        Connection conn = null;

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

        try {
            conn = conexao.conectar();
            PreparedStatement pstmt = conn.prepareStatement(comando);
            pstmt.setString(1, admin.getCpf());
            pstmt.setString(2, admin.getNomeCompleto());
            pstmt.setString(3, admin.getEmail());

            if (novaSenha != null && !novaSenha.isEmpty()) {
                pstmt.setString(4, novaSenha);
                pstmt.setInt(5, admin.getId());
            }
            else {
                pstmt.setInt(4, admin.getId());
            }

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

    // Método mostrar os registros
    public List<Administrador> buscarTodos() {
        Conexao conexao = new Conexao();
        Connection conn = null;

        ResultSet rs;
        List<Administrador> listaRetorno = new ArrayList<>();
        String comando = "SELECT * FROM administrador ORDER BY id";

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
        String comando = "SELECT COUNT(*) AS total FROM administrador";

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
    public List<Administrador> filtrarAdministradoresMultiplos(String filtroId, String filtroNome, String filtroCpf, String filtroEmail, String filtroTelefone) {
        Conexao conexao = new Conexao();
        Connection conn = null;
        ResultSet rs;
        List<Administrador> listaRetorno = new ArrayList<>();

        try {
            conn = conexao.conectar();
            StringBuilder sql = new StringBuilder("SELECT * FROM administrador WHERE 1=1");
            List<Object> parametros = new ArrayList<>();

            // Filtro por ID (busca parcial)
            if (filtroId != null && !filtroId.trim().isEmpty()) {
                sql.append(" AND id::text LIKE ?");
                parametros.add("%" + filtroId.trim() + "%");
            }

            // Filtro por Nome (busca parcial case-insensitive)
            if (filtroNome != null && !filtroNome.trim().isEmpty()) {
                sql.append(" AND nome_completo ILIKE ?");
                parametros.add("%" + filtroNome.trim() + "%");
            }

            // Filtro por CPF (busca parcial)
            if (filtroCpf != null && !filtroCpf.trim().isEmpty()) {
                sql.append(" AND cpf LIKE ?");
                parametros.add("%" + filtroCpf.trim() + "%");
            }

            // Filtro por Email (busca parcial case-insensitive)
            if (filtroEmail != null && !filtroEmail.trim().isEmpty()) {
                sql.append(" AND email ILIKE ?");
                parametros.add("%" + filtroEmail.trim() + "%");
            }

            // Filtro por Telefone (busca parcial)
            if (filtroTelefone != null && !filtroTelefone.trim().isEmpty()) {
                sql.append(" AND telefone LIKE ?");
                parametros.add("%" + filtroTelefone.trim() + "%");
            }

            sql.append(" ORDER BY id");

            PreparedStatement pstmt = conn.prepareStatement(sql.toString());

            // Preenche os parâmetros
            for (int i = 0; i < parametros.size(); i++) {
                pstmt.setObject(i + 1, parametros.get(i));
            }

            System.out.println("SQL Administrador: " + sql.toString());
            System.out.println("Parâmetros Administrador: " + parametros);

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
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
            return null;
        }
        finally {
            conexao.desconectar(conn);
        }
    }

    // Método buscar por ID
    public List<Administrador> buscarPorId(int idAdmin) {
        Conexao conexao = new Conexao();
        Connection conn = null;

        ResultSet rs;
        List<Administrador> listaRetorno = new ArrayList<>();
        String comando = "SELECT * FROM administrador WHERE id = ?";

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
                        rs.getString("senha"),
                        rs.getString("telefone")
                );
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

    // Método para verificar se é administrador
    public String ehAdmin(String email, String senha){
        Conexao conexao = new Conexao();
        Connection conn = null;

        Hash hash = new Hash();
        PreparedStatement pstmt;
        ResultSet rs;
        String sql = "SELECT senha, nome_completo FROM ADMINISTRADOR WHERE EMAIL = ?";
        String senhaBanco, senhaCriptografada, nome;

        try {
            conn = conexao.conectar();
            pstmt = conn.prepareStatement(sql);

            // Setando valores
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();
            if (rs.next()){
                senhaBanco = rs.getString("senha");
                nome = rs.getString("nome_completo");


                if (hash.verificarSenha(senha,senhaBanco)){
                    return nome;
                }
            }
            return null;
        }
        catch (SQLException sqle){
            sqle.printStackTrace();
        }
        finally {
            conexao.desconectar(conn);
        }
        return null;
    }
}