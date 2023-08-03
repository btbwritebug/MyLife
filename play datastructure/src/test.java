import java.text.SimpleDateFormat;
import java.util.*;

public class test {
    public static void main(String[] args) {
        Cat cat = new Cat();
        System.out.println(cat.getAge());
    }
    public static String getLastTimeInterval() {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        int dayOfWeek = calendar1.get(Calendar.DAY_OF_WEEK) - 1;
        int offset1 = 1 - dayOfWeek;
        int offset2 = 7 - dayOfWeek;
        calendar1.add(Calendar.DATE, offset1 - 7);
        calendar2.add(Calendar.DATE, offset2 - 7);
        // System.out.println(sdf.format(calendar1.getTime()));// last Monday
        SimpleDateFormat sdf = new SimpleDateFormat();
        String lastBeginDate = sdf.format(calendar1.getTime());
        // System.out.println(sdf.format(calendar2.getTime()));// last Sunday
        String lastEndDate = sdf.format(calendar2.getTime());
        return lastBeginDate + "," + lastEndDate;
    }

    /**
     * 工具类，获取时间
     * @param field
     * @param amount
     * @return
     */
    public static String getTime(int field, int amount) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(field, amount);
        Date d = c.getTime();
        String day = format.format(d);
        return day;
    }
}

class Cat{
    private int age;
    private String name;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}