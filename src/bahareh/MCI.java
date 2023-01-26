//بهاره دهقانی
//شماره دانشجویی : ۴۰۰۱۱۹۷۳
//تمرین کلاس برنامه سازی پیشرفته
//اپراتور همراه اول

package bahareh;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.Scanner;

public class MCI {
    private User[] users;
    private int countUser;
    private static final long USER_INFO_SIZE = 98;

    public MCI() {
        this.countUser = 0;
        this.users = new User[1];
    }

    public void setUsers(User[] users) {
        this.users = users;
        this.countUser = users.length;
    }

    public static long getUserInfoSize(){ return USER_INFO_SIZE;}

    /**
     *این تابع برای اضاقه کردن کاربر وارد شده به عنوان آرگمان ورودی به آرایه ی کاربران موجود در کلاس MCI است
     * @param user which we want to add to the userArray in MCI class
     * @return a boolean which is true if the user(User phone number) is not registered before
     */
    public boolean addUser(User user, RandomAccessFile rFile){
        if(user != null && registeredBefore(user.getPhoneNumber()) == -1) {
            if(users.length == countUser )
                users = Arrays.copyOf(users, countUser * 2);

            users[countUser].writeInfile(rFile, USER_INFO_SIZE*countUser);
            users[countUser++] = user;
            return true;
        }
        else if(user != null)
            System.out.println("\n you had registered before " +
                    "\n so try to login");
        return false;
    }

    /**
     *  این تابع اندیس کاربری که شماره ی وارد شده متعلق به آن است را بر میگرداند
     * اگر شماره متعلق به هیچ یک از کاربران نباشد -1 را برمیگرداند
     * @return index of user with enteral phone Number
     * if the phone Number isn't belong to eny user return -1
     */
    public int registeredBefore(String phoneNumber) {
        for (int userIndex = 0; userIndex < countUser; userIndex++) {
            if (users[userIndex].getPhoneNumber().equals(phoneNumber)) {
                return userIndex;
            }
        }
        return -1;
    }
    /**
     *  این تابع برای ثبت نام کاربر جدید دربرنامه
     *   و افزودن او به آرایه ی کاربران موجود در کلاس mci است
     */
    public void register(RandomAccessFile rFile){
        if(addUser(newUser(), rFile)){
            int userIndex = countUser-1;
            users[userIndex].userDashboard();
            users[userIndex].writeInfile(rFile, USER_INFO_SIZE*userIndex);

        }
    }

    /**
     * این تابع کاربری که قبلا ثبت نام کرده و در آرایه ی کاربران هست را وارد داشبوردش میکند
     */
    public void login(RandomAccessFile rFile){
        Scanner input = new Scanner(System.in);
        System.out.print("\n phoneNumber : ");
        String phoneNumber = input.next();
        if(phoneNumber.equals("*0#")) return;
        int userIndex = registeredBefore(phoneNumber);
        if(printUserInfo(userIndex)){
            users[userIndex].userDashboard();
            users[userIndex].writeInfile(rFile, USER_INFO_SIZE *userIndex);

        }
        else {
            System.out.print("\n Sorry; your phone number is invalid!" +
                    "\n you have to register first :(");
        }
    }

    /**
     * این تابع مشخصات (ویژگی های) کاربر را میگیرد
     * واگر کاربر قبلا در برنامه ثبت نام نکرده باشد (با توجه به شماره تلفن)
     * کاربری با مشخصات ورودی ایجاد کرده و باز میگرداند
     * @return a user
     */
    public User newUser(){
        while (true){
            Scanner input = new Scanner(System.in);
            System.out.print("\n user name : ");
            String userName = input.next();
            if(userName.equals("*0#")) return null;
            System.out.print("\n phone number : ");
            String phoneNumber = input.next();
            if (phoneNumber.equals("*0#")) return null;
            if(phoneNumber.length() == 11 && beingNum(phoneNumber, 11) && phoneNumber.startsWith("09")){
                return new User(userName, phoneNumber);
            }
            else{
                System.out.print("\n your phone number is invalid" +
                        "\n try again");
            }
        }
    }
    //اين تابع چك ميكند كه همه ی كاراكتر های ورودي عددي هست يا خير
    public boolean beingNum(String str, int n)
    {
        char[] charArray = str.toCharArray();
        for (int i = 0; i < n; i++)
        {
            if (charArray[i] < '0' || charArray[i] > '9')
                return false;
        }
        return true;
    }

    /**
     * این تابع اطلاعات کاربر موجود را چاپ میکند
     * @param userIndex
     *      * @return a boolean which show this user with enteral user index exist in our userArray or no
     */
    public boolean printUserInfo(int userIndex){
        if(userIndex >=0 && userIndex < countUser){
            System.out.print("\n user name : " + users[userIndex].getUserName()+
                    "\n phone number : " + users[userIndex].getPhoneNumber());
            return true;
        }
        return false;
    }
}

