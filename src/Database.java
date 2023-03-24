import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Database implements Serializable
{
    public ArrayList<Tweet> tweets;
    public ArrayList<User> users;

    public Database()
    {
        tweets = new ArrayList<>();
        users = new ArrayList<>();
    }
    public void load()
    {
        Database db = FileOperations.readAllFromFile();
        if(db!=null)
        {
            this.tweets = db.getTweets();
            this.users = db.getUsers();
        }
    }
    public void save()
    {
        FileOperations.writeEntireDataBaseToFile(this);
    }

    public ArrayList<Tweet> getTweets() {
        return tweets;
    }
    public void setTweets(ArrayList<Tweet> tweets) {
        this.tweets = tweets;
    }
    public ArrayList<User> getUsers() {
        return users;
    }
    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }
}
