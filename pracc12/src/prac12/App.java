package prac12;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Scanner;

public class App extends JFrame {
    JTextArea area = new JTextArea();
    JMenuBar menu = new JMenuBar();
    JPanel pnl = new JPanel();
    ICreateDocument c = new CreateAbstractDocument();
    IDocument d = c.CreateNew();
    App(){
        super("Text Editor");
        menu.add(CreateFile());
        pnl.setLayout(new BorderLayout());
        setJMenuBar(menu);
        setBounds(700,230, 500, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        add(pnl);
        setVisible(true);
    }
    private JMenu CreateFile(){
        JMenu file = new JMenu("File");
        JMenu New = new JMenu("New File");
        JMenuItem open = new JMenuItem("Open File");
        JMenuItem exit = new JMenuItem("Exit");
        JMenuItem save = new JMenuItem("Save");
        JMenuItem t = new JMenuItem("Текстовый документ");
        JMenuItem m = new JMenuItem("Музыкальный файл");
        JMenuItem i = new JMenuItem("Изображение");
        New.add(t);
        New.addSeparator();
        New.add(m);
        New.addSeparator();
        New.add(i);
        file.add(New);
        file.addSeparator();
        file.add(open);
        file.addSeparator();
        file.add(save);
        file.addSeparator();
        file.add(exit);
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(1);
            }
        });

        t.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ICreateDocument c = new CreateAbstractDocument();
                IDocument d = c.CreateNew();
                d = new TextDocument();
                area.setText("");
                pnl.add(area);
                pnl.revalidate();
                pnl.repaint();
                try {
                    PrintWriter writer = new PrintWriter("textfile.txt", "UTF-8");
                } catch (FileNotFoundException | UnsupportedEncodingException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }
                System.out.println(d.info());
            }
        });

        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                d = new TextDocument();
                if(d instanceof TextDocument) {
                    try (FileReader reader = new FileReader("textfile.txt")) {
                        StringBuilder str = new StringBuilder();
                        Scanner scan = new Scanner(reader);
                        while (scan.hasNextLine()) {
                            str.append(scan.nextLine()).append("\n");
                        }
                        area.setText(String.valueOf(str));
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                    System.out.println(d.info());
                }
            }
        });

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (d instanceof TextDocument) {
                    try (FileWriter writer1 = new FileWriter("textfile.txt", false)) {
                        writer1.write(area.getText());
                        writer1.flush();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });
        return file;
    }
}

