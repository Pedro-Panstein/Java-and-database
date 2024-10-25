public class VerificadorContaCliente {
    public static boolean verificar(Conta conta, Cliente cliente) {
        if (cliente.getId() != conta.getTitular().getId()) {
            return false;
        } else {
            return true;
        }
    }
}
