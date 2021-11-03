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
import javax.swing.JPanel;

import edu.episen.si.ing1.fise.pds.client.ClientToServer;
import edu.episen.si.ing1.fise.pds.client.GeneralServices;

public class Welcome extends JFrame implements ActionListener{
    Init init ;
    Welcome welcome;
    Map n;
    static GeneralServices company=null;
    ClientToServer connection=new ClientToServer();

    /**
     *
     */

    private static final long serialVersionUID = 1L;

    public Welcome(GeneralServices gs) {

        super();
        company = gs;
        //this.id_gs=id_gs;

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

        JPanel northPanel = new JPanel();
        northPanel.setLayout(new FlowLayout());

        JLabel lab1 = new JLabel("Interface des fenetres");
        JLabel lab2 = new JLabel("electro-chromatiques");

        lab1.setFont(new Font("Calibri", Font.BOLD, 30));
        lab2.setFont(new Font("Calibri", Font.BOLD, 30));

        northPanel.add(lab1);
        northPanel.add(lab2);
        mainPanel.add(northPanel);

        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new FlowLayout());

        JButton bouton2 = new JButton("Voir mes équipements");
        bouton2.setFont(new Font("Tahoma", Font.PLAIN, 20));
        bouton2.addActionListener(this);

        middlePanel.add(bouton2);

        mainPanel.add(middlePanel);


        JPanel southPanel = new JPanel();
        northPanel.setLayout(new FlowLayout());
        JButton bouton3 = new JButton("Quitter");
        bouton3.setFont(new Font("Tahoma", Font.PLAIN, 20));
        bouton3.addActionListener(this);

        southPanel.add(bouton3);
        mainPanel.add(southPanel);

        this.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if (e.getActionCommand() == "Quitter") {
            System.exit(0);
        }
        else if (e.getActionCommand() == "Voir mes équipements") {
            ArrayList<Map>ws=WindowsTable.allRentedWorkSpace(connection, company.getId_generalservices());
            for(Map n:ws)
            {
                int id_workspace = (int) n.get("id_workspace");
                String type_workspace = (String) n.get("type_workspace");
                int floor_number = (int) n.get("floor_number");
                boolean is_available = (boolean) n.get("is_available");
                int id_building = (int) n.get("id_building");
                int id_gs = (int) n.get("id_gs");
            }

            init = new Init(company);
            this.dispose();

        }

    }

    public static void main(String[] args) {

        //id_ws = n.get("id_workspace");
        Welcome welcome = new Welcome(company);

    }

}