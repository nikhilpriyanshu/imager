package ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainWindow {

    private JFrame frame;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            EventQueue.invokeAndWait(new Runnable() {
                public void run() {
                    try {
                        MainWindow window = new MainWindow();
                        window.frame.setVisible(true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (InvocationTargetException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the application.
     */
    public MainWindow() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setUndecorated(true);
        frame.setBackground(new Color(0, 0, 0, 0));
        frame.setBounds(100, 100, 600, 400);
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        JButton closeButton = new JButton("X");
        closeButton.setOpaque(true);
        panel.add(closeButton);
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("/home/npriyanshu/Desktop/20190420_052845.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        JLabel label = new JLabel();
        label.setBounds(100, 100, 450, 300);
        Image dimg = img.getScaledInstance(label.getWidth(), label.getHeight(),
                Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(dimg);
        label.setIcon(image);
        panel.add(label);
        frame.add(panel);
        frame.add(panel);
        closeButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent arg0) {
                frame.dispose();
            }
        });
    }

}
