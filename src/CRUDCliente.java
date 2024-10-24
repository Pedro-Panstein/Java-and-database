import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CRUDCliente {

    private static ArrayList<Cliente> clientes = new ArrayList<>();

    public static int salvar(Cliente cliente) {
        try (Connection co = ConexaoBanco.getConnection()) {
            PreparedStatement ps = co.prepareStatement("INSERT INTO tb_cliente (cpf, nome) VALUES (?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, (int) cliente.getCpf());
            ps.setString(2, cliente.getNome());
            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Erro ao salvar cliente");
    }


    public static Cliente buscarPorId(int id) {
        try (Connection co = ConexaoBanco.getConnection()) {
            PreparedStatement ps = co.prepareStatement("SELECT * FROM tb_cliente WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Cliente(rs.getInt("id"), rs.getInt("cpf"), rs.getString("nome"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Cliente não encontrado");
    }

    public static ArrayList<Cliente> buscarTodosClientes() {
        try (Connection connection = ConexaoBanco.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM tb_cliente");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                clientes.add(new Cliente(rs.getInt("id"), rs.getInt("cpf"), rs.getString("nome")));
            }
            return clientes;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Ocorreu um erro ao listar os clientes");
    }

    public static void deletar(int id) {
        try (Connection co = ConexaoBanco.getConnection()) {
            PreparedStatement ps = co.prepareStatement("DELETE FROM tb_cliente WHERE id = ?");
            ps.setInt(1, id);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Ocorreu um erro inesperado: " + e.getClass().getSimpleName());
        }
    }

    public static void update (int cpf, String nome, Cliente cliente) {
        try(Connection co = ConexaoBanco.getConnection()) {
            PreparedStatement ps = co.prepareStatement("UPDATE tb_cliente SET cpf = ?, nome = ? WHERE id = ?");
            ps.setInt(1, cpf);
            ps.setString(2, nome);
            ps.setInt(3, cliente.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
