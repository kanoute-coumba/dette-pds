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

    /**
     *
     */

    private static final long serialVersionUID = 1L;

    public Config(GeneralServices gs) {
        super();
        company = gs;
        this.setSize(1200,600);
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
        mainPanel.setLayout(new GridLayout(1,1));
        this.getContentPane().add(mainPanel, BorderLayout.CENTER);

        JPanel pan1 = new JPanel();
        pan1.setLayout(new FlowLayout());

        JButton bouton1 = new JButton("Statut fenetre");
        bouton1.setFont(new Font("Tahoma", Font.PLAIN, 20));
        JButton bouton4 = new JButton("Actualiser");
        bouton4.setFont(new Font("Tahoma", Font.PLAIN, 20));
        bouton1.addActionListener(this);

        table1.setModel(new DefaultTableModel(
                new Object[][] {
                },
                new String[] {
                        "id_fenetre", "statut", "temperature", "luminosité (en lux)", "store", "opacité(en %)", "id_equipement", "id_conf"
                }
        ));
        pan1.add(bouton1);
        pan1.add(bouton4);
        pan1.add(table1.getTableHeader());
        pan1.add(table1);
        mainPanel.add(pan1);

        bouton4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                Map window = WindowsTable.getWindow(connection, Windows.selection);

                Integer id_win = (Integer) window.get("id_windows");

                WindowsTable wTab = new WindowsTable ((Integer)window.get("id_windows"),
                        (String)window.get("status"),(Integer)window.get("temperature"),
                        (Integer)window.get("light"),(String)window.get("blind"),
                        (Integer)window.get("opacity"),(Integer)window.get("id_equipment"),
                        (Integer)window.get("idConf") );

                logger.info(" " +wTab.toString());

                Map values = WindowsPreConf.getValues(connection, id_win);
                Map temperature = TemperatureTable.degreeFromTemperature(connection, id_win);
                Map intensity = LightingTable.getIntensity(connection, id_win);
                //Map light = LightingTable.levelFromLighting(connection, id_win);

                //String level = (String) light.get("level");

                int degree = (Integer) temperature.get("degree");
                int lux = (Integer) intensity.get("intensity");

                int openValue = (Integer) values.get("openValue");;
                int reducedValue = (Integer) values.get("reducedValue");
                int lowIntensity = (Integer) values.get("lowIntensity");
                int mediumIntensity = (Integer) values.get("mediumIntensity");
                int highIntensity = (Integer) values.get("highIntensity");

                Boolean update1;

                if ( degree < openValue ) {
                    update1 = WindowsTable.windowsUpdateForTemperatureDegreeLessThan18(connection, id_win, degree);
                }
                else if (degree>openValue && degree<reducedValue ) {
                    update1 = WindowsTable.windowsUpdateForTemperatureDegree18_22(connection, id_win, degree);
                }
                else {
                    update1 = WindowsTable.windowsUpdateForTemperatureDegree22(connection, id_win, degree);
                }

                Boolean update2 = false ;


                if (lux < lowIntensity) {
                    int opacity= 100*lux/lowIntensity-50;
                    update2 = WindowsTable.windowsUpdateForLightLevelAucun(connection, id_win, lux, opacity);
                }
                else if (lowIntensity<lux && lux<mediumIntensity) {
                    int opacity= 100*lux/mediumIntensity-30;
                    update2 = WindowsTable.windowsUpdateForLightLevelFaible(connection, id_win, lux, opacity);
                }
                else if (mediumIntensity<lux && lux<highIntensity) {
                    int opacity= 100*lux/highIntensity-10;
                    update2 = WindowsTable.windowsUpdateForLightLevelMoyen(connection, id_win, lux, opacity);
                }
                else {
                    int opacity= 100*lux/highIntensity;
                    update2 = WindowsTable.windowsUpdateForLightLevelFort(connection, id_win, lux, opacity);
                }


                //System.out.println(degree);
                /*switch (level) {

                    case "Aucun":
                        update1 = WindowsTable.windowsUpdateForLightLevelAucun(connection, id_win, level);
                        break;
                    case "Faible":
                        update1 = WindowsTable.windowsUpdateForLightLevelFaible(connection, id_win, level);
                        break;
                    case "Moyen":
                        update1 = WindowsTable.windowsUpdateForLightLevelMoyen(connection, id_win, level);
                        break;
                    case "Fort":
                        update1 = WindowsTable.windowsUpdateForLightLevelFort(connection, id_win, level);
                        break;
                    default:
                        update1 = WindowsTable.windowsUpdateForLightLevelAutre(connection, id_win, level);
                }*/


                if(update1==true || update2==true) {
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

        });

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if (e.getActionCommand() == "Quitter") {
            System.exit(0);
        }
        else if (e.getActionCommand() == "Statut fenetre") {
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
                String idConf = String.valueOf((int) n.get("idConf"));

                String [] data = {id_windows, status, temperature, light, blind, opacity, id_equipment, idConf};
                DefaultTableModel tblModel = (DefaultTableModel) table1.getModel();

                tblModel.addRow(data);
            }

        }

        else if (e.getActionCommand() == "Fenetres electro-chromatiques") {

            welcome = new Welcome(company);
            this.dispose();
        }
    }
}