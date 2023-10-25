package com.minhaempresa.crudsenac.dao;

// Data Access Object
import com.minhaempresa.crudsenac.models.NotaFiscal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Resposnavel para fazer o insert no banco de dados
*/

public class NotaFiscalDAO {
    // variaveis para acessar o banco
    private static String url = "jdbc:mysql://localhost:3306/basenotafiscal";
    private static String login = "root";
    private static String senha = "P@$$w0rd";
    
    // CREATE
    public static boolean salvar(NotaFiscal obj){
        boolean retorno = false;
        // instanciando objeto para realizar a conexão
        Connection conexao = null;
        // 
        PreparedStatement comandoSQL = null;
        
        // receita de bolo para acessar o banco de dados
        try {
            // Passo1 - carregar o driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Passo2 - abrir a conexão com o banco
            conexao = DriverManager.getConnection(url, login, senha);
            // Passo3 - Preparar o comando SQL
            comandoSQL = conexao.prepareStatement("INSERT INTO NotaFiscal (numeroNota, valorNota) VALUES(?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);
            
            // pega o numero da nota
            comandoSQL.setInt(1, obj.getNumeroNota());
            // pega o valor da nota
            comandoSQL.setDouble(2, obj.getValorNota());
            
            // Passo4 - Executar o comando
            int linhasAfetadas = comandoSQL.executeUpdate();
            // se as linhas afetadas forem maior que 0 
            if(linhasAfetadas > 0){
                // então foi gravado no bd com sucesso
                retorno = true;
                
                // pega o ID auto incremental do banco
                ResultSet rs = comandoSQL.getGeneratedKeys();
                if (rs != null){
                    while(rs.next()){
                        int idGerado = rs.getInt(1);
                        obj.setIdNota(idGerado);
                    }
                }
            }
        }catch(ClassNotFoundException ex){
            retorno = false;
        } catch (SQLException ex) {
            retorno = false;
        }
        return retorno;
    }
    
    // READ
    public static ArrayList<NotaFiscal> listar(){
        ArrayList<NotaFiscal> lista = new ArrayList<>();
        // classes para utilizar
        Connection conexao = null;
        PreparedStatement comandoSQL = null;
        ResultSet rs = null; // recebe o resultado da consulta
        
        try {
            // Passo1 - carregar o driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Passo2 - abrir a conexão com o banco
            conexao = DriverManager.getConnection(url, login, senha);
            
            // Passo3 - Preparar o comando SQL
            comandoSQL = conexao.prepareStatement("SELECT * FROM NotaFiscal");
            
            // Passo4 - Executar a CONSULTA
            rs = comandoSQL.executeQuery();
            
            // se existir uma yabela ele inicia um while
            if (rs != null){
                while(rs.next()){
                    // cada volta do while cria um objeto para cada linha da lista
                    int id = rs.getInt("idNota");
                    int numero = rs.getInt("numeroNota");
                    double valor = rs.getDouble("valorNota");
                    
                    NotaFiscal item = new NotaFiscal(id, numero, valor);
                    
                    lista.add(item);
                }
            }
            
        } catch (Exception e) {
            lista = null;
        }finally{
            if(conexao != null){
                try {
                    conexao.close();
                } catch (SQLException ex) {
                    Logger.getLogger(NotaFiscalDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        return lista;
    }
    
    // UPDATE
    public static boolean alterar(NotaFiscal obj){
        boolean retorno = false;
        // instanciando objeto para realizar a conexão
        Connection conexao = null;
        // 
        PreparedStatement comandoSQL = null;
        
        // receita de bolo para acessar o banco de dados
        try {
            // Passo1 - carregar o driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Passo2 - abrir a conexão com o banco
            conexao = DriverManager.getConnection(url, login, senha);
            // Passo3 - Preparar o comando SQL
            comandoSQL = conexao.prepareStatement("UPDATE NotaFiscal SET numeroNota = ?, valorNota = ? WHERE idNota = ?");
            
            // pega o numero da nota
            comandoSQL.setInt(1, obj.getNumeroNota());
            // pega o valor da nota
            comandoSQL.setDouble(2, obj.getValorNota());
            // pega o valor do ID
            comandoSQL.setDouble(3, obj.getIdNota());
            
            // Passo4 - Executar o comando
            int linhasAfetadas = comandoSQL.executeUpdate();
            // se as linhas afetadas forem maior que 0 
            if(linhasAfetadas > 0){
                // então foi gravado no bd com sucesso
                retorno = true;
            }
        }catch(ClassNotFoundException ex){
            retorno = false;
        } catch (SQLException ex) {
            retorno = false;
        }
        return retorno;
    }
    
    // DELETE
    public static boolean excluir(int idExcluir){
        boolean retorno = false;
        // instanciando objeto para realizar a conexão
        Connection conexao = null;
        // 
        PreparedStatement comandoSQL = null;
        
        // receita de bolo para acessar o banco de dados
        try {
            // Passo1 - carregar o driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Passo2 - abrir a conexão com o banco
            conexao = DriverManager.getConnection(url, login, senha);
            // Passo3 - Preparar o comando SQL
            comandoSQL = conexao.prepareStatement("DELETE FROM NotaFiscal WHERE idNota = ?");
            
            // pega o valor do ID
            comandoSQL.setDouble(1, idExcluir);
            
            // Passo4 - Executar o comando
            int linhasAfetadas = comandoSQL.executeUpdate();
            // se as linhas afetadas forem maior que 0 
            if(linhasAfetadas > 0){
                // então foi gravado no bd com sucesso
                retorno = true;
            }
        }catch(ClassNotFoundException ex){
            retorno = false;
        } catch (SQLException ex) {
            retorno = false;
        }
        return retorno;
    }
    
    // aaa
    public static ArrayList<NotaFiscal> buscarPorNumero(int numeroNota){
        ArrayList<NotaFiscal> lista = new ArrayList<>();
        // classes para utilizar
        Connection conexao = null;
        PreparedStatement comandoSQL = null;
        ResultSet rs = null; // recebe o resultado da consulta
        
        try {
            // Passo1 - carregar o driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Passo2 - abrir a conexão com o banco
            conexao = DriverManager.getConnection(url, login, senha);
            
            // Passo3 - Preparar o comando SQL
            comandoSQL = conexao.prepareStatement("SELECT * FROM NotaFiscal WHERE numeroNota = ? ");
            
            comandoSQL.setInt(1, numeroNota);
            
            // Passo4 - Executar a CONSULTA
            rs = comandoSQL.executeQuery();
            
            // se existir uma tabela ele inicia um while
            if (rs != null){
                while(rs.next()){
                    // cada volta do while cria um objeto para cada linha da lista
                    int id = rs.getInt("idNota");
                    int numero = rs.getInt("numeroNota");
                    double valor = rs.getDouble("valorNota");
                    
                    NotaFiscal item = new NotaFiscal(id, numero, valor);
                    
                    lista.add(item);
                }
            }
            
        } catch (Exception e) {
            lista = null;
        }finally{
            if(conexao != null){
                try {
                    conexao.close();
                } catch (SQLException ex) {
                    Logger.getLogger(NotaFiscalDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        return lista;
    }
}
