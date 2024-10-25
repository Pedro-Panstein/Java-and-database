import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CRUDConta {
    private static Scanner sc = new Scanner(System.in);
    private static List<Conta> contas = new ArrayList<>();

    public static Conta buscarPeloNumero(int numeroConta) {
        try (Connection connection = ConexaoBanco.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM tb_conta where numero = ?");

            ps.setInt(1, numeroConta);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int numero = rs.getInt(1);
                int idTitular = rs.getInt(2);
                double saldo = rs.getDouble(3);
                double limite = rs.getDouble(4);
                Cliente titular = CRUDCliente.buscarPorId(idTitular);
                return new Conta(numero, titular, saldo, limite);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("A conta de numero " + numeroConta + " não foi encontrada");
    }

    public static List<Conta> buscarTodasAsContas() {
        try (Connection connection = ConexaoBanco.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM tb_conta");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
//                int numero = rs.getInt(1);
//                String titular = rs.getString(2);
//                Double saldo = rs.getDouble(3);
//                Double limite = rs.getDouble(4);
//                contas.add(new Conta(numero, titular, saldo, limite)); //jeito mais dificil

                int idTitular = rs.getInt("id_titular");
                Cliente titular = CRUDCliente.buscarPorId(idTitular);
                contas.add(new Conta(rs.getInt("numero"),
                        titular,
                        rs.getDouble("saldo"), //jeito mais prático
                        rs.getDouble("limite")));
            }

            return contas;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Nenhum registro de conta encontrado!");
    }

    public static void salvar(Conta conta) {
        try (Connection connection = ConexaoBanco.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO tb_conta (numero, id_titular, saldo, limite) VALUES (?,?,?,?)");

            ps.setInt(1, conta.getNumeroConta());
            ps.setInt(2, conta.getTitular().getId());
            ps.setDouble(3, conta.getSaldo());
            ps.setDouble(4, conta.getLimite());
            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("deu ruim");
        }
    }

    public static void update(int item, Conta conta) {

        if (item == 1) {
            sc.nextLine();
            System.out.println("Qual novo titular da conta?");
            String titular = sc.nextLine();
            System.out.println("Qual novo saldo da conta?");
            double saldo = sc.nextDouble();
            System.out.println("Qual novo limite da conta?");
            double limite = sc.nextDouble();

            try (Connection connection = ConexaoBanco.getConnection()) {
                PreparedStatement ps = connection.prepareStatement("UPDATE tb_conta SET id_titular = ?, saldo = ?, limite = ? WHERE numero = ?");

                ps.setInt(1, conta.getTitular().getId());
                ps.setDouble(2, saldo);
                ps.setDouble(3, limite);
                ps.setInt(4, conta.getNumeroConta());

                ps.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (item == 2) {
            System.out.println("Qual o numero da conta que você quer atualizar?");
            int numero = sc.nextInt();
            sc.nextLine();
            System.out.println("Qual novo titular da conta?");
            String titular = sc.nextLine();

            try (Connection connection = ConexaoBanco.getConnection()) {
                PreparedStatement ps = connection.prepareStatement("UPDATE tb_conta SET id_titular = ? WHERE numero = ?");

                ps.setInt(1, conta.getTitular().getId());
                ps.setInt(2, numero);

                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (item == 3) {
            System.out.println("Qual o numero da conta que você quer atualizar?");
            int numero = sc.nextInt();
            System.out.println("Qual novo Saldo da conta?");
            double saldo = sc.nextDouble();

            try (Connection connection = ConexaoBanco.getConnection()) {
                PreparedStatement ps = connection.prepareStatement("UPDATE tb_conta SET saldo = ? WHERE numero = ?");

                ps.setDouble(1, saldo);
                ps.setInt(2, numero);

                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (item == 4) {
            System.out.println("Qual o numero da conta que você quer atualizar?");
            int numero = sc.nextInt();
            System.out.println("Qual novo Limite da conta?");
            double limite = sc.nextDouble();

            try (Connection connection = ConexaoBanco.getConnection()) {
                PreparedStatement ps = connection.prepareStatement("UPDATE tb_conta SET limite = ? WHERE numero = ?");

                ps.setDouble(1, limite);
                ps.setInt(2, numero);

                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            throw new RuntimeException("Ocorreu um erro ao fazer a atualização de: " + item);
        }
    }

    public static void deletarConta(Conta conta) {
        try (Connection connection = ConexaoBanco.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM tb_conta WHERE numero = ?");

            ps.setInt(1, conta.getNumeroConta());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Conta buscarPeloTitular(Cliente cliente) {
        try (Connection co = ConexaoBanco.getConnection()) {
            PreparedStatement ps = co.prepareStatement("SELECT * FROM tb_conta WHERE id_titular = ?");
            ps.setInt(1, cliente.getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Conta(rs.getInt("numero"), cliente, rs.getDouble("saldo"), rs.getDouble("limite") );
            } else {
                Cliente clienteFalso = new Cliente(-200, "null");
                return new Conta(-2000000, clienteFalso, -200, -200);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Não foi possível encontrar nenhuma conta para o titular de numero " + cliente.getId());
    }
}
