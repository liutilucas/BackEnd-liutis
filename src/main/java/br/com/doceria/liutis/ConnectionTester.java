// src/main/java/br/com/doceria/liutis/ConnectionTester.java
package br.com.doceria.liutis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionTester {
    public static void main(String[] args) {
        // Pegamos os dados das variáveis de ambiente, exatamente como a Render faz
        String dbUrl = System.getenv("SPRING_DATASOURCE_URL");
        String user = System.getenv("SPRING_DATASOURCE_USERNAME");
        String password = System.getenv("SPRING_DATASOURCE_PASSWORD");

        System.out.println("--- INICIANDO TESTE DE CONEXÃO ---");
        System.out.println("URL: " + dbUrl);
        System.out.println("Usuário: " + user);

        if (dbUrl == null || user == null || password == null) {
            System.out.println("ERRO: Uma ou mais variáveis de ambiente não foram encontradas!");
            return;
        }

        Connection connection = null;
        try {
            // Tentamos estabelecer a conexão
            connection = DriverManager.getConnection(dbUrl, user, password);
            if (connection != null) {
                System.out.println(">>> SUCESSO! Conexão com o banco de dados estabelecida com sucesso! <<<");
            }
        } catch (SQLException e) {
            System.out.println(">>> FALHA! Não foi possível conectar ao banco de dados. <<<");
            System.out.println("Erro: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("--- TESTE DE CONEXÃO FINALIZADO ---");
        }
    }
}