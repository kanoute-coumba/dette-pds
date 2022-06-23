package edu.episen.si.ing1.fise.pds.backend.connectionPool;
import java.sql.SQLException;
import java.util.*;
public class JDBCConnectionPool {

    //les attributs
    private ArrayList<ConnectionDB> collection=new ArrayList<ConnectionDB>();

    private int maxConnection;

    private int usedConnection=0;

    //les accesseurs
    public int getMaxConnection() {
        return maxConnection;
    }

    public void setMaxConnection(int maxCon) {
        maxConnection = maxCon;
    }


    public int getUsedConnection() {
        return usedConnection;
    }

    public void setUsedConnection(int usedCon) {
        usedConnection = usedCon;
    }

    //le constructeur
    public JDBCConnectionPool () {}

    //les methodes
    public synchronized void feed(Collection<ConnectionDB> con)
    {
        collection.addAll(con);
    }

    public synchronized ConnectionDB connectionEntity()
    {

        if(usedConnection< maxConnection  && collection.size()>0) {
            ConnectionDB con = collection.get(collection.size()-1);
            usedConnection++;
            collection.remove(con);
            return con;
        }
        else
            return  null;
    }
    public synchronized void returnCon(ConnectionDB con)
    {
        collection.add(con);
        usedConnection--;
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