package edu.episen.si.ing1.fise.pds.backend.connectionPool;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerToClient {

    private final static Logger logger = LoggerFactory.getLogger(ServerToClient.class.getName());
    private DataSource data_source;
    private PrintWriter out;
    private BufferedReader in;
    private ObjectMapper mapper = new ObjectMapper();


    public String SendResponse(Request request) throws Exception {
        ConnectionDB con = data_source.takeCon();
        Connection connection = con.connection;
        String request_name = request.getName_request();
        System.out.println(request_name);
        String response_string = "";
        logger.info("++++++++++++++Send++++++++Response+++++++++++++++++");

        if (request_name.equals("all_generalServices")) {
            Map data_loading = (Map) request.getData();
            ResultSet rs1 = connection.createStatement()
                    .executeQuery("SELECT * FROM generalservices Order by company_name");
            List<Map> workSpaces = new ArrayList<Map>();
            while (rs1.next()) {
                Map<String, Object> hm = new HashMap<String, Object>();
                hm.put("id_gs", rs1.getInt("id_gs"));
                hm.put("company_name", rs1.getString("company_name"));
                workSpaces.add(hm);
            }
            rs1.close();
            Map<String, Object> response = new HashMap<String, Object>();
            response.put("name_request", request_name);
            response.put("data", workSpaces);
            response_string = mapper.writeValueAsString(response);
        }

        else if (request_name.equals("all_rented_workspaces")) {
            Map data_loading = (Map) request.getData();
            ResultSet rs1 = connection.createStatement().executeQuery("SELECT * FROM workspace where id_gs="
                    + (Integer) data_loading.get("id_gs") + " Order by id_workspace");
            List<Map> workSpaces = new ArrayList<Map>();
            while (rs1.next()) {
                Map<String, Object> hm = new HashMap<String, Object>();
                hm.put("id_workspace", rs1.getInt("id_workspace"));
                hm.put("type_workspace", rs1.getString("type_workspace"));
                hm.put("floor_number", rs1.getInt("floor_number"));
                hm.put("is_available", rs1.getBoolean("is_available"));
                hm.put("id_building", rs1.getInt("id_building"));
                hm.put("id_gs", rs1.getInt("id_gs"));
                workSpaces.add(hm);
            }
            rs1.close();
            Map<String, Object> response = new HashMap<String, Object>();
            response.put("name_request", request_name);
            response.put("data", workSpaces);
            response_string = mapper.writeValueAsString(response);
        }

        else if (request_name.equals("own_equipment")) {
            Map data_loading = (Map) request.getData();
            ResultSet rs1 = connection.createStatement().executeQuery("SELECT * FROM Equipment WHERE id_gs = "
                    + (Integer) data_loading.get("id_gs") + " ORDER BY id_equipment");
            List<Map> equipments = new ArrayList<Map>();
            while (rs1.next()) {
                Map<String, Object> hm = new HashMap<String, Object>();
                hm.put("id_equipment", rs1.getInt("id_equipment"));
                hm.put("type_equipment", rs1.getString("type_equipment"));
                hm.put("is_available", rs1.getBoolean("is_available"));
                hm.put("is_working", rs1.getBoolean("is_working"));
                hm.put("id_gs", rs1.getInt("id_gs"));
                hm.put("id_position", rs1.getInt("id_position"));
                equipments.add(hm);
            }
            rs1.close();
            Map<String, Object> response = new HashMap<String, Object>();
            response.put("name_request", request_name);
            response.put("data", equipments);
            response_string = mapper.writeValueAsString(response);
        }

        else if (request_name.equals("own_windows")) {
            Map data_loading = (Map) request.getData();
            ResultSet rs1 = connection.createStatement()
                    .executeQuery("SELECT * FROM Equipment WHERE id_gs = " + (Integer) data_loading.get("id_gs")
                            + " AND type_equipment = 'fenetre electro-chromatique' ORDER BY id_equipment ");
            List<Map> windows = new ArrayList<Map>();
            while (rs1.next()) {
                Map<String, Object> hm = new HashMap<String, Object>();
                hm.put("id_equipment", rs1.getInt("id_equipment"));
                hm.put("type_equipment", rs1.getString("type_equipment"));
                hm.put("is_available", rs1.getBoolean("is_available"));
                hm.put("is_working", rs1.getBoolean("is_working"));
                hm.put("id_gs", rs1.getInt("id_gs"));
                hm.put("id_position", rs1.getInt("id_position"));
                windows.add(hm);
            }
            rs1.close();
            Map<String, Object> response = new HashMap<String, Object>();
            response.put("name_request", request_name);
            response.put("data", windows);
            response_string = mapper.writeValueAsString(response);
        }

        else if (request_name.equals("default_status")) {
            Map data_loading = (Map) request.getData();
            ResultSet rs1 = connection.createStatement().executeQuery("SELECT * FROM Windows WHERE id_equipment = "
                    + (Integer) data_loading.get("id_equipment") + " LIMIT 1 ");
            List<Map> windowStatus = new ArrayList<Map>();
            while (rs1.next()) {
                Map<String, Object> hm = new HashMap<String, Object>();
                hm.put("id_windows", rs1.getInt("id_windows"));
                hm.put("status", rs1.getString("status"));
                hm.put("temperature", rs1.getInt("temperature"));
                hm.put("light", rs1.getInt("light"));
                hm.put("blind", rs1.getString("blind"));
                hm.put("opacity", rs1.getInt("opacity"));
                hm.put("id_equipment", rs1.getInt("id_equipment"));
                hm.put("idConf", rs1.getInt("idConf"));
                windowStatus.add(hm);
            }
            rs1.close();
            Map<String, Object> response = new HashMap<String, Object>();
            response.put("name_request", request_name);
            response.put("data", windowStatus);
            response_string = mapper.writeValueAsString(response);
        }

        else if (request_name.equals("get_window")) {
            Map data_loading = (Map) request.getData();
            ResultSet rs1 = connection.createStatement().executeQuery(
                    "SELECT * FROM Windows WHERE id_equipment = " + (Integer) data_loading.get("id_equipment") + " ");
            List<Map> window = new ArrayList<Map>();
            while (rs1.next()) {
                Map<String, Object> hm = new HashMap<String, Object>();
                hm.put("id_windows", rs1.getInt("id_windows"));
                hm.put("status", rs1.getString("status"));
                hm.put("temperature", rs1.getInt("temperature"));
                hm.put("light", rs1.getInt("light"));
                hm.put("blind", rs1.getString("blind"));
                hm.put("opacity", rs1.getInt("opacity"));
                hm.put("id_equipment", rs1.getInt("id_equipment"));
                hm.put("idConf", rs1.getInt("idConf"));
                window.add(hm);
            }
            rs1.close();
            Map<String, Object> response = new HashMap<String, Object>();
            response.put("name_request", request_name);
            response.put("data", window);
            response_string = mapper.writeValueAsString(response);
        }

        else if (request_name.equals("get_temperature")) {
            Map data_loading = (Map) request.getData();
            ResultSet rs1 = connection.createStatement().executeQuery(
                    "SELECT * FROM Temperature WHERE id_windows = " + (Integer) data_loading.get("id_windows") + " ");
            List<Map> temperature = new ArrayList<Map>();
            while (rs1.next()) {
                Map<String, Object> hm = new HashMap<String, Object>();
                hm.put("id_temperature", rs1.getInt("id_temperature"));
                hm.put("degree", rs1.getInt("degree"));
                hm.put("id_windows", rs1.getInt("id_windows"));
                temperature.add(hm);
            }
            rs1.close();
            Map<String, Object> response = new HashMap<String, Object>();
            response.put("name_request", request_name);
            response.put("data", temperature);
            response_string = mapper.writeValueAsString(response);
        }

        else if (request_name.equals("get_light")) {
            Map data_loading = (Map) request.getData();
            ResultSet rs1 = connection.createStatement().executeQuery(
                    "SELECT * FROM Lighting WHERE id_windows = " + (Integer) data_loading.get("id_windows") + " ");
            List<Map> light = new ArrayList<Map>();
            while (rs1.next()) {
                Map<String, Object> hm = new HashMap<String, Object>();
                hm.put("id_light", rs1.getInt("id_light"));
                hm.put("intensity", rs1.getString("intensity"));
                hm.put("id_windows", rs1.getInt("id_windows"));
                light.add(hm);
            }
            rs1.close();
            Map<String, Object> response = new HashMap<String, Object>();
            response.put("name_request", request_name);
            response.put("data", light);
            response_string = mapper.writeValueAsString(response);
        }

        else if (request_name.equals("light_aucun")) {
            Map data_loading = (Map) request.getData();

            int op = 0;
            connection.setAutoCommit(false);

            op = connection.createStatement()
                    .executeUpdate(" UPDATE Windows SET light = '" + (Integer) data_loading.get("light")
                            + "', blind = 'Niveau 0', opacity = '"+(Integer) data_loading.get("opacity")+"' where id_windows = "
                            + (Integer) data_loading.get("id_windows") + " ");
            List<Map> update = new ArrayList<Map>();
            logger.info(op + " " + op);
            Map<String, Object> hm = new HashMap<String, Object>();
            if (op > 0) {
                connection.commit();
                connection.setAutoCommit(true);
                hm.put("update_done", true);
            } else {
                connection.rollback();
                connection.setAutoCommit(true);
                hm.put("not_done", false);

            }
            update.add(hm);
            Map<String, Object> response = new HashMap<String, Object>();
            response.put("name_request", request_name);
            response.put("data", update);
            response_string = mapper.writeValueAsString(response);
        }

        else if (request_name.equals("light_faible")) {
            Map data_loading = (Map) request.getData();

            int op = 0;
            connection.setAutoCommit(false);

            op = connection.createStatement()
                    .executeUpdate(" UPDATE Windows SET light = '" + (String) data_loading.get("light")
                            + "', blind = 'Niveau 1', opacity = '"+(Integer) data_loading.get("opacity")+"' where id_windows = "
                            + (Integer) data_loading.get("id_windows") + " ");
            List<Map> update = new ArrayList<Map>();
            logger.info(op + " " + op);
            Map<String, Object> hm = new HashMap<String, Object>();
            if (op > 0) {
                connection.commit();
                connection.setAutoCommit(true);
                hm.put("update_done", true);
            } else {
                connection.rollback();
                connection.setAutoCommit(true);
                hm.put("not_done", false);

            }
            update.add(hm);
            Map<String, Object> response = new HashMap<String, Object>();
            response.put("name_request", request_name);
            response.put("data", update);
            response_string = mapper.writeValueAsString(response);
        }

        else if (request_name.equals("light_moyen")) {
            Map data_loading = (Map) request.getData();

            int op = 0;
            connection.setAutoCommit(false);

            op = connection.createStatement()
                    .executeUpdate(" UPDATE Windows SET light = '" + (String) data_loading.get("light")
                            + "', blind = 'Niveau 2', opacity = '"+(Integer) data_loading.get("opacity")+"' where id_windows = "
                            + (Integer) data_loading.get("id_windows") + " ");
            List<Map> update = new ArrayList<Map>();
            logger.info(op + " " + op);
            Map<String, Object> hm = new HashMap<String, Object>();
            if (op > 0) {
                connection.commit();
                connection.setAutoCommit(true);
                hm.put("update_done", true);
            } else {
                connection.rollback();
                connection.setAutoCommit(true);
                hm.put("not_done", false);

            }
            update.add(hm);
            Map<String, Object> response = new HashMap<String, Object>();
            response.put("name_request", request_name);
            response.put("data", update);
            response_string = mapper.writeValueAsString(response);
        }

        else if (request_name.equals("light_fort")) {
            Map data_loading = (Map) request.getData();

            int op = 0;
            connection.setAutoCommit(false);

            op = connection.createStatement()
                    .executeUpdate(" UPDATE Windows SET light = '" + (String) data_loading.get("light")
                            + "', blind = 'Niveau 3', opacity = '"+(Integer) data_loading.get("opacity")+"' where id_windows = "
                            + (Integer) data_loading.get("id_windows") + " ");
            List<Map> update = new ArrayList<Map>();
            logger.info(op + " " + op);
            Map<String, Object> hm = new HashMap<String, Object>();
            if (op > 0) {
                connection.commit();
                connection.setAutoCommit(true);
                hm.put("update_done", true);
            } else {
                connection.rollback();
                connection.setAutoCommit(true);
                hm.put("not_done", false);

            }
            update.add(hm);
            Map<String, Object> response = new HashMap<String, Object>();
            response.put("name_request", request_name);
            response.put("data", update);
            response_string = mapper.writeValueAsString(response);
        }

        else if (request_name.equals("light_autre")) {
            Map data_loading = (Map) request.getData();

            int op = 0;
            connection.setAutoCommit(false);

            op = connection.createStatement()
                    .executeUpdate(" UPDATE Windows SET light = '" + (String) data_loading.get("light")
                            + "', blind = 'Niveau 4', opacity = 'Fort' where id_windows = "
                            + (Integer) data_loading.get("id_windows") + " ");
            List<Map> update = new ArrayList<Map>();
            logger.info(op + " " + op);
            Map<String, Object> hm = new HashMap<String, Object>();
            if (op > 0) {
                connection.commit();
                connection.setAutoCommit(true);
                hm.put("update_done", true);
            } else {
                connection.rollback();
                connection.setAutoCommit(true);
                hm.put("not_done", false);

            }
            update.add(hm);
            Map<String, Object> response = new HashMap<String, Object>();
            response.put("name_request", request_name);
            response.put("data", update);
            response_string = mapper.writeValueAsString(response);
        }

        else if (request_name.equals("less_than_18")) {
            Map data_loading = (Map) request.getData();

            int op = 0;
            connection.setAutoCommit(false);

            op = connection.createStatement()
                    .executeUpdate(" UPDATE Windows SET status = 'Ferme' , temperature = "
                            + (Integer) data_loading.get("temperature") + " WHERE id_windows = "
                            + (Integer) data_loading.get("id_windows") + " ");
            List<Map> update = new ArrayList<Map>();
            logger.info(op + " " + op);
            Map<String, Object> hm = new HashMap<String, Object>();
            if (op > 0) {
                connection.commit();
                connection.setAutoCommit(true);
                hm.put("update_done", true);
            } else {
                connection.rollback();
                connection.setAutoCommit(true);
                hm.put("not_done", false);

            }
            update.add(hm);
            Map<String, Object> response = new HashMap<String, Object>();
            response.put("name_request", request_name);
            response.put("data", update);
            response_string = mapper.writeValueAsString(response);
        }

        else if (request_name.equals("between_18_22")) {
            Map data_loading = (Map) request.getData();

            int op = 0;
            connection.setAutoCommit(false);

            op = connection.createStatement()
                    .executeUpdate(" UPDATE Windows SET status = 'Reduit', temperature = "
                            + (Integer) data_loading.get("temperature") + " WHERE id_windows = "
                            + (Integer) data_loading.get("id_windows") + " ");
            List<Map> update = new ArrayList<Map>();
            logger.info(op + " " + op);
            Map<String, Object> hm = new HashMap<String, Object>();
            if (op > 0) {
                connection.commit();
                connection.setAutoCommit(true);
                hm.put("update_done", true);
            } else {
                connection.rollback();
                connection.setAutoCommit(true);
                hm.put("not_done", false);

            }
            update.add(hm);
            Map<String, Object> response = new HashMap<String, Object>();
            response.put("name_request", request_name);
            response.put("data", update);
            response_string = mapper.writeValueAsString(response);
        }

        else if (request_name.equals("more_than_22")) {
            Map data_loading = (Map) request.getData();

            int op = 0;
            connection.setAutoCommit(false);

            op = connection.createStatement()
                    .executeUpdate(" UPDATE Windows SET status = 'Ouvert', temperature = "
                            + (Integer) data_loading.get("temperature") + " WHERE id_windows = "
                            + (Integer) data_loading.get("id_windows") + " ");
            List<Map> update = new ArrayList<Map>();
            logger.info(op + " " + op);
            Map<String, Object> hm = new HashMap<String, Object>();
            if (op > 0) {
                connection.commit();
                connection.setAutoCommit(true);
                hm.put("update_done", true);
            } else {
                connection.rollback();
                connection.setAutoCommit(true);
                hm.put("not_done", false);

            }
            update.add(hm);
            Map<String, Object> response = new HashMap<String, Object>();
            response.put("name_request", request_name);
            response.put("data", update);
            response_string = mapper.writeValueAsString(response);
        }

        else if (request_name.equals("updated_status")) {
            Map data_loading = (Map) request.getData();
            ResultSet rs1 = connection.createStatement().executeQuery(
                    "SELECT * FROM Windows WHERE id_windows = " + (Integer) data_loading.get("id_windows") + " ");
            List<Map> windowStatus = new ArrayList<Map>();
            while (rs1.next()) {
                Map<String, Object> hm = new HashMap<String, Object>();
                hm.put("id_windows", rs1.getInt("id_windows"));
                hm.put("status", rs1.getString("status"));
                hm.put("temperature", rs1.getInt("temperature"));
                hm.put("light", rs1.getString("light"));
                hm.put("blind", rs1.getString("blind"));
                hm.put("opacity", rs1.getString("opacity"));
                hm.put("id_equipment", rs1.getInt("id_equipment"));
                hm.put("idConf", rs1.getInt("idConf"));
                windowStatus.add(hm);
            }
            rs1.close();
            Map<String, Object> response = new HashMap<String, Object>();
            response.put("name_request", request_name);
            response.put("data", windowStatus);
            response_string = mapper.writeValueAsString(response);
        }

        else if (request_name.equals("get_values")) {
            Map data_loading = (Map) request.getData();
            ResultSet rs1 = connection.createStatement().executeQuery("SELECT * FROM WindowsPreConf WHERE idConf = "
                    + (Integer) data_loading.get("idConf") + " ORDER BY idConf");
            List<Map> values = new ArrayList<Map>();
            while (rs1.next()) {
                Map<String, Object> hm = new HashMap<String, Object>();
                hm.put("idConf", rs1.getInt("idConf"));
                hm.put("openValue", rs1.getString("openValue"));
                hm.put("reducedValue", rs1.getBoolean("reducedValue"));
                hm.put("lowIntensity", rs1.getInt("lowIntensity"));
                hm.put("mediumIntensity", rs1.getInt("mediumIntensity"));
                hm.put("highIntensity", rs1.getInt("highIntensity"));
                values.add(hm);
            }
            rs1.close();
            Map<String, Object> response = new HashMap<String, Object>();
            response.put("name_request", request_name);
            response.put("data", values);
            response_string = mapper.writeValueAsString(response);
        }

        else if (request_name.equals("get_intensity")) {
            Map data_loading = (Map) request.getData();
            ResultSet rs1 = connection.createStatement().executeQuery(
                    "SELECT * FROM Lighting WHERE id_windows = " + (Integer) data_loading.get("id_windows") + " ");
            List<Map> intensity = new ArrayList<Map>();
            while (rs1.next()) {
                Map<String, Object> hm = new HashMap<String, Object>();
                hm.put("id_light", rs1.getInt("id_light"));
                hm.put("intensity", rs1.getString("intensity"));
                hm.put("id_windows", rs1.getInt("id_windows"));
                intensity.add(hm);
            }
            rs1.close();
            Map<String, Object> response = new HashMap<String, Object>();
            response.put("name_request", request_name);
            response.put("data", intensity);
            response_string = mapper.writeValueAsString(response);
        }

        data_source.returnCon(con);
        return response_string;
    }

    public ServerToClient(DataSource ds) {
        try {
            data_source = ds;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}