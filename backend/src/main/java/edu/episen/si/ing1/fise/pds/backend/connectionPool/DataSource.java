package edu.episen.si.ing1.fise.pds.backend.connectionPool;

import java.util.ArrayList;
public class DataSource {

    //attributs
    static JDBCConnectionPool conPool=new JDBCConnectionPool ();
    private int connection_interval;

    public int getMaxConnection()
    {
        return conPool.getMax_connection();
    }

    public int getUsedConnection()
    {
        return conPool.getUsed_connection();
    }
    public void setMaxConnection(int max_con)
    {
        conPool.setMax_connection(max_con);

    }

    public void setUsedConnection(int used_con)
    {
        conPool.setUsed_connection(used_con);
    }

    //constructor
    public DataSource(int max_con, int con_interv) {
        setUsedConnection(0);
        setMaxConnection(max_con);
        connection_interval=con_interv;
        ArrayList<ConnectionDB> cons=new ArrayList<ConnectionDB>();
        for(int i=0;i<max_con;i++)
        {
            ConnectionDB c=new ConnectionDB();
            cons.add(c);
        }
        conPool.feed(cons);
    }


    //methods
    public static ConnectionDB takeCon()
    {
        return conPool.connectionEntity();
    }
    public static void returnCon(ConnectionDB con)
    {
        conPool.returnCon(con);
    }
    public static void closure()
    {
        conPool.Close();
    }

}

