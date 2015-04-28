package com.ascom.dev.tools;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class GUI implements ActionListener, DocumentListener, KeyListener {

    public JTextField textfield;
    public JTextField filter;
    public JTextField t_freeformat;

    public JButton openFile;
    public JButton run;
    public JButton exit;

    public JFileChooser chooser;

    public JMenuBar menuBar;

    public JMenu menu;

    public JMenuItem menuabout;
    public JMenuItem menuhelp;
    public JMenuItem menuexit;

    public JCheckBox c_openfolder;
    public JCheckBox c_fileformat_txt;
    public JCheckBox c_fileformat_log;
    public JCheckBox c_freeformat;

    public static JRadioButton r_codec_UTF8;
    public static JRadioButton r_codec_UTF16;
    public static JRadioButton r_codec_UTF16LE;

    public void launchGUI() {

        // Menu Bar
        menuBar = new JMenuBar();
        menu = new JMenu("Options");
        menu.setMnemonic(KeyEvent.VK_P);

        menuBar.add(menu);

        menuhelp = new JMenuItem("Help", KeyEvent.VK_H);
        menuhelp.addActionListener(this);
        menuabout = new JMenuItem("About", KeyEvent.VK_A);
        menuabout.addActionListener(this);

        menu.add(menuhelp);
        menu.add(menuabout);

        menuexit = new JMenuItem("Exit", KeyEvent.VK_E);
        KeyStroke ctrlW = KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK);
        menuexit.setAccelerator(ctrlW);
        menuexit.addActionListener(this);

        menuBar.add(menuexit);

        // Frame
        JFrame f = new JFrame("DGREP v1.0 \u00a9 by dbon");
        f.setLayout(new BorderLayout());
        f.setSize(400, 400);
        f.setLocation(600, 400);
        f.setResizable(false);
        f.setJMenuBar(menuBar);

        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());
        p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Header
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));

        JLabel headline = new JLabel("DGREP v1.0");
        headline.setFont(new Font("Arial", Font.BOLD, 24));
        JLabel desc = new JLabel("Open the path to your log files.");

        header.add(headline);
        header.add(desc);

        // Main
        JPanel mainpane = new JPanel();
        mainpane.setLayout(new GridBagLayout());
        mainpane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(4, 5, 4, 5);

        // Open Dialog
        JLabel path = new JLabel("Path:");
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        mainpane.add(path, c);

        textfield = new JTextField("please select a folder...");
        textfield.addActionListener(this);
        textfield.setEditable(false);
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 1;
        c.weightx = 1;
        c.insets = new Insets(4, 13, 4, 5);
        mainpane.add(textfield, c);

        openFile = new JButton("Open");
        openFile.addActionListener(this);
        c.gridx = 2;
        c.gridy = 0;
        c.weightx = 0;
        c.insets = new Insets(4, 5, 4, 5);
        openFile.setMnemonic(KeyEvent.VK_O);
        mainpane.add(openFile, c);

        // Search String
        JLabel grepdesc = new JLabel("Search-String:");
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        mainpane.add(grepdesc, c);

        filter = new JTextField("", 25);
        filter.getDocument().addDocumentListener(this);
        filter.addKeyListener(this);
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 2;
        c.insets = new Insets(4, 13, 4, 5);
        mainpane.add(filter, c);

        // File Format
        JLabel l_fileformat = new JLabel("File-Formats:");
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        c.insets = new Insets(4, 5, 4, 5);
        mainpane.add(l_fileformat, c);

        JPanel formatpane = new JPanel();
        formatpane.setLayout(new FlowLayout(FlowLayout.LEFT));
        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 2;

        c_fileformat_txt = new JCheckBox(".txt", true);
        c_fileformat_log = new JCheckBox(".log", true);

        c_freeformat = new JCheckBox();
        c_freeformat.addActionListener(this);
        t_freeformat = new JTextField(".example", 10);
        t_freeformat.setEnabled(false);

        formatpane.add(c_fileformat_txt);
        formatpane.add(c_fileformat_log);
        formatpane.add(c_freeformat);
        formatpane.add(t_freeformat);

        mainpane.add(formatpane, c);

        // Codecs
        JLabel l_codec = new JLabel("Input-Codec:");
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 1;
        mainpane.add(l_codec, c);

        JPanel codecpane = new JPanel();
        codecpane.setLayout(new FlowLayout(FlowLayout.LEFT));
        c.gridx = 1;
        c.gridy = 3;
        c.gridwidth = 2;

        r_codec_UTF8 = new JRadioButton("UTF-8", true);
        r_codec_UTF16 = new JRadioButton("UTF-16", false);
        r_codec_UTF16LE = new JRadioButton("UTF16-LE", false);

        codecpane.add(r_codec_UTF8);
        codecpane.add(r_codec_UTF16);
        codecpane.add(r_codec_UTF16LE);

        ButtonGroup bgroup = new ButtonGroup();
        bgroup.add(r_codec_UTF8);
        bgroup.add(r_codec_UTF16);
        bgroup.add(r_codec_UTF16LE);

        mainpane.add(codecpane, c);

        // Open Folder Checkbox
        c_openfolder = new JCheckBox();
        c_openfolder.setText("open directory");
        c_openfolder.setMnemonic(KeyEvent.VK_X);
        c.gridx = 1;
        c.gridy = 4;
        c.gridwidth = 1;
        c.insets = new Insets(4, 9, 4, 5);
        mainpane.add(c_openfolder, c);

        // GREP Button
        run = new JButton("GREP");
        run.setEnabled(false);
        run.addActionListener(this);
        run.setMnemonic(KeyEvent.VK_G);
        c.gridx = 1;
        c.gridy = 5;
        c.gridwidth = 2;
        c.insets = new Insets(4, 13, 4, 5);
        mainpane.add(run, c);

        // Footer
        JPanel footerpane = new JPanel();
        footerpane.setLayout(new BorderLayout());
        footerpane.setSize(100, 300);

        exit = new JButton("Exit");
        exit.addActionListener(this);
        exit.setMnemonic(KeyEvent.VK_E);

        JLabel copyright = new JLabel("\u00a9 by dbon 2012");

        footerpane.add(exit, BorderLayout.EAST);
        footerpane.add(copyright, BorderLayout.WEST);

        p.add(header, BorderLayout.NORTH);
        p.add(mainpane, BorderLayout.CENTER);
        p.add(footerpane, BorderLayout.SOUTH);

        f.getContentPane().add(p, BorderLayout.CENTER);
        f.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent a) {
        if (a.getActionCommand() != null && a.getSource() == this.exit) {
            System.exit(0);
        } else if (a.getActionCommand() != null && a.getSource() == this.openFile) {
            File c;

            if (DGrep.path == null) {
                c = new File("C:\\");
            } else {
                c = new File(DGrep.path);
            }

            chooser = new JFileChooser(c);
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int result = chooser.showOpenDialog(null);

            if (result == JFileChooser.APPROVE_OPTION) {
                DGrep.path = chooser.getSelectedFile().getAbsolutePath();
                this.textfield.setText(DGrep.path);

                UpdateRunButton();
            }
        } else if (a.getActionCommand() != null && a.getSource() == this.run) {
            Date d = new Date();
            Format formatter = new SimpleDateFormat("yyyyMMdd-HHmmss");

            String timestamp;
            timestamp = formatter.format(d);

            DGrep.tempFile = DGrep.path + "\\" + "DGrep-" + timestamp + "-" + DGrep.out_full;
            DGrep.outputFile = DGrep.path + "\\" + "DGrep-" + timestamp + "-" + DGrep.out_filtered;

            DGrep.keyword = filter.getText();

            DGrep.formats = new ArrayList<String>();
            if (c_fileformat_log.isSelected()) {
                DGrep.formats.add(this.c_fileformat_log.getText());
            }
            if (c_fileformat_txt.isSelected()) {
                DGrep.formats.add(this.c_fileformat_txt.getText());
            }
            if (c_freeformat.isSelected()) {
                DGrep.formats.add(this.t_freeformat.getText());
            }

            DGrep.generateOutputFile();

            if (c_openfolder.isSelected()) {
                try {
                    Runtime.getRuntime().exec("explorer.exe " + DGrep.path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (a.getActionCommand() != null && a.getSource() == this.textfield) {
            UpdateRunButton();
        } else if (a.getActionCommand() != null && a.getSource() == this.menuexit) {
            System.exit(0);
        } else if (a.getActionCommand() != null && a.getSource() == this.menuabout) {
            AboutDialog ad = new AboutDialog();
            ad.setVisible(true);
        } else if (a.getActionCommand() != null && a.getSource() == this.menuhelp) {
            System.out.println("!");
            HelpDialog hd = new HelpDialog();
            hd.setVisible(true);
        } else if (a.getActionCommand() != null && a.getSource() == this.c_freeformat) {
            if (this.c_freeformat.isSelected()) {
                this.t_freeformat.setEnabled(true);
            } else {
                this.t_freeformat.setEnabled(false);
            }
        }
    }

    @Override
    public void changedUpdate(DocumentEvent documentevent) {

    }

    @Override
    public void insertUpdate(DocumentEvent documentevent) {
        UpdateRunButton();
    }

    @Override
    public void removeUpdate(DocumentEvent documentevent) {

    }

    @Override
    public void keyPressed(KeyEvent keyevent) {

    }

    @Override
    public void keyReleased(KeyEvent keyevent) {

    }

    @Override
    public void keyTyped(KeyEvent keyevent) {
        UpdateRunButton();
    }

    private void UpdateRunButton() {
        run.setEnabled(!filter.getText().isEmpty() && new File(textfield.getText()).isDirectory());
    }
}
