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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.commons.io.IOUtils;

public class DGrep implements ActionListener, DocumentListener, KeyListener {
	public static String out_full = "all.txt";
	public static String out_filtered = "filtered.txt";

	private String path;
	private String keyword;
	private String tempFile;
	private String outputFile;

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

	public JRadioButton r_codec_UTF8;
	public JRadioButton r_codec_UTF16;
	public JRadioButton r_codec_UTF16LE;

	public ArrayList<String> formats;

	public String timestamp;

	public static void main(String[] args) {
		DGrep dgrep = new DGrep();

		dgrep.launchGUI();
	}

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
		KeyStroke ctrlW = KeyStroke.getKeyStroke(KeyEvent.VK_Q,
				InputEvent.CTRL_MASK);
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
		} else if (a.getActionCommand() != null
				&& a.getSource() == this.openFile) {
			File c;

			if (this.path == null) {
				c = new File("C:\\");
			} else {
				c = new File(this.getPath());
			}

			chooser = new JFileChooser(c);
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int result = chooser.showOpenDialog(null);

			if (result == JFileChooser.APPROVE_OPTION) {
				this.setPath(chooser.getSelectedFile().getAbsolutePath());
				this.textfield.setText(this.getPath());

				UpdateRunButton();
			}
		} else if (a.getActionCommand() != null && a.getSource() == this.run) {
			Date d = new Date();
			Format formatter = new SimpleDateFormat("yyyyMMdd-HHmmss");
			this.timestamp = formatter.format(d);

			this.setTempFile(this.getPath() + "\\" + "DGrep-" + timestamp + "-"
					+ DGrep.out_full);
			this.setOutputFile(this.getPath() + "\\" + "DGrep-" + timestamp
					+ "-" + DGrep.out_filtered);

			this.keyword = this.filter.getText();

			formats = new ArrayList<String>();
			if (c_fileformat_log.isSelected()) {
				formats.add(this.c_fileformat_log.getText());
			}
			if (c_fileformat_txt.isSelected()) {
				formats.add(this.c_fileformat_txt.getText());
			}
			if (c_freeformat.isSelected()) {
				formats.add(this.t_freeformat.getText());
			}

			DGrep.generateOutputFile(this);

			if (c_openfolder.isSelected()) {
				try {
					Runtime.getRuntime().exec("explorer.exe " + getPath());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else if (a.getActionCommand() != null
				&& a.getSource() == this.textfield) {
			UpdateRunButton();
		} else if (a.getActionCommand() != null
				&& a.getSource() == this.menuexit) {
			System.exit(0);
		} else if (a.getActionCommand() != null
				&& a.getSource() == this.menuabout) {
			AboutDialog ad = new AboutDialog();
			ad.setVisible(true);
		} else if (a.getActionCommand() != null
				&& a.getSource() == this.menuhelp) {
			System.out.println("!");
			HelpDialog hd = new HelpDialog();
			hd.setVisible(true);
		} else if (a.getActionCommand() != null
				&& a.getSource() == this.c_freeformat) {
			if (this.c_freeformat.isSelected()) {
				this.t_freeformat.setEnabled(true);
			} else {
				this.t_freeformat.setEnabled(false);
			}
		}

	}

	private static void generateOutputFile(DGrep dgrep) {
		File path = new File(dgrep.getPath());
		File[] files = path.listFiles();

		String fileContent = "";
		int counter = 0;
		boolean ignoreSomethingFound = false;

		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				int pos = files[i].getName().lastIndexOf(".");
				String ext = files[i].getName().substring(pos);

				if (dgrep.formats.contains(ext)
						&& !files[i].getName().contains(DGrep.out_filtered)
						&& !files[i].getName().contains(DGrep.out_full)) {
					System.out.println("... processing: " + files[i].getName());

					try {
						counter++;

						String encoding = "";

						if (dgrep.r_codec_UTF8.isSelected()) {
							encoding = "UTF-8";
						} else if (dgrep.r_codec_UTF16.isSelected()) {
							encoding = "UTF-16";
						} else if (dgrep.r_codec_UTF16LE.isSelected()) {
							encoding = "UTF-16LE";
						}

						fileContent = fileContent
								+ "########## "
								+ counter
								+ " ##########"
								+ "\n"
								+ IOUtils.toString(
										new FileInputStream(files[i]
												.getAbsolutePath()), encoding)
								+ "\n";
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else if (!dgrep.formats.contains(ext) && i == files.length) {
					JOptionPane.showMessageDialog(null,
							"Error! No file was found.", "Error",
							JOptionPane.INFORMATION_MESSAGE);
					ignoreSomethingFound = true;
				}
			}
		}

		FileReader f = null;
		BufferedReader br = null;

		try {
			// Full File nur erstellen wenn FileContent Inhalt hat
			if (fileContent != null && !fileContent.equals("")) {
				IOUtils.write(fileContent,
						new FileOutputStream(dgrep.getTempFile()), "UTF-8");
				System.out.println("... out-full.txt written successfully");
			}

			// Prüfen ob Temp File erstellt wurde
			if ((new File(dgrep.getTempFile())).isFile()) {
				f = new FileReader(dgrep.getTempFile());
				br = new BufferedReader(f);
				String line = "";
				StringBuffer output = new StringBuffer(); // = "";
				boolean somethingFound = false;
				Pattern pattern = Pattern.compile(dgrep.getKeyword());

				while ((line = br.readLine()) != null) {

					if (pattern.matcher(line).find()) {
						System.out.println(counter);
						counter++;
						// line = line.replace("$", "\t");
						output.append(line).append("\n"); // = output +
						// line.toString() +
						// "\n";

						somethingFound = true;
					}

					// if(line.matches(dgrep.getKeyword()))
					// {
					// System.out.println(counter);
					// counter++;
					// // line = line.replace("$", "\t");
					// output.append(line).append("\n"); // = output +
					// line.toString() + "\n";
					//
					// somethingFound = true;
					// }
				}

				if (somethingFound == true) {
					IOUtils.write(output.toString(),
							new FileOutputStream(dgrep.getOutputFile()),
							"UTF-8");
					JOptionPane.showMessageDialog(null,
							"Success! Log files are created.", "Info",
							JOptionPane.INFORMATION_MESSAGE);
				} else if (somethingFound == false
						&& ignoreSomethingFound == false) {
					JOptionPane.showMessageDialog(null,
							"Error! Search-String not found.", "Error",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getTempFile() {
		return tempFile;
	}

	public void setTempFile(String tempFile) {
		this.tempFile = tempFile;
	}

	public String getOutputFile() {
		return outputFile;
	}

	public void setOutputFile(String outputFile) {
		this.outputFile = outputFile;
	}

	private void UpdateRunButton() {
		run.setEnabled(!filter.getText().isEmpty()
				&& new File(textfield.getText()).isDirectory());
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
}
