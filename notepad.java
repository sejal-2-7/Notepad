import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;
import java.io.*;
import javax.swing.text.DefaultEditorKit;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Lenovo
 */
public abstract class notepad extends JFrame implements ActionListener{
    
    private JTextArea textarea = new JTextArea();       //try removing the instance for this method
    private JScrollPane areascrollpane;                     //scroll pane does not need an instance
    private JMenuBar menubar = new JMenuBar();          //we define the menu bar 
    private JMenu file = new JMenu();                   // we state the main components of the menubar
    private JMenu edit = new JMenu();
    private JMenu format = new JMenu();
    private JMenu help = new JMenu();
    private JMenu view = new JMenu();
    private JMenuItem open = new JMenuItem();           //these are the components of the items of the menu
    private JMenuItem save = new JMenuItem();
    private JMenuItem close = new JMenuItem();
    private JMenuItem newfile = new JMenuItem();
    private JMenuItem undo = new JMenuItem();
    private JMenuItem cut = new JMenuItem(new DefaultEditorKit.CutAction());
    private JMenuItem copy = new JMenuItem(new DefaultEditorKit.CopyAction());
    private JMenuItem paste = new JMenuItem(new DefaultEditorKit.PasteAction());
    private JMenuItem delete = new JMenuItem();
    private JMenuItem wordwrap = new JMenuItem();
    private JMenuItem about = new JMenuItem();
    
    public notepad()
    {
        this.setSize(500,300);
        this.setTitle("My Notepad");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.textarea.setFont(new Font("Century Gothic",Font.BOLD, 12));
        this.textarea.setDragEnabled(true);
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(textarea);
        this.areascrollpane = new JScrollPane(textarea);
        this.areascrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.areascrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.areascrollpane.setPreferredSize(new Dimension(250,250));
        this.getContentPane().add(areascrollpane);
        
        this.setJMenuBar(this.menubar);                     //here we are finally adding all the menu options to the menubar
        this.menubar.add(this.file);
        this.menubar.add(this.edit);
        this.menubar.add(this.help);
        
        this.file.setText("File");                          //here we decide the heading of the menubar components
        this.edit.setText("Edit");
        this.help.setText("Help");
        this.format.setText("Format");
        this.view.setText("View");
        
        this.newfile.setText("New");                          //instead of a pop down menu for the menubar component we are using keyboard input using action;istener
        this.newfile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        this.newfile.addActionListener(this);
        this.newfile.setMnemonic(KeyEvent.VK_N);
        this.file.add(this.newfile);                            //we just added newfile as a menu item in file option
        
        this.open.setText("Open");
        this.open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        this.open.addActionListener(this);
        this.open.setMnemonic(KeyEvent.VK_O);
        this.file.add(this.open);
        
        this.save.setText("Save");
        this.save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        this.save.addActionListener(this);
        this.save.setMnemonic(KeyEvent.VK_S);
        this.file.add(this.save);
        
        this.close.setText("Close");
        this.close.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.CTRL_MASK));
        this.close.addActionListener(this);
        this.close.setMnemonic(KeyEvent.VK_F4);
        this.file.add(this.close);
        
        this.undo.setText("Undo");
        this.undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
        this.undo.addActionListener(this);
        this.undo.setMnemonic(KeyEvent.VK_U);
        this.edit.add(this.undo);
        
        this.cut.setText("Cut");
        this.cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        this.cut.setMnemonic(KeyEvent.VK_T);
        this.edit.add(this.copy);
        
        this.copy.setText("Copy");
        this.copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        this.copy.addActionListener(this);
        this.copy.setMnemonic(KeyEvent.VK_C);
        this.edit.add(this.copy);
        
        this.paste.setText("Paste");
        this.paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        this.paste.addActionListener(this);
        this.paste.setMnemonic(KeyEvent.VK_P);
        this.edit.add(this.paste);
        
        this.about.setText("Authors");
        this.about.addActionListener(this);
        this.help.add(this.about);
    }
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == this.close)
            this.dispose();
        
        else if(e.getSource() == this.open)
        {
            JFileChooser open = new JFileChooser();
            int option = open.showOpenDialog(this);
            if(option == JFileChooser.APPROVE_OPTION)
            {
                this.textarea.setText("");
                try
                {
                    Scanner scan = new Scanner(new FileReader(open.getSelectedFile().getPath()));
                    while(scan.hasNext())
                        this.textarea.append(scan.nextLine()+"\n");
                }
                catch(Exception ex)
                {
                    System.out.println(ex.getMessage());
                }
            }
        }
        
        else if(e.getSource() == this.save)
        {
            JFileChooser save = new JFileChooser();
            int option = save.showSaveDialog(this);
            if(option == JFileChooser.APPROVE_OPTION)
            {
                try
                {
                    BufferedWriter out = new BufferedWriter(new FileWriter(save.getSelectedFile().getPath()));
                    out.write(this.textarea.getText());
                    out.close();
                }
                catch(Exception ex)
                {
                    System.out.println(ex.getMessage());
                }
            }
        }
        
        else if(e.getSource() == this.newfile)
        {
            JOptionPane jp = new JOptionPane();
            Object[] options = {"save","don't save","cancel"};
            int button = jp.showOptionDialog(null, "Do you want to save changes to" + getTitle(), "Notepad", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options,options[0]);
            if(button == 0)
            {
                JFileChooser newFile = new JFileChooser();
                int option = newFile.showSaveDialog(this);
                if(option ==  JFileChooser.APPROVE_OPTION )
                {
                try
                {
                    BufferedWriter out = new BufferedWriter(new FileWriter(newFile.getSelectedFile().getPath()));
                    out.write(this.textarea.getText());
                    out.close();
                }
                catch(Exception ex)
                {
                    System.out.println(ex.getMessage());
                }
                }
            }
        
        else if(button ==1)
        {
            textarea.setText(null);
        }
        else if(button == 2)
        {
            
        }
        else if(e.getSource() == this.about)
        {
            JOptionPane hp = new JOptionPane();
            hp.showMessageDialog(null, "Created by Sejal Joshi");
        }
    }}
    public static void main(String args[])
        {
            notepad app = new notepad() {};
            app.setVisible(true);
        }
}
