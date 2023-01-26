package bahareh.creatDatFilesAtFirst;

import bahareh.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class SaveTenUser {

    private static final String UsersFileName ="usersData.dat";
    public static void main(String[] args){

        User[] users = new User[10];
        users[0] = new User("Fateme"  , "09131590286");
        users[1] = new User("Ali"     , "09135282893");
        users[2] = new User("Zahra"   , "09136367847");
        users[3] = new User("Hosein"  , "09377689726");
        users[4] = new User("Mohamad" , "09131572840");
        users[5] = new User("Mahdiyeh", "09398537311");
        users[6] = new User("Bahare"  , "09397943699");
        users[7] = new User("Behnaz"  , "09133592699");
        users[8] = new User("Arezoo"  , "09132990409");
        users[9] = new User("Maryam"  , "09132571934");

        try {
            File file = new File(UsersFileName);
            file.delete();
            RandomAccessFile rFile = new RandomAccessFile(UsersFileName, "rw");
        for (int i = 0; i < 10; i++) {
            users[i].writeInfile(rFile);
        }
        rFile.close();
        System.out.println("Users are successfully written in usersData file");

        } catch (FileNotFoundException e) {
            System.err.println("user Data file not found");;
        } catch (IOException e) {
            e.printStackTrace();
        } finally{}
    }
}
