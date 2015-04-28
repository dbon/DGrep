package com.ascom.dev.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import org.apache.commons.io.IOUtils;

public class DGrep {
    public static String out_full = "all.txt";
    public static String out_filtered = "filtered.txt";

    public static String path;

    public static ArrayList<String> formats;

    static String keyword;
    static String tempFile;
    static String outputFile;

    public static void main(String[] args) {
        new GUI().launchGUI();
    }

    static void generateOutputFile() {
        File filePath = new File(path);
        File[] files = filePath.listFiles();

        String fileContent = "";
        int counter = 0;
        boolean ignoreSomethingFound = false;

        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                int pos = files[i].getName().lastIndexOf(".");
                String ext = files[i].getName().substring(pos);

                if (formats.contains(ext) && !files[i].getName().contains(DGrep.out_filtered) && !files[i].getName().contains(DGrep.out_full)) {
                    System.out.println("... processing: " + files[i].getName());

                    try {
                        counter++;

                        String encoding = "";

                        if (GUI.r_codec_UTF8.isSelected()) {
                            encoding = "UTF-8";
                        } else if (GUI.r_codec_UTF16.isSelected()) {
                            encoding = "UTF-16";
                        } else if (GUI.r_codec_UTF16LE.isSelected()) {
                            encoding = "UTF-16LE";
                        }

                        fileContent = fileContent + "########## " + counter + " ##########" + "\n"
                                + IOUtils.toString(new FileInputStream(files[i].getAbsolutePath()), encoding) + "\n";
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (!formats.contains(ext) && i == files.length) {
                    JOptionPane.showMessageDialog(null, "Error! No file was found.", "Error", JOptionPane.INFORMATION_MESSAGE);
                    ignoreSomethingFound = true;
                }
            }
        }

        FileReader f = null;
        BufferedReader br = null;

        try {
            // Full File nur erstellen wenn FileContent Inhalt hat
            if (fileContent != null && !fileContent.equals("")) {
                IOUtils.write(fileContent, new FileOutputStream(tempFile), "UTF-8");
                System.out.println("... out-full.txt written successfully");
            }

            // Prüfen ob Temp File erstellt wurde
            if ((new File(tempFile)).isFile()) {
                f = new FileReader(tempFile);
                br = new BufferedReader(f);
                String line = "";
                StringBuffer output = new StringBuffer(); // = "";
                boolean somethingFound = false;
                Pattern pattern = Pattern.compile(keyword);

                while ((line = br.readLine()) != null) {
                    if (pattern.matcher(line).find()) {
                        System.out.println(counter);
                        counter++;
                        output.append(line).append("\n"); // = output +
                        somethingFound = true;
                    }
                }

                if (somethingFound == true) {
                    IOUtils.write(output.toString(), new FileOutputStream(outputFile), "UTF-8");
                    JOptionPane.showMessageDialog(null, "Success! Log files are created.", "Info", JOptionPane.INFORMATION_MESSAGE);
                } else if (somethingFound == false && ignoreSomethingFound == false) {
                    JOptionPane.showMessageDialog(null, "Error! Search-String not found.", "Error", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
