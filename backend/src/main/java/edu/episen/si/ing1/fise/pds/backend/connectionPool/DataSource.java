package edu.episen.si.ing1.fise.pds.backend.connectionPool;

import java.util.ArrayList;
public class DataSource {
    //attributs
    JDBCConnectionPool conPool=new JDBCConnectionPool ();
    private int connectionInterval;

    public int getMaxConnection()
    {
        return conPool.getMaxConnection();
    }

    public int getUsedConnection()
    {
        return conPool.getUsedConnection();
    }
    public void setMaxConnection(int maxCon)
    {
        conPool.setMaxConnection(maxCon);

    }

    public void setUsedConnection(int usedCon)
    {
        conPool.setUsedConnection(usedCon);
    }

    //constructor
    public DataSource(int maxCon, int conInterv) {
        setUsedConnection(0);
        setMaxConnection(maxCon);
        connectionInterval=conInterv;
        ArrayList<ConnectionDB> cons=new ArrayList<ConnectionDB>();
        for(int i=0;i<maxCon;i++)
        {
            ConnectionDB c=new ConnectionDB();
            cons.add(c);
        }
        conPool.feed(cons);
    }


    //methods
    public ConnectionDB takeCon()
    {
        return conPool.connectionEntity();
    }
    public void returnCon(ConnectionDB con)
    {
        conPool.returnCon(con);
    }
    public void closure()
    {
        conPool.Close();
    }

}
