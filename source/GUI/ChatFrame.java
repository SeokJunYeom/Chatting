package GUI;

import Socket.Network;

import java.awt.Graphics;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;


public class ChatFrame extends JFrame {
    private JScrollPane scrollPane;
    private ImageIcon icon;
    private JPanel content;
    private JTextField message;
    private JButton trans;
    private JTextArea chat;

    private Network socket;

    private String messageToSend;

    private Thread readerThread;
    private Thread writerThread;

    public ChatFrame(Network network) {
        socket = network;
        messageToSend = "";
        setGUI();

        readerThread = new Reader();
        writerThread = new Writer();
        readerThread.start();
        writerThread.start();
    }

    public void setGUI() {

        JPanel MainPanel = new JPanel() {

            public void paintComponent(Graphics g) {
                g.drawImage(icon.getImage(), 0, 0, null);
                setOpaque(false);
                super.paintComponent(g);
            }
        };

        MainPanel.setLayout(null);
        MainPanel.setBounds(0, 0, 1280, 960);
        icon = new ImageIcon(ChatFrame.class.getClassLoader().getResource("background.jpg").getPath());
        setContentPane(MainPanel);

        content = new JPanel();
        message = new JTextField("", 30);
        trans = new JButton("전송");
        chat = new JTextArea();

        chat.setEditable(false);

        content.setLayout(new BorderLayout());
        content.setBounds(400, 50, 520, 700);
        message.setBounds(400, 800, 400, 30);
        trans.setBounds(820, 790, 100, 50);

        JScrollPane scrollPane = new JScrollPane(chat);
        content.add(scrollPane);

        MainPanel.add(content);
        MainPanel.add(message);
        MainPanel.add(trans);

        trans.addActionListener(e -> {
            messageToSend = message.getText();
            message.setText("");
        });

        getRootPane().setDefaultButton(trans);

        this.setTitle("ChatProgram");
        this.setSize(1280, 960);
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                socket.close();
                readerThread.interrupt();
                writerThread.interrupt();
            }
        });
    }

    private void addMessage(String message) {
        chat.setEditable(true);
        chat.append(message + "\n");
        chat.setCaretPosition(chat.getDocument().getLength());
        chat.setEditable(false);
    }


    private class Reader extends Thread {

        @Override
        public void run() {
            addMessage("연결 대기중입니다 ...");

            boolean succeed;

            message.setEditable(false);

            succeed = socket.init();

            message.setEditable(true);

            if (succeed) {
                addMessage("연결을 성공하였습니다.");

                while (!Thread.currentThread().isInterrupted()) {
                    socket.receive();
                    String receivedMessage = socket.getMessage();

                    if (!receivedMessage.equals("")) {
                        addMessage("상대방 : " + receivedMessage);
                    }
                }
            } else {
                addMessage("연결을 실패하였습니다.");
            }
        }
    }

    private class Writer extends Thread {

        @Override
        public void run() {

            while (!Thread.currentThread().isInterrupted()) {

                try {
                    Thread.sleep(1);
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }

                if (!messageToSend.equals("")) {
                    addMessage("나 : " + messageToSend);
                    socket.send(messageToSend);
                    messageToSend = "";
                }
            }
        }
    }
}