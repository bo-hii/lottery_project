package bahareh.creatDatFilesAtFirst;

import bahareh.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class SaveTenUser2 {

    private static final String UsersFileName ="usersData.dat";
    public static void main(String[] args){

        User[] users = new User[10];
        try {
            File file = new File(UsersFileName);
            file.delete();

            Scanner in = new Scanner(new File("10UsersData.txt"));
            RandomAccessFile rFile = new RandomAccessFile(UsersFileName, "rw");
            for (int i = 0; in.hasNext(); i++) {
                new User(in.next(), in.next()).writeInfile(rFile);
            }
            in.close();
            rFile.close();
            System.out.println("Users are successfully written in our file");
        } catch (FileNotFoundException e) {
            System.err.println("");;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
