package edu.episen.si.ing1.fise.pds.backend.connectionPool;
import java.sql.SQLException;
import java.util.*;
public class JDBCConnectionPool {

    //les attributs
    private ArrayList<ConnectionDB> collection=new ArrayList<>();

    private static int max_connection;

    private static int used_connection=0;

    //les accesseurs
    public static int getMax_connection() {
        return max_connection;
    }
    public static int getUsed_connection() {
        return used_connection;
    }

    public static void setMax_connection(int max_con) {
        max_connection = max_con;
    }
    public static void setUsed_connection(int used_con) {
        used_connection = used_con;
    }
    
  //les methodes
    public synchronized void feed(Collection<ConnectionDB> con)
    {
    	collection.addAll(con);
    }

    public synchronized ConnectionDB connectionEntity()
    {

        if(used_connection<max_connection  && collection.size()>0) {
            ConnectionDB con = collection.get(collection.size()-1);
            used_connection++;
            collection.remove(con);
            return con;
        }
        else
            return  null;
    }
    public synchronized void returnCon(ConnectionDB con)
    {
    	collection.add(con);
    	used_connection--;
    }
    public synchronized void Close()
    {
    	for(ConnectionDB c: collection)
    	{try {
			c.connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}}
    	
    }
 }