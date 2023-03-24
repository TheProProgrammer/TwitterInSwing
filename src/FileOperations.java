import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class FileOperations
{
    static Database readAllFromFile()
    {
        Database db = null;
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("database.ser"));
            db = (Database)ois.readObject();
        }
        catch (ClassNotFoundException e)
        {}
        catch (EOFException e)
        {}
        catch (IOException e)
        {}
        return db;
    }
    static void writeEntireDataBaseToFile(Database db)
    {
        try {
            File f = new File("database.ser");
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
            oos.writeObject(db);
            oos.close();
        }catch (IOException e)
        {
            System.out.println("Error while file writing.");
            e.printStackTrace();
        }
    }
}
