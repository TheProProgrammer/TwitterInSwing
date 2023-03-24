import java.io.Serializable;

public class Tweet implements Serializable
{
    private String username;
    private String text;
    private int likes;

    public Tweet(String username, String text)
    {
        this.username = username;
        this.text = text;
        this.likes = likes;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
}
