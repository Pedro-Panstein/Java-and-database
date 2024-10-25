import java.util.Scanner;

public class Executavel {
    private static final Scanner sc = new Scanner(System.in);

    private static Conta criarConta(Cliente titular) {
        System.out.println("id da conta");
        int numero = sc.nextInt();
        System.out.println("saldo da conta");
        double saldo = sc.nextDouble();
        System.out.println("limite de saque");
        double limite = sc.nextDouble();
        return new Conta(numero, titular, saldo, limite);
    }

    private static Cliente criarCliente() {
        System.out.println("Qual o cpf do cliente?");
        int cpf = sc.nextInt();
        sc.nextLine();
        System.out.println("Qual o nome do cliente?");
        String nome = sc.nextLine();
        return new Cliente(cpf, nome);
    }

    public static void main(String[] args) {
        int resposta = 200;
        while (resposta != 11) {
            System.out.println("====Crud banco de dados====");
            System.out.println("1 - Criar conta");
            System.out.println("2 - Criar cliente");
            System.out.println("3 - Atualizar conta");
            System.out.println("4 - Deletar conta");
            System.out.println("5 - Buscar conta pelo ID");
            System.out.println("6 - Buscar todas as contas");
            System.out.println("7 - Buscar Cliente por ID");
            System.out.println("8 - Buscar Todos os clientes");
            System.out.println("9 - Atualizar cliente");
            System.out.println("10 - Deletar cliente");
            System.out.println("11 - Sair");
            resposta = sc.nextInt();
            switch (resposta) {
                case 3:
                    int item = 0;
                    System.out.println("Qual o ID da conta que você quer atualizar?");
                    int idAtualizar = sc.nextInt();
                    do {
                        System.out.println("O que você quer atualizar?");
                        System.out.println("1 - Tudo");
                        System.out.println("2 - Titular");
                        System.out.println("3 - Saldo");
                        System.out.println("4 - Limite");
                        item = sc.nextInt();
                        CRUDConta.update(item, CRUDConta.buscarPeloNumero(idAtualizar)); //atualiza uma conta no banco
                    } while (item <= 0 || item > 4);
                    break;
                case 1:
                    System.out.println("Qual o ID do cliente que você quer adicionar a conta?");
                    int idCliente = sc.nextInt();
                    Cliente clienteExistente = CRUDCliente.buscarPorId(idCliente);
                    Conta conta = criarConta(clienteExistente);
                    CRUDConta.salvar(conta);
                    break;
                case 2:
                    Cliente cliente = criarCliente();
                    int id = CRUDCliente.salvar(cliente);
                    cliente.setId(id);
                    break;
                case 4:
                    System.out.println("Qual o id da conta que você deseja deletar?");
                    int idDeletarConta = sc.nextInt();
                    CRUDConta.deletarConta(CRUDConta.buscarPeloNumero(idDeletarConta)); // deleta uma conta no banco
                    break;
                case 5:
                    System.out.println("Qual o numero da conta que você quer buscar?");
                    int numero = sc.nextInt();
                    System.out.println(CRUDConta.buscarPeloNumero(numero));
                    break;
                case 6:
                    System.out.println(CRUDConta.buscarTodasAsContas()); //busca todas as contas no banco
                    break;
                case 7:
                    System.out.println("Qual o ID do cliente que você quer buscar?");
                    int buscarCliente = sc.nextInt();
                    System.out.println(CRUDCliente.buscarPorId(buscarCliente));
                    break;
                case 8:
                    System.out.println(CRUDCliente.buscarTodosClientes());
                    break;
                case 9:
                    System.out.println("Qual o ID do cliente que você quer atualizar?");
                    int idAtualizarCliente = sc.nextInt();
                    System.out.println(CRUDCliente.buscarPorId(idAtualizarCliente));
                    System.out.println("Novo cpf: ");
                    int cpf = sc.nextInt();
                    System.out.println("Novo nome: ");
                    String nome = sc.next();
                    CRUDCliente.update(cpf, nome, CRUDCliente.buscarPorId(idAtualizarCliente));
                    break;
                case 10:
                    System.out.println("Qual o ID do cliente que você quer deletar");
                    int idDeletar = sc.nextInt();
                    System.out.println(CRUDCliente.buscarPorId(idDeletar));
                    System.out.println("Ao deletar este cliente você também deletará sua conta, tem certeza disso?");
                    System.out.println("1 - sim");
                    System.out.println("2 - não");
                    int opc = sc.nextInt();
                    if (opc == 1) {
                        Cliente clienteDelete = CRUDCliente.buscarPorId(idDeletar);
                        if (clienteDelete.getConta() == null) {

                        }
                        Conta contaDelete = CRUDConta.buscarPeloTitular(clienteDelete);
                        CRUDConta.deletarConta(contaDelete);
                        CRUDCliente.deletar(idDeletar);
                    } else {
                        System.out.println("Voltando...");
                    }
                    break;
                case 11:
                    System.out.println("Obrigado");
                    break;
                default:
                    System.out.println("Digite um valor válido");
            }
        }
    }

}



