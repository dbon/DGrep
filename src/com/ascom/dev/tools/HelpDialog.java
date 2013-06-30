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

public class HelpDialog extends JDialog
{	
	
	private static final long serialVersionUID = 1L;

	public HelpDialog()
	{
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel name = new JLabel("Help");
        name.setFont(new Font("Arial", Font.BOLD, 18));
        name.setAlignmentX(0.5f);
        add(name);
        
        add(Box.createRigidArea(new Dimension(0, 10)));
        
        JLabel text = new JLabel("<html><body>" +
        		"1. Select the folder which contains the log files<br>" +
        		"2.	Enter a search string<br>" +
        		"3. Select the file formats which should be included in your search.<br>" +
        		"4. Chose between the input file codec (default: UTF-8)<br>" +
        		"5. Decide if you want to open the search folder afterwards<br>" +
        		"6. Press the GREP button to start the search<br><br>" +
        		"Notice:<br> After you've pressed GREP two files will be generated in the chosen path.<br>" +
        		"The first file (GREP-timestamp-all.txt) is a summary of all the files in which the search string was found.<br>" +
        		"The second file (GREP-timestamp-filtered.txt) is the important one.<br>" +
        		"It includes only the rows that contain the search string." +        		
        		"</body></html>");
        text.setAlignmentX(0.5f);
        text.setHorizontalAlignment((int) CENTER_ALIGNMENT);
        add(text);

        add(Box.createRigidArea(new Dimension(0, 30)));

        JButton close = new JButton("Close");
        close.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                dispose();
           };
        });
        
        close.setAlignmentX(0.5f);
        add(close);

        setModalityType(ModalityType.APPLICATION_MODAL);

        setTitle("Help");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(650, 350);
	}
}
