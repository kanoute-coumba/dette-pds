package edu.episen.si.ing1.fise.pds.client.electroChromaticWindows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import edu.episen.si.ing1.fise.pds.client.ClientToServer;
import edu.episen.si.ing1.fise.pds.client.GeneralServices;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

public class Config extends JFrame implements ActionListener {
    private final static Logger logger = (Logger) LoggerFactory.getLogger(Config.class.getName());
    Welcome welcome;
    Windows window;
    GeneralServices company=null;
    ClientToServer connection=new ClientToServer();
    //int id_gs = company.getId_generalservices();
    JTable table1 = new JTable();

    JTable table2 = new JTable();


    /**
     *
     */

    private static final long serialVersionUID = 1L;

    public Config(GeneralServices gs) {
        super();
        company = gs;
        this.setSize(900,600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel leftPanel = new JPanel();
        GridLayout grille = new GridLayout(3,1);
        leftPanel.setLayout(grille);
        leftPanel.setBackground(Color.GREEN);
        leftPanel.setPreferredSize(new Dimension(300, 600));

        JPanel north = new JPanel();
        north.setLayout(new FlowLayout(FlowLayout.LEFT));
        ImageIcon icon = new ImageIcon("images/logo.png");

        JLabel logoLabel = new JLabel(icon);
        logoLabel.setIcon( new ImageIcon(icon.getImage().getScaledInstance(130,130, Image.SCALE_SMOOTH)));
        leftPanel.add(logoLabel);

        JPanel south = new JPanel();
        south.setBackground(Color.GREEN);
        JButton uc1 = new JButton ("Fonctionnalite 1");
        uc1.setFont(new Font("Tahoma", Font.PLAIN, 20));
        JButton uc2 = new JButton ("Fonctionnalite 2");
        uc2.setFont(new Font("Tahoma", Font.PLAIN, 20));
        JButton uc3 = new JButton ("Fenetres electro-chromatiques");
        uc3.addActionListener(this);
        uc3.setFont(new Font("Tahoma", Font.PLAIN, 20));
        JButton uc4 = new JButton ("Fonctionnalite 4");
        uc4.setFont(new Font("Tahoma", Font.PLAIN, 20));

        south.add(uc1);
        south.add(uc2);
        south.add(uc3);
        south.add(uc4);

        leftPanel.add(south);

        this.getContentPane().add(leftPanel, BorderLayout.WEST);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3,1));
        this.getContentPane().add(mainPanel, BorderLayout.CENTER);

        JPanel pan1 = new JPanel();
        pan1.setLayout(new FlowLayout());

        JButton bouton1 = new JButton("Statut par defaut");
        bouton1.setFont(new Font("Tahoma", Font.PLAIN, 20));
        bouton1.addActionListener(this);

        table1.setModel(new DefaultTableModel(
                new Object[][] {
                },
                new String[] {
                        "id_windows", "status", "temperature", "light", "blind", "opacity", "id_equipment"
                }
        ));
        pan1.add(bouton1);
        pan1.add(table1.getTableHeader());
        pan1.add(table1);
        mainPanel.add(pan1);

        JPanel pan2 = new JPanel();
        pan2.setLayout(new FlowLayout());

        JButton bouton2 = new JButton("Configurer température");
        bouton2.setFont(new Font("Tahoma", Font.PLAIN, 20));
        bouton2.addActionListener(this);
        JButton bouton3 = new JButton("Configurer éclairage");
        bouton3.setFont(new Font("Tahoma", Font.PLAIN, 20));
        bouton3.addActionListener(this);
        pan2.add(bouton2);
        pan2.add(bouton3);

        mainPanel.add(pan2);


        JPanel pan3 = new JPanel();
        pan3.setLayout(new FlowLayout());

        JButton bouton4 = new JButton("Actualiser statut");
        bouton4.setFont(new Font("Tahoma", Font.PLAIN, 20));
        bouton4.addActionListener(this);

        table2.setModel(new DefaultTableModel(
                new Object[][] {
                },
                new String[] {
                        "id_windows", "status", "temperature", "light", "blind", "opacity", "id_equipment"
                }
        ));

        pan3.add(bouton4);
        pan3.add(table2.getTableHeader());
        pan3.add(table2);

        mainPanel.add(pan3);


        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if (e.getActionCommand() == "Quitter") {
            System.exit(0);
        }
        else if (e.getActionCommand() == "Statut par defaut") {
            ArrayList<Map> rs1 = WindowsTable.windowsDefaultStatus(connection, Windows.selection);
            try
            {
                connection.client.close();


            }
            catch(Exception e1)
            {

            }

            for(Map n:rs1)

            {
                String id_windows = String.valueOf((int) n.get("id_windows"));
                String status = (String) n.get("status");
                String temperature = String.valueOf((int) n.get("temperature"));
                String light = (String) n.get("light");
                String blind = (String) n.get("blind");
                String opacity = (String) n.get("opacity");
                String id_equipment = String.valueOf((int) n.get("id_equipment"));

                String [] data = {id_windows, status, temperature, light, blind, opacity, id_equipment};
                DefaultTableModel tblModel = (DefaultTableModel) table1.getModel();

                tblModel.addRow(data);
            }

        }

        else if (e.getActionCommand() == "Configurer éclairage") {

            Map window = WindowsTable.getWindow(connection, Windows.selection);
            logger.info(" "+window);

            Integer id_win = (Integer) window.get("id_windows");
            System.out.println(id_win);

            WindowsTable wTab = new WindowsTable ((Integer)window.get("id_windows"),
                    (String) window.get("status"),(Integer)window.get("temperature"),
                    (String) window.get("light"),(String)window.get("blind"),
                    (String)window.get("opacity"),(Integer)window.get("id_equipment") );

            logger.info(" " +wTab.toString());


            Map light = LightingTable.levelFromLighting(connection, id_win);

            String level = (String) light.get("level");
            System.out.println(level.toString());

            Boolean update = false;

            switch (level) {

                case "Aucun":
                    update = WindowsTable.windowsUpdateForLightLevelAucun(connection, id_win, level);
                    break;
                case "Faible":
                    update = WindowsTable.windowsUpdateForLightLevelFaible(connection, id_win, level);
                    break;
                case "Moyen":
                    update = WindowsTable.windowsUpdateForLightLevelMoyen(connection, id_win, level);
                    break;
                case "Fort":
                    update = WindowsTable.windowsUpdateForLightLevelFort(connection, id_win, level);
                    break;
                default:
                    update = WindowsTable.windowsUpdateForLightLevelAutre(connection, id_win, level);
            }

            if(update==true) {
                JOptionPane.showMessageDialog(new JFrame(),
                        " Configuration terminée ! ",
                        " Succès ",
                        JOptionPane.PLAIN_MESSAGE);

            }
            else {
                JOptionPane.showMessageDialog(new JFrame(),
                        " Aucune modification apportée ",
                        " Echec ",
                        JOptionPane.PLAIN_MESSAGE);
            }


        }

        else if (e.getActionCommand() == "Configurer température") {

            Map window = WindowsTable.getWindow(connection, Windows.selection);

            Integer id_win = (Integer) window.get("id_windows");

            Map temperature = TemperatureTable.degreeFromTemperature(connection, id_win);

            int degree = (Integer) temperature.get("degree");
            System.out.println(degree);

            Boolean update = false ;

            if ( degree < 18 ) {
                update = WindowsTable.windowsUpdateForTemperatureDegreeLessThan18(connection, id_win, degree);
            }
            else if (degree>=18 && degree<22 ) {
                update = WindowsTable.windowsUpdateForTemperatureDegree18_22(connection, id_win, degree);
            }
            else {
                update = WindowsTable.windowsUpdateForTemperatureDegree22(connection, id_win, degree);
            }

            if (update==true) {
                JOptionPane.showMessageDialog(new JFrame(),
                        " Configuration terminée ! ",
                        " Succès ",
                        JOptionPane.PLAIN_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(new JFrame(),
                        " Aucune modification apportée ",
                        " Echec ",
                        JOptionPane.PLAIN_MESSAGE);
            }
        }

        else if (e.getActionCommand() == "Actualiser statut") {

            ArrayList<Map> rs4 = WindowsTable.windowsUpdatedStatus(connection, Windows.selection);
            try
            {
                connection.client.close();

            }
            catch(Exception e1)
            {

            }

            for(Map n:rs4)

            {

                String id_windows = String.valueOf((int) n.get("id_windows"));
                String status = (String) n.get("status");
                String temperature = String.valueOf((int) n.get("temperature"));
                String light = (String) n.get("light");
                String blind = (String) n.get("blind");
                String opacity = (String) n.get("opacity");
                String id_equipment = String.valueOf((int) n.get("id_equipment"));

                String [] data = {id_windows, status, temperature, light, blind, opacity, id_equipment};
                DefaultTableModel tblModel = (DefaultTableModel) table2.getModel();

                tblModel.addRow(data);
            }

        }
        else if (e.getActionCommand() == "Fenetres electro-chromatiques") {

            welcome = new Welcome(company);
            this.dispose();
        }
    }
}