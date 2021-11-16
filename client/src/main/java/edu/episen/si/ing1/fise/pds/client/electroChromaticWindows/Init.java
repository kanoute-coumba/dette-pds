package edu.episen.si.ing1.fise.pds.client.electroChromaticWindows;

import edu.episen.si.ing1.fise.pds.client.ClientToServer;
import edu.episen.si.ing1.fise.pds.client.GeneralServices;

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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Init extends JFrame implements ActionListener{

    Welcome welcome;
    Windows window;
    GeneralServices company=null;
    //int id_gs = company.getId_generalservices();
    JTable table = new JTable();
    ClientToServer connection=new ClientToServer();

    private static final long serialVersionUID = 1L;

    public Init(GeneralServices gs) {
        super();
        company = gs;
        //this.id_gs = id_gs;
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
        mainPanel.setLayout(new GridLayout(2,1));
        this.getContentPane().add(mainPanel, BorderLayout.CENTER);

        JPanel northPanel = new JPanel();
        northPanel.setLayout(new FlowLayout());

        JButton load = new JButton("Charger mes équipements");
        load.setFont(new Font("Tahoma", Font.PLAIN, 20));
        load.addActionListener(this);
        northPanel.add(load);

        table.setModel(new DefaultTableModel(
                new Object[][] {
                },
                new String[] {
                        "id_equipement", "type_equipement", "disponible", "en fonction", "id_gs", "id_position"
                }
        ));

        northPanel.add(table.getTableHeader());
        northPanel.add(table);

        mainPanel.add(northPanel);

        JPanel southPanel = new JPanel();
        northPanel.setLayout(new FlowLayout());
        JButton bouton1 = new JButton("Retour");
        bouton1.setFont(new Font("Tahoma", Font.PLAIN, 20));
        bouton1.addActionListener(this);

        JButton bouton2 = new JButton("Voir mes fenetres");
        bouton2.setFont(new Font("Tahoma", Font.PLAIN, 20));
        bouton2.addActionListener(this);

        southPanel.add(bouton1);
        southPanel.add(bouton2);
        mainPanel.add(southPanel);

        this.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if (e.getActionCommand() == "Retour") {


            welcome = new Welcome(company);
            this.dispose();
        }
        else if (e.getActionCommand() == "Voir mes fenetres") {

            try
            {
                connection.client.close();

            }
            catch(Exception e1)
            {

            }

            window = new Windows(company);
            this.dispose();
        }

        else if (e.getActionCommand() == "Fenetres electro-chromatiques") {

            try
            {
                connection.client.close();

            }
            catch(Exception e1)
            {

            }

            welcome = new Welcome(company);
            this.dispose();

        }
        else if(e.getActionCommand() == "Charger mes équipements") {

            String isAvailable;
            String isWorking;
            ArrayList<Map> rs = WindowsTable.ownEquipment(connection, company.getId_generalservices());

            if (rs.isEmpty()) {

                JOptionPane.showMessageDialog(new JFrame(),
                        "Pas encore d'équipement mappé pour cet emplacement"," Ressayer !",
                        JOptionPane.PLAIN_MESSAGE);
            }
            else if(rs==null )
            {
                JOptionPane.showMessageDialog(new JFrame(),
                        " Pas de location effectuée "," Aucun equipment disponible",
                        JOptionPane.ERROR_MESSAGE);
            }
            else {
                try
                {
                    connection.client.close();

                }
                catch(Exception e1)
                {

                }

                for(Map n:rs)
                {
                    String id_equipment = String.valueOf((int) n.get("id_equipment"));
                    String type_equipment = (String) n.get("type_equipment");
                    String is_available = String.valueOf((boolean) n.get("is_available"));
                    if (is_available=="true")
                        isAvailable= "oui" ;
                    else isAvailable= "non";
                    String is_working = String.valueOf((boolean) n.get("is_working"));
                    if (is_working=="true")
                        isWorking= "oui" ;
                    else isWorking= "non";
                    String id_gs = String.valueOf((int) n.get("id_gs"));
                    String id_position = String.valueOf((int) n.get("id_position"));


                    String [] data = {id_equipment, type_equipment, isAvailable, isWorking, id_gs, id_position};
                    DefaultTableModel tblModel = (DefaultTableModel) table.getModel();


                    tblModel.addRow(data);
                }

            }

        }

    }

}