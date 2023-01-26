package bahareh.creatDatFilesAtFirst;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class SavePrize {
    private static final String PrizeFileName ="PrizeList.dat";
    private static final int SIZE = 18;
    public static void main(String[] args){
        File file = new File(PrizeFileName);
        file.delete();

        writeInfile(0);
        System.out.println("prizes are successfully written in our file");
        }
    public static void writeInfile(long position){
        try {
            RandomAccessFile rFile = new RandomAccessFile(PrizeFileName, "rw");
            rFile.seek(position);
            rFile.writeChars(fixLen("50 million cash")); //36 byte
            rFile.writeChars(fixLen("10 million cash"));      //36 byte -->position = 72
            rFile.writeChars(fixLen("Unlimited internet"));    //36 byte -->position = 108

            rFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static String fixLen(String str){
        if(str.length() > SIZE)
            return str.substring(0, SIZE);
        while(str.length() < SIZE)
            str += " ";
        return str;
    }

}
