package test;

import java.text.SimpleDateFormat;
import java.util.*;

public class TimeTest {

    public static void main(String[] args) {
        List<String> list= new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");

        Iterator var2 = list.iterator();

        while(var2.hasNext()) {
            String s = (String)var2.next();
            if (s.equals("1")) {
                list.remove(s);
            }
        }

        System.out.println(list);
    }

    public static int getNowToNextDaySeconds() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return (int) ((cal.getTimeInMillis() - System.currentTimeMillis()) / 1000);
    }
    public static String getLastTimeInterval() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        int dayOfWeek = calendar1.get(Calendar.DAY_OF_WEEK) - 1;
        int offset1 = 1 - dayOfWeek;
        int offset2 = 7 - dayOfWeek;
        calendar1.add(Calendar.DATE, offset1 - 7);
        calendar2.add(Calendar.DATE, offset2 - 7);
        // System.out.println(sdf.format(calendar1.getTime()));// last Monday
        String lastBeginDate = sdf.format(calendar1.getTime());
        // System.out.println(sdf.format(calendar2.getTime()));// last Sunday
        String lastEndDate = sdf.format(calendar2.getTime());
        return lastBeginDate + "," + lastEndDate;
    }
}
