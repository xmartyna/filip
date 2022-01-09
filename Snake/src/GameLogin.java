import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;


    public class GameLogin {

    private static JLabel userLabel;
    private static JTextField userText;
    private static JLabel passwordLabel;
    private static JPasswordField passwordText;
    private static JButton button;
    private static JLabel success;




    public static void main(String[] args) {
        //połaczenie z bazą danych
        try{
            System.out.println(DriverManager.getConnection("jdbc:mysql://localhost:3306/snake", "root", "").isValid(100));
        } catch(SQLException e){

        }




        JPanel panel = new JPanel();

        JFrame frame1 = new JFrame();
        frame1.setSize(500,300);
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame1.add(panel);

        panel.setLayout(null);

        JLabel userLabel = new JLabel("Podaj nick");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        JTextField userText = new JTextField(20);
        userText.setBounds(100, 20, 165, 25);
        panel.add(userText);

        JButton button = new JButton("Login");
        button.setBounds(10, 80, 80, 25);

        panel.add(button);


        JLabel success = new JLabel();
        success.setBounds(10, 110, 300, 25);
        panel.add(success);

        frame1.setVisible(true);

        ActionListener actionListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent actionEvent) {


                Object o = actionEvent.getSource();
                if(o == button) {
                    String user = userText.getText();
                    System.out.println(user);
                    if(user.equals("user")){
                        success.setText("Miłej gry!");
                        GameFrame gameFrame = new GameFrame();
                        gameFrame.setVisible(true);
                    } else {
                        success.setText("Zły login");

                }
            }}
        };
        button.addActionListener(actionListener);

    }


}