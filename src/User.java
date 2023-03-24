import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable
{
    private String username;
    private String password;
    private ArrayList<String> following;

    public User(String username, String password, ArrayList<String> folllowing)
    {
        this.username = username;
        this.password = password;
        this.following = folllowing;
    }

    public String getUsername()
    {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public ArrayList<String> getFollowing() {
        return following;
    }
    public void setFollowing(ArrayList<String> folllowing) {
        this.following = folllowing;
    }
    public void addFollowing(String acc_to_follow)
    {
        following.add(acc_to_follow);
    }
}
