package com.ascom.dev.tools;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

public class AboutDialog extends JDialog {
    private static final long serialVersionUID = 1L;

    public AboutDialog() {
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel name = new JLabel("About");
        name.setFont(new Font("Arial", Font.BOLD, 18));
        name.setAlignmentX(0.5f);
        add(name);

        add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel text = new JLabel("<html><body><center>" + "DGrep Version 1.0<br>" + "\u00a9 by dbon<br>" + "2012<br><br>"
                + "to report bugs please use: daniel.bongartz@gmail.com" + "</center></body></html>");
        text.setAlignmentX(0.5f);
        text.setHorizontalAlignment((int) CENTER_ALIGNMENT);
        add(text);

        add(Box.createRigidArea(new Dimension(0, 20)));

        JButton close = new JButton("Close");
        close.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                dispose();
            };
        });

        close.setAlignmentX(0.5f);
        add(close);

        setModalityType(ModalityType.APPLICATION_MODAL);

        setTitle("About");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(250, 235);
    }
}
