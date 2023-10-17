/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.minhaempresa.crudsenac.dao;

import com.minhaempresa.crudsenac.models.NotaFiscal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ftfer
 */
public class NotaFiscalDAO {

    private static String url = "jdbc:mysql://localhost:3306/basenotafiscal";
    private static String login = "root";
    private static String senha = "P@$$w0rd";

    public static boolean salvar(NotaFiscal obj) {

        boolean retorno = false;
        Connection conexao = null;
        PreparedStatement comandoSQL = null;

        try {
            //Receita de bolo para acessar o banco de dados

            // Passo 1: Carregar o Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            //Passo 2 - Abrir a conexão com o banco
            conexao = DriverManager.getConnection(url, login, senha);

            //Passo 3 - Preparar o comando Sql
            comandoSQL
                    = conexao.prepareStatement("INSERT INTO NotaFiscal (numeroNota,valorNota) VALUES(?,?) ",
                            PreparedStatement.RETURN_GENERATED_KEYS);

            comandoSQL.setInt(1, obj.getNumeroNota());
            comandoSQL.setDouble(2, obj.getValorNota());

            //Passo 4 - Executar o comando
            int linhasAfetadas = comandoSQL.executeUpdate();

            if (linhasAfetadas > 0) {
                retorno = true;

                ResultSet rs = comandoSQL.getGeneratedKeys();
                if (rs != null) {
                    while (rs.next()) {
                        int idGerado = rs.getInt(1);
                        obj.setIdNota(idGerado);
                    }
                }
            }

        } catch (ClassNotFoundException ex) {
            retorno = false;
        } catch (SQLException ex) {
            retorno = false;
        }

        return retorno;
    }//fim metodo salvar

    public static ArrayList<NotaFiscal> listar() {

        ArrayList<NotaFiscal> lista = new ArrayList<>();
        Connection conexao = null;
        PreparedStatement comandoSQL = null;
        ResultSet rs = null;

        try {
            // Passo 1: Carregar o Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            //Passo 2 - Abrir a conexão com o banco
            conexao = DriverManager.getConnection(url, login, senha);

            //Passo 3 - Preparar o comando Sql
            comandoSQL
                    = conexao.prepareStatement("SELECT * FROM NotaFiscal");

            //Passo 4 - Executar o comando
            rs = comandoSQL.executeQuery();

            if (rs != null) {

                while (rs.next()) {

                    int id = rs.getInt("idNota");
                    int numero = rs.getInt("numeroNota");
                    double valor = rs.getDouble("valorNota");

                    NotaFiscal item = new NotaFiscal(id, numero, valor);

                    lista.add(item);

                }

            }

        } catch (Exception e) {
            lista = null;
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException ex) {
                    Logger.getLogger(NotaFiscalDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return lista;

    }

}//Fim da classe
