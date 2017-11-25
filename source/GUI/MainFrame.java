package GUI;

import Socket.Client;
import Socket.Server;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JOptionPane;


public class MainFrame extends JFrame {
    private JScrollPane scrollPane;
    private ImageIcon icon;
    private Font font1;
    private Font font2;
    private JLabel jb1;
    private JLabel ip;
    private JLabel port;
    private ImageIcon ima;
    private JLabel image;
    private JButton connect;
    private JTextField ip_tf;
    private JTextField port_tf;
    private JRadioButton[] rbgroup;

    public MainFrame() {
        JPanel MainPanel = new JPanel(){

            public void paintComponent(Graphics g){
                g.drawImage(icon.getImage(), 0, 0, null);
                setOpaque(false);
                super.paintComponent(g);
            }
        };

        MainPanel.setLayout(null);
        MainPanel.setBounds(0,0,1280,960);
        icon = new ImageIcon(MainFrame.class.getClassLoader().getResource("background.jpg").getPath());
        scrollPane = new JScrollPane(MainPanel);
        setContentPane(scrollPane);

        font1 = new Font("돋움", Font.PLAIN, 30);
        font2 = new Font("Sans-serif", Font.BOLD, 20);
        jb1 = new JLabel("채팅 서버!");
        ip = new JLabel("IP: ");
        port = new JLabel("Port: ");
        ima = new ImageIcon(MainFrame.class.getClassLoader().getResource("fox.png").getPath());
        image = new JLabel("",ima,JLabel.CENTER);
        connect = new JButton("접속");
        ip_tf = new JTextField("127.0.0.1",10);
        port_tf = new JTextField("8000",10);
        rbgroup = new JRadioButton[2];
        rbgroup[0] = new JRadioButton("서버", true);
        rbgroup[1] = new JRadioButton("클라이언트");
        ButtonGroup bgroup = new ButtonGroup();
        bgroup.add(rbgroup[0]);
        bgroup.add(rbgroup[1]);

        image.setBounds(500,100,300,220);
        MainPanel.add(image);
        jb1.setBounds(580, 300, 140, 30);
        jb1.setFont(font1);
        MainPanel.add(jb1);

        rbgroup[0].setBounds(580, 400, 60, 30);
        rbgroup[0].setOpaque(false);
        rbgroup[1].setBounds(640, 400, 100, 30);
        rbgroup[1].setOpaque(false);
        ip.setBounds(540, 450, 50, 30);
        ip.setFont(font2);
        ip_tf.setBounds(540, 480, 240, 30);
        port.setBounds(540, 510, 70, 30);
        port.setFont(font2);
        port_tf.setBounds(540, 540, 240, 30);
        connect.setBounds(560, 600, 200, 50);
        MainPanel.add(rbgroup[0]);
        MainPanel.add(rbgroup[1]);
        MainPanel.add(ip);
        MainPanel.add(ip_tf);
        MainPanel.add(port);
        MainPanel.add(port_tf);
        MainPanel.add(connect);

        connect.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String errorMessage = "IP 주소 혹은 포트 번호가 유효하지 않습니다.";

                String ip = ip_tf.getText();
                int port;

                try {
                    port = Integer.parseInt(port_tf.getText());
                } catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(new JFrame(), errorMessage, "Invalid", JOptionPane.ERROR_MESSAGE);

                    return;
                }

                if (ipIsValid(ip) && portIsValid(port)) {

                    if (rbgroup[0].isSelected()) {
                        new ChatFrame(new Server());
                    } else if (rbgroup[1].isSelected()) {
                        new ChatFrame(new Client());
                    }
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(new JFrame(), errorMessage, "Invalid", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        this.setTitle("ChatProgram");
        this.setSize(1280, 960);
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private boolean ipIsValid(String ip) {
        final String IP_PATTERN =
                "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

        Pattern pattern = Pattern.compile(IP_PATTERN);
        Matcher matcher = pattern.matcher(ip);

        return matcher.matches();
    }

    private boolean portIsValid(int port) {

        if (port >= 0 && port <= 65535) {
            return true;
        } else {
            return false;
        }
    }
}
