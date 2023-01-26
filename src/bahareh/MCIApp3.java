//بهاره دهقانی
//شماره دانشجویی : ۴۰۰۱۱۹۷۳
//تمرین کلاس برنامه سازی پیشرفته
//کلاس اصلی اجرای برنامه
//این برنامه علاوه بر 10 نفر که از قبل در فایل ثبت نام شده اند هر کسی را که در برنامه ثبت نام کند در فایل ذخیره کرده
// و پس از بسته شدن برنامه بار بعد که برنامه اجرا شود آنها ثبت نام شده اند

package bahareh;


import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class MCIApp3 {
    private static final String UsersFileName ="usersData.dat";
    public static void main(String[] args){
        try {
            RandomAccessFile rFile = new RandomAccessFile(UsersFileName, "rw");
            MCI mci = new MCI();
            Scanner input = new Scanner(System.in);
            //منوی اصلی برنامه
            while(true){
                printMenu();
                String enterCode = input.next();
                switch (enterCode) {
                    case "*10*0#" -> mci.register(rFile);
                    case "*10*1#" -> mci.login(rFile);
                    case "*0#" -> {
                        rFile.close();
                        return;
                    }
                    default -> System.out.print("\n Sorry; your entered code is invalid! :(" +
                            "\n try again");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    /**
     * چاپ گزینه های منوی اصلی برنامه
     */
    public static void printMenu(){
        System.out.print("\n Welcome to Hamrahe Avval(mci)" +
                "\n enter code *10*0# to register " +
                "\n enter code *10*1# to login" +
                "\n enter code *0# to Exit the program" +
                "\n >> ");

    }
}