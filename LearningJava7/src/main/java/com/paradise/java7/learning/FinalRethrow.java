package com.paradise.java7.learning;

import java.io.IOException;
import java.sql.SQLException;

public class FinalRethrow {
    public static void main(String[] args) throws Exception {
        previousRethrow2();
    }
    
    public static void previousRethrow1() throws IOException, SQLException {
        try {
            mayThrowIOException();
            mayThrowSQLException();
        } catch (IOException e) {            
            e.printStackTrace();
            throw e; //rethrow
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; //rethrow
        }
    }
    
    public static void previousRethrow2() throws Exception {
        try {
            mayThrowIOException();
            mayThrowSQLException();
        } catch (Exception e) {            
            e.printStackTrace();
            throw e; //rethrow
        }
    }
    
    public static void finalRethrow() throws IOException, SQLException {
        try {
            mayThrowIOException();
            mayThrowSQLException();
        } catch (final Exception e) {    //In fact no need to add "final" keyword. Just use Jdk 7 compiler is ok.
            e.printStackTrace();
            throw e; //rethrow
        }
    }
    
    public static void noFinalRethrow() throws IOException, SQLException {
        try {
            mayThrowIOException();
            mayThrowSQLException();
        } catch (Exception e) {    //In fact no need to add "final" keyword. Just use Jdk 7 compiler is ok.
            e.printStackTrace();
            throw e; //rethrow
        }
    }
    
    public static void mayThrowIOException() throws IOException {
        throw new IOException();
    }
    
    public static void mayThrowSQLException() throws SQLException {
        throw new SQLException();
    }

}
