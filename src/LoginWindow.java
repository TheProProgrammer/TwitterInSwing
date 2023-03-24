import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.StrokeBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class LoginWindow
{
    Database db;
    JFrame jfrm;
    LoginWindow()
    {
        db = new Database();
        db.load();

        Dimension loginWindowSize = new Dimension(400,250);
        jfrm = new JFrame("Twitter");
        Color bg = new Color(41, 114, 158, 255);

        jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jfrm.setMinimumSize(loginWindowSize);
        jfrm.setResizable(false);
        jfrm.setLayout(new FlowLayout());
        jfrm.getContentPane().setBackground(bg);

        ImageIcon frameIcon = new ImageIcon("D:\\Importants\\Education Shit\\OOP\\Twitter\\frameLogo.png");
        jfrm.setIconImage(frameIcon.getImage());

        JPanel panel = new JPanel();
        panel.setBackground(bg);
        jfrm.add(panel);

        BoxLayout layout = new BoxLayout(panel,BoxLayout.Y_AXIS);
        panel.setLayout(layout);

        ImageIcon image = new ImageIcon("D:\\Importants\\Education Shit\\OOP\\Twitter\\logo.png");
        JLabel logo = new JLabel(image);
        logo.setSize(400,20);
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(logo);

        JPanel usernamePanel = new JPanel();
        usernamePanel.setBackground(bg);
        BoxLayout usernameLayout = new BoxLayout(usernamePanel,BoxLayout.X_AXIS);
        usernamePanel.setLayout(usernameLayout);
            JLabel usernameText = new JLabel("Username:  ");
            usernamePanel.add(usernameText);
            JTextField usernameField = new JTextField();
            usernamePanel.add(usernameField);
        panel.add(usernamePanel);

        JPanel passwordPanel = new JPanel();
        passwordPanel.setBackground(bg);
        BoxLayout passwordLayout = new BoxLayout(passwordPanel,BoxLayout.X_AXIS);
        passwordPanel.setLayout(passwordLayout);
            JLabel passwordText = new JLabel("Password:  ");
            passwordPanel.add(passwordText);
            JPasswordField passwordField = new JPasswordField();
            passwordPanel.add(passwordField);
        panel.add(passwordPanel);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(bg);
        BoxLayout buttonsLayout = new BoxLayout(buttonsPanel,BoxLayout.X_AXIS);
        buttonsPanel.setLayout(buttonsLayout);
            JButton button1 = new JButton("Login");
            button1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    login(usernameField.getText(),String.valueOf(passwordField.getPassword()));
                }
            });
            buttonsPanel.add(button1);
            JButton button2 = new JButton("Register");
            button2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent)
                {
                    register(usernameField.getText(),String.valueOf(passwordField.getPassword()));
                }
            });
            buttonsPanel.add(button2);
            buttonsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
            buttonsPanel.setBorder(new EmptyBorder(15,15,15,15));
        panel.add(buttonsPanel);

        jfrm.setLocationRelativeTo(null);
        jfrm.setVisible(true);
    }
    public void login(String username, String password)
    {
        ArrayList<User> users = db.getUsers();
        for (int i=0; i<users.size(); i++)
        {
            if(users.get(i).getUsername().equals(username) && users.get(i).getPassword().equals(password))
            {
                System.out.println("Found user");
                HomePage homePage = new HomePage(i,"All");
                jfrm.setVisible(false);
                return;
            }
        }
        System.out.println("The credentials do not match our records");
    }
    public void register(String username, String password)
    {
        ArrayList<User> users = db.getUsers();
        for (User user:users)
        {
            if(user.getUsername().equals(username))
            {
                System.out.println("Username already exists");
                return;
            }
        }
        users.add(new User(username,password,new ArrayList<String>()));
        db.save();
    }
}
