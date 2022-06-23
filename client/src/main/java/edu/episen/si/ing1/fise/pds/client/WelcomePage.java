package edu.episen.si.ing1.fise.pds.client;

import edu.episen.si.ing1.fise.pds.client.electroChromaticWindows.Welcome;
import edu.episen.si.ing1.fise.pds.client.electroChromaticWindows.WindowsTable;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.ArrayList;
import java.util.Map;
import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class WelcomePage extends JFrame {

    private ClientToServer connection=new ClientToServer();
    GeneralServices company;
    JPanel right=new JPanel(new BorderLayout());
    JPanel left = new JPanel(new GridLayout(5,1));
    Color color=new Color(190,245,116);

    private void myInterface()
    {
        setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        //Left menu creation

        left.setMinimumSize(new Dimension(250, 480));
        left.setPreferredSize(new Dimension(250, 480));
        left.setMaximumSize(new Dimension(250, 480));

        JPanel p=new JPanel(new BorderLayout());
        JLabel image = new JLabel();
        JLabel welcomeSentence = new JLabel("Bienvenue "+company.getCompany_name());
        welcomeSentence.setHorizontalAlignment(JLabel.CENTER);
        welcomeSentence.setFont(new Font("Serif", Font.ITALIC, 20));
        p.add(image,BorderLayout.NORTH);
        p.add(welcomeSentence,BorderLayout.CENTER);
        p.setBackground(color);
        left.add(p);

        p=new JPanel(new GridLayout());
        JLabel use_case1 = new JLabel("Gestion de Location");
        use_case1.setHorizontalAlignment(JLabel.CENTER);
        use_case1.setFont(new Font("Serif", Font.BOLD,17));
        p.add(use_case1,BorderLayout.CENTER);
        p.setBackground(color);
        left.add(p);

        p=new JPanel(new GridLayout());
        JLabel use_case2 = new JLabel("Mappage Capteur/Equipement");
        use_case2.setHorizontalAlignment(JLabel.CENTER);
        use_case2.setFont(new Font("Serif", Font.BOLD,17));
        p.add(use_case2,BorderLayout.CENTER);
        p.setBackground(color);
        left.add(p);

        p=new JPanel(new GridLayout());
        JLabel use_case3 = new JLabel("Configuration Fenetre EC");
        use_case3.setHorizontalAlignment(JLabel.CENTER);
        use_case3.setFont(new Font("Serif", Font.BOLD,17));
        p.add(use_case3,BorderLayout.CENTER);
        p.setBackground(color);
        left.add(p);
        p.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                ArrayList<Map>ws= WindowsTable.allRentedWorkSpace(connection, company.getId_generalservices());

                    if (ws == null) {
                        JOptionPane.showMessageDialog(new JFrame(),
                                "Veuillez d'abord louer un espace", "Configuration fenetres, impossible pour le moment",
                                JOptionPane.ERROR_MESSAGE);
                    } else if (ws.isEmpty()) {

                        JOptionPane.showMessageDialog(new JFrame(),
                                "Veuillez d'abord louer un espace", "Configuration fenetres, impossible pour le moment",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        try
                        {
                            connection.client.close();

                        }
                        catch(Exception e1)
                        {

                        }
                        Welcome welcome = new Welcome(company);
                        //t.setVisible(true);
                        dispose();
                    }
                }
            });
        p.addMouseListener(new MouseAdapter()
        {
            public void mouseEntered(MouseEvent e)
            {
                e.getComponent().setBackground(Color.white);
            }
            public void mouseExited(MouseEvent e)
            {
                e.getComponent().setBackground(color);
            }
        });

        p=new JPanel(new GridLayout());
        JLabel use_case4 = new JLabel("Gestion Accés");
        use_case4.setHorizontalAlignment(JLabel.CENTER);
        use_case4.setFont(new Font("Serif", Font.BOLD,17));
        p.add(use_case4,BorderLayout.CENTER);
        p.setBackground(color);
        left.add(p);
        p.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {

                HomePage t = new HomePage();
                t.setVisible(true);
                dispose();
            }
        });
        p.addMouseListener(new MouseAdapter()
        {
            public void mouseEntered(MouseEvent e)
            {
                e.getComponent().setBackground(Color.white);
            }
            public void mouseExited(MouseEvent e)
            {
                e.getComponent().setBackground(color);
            }
        });

        //creation of the right menu
        right.setBackground(Color.white);
        right.setLayout(new BorderLayout());

        JMenuBar menuBar=new JMenuBar();

        menuBar.setBorderPainted(isDoubleBuffered());
        menuBar.setSize(750,45);

        menuBar.add(Box.createHorizontalGlue());
        JMenu leave=new JMenu("Quitter");
        leave.addMenuListener(new MenuListener() {


            @Override
            public void menuSelected(MenuEvent e) {
                dispose();
            }

            @Override
            public void menuDeselected(MenuEvent e) {
            }

            @Override
            public void menuCanceled(MenuEvent e) {

            }
        });
        menuBar.add(leave);
        right.add(menuBar, BorderLayout.NORTH);

        this.getContentPane().add(left,BorderLayout.CENTER);

        left.setBackground(color);

        setSize(new Dimension(950,780));
        setLocationRelativeTo(null);
        setResizable(false);

    }
    public WelcomePage(GeneralServices GS)
    {

        company=GS;
        myInterface();
        setVisible(true);
    }

}
