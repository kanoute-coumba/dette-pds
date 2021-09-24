package edu.episen.si.ing1.fise.pds.backend.connectionPool;
import java.sql.SQLException;
import java.util.*;
public class JDBCConnectionPool {

    //les attributs
    private ArrayList<ConnectionDB> collection=new ArrayList<ConnectionDB>();

    private static int max_connection;

    private static int used_connection=0;

    //les accesseurs
    public static int getMax_connection() {
        return max_connection;
    }

    public static void setMax_connection(int max_con) {
        max_connection = max_con;
    }

    public static int getUsed_connection() {
        return used_connection;
    }

    public static void setUsed_connection(int used_con) {
        used_connection = used_con;
    }


 }