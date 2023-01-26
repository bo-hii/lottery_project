//بهاره دهقانی
//شماره دانشجویی : ۴۰۰۱۱۹۷۳
//تمرین کلاس برنامه سازی پیشرفته
//کاربران ثبت نام شده در برنامه

package bahareh;
import org.jetbrains.annotations.NotNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;
import java.util.Scanner;

public class User {
    private String userName;
    private String phoneNumber;
    private String prize;
    private int lotCounter;
    private static final int SIZE = 18;
    private static final int PRIZE_NUM = 3;

    public User(String userName, String phoneNumber){
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        lotCounter = 0;
    }
    public User(String phoneNumber){
        this("user", phoneNumber);
    }
    public User(){
        lotCounter = 0;
    }

    public String getUserName(){
        return userName;
    }
    public String getPhoneNumber(){
        return phoneNumber;
    }
    public String getPrize(){
        return prize;
    }
    public int getLotCounter() {
        return lotCounter;
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void setPrize(String prize) {
        this.prize = prize;
    }
    public void setLotCounter(int lotCounter) {
        this.lotCounter = lotCounter;
    }

    /**
     * این تابع برسی میکند آیا شماره کاربر جز شماره های دایمی همراه اول که مجاز به شرکت در قرعه کشی هستند هست یا خیر
     * @return a boolean value which is tell if the number is valid for lotMci
     */
    public boolean validPhoneNumber(){
        String preCode = phoneNumber.substring(0, 4);
        String[] validPreCode = {"0993" ,"0992" ,"0991" ,"0919" ,"0918" ,"0917" ,"0916" ,"0915" ,"0914" ,"0913" ,"0912" ,"0911" ,"0910"};
        for (String s : validPreCode) {
            if (preCode.equals(s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * این تابع برای شرکت کاربر قرعه کشی همراه اول است هست
     */
    public void mciLot(){
        String[] prizeList = new String[100];
        prizeList = readFromPrizeListFile(prizeList);

        Random rand =  new Random();
        int randomIndex = rand.nextInt(100);
        prize = prizeList[randomIndex];
    }

    /**
     * این تابع منوی داشبورد کاربر وارد شده است
     */
    public void userDashboard(){
        Scanner input = new Scanner(System.in);
        while(true) {
            printMenu();
            String enterCode = input.next();
            switch (enterCode) {
                case "*10*355#":
                    participateLot();
                    break;
                case "*10*350#":
                    showResult();
                    break;
                case"*0#":
                case "*10*100#":
                    return;
                default:
                    System.out.print("\nSorry; your entered code is invalid! :(");
                    break;
            }
        }
    }
    /**
     * چاپ گزینه های منو ی داشبورد کاربر
     */
    public void printMenu(){
        System.out.print("\n Please insert code to continue : " +
                "\n enter code *10*355# to Participate in mci (lot)" +
                "\n enter code *10*350# to show result " +
                "\n enter code *10*100# to log out " +
                "\n >> ");
    }


    /**
     *  تابع شرکت دادن کاربر در قرعه کشی
     */
    public void participateLot(){
        boolean validPhoneNum = validPhoneNumber();
        if ( lotCounter < 2 &&( prize == null || prize.equals("") )&& validPhoneNum ) {
            mciLot();

            if(prize == null || prize.equals("")) {
                System.out.print("\n oh you didn't win any thing ");
                if(lotCounter == 0) System.out.print("\n you can try again ");
            }
            else {
                System.out.print("\n you win "+ prize);
            }
            lotCounter++;
        }
        else if ( !validPhoneNum){
            System.out.print("\n sorry; your phone number is invalid"
                    +"\n only holders of a permanent Hamrahe Avval SIM can participate in lot");
        }
        else{
            System.out.print("\n sorry; you can't participate in mci anymore ");
        }
        backToPrevious();
    }

    /**
     * تابع نمایش نتایج قرعه کشی
     */
    public void showResult(){
        if(prize == null || prize.equals(""))
            System.out.print("\n you didn't win any prize :(");
        else{
            System.out.println("\n !!! Congratulation :) !!! you win "+ prize);
        }
        backToPrevious();
    }
    public static void backToPrevious(){
        Scanner input = new Scanner(System.in);
        String enterCode;
        do{
            System.out.print("\n enter *0# to back to the menu >> ");
            enterCode = input.next();
        }while(!enterCode.equals("*0#"));
    }

    //***********************************************************************************************
    public void writeInfile(@NotNull RandomAccessFile rFile) throws IOException {
            rFile.writeChars(fixLen(userName)); //36 byte
            rFile.writeChars(phoneNumber);      //22 byte -->position = 58
            rFile.writeChars(fixLen(prize));    //36 byte -->position = 94
            rFile.writeInt(lotCounter);         //4 byte --> position = 98

    }
    public void writeInfile(@NotNull RandomAccessFile rFile, long position){
        try {
            rFile.seek(position);
            writeInfile(rFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String fixLen(String str){
        if(str == null) return "                  ";

        String temp = str;
        if(str.length() > SIZE)
            return str.substring(0, SIZE);

        while(temp.length() < SIZE)
            temp += " ";
        return temp;
    }

    public void readFromUserDataFile(RandomAccessFile rFile) throws IOException {
        userName = readFix(rFile, SIZE);
        phoneNumber = readFix(rFile,11);
        prize = readFix(rFile, SIZE);
        lotCounter = rFile.readInt();
    }

    public String[] readFromPrizeListFile(String[] prize)   {
            try {
                RandomAccessFile rFile = new RandomAccessFile("prizeList.dat","rw");
                for(int i = 0; i<PRIZE_NUM; i++){
                    prize[i] = readFix(rFile, SIZE);
                }
                rFile.close();
            } catch (FileNotFoundException e) {
                System.err.println("The prize List file not found!!!");
            } catch (IOException e) {
                System.err.println("some Exception about prizeList we meet; we will try again ....");
            }
        return prize;
    }


    public String readFix(RandomAccessFile rFile, int size) throws IOException {
        String str ="";
        for (int i = 0; i<size; i++){
            str += rFile.readChar();
        }
        return str.trim();
    }


}
