import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBanco {
    private static final String URLDATABASE = "jdbc:mysql://localhost:3306/db_sistema_bancario?createDatabaseIfNotExist=true";
    private static final String USUARIO = "root";
    private static final String SENHA = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URLDATABASE, USUARIO, SENHA);
    }

}