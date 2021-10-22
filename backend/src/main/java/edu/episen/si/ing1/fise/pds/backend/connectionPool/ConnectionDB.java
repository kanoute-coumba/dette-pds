package edu.episen.si.ing1.fise.pds.backend.connectionPool;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.sql.*;
import java.util.List;
import java.util.Random;


public class ConnectionDB {
        //attribute

        private List<Users> person;
        private static final String crud_script_enVar = "CRUD";
        private static final String data_smart_city_enVar = "SERVER_CONFIG";
        private Config config=null;
        public Connection connection;
        //the builder
        public ConnectionDB()
        {
                try {
                        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
                        config = mapper.readValue(new File(System.getenv(data_smart_city_enVar)), Config.class);
                        mapper = new ObjectMapper();
                        person = mapper.readValue(new File(System.getenv(crud_script_enVar)), new TypeReference<List<Users>>(){});
                        Class.forName(config.getDriver());
                        this.connection = DriverManager.getConnection(config.getURL(),
                                config.getUsername(),config.getPassword());
                } catch (Exception e) {
                        e.printStackTrace();
                }

        }

        public Users RandomPerson()
        {
                Random index=new Random();
                int size=person.size();
                Users user=person.get(index.nextInt(size-1));
                return user;
        }

        public int RandomId()
        {
                int[] id_list=new int[100];
                ResultSet result=null;
                try {
                        Statement request=connection.createStatement();
                        result=request.executeQuery(config.getRead());
                } catch (Exception e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                }
                int i=0;
                try {
                        while (result.next()) {
                                id_list[i]=result.getInt(1);
                                i++;
                        }
                } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
                Random index=new Random();
                return id_list[index.nextInt(i)];
        }
        public String createPerson()
        {
                try {

                        Users user=RandomPerson();
                        PreparedStatement pc = connection.prepareStatement(config.getCreate());
                        pc.setString(1,user.getName());
                        pc.setInt(2,user.getAge());
                        int nb = 0;
                        nb = pc.executeUpdate();
                        if(nb!=0)
                                return "The user with the name "+user.getName()+ " is added with success!!";
                }catch(SQLException ex)
                {
                        return "failed with exception !!";
                }
                return "failed!!";
        }

        public String updatePerson()
        {
                try {
                        PreparedStatement pc = connection.prepareStatement(config.getUpdate());
                        Random age=new Random();
                        int id=RandomId();
                        //pc.setString(3,"name_updated_"+age.nextInt(80));
                        pc.setInt(2,age.nextInt(90));
                        pc.setInt(1,id);
                        int nb = 0;
                        nb = pc.executeUpdate();
                        if(nb!=0)
                                return "The person with the id "+id + " is modified with success!!";
                }catch(SQLException ex)
                {
                        return "failed with exception !!";
                }
                return "failed!!";
        }
        public String deletePerson()
        {
                try {
                        PreparedStatement pc = connection.prepareStatement(config.getDelete());
                        int id=RandomId();
                        pc.setInt(1,id);
                        int nb = 0;
                        nb = pc.executeUpdate();
                        if(nb!=0)
                                return "The user with the id "+id+" is deleted with success!!";
                }catch(SQLException ex)
                {
                        return "failed with exception!!";
                }
                return "failed!!";
        }
        public StringBuilder listPerson() throws Exception
        {
                Statement request=connection.createStatement();
                ResultSet result=null;
                StringBuilder r=new StringBuilder();
                result=request.executeQuery(config.getRead());
                while (result.next()) {
                        String name_ = result.getString(2);
                        int age_ = result.getInt(3);
                        int id_ = result.getInt(1);
                        r.append("ID : "+id_+"  and name : " + name_ + "  and age : " + age_);
                        r.append(System.getProperty("line.separator"));
                }
                result.close();
                return r;
        }


}
