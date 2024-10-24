public class Cliente {
    private int id;
    private long cpf;
    private String nome;
    private Conta conta;

    public Cliente(long cpf, String nome) {
        this.cpf = cpf;
        this.nome = nome;
    }

    public Cliente(int id, long cpf, String nome) {
        this(cpf, nome);
        this.id = id;
    }

    public Cliente(int id, long cpf, String nome, Conta conta) {
        this(id, cpf, nome);
        this.nome = nome;
        this.conta = conta;
    }

    public int getId() {
        return id;
    }

    public long getCpf() {
        return cpf;
    }

    public String getNome() {
        return nome;
    }

    public Conta getConta() {
        return conta;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return  "\nID titular: " + id +
                "\nCPF titular: " + cpf +
                "\nNome titular: " + nome +
                "\n------------";
    }

    public String toStringComConta() {
        return "Cliente{" +
                "id=" + id +
                ", cpf=" + cpf +
                ", nome='" + nome + '\'' +
                ", conta=" + conta.toStringSemCliente() +
                '}';
    }
}