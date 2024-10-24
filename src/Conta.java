public class Conta {
    private int numeroConta;
    private Cliente titular;
    private double saldo;
    private double limite;

    public Conta(int numeroConta, Cliente titular, double saldo, double limite) {
        this.numeroConta = numeroConta;
        this.titular = titular;
        this.saldo = saldo;
        this.limite = limite;
    }

    public int getNumeroConta() {
        return numeroConta;
    }

    public Cliente getTitular() {
        return titular;
    }

    public double getSaldo() {
        return saldo;
    }

    public double getLimite() {
        return limite;
    }

    public void saque(double valorSaque) throws SaldoInsuficienteException, ValorInvalidoException, LimiteExcedidoException {
        validaValor(valorSaque);  // método para validar o valor invalido
        validaSaldo(valorSaque);  // metodo para avaliar se o saldo é suficiente
        validaLimite(valorSaque); // metodo para avaliar o limite
        this.saldo -= valorSaque;
    }

    public void deposito(double valorDeposito) throws ValorInvalidoException {
        validaValor(valorDeposito); // método para validar o valor invalido

        this.saldo += valorDeposito;
    }

    public void transferencia(double valorTransferencia, Conta contaATransferir) throws ContaInvalidaException, ValorInvalidoException,
            SaldoInsuficienteException, LimiteExcedidoException {

        validaConta(contaATransferir);
        this.saque(valorTransferencia);
        contaATransferir.deposito(valorTransferencia);
    }


    private void validaValor(double valor) throws ValorInvalidoException {
        if (valor <= 0) {
            throw new ValorInvalidoException();
        }
    }

    private void validaSaldo(double valor) throws SaldoInsuficienteException {
        if (this.saldo < valor) {
            throw new SaldoInsuficienteException();
        }
    }

    private void validaLimite(double valor) throws LimiteExcedidoException {
        if (this.limite < valor) {
            throw new LimiteExcedidoException();
        }
    }

    private void validaConta(Conta conta) throws ContaInvalidaException {
        if (conta == null) {
            throw new ContaInexistenteException();
        }
        if (conta.equals(this)) {
            throw new ContaPropriaException();
        }
    }

    @Override
    public String toString() {
        return "\nConta - " +
                "\nNumero da conta: " + numeroConta +
                "\nTitular - " + titular +
                "\nSaldo da conta: " + saldo +
                "\nLimite da conta: " + limite +
                "\n------------";
    }

    public String toStringSemCliente() {
        return "Conta{" +
                "numeroConta=" + numeroConta +
//                ", titular=" + titular +
                ", saldo=" + saldo +
                ", limite=" + limite +
                '}';
    }
}
