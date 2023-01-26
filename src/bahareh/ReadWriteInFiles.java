package bahareh;

import java.io.IOException;
import java.io.RandomAccessFile;

public class ReadWriteInFiles {

    private static final int SIZE = 18;
    private static  final int PRIZE_NUM = 3;
    private User user;

    public ReadWriteInFiles(User user) {
        this.user = user;
    }

    public void writeInfile(RandomAccessFile rFile, long position){

        try {
            rFile.seek(position);
            rFile.writeChars(fixLen(user.getUserName())); //36 byte
            rFile.writeChars(user.getPhoneNumber());      //22 byte -->position = 58
            rFile.writeChars(fixLen(user.getPrize()));    //36 byte -->position = 94
            rFile.writeInt(user.getLotCounter());         //4 byte --> position = 98

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
        user.setUserName( readFix(rFile, SIZE));
        user.setPhoneNumber(readFix(rFile,11));
        user.setPrize(readFix(rFile, SIZE));
        user.setLotCounter(rFile.readInt());
    }

    public String readFix(RandomAccessFile rFile, int size) throws IOException {
        String str ="";
        for (int i = 0; i<size; i++){
            str += rFile.readChar();
        }
        return str.trim();
    }

    public String[] readFromPrizeListFile(String[] prize) throws IOException {
        RandomAccessFile rFile = new RandomAccessFile("prizeList","rw");
        for(int i = 0; i<PRIZE_NUM; i++){
            prize[i] = readFix(rFile, SIZE);
        }
        return prize;
    }


}
