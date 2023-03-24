import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class HomePage extends JFrame
{
    Database db;
    JFrame jfrm;
    String currentViewType;
    HomePage(int userID, String viewType)
    {
        currentViewType = viewType;
        db = new Database();
        db.load();
        Dimension loginWindowSize = new Dimension(600,700);
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
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        jfrm.add(panel);

        BoxLayout layout = new BoxLayout(panel,BoxLayout.Y_AXIS);
        panel.setLayout(layout);

        JLabel topLogo = new JLabel();
        topLogo.setIcon(new ImageIcon("D:\\Importants\\Education Shit\\OOP\\Twitter\\logo.png"));
        topLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(topLogo);


        JPanel tweetBox = new JPanel();
        tweetBox.setBackground(bg);
        tweetBox.setLayout(new BoxLayout(tweetBox,BoxLayout.Y_AXIS));
        tweetBox.setAlignmentX(Component.CENTER_ALIGNMENT);
            JLabel tweetLabel = new JLabel("Share your thoughts");
            tweetLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            tweetBox.add(tweetLabel);

            JTextField tweet = new JTextField();
            tweetBox.add(tweet);

            JButton tweetButton = new JButton("Tweet!");
            tweetButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            tweetButton.setSize(40,tweetButton.getSize().height);
            tweetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(!tweet.getText().equals(""))
                {
                    db.tweets.add(new Tweet(db.users.get(userID).getUsername(),tweet.getText()));
                    db.save();
                    new HomePage(userID,currentViewType);
                    jfrm.dispose();
                }
            }
        });
            tweetBox.add(tweetButton);
        panel.add(tweetBox);

        JComboBox<String> jcb;
        String[] choices = {"All", "Following", "Self"};
        jcb = new JComboBox<String>(choices);
        panel.add(jcb);
        // Handle selections.
        jcb.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                currentViewType = (String)jcb.getSelectedItem();
                new HomePage(userID,currentViewType);
                jfrm.dispose();
            }
        });

        JPanel tweetsList = new JPanel();
        GridLayout layout1 = new GridLayout(db.tweets.size(),1);
        tweetsList.setLayout(layout1);
        for(int i = 0; i < db.tweets.size(); i++)
        {
            if(currentViewType.equals("Following") && !db.getUsers().get(userID).getFollowing().contains(db.tweets.get(i).getUsername()))
            {
                continue;
            }
            if(currentViewType.equals("Self") && !db.tweets.get(i).getUsername().equals(db.users.get(userID).getUsername()))
            {
                continue;
            }
            JPanel tweetPlusPFPContainer = new JPanel();
            tweetPlusPFPContainer.setLayout(new BoxLayout(tweetPlusPFPContainer,BoxLayout.X_AXIS));

            JLabel pfp = new JLabel();
            pfp.setIcon(new ImageIcon("D:\\Importants\\Education Shit\\OOP\\Twitter\\DefPFP.png"));
            tweetPlusPFPContainer.add(pfp);

            JPanel tweetContainer = new JPanel();
            tweetContainer.setLayout(new BoxLayout(tweetContainer,BoxLayout.Y_AXIS));
            JLabel usernameInsideBox = new JLabel("@"+db.tweets.get(i).getUsername());
            usernameInsideBox.setAlignmentX(Component.LEFT_ALIGNMENT);
            usernameInsideBox.setFont(new Font(Font.SANS_SERIF,Font.BOLD,14));
            tweetContainer.add(usernameInsideBox);
            JLabel tweetTextInsideBox = new JLabel(db.tweets.get(i).getText());
            tweetTextInsideBox.setAlignmentX(Component.LEFT_ALIGNMENT);
            tweetTextInsideBox.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,12));
            tweetContainer.add(tweetTextInsideBox);
            tweetPlusPFPContainer.add(tweetContainer);
            if(db.tweets.get(i).getUsername() .equals( db.users.get(userID).getUsername()))
            {
                JButton delete = new JButton("Delete");
                delete.setToolTipText(String.valueOf(i));
                delete.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        db.tweets.remove(Integer.parseInt(((JButton)actionEvent.getSource()).getToolTipText()));
                        db.save();
                        new HomePage(userID,currentViewType);
                        jfrm.dispose();
                    }
                });
                tweetPlusPFPContainer.add(delete);

                JButton edit = new JButton(".");
                edit.setToolTipText(String.valueOf(i));
                edit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        int konsiWali = Integer.parseInt(((JButton)actionEvent.getSource()).getToolTipText());
                        JFrame change = new JFrame();
                            JPanel editPanel = new JPanel();
                                editPanel.setLayout(new BoxLayout(editPanel,BoxLayout.Y_AXIS));
                                JTextField editedText = new JTextField();
                                editedText.setPreferredSize(new Dimension(100,20));
                                JButton edit = new JButton("EDIT");
                                edit.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent actionEvent) {
                                        db.getTweets().get(konsiWali).setText(editedText.getText());
                                        db.save();
                                        change.dispose();
                                        new HomePage(userID,currentViewType);
                                        jfrm.dispose();
                                    }
                                });
                                editPanel.add(editedText);
                                editPanel.add(edit);
                        change.add(editPanel);
                        change.setMinimumSize(new Dimension(200,100));
                        change.setLocationRelativeTo(null);
                        change.setVisible(true);
                    }
                });
                tweetPlusPFPContainer.add(edit);
            }
            tweetsList.add(tweetPlusPFPContainer);
        }
        JScrollPane jsp  = new JScrollPane(tweetsList);
        jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jsp.setPreferredSize(new Dimension(100,300));

        JPanel usersList = new JPanel();
        GridLayout layout2 = new GridLayout(db.users.size(),1);
        usersList.setLayout(layout2);
        for(int i = 0; i < db.users.size(); i++)
        {
            if(!db.users.get(i).getUsername().equals(db.users.get(userID).getUsername()))
            {
                JPanel tweetPlusPFPContainer = new JPanel();
                tweetPlusPFPContainer.setLayout(new BoxLayout(tweetPlusPFPContainer,BoxLayout.X_AXIS));

                JLabel pfp = new JLabel();
                pfp.setIcon(new ImageIcon("D:\\Importants\\Education Shit\\OOP\\Twitter\\DefPFP.png"));
                tweetPlusPFPContainer.add(pfp);

                JPanel tweetContainer = new JPanel();
                tweetContainer.setLayout(new BoxLayout(tweetContainer,BoxLayout.Y_AXIS));
                    JLabel usernameInsideBox = new JLabel("@"+db.users.get(i).getUsername());
                    usernameInsideBox.setAlignmentX(Component.LEFT_ALIGNMENT);
                    usernameInsideBox.setFont(new Font(Font.SANS_SERIF,Font.BOLD,14));
                tweetContainer.add(usernameInsideBox);
                JButton follow = new JButton("FOLLOW/UNFOLLOW");
                follow.setToolTipText(String.valueOf(i));
                follow.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        JButton btn = (JButton) actionEvent.getSource();
                        if(!db.users.get(userID).getFollowing().contains(db.users.get(Integer.parseInt(btn.getToolTipText())).getUsername()))
                        {
                            db.users.get(userID).getFollowing().add(db.users.get(Integer.parseInt(btn.getToolTipText())).getUsername());
                            db.save();
                            new HomePage(userID,currentViewType);
                        }
                        else {
                            db.users.get(userID).getFollowing().remove(db.users.get(Integer.parseInt(btn.getToolTipText())).getUsername());
                            db.save();
                            new HomePage(userID,currentViewType);
                        }
                    }
                });

                tweetPlusPFPContainer.add(tweetContainer);
                tweetPlusPFPContainer.add(follow);

                usersList.add(tweetPlusPFPContainer);
            }
        }
        JScrollPane jsp2  = new JScrollPane(usersList);
        jsp2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jsp2.setPreferredSize(new Dimension(100,300));

        JTabbedPane jtp = new JTabbedPane();
        jtp.addTab("Tweets", jsp);
        jtp.addTab("Users", jsp2);

        jtp.setPreferredSize(new Dimension(100,300));
        panel.add(jtp);

        JButton logout = new JButton("LOGOUT");
        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                new LoginWindow();
                jfrm.dispose();
            }
        });
        panel.add(logout);

        jfrm.setLocationRelativeTo(null);
        jfrm.setVisible(true);

    }

}
