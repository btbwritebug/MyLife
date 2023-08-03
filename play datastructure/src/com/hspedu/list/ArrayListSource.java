package com.hspedu.list;

public class ArrayListSource {
    public static void main(String[] args) {
        String str = "1234";
        str = changeStr(str);
        System.out.println(str.contains("/group/guest"));
    }

    public static String changeStr(String str) {
        str = str.concat("/group/guest");
        return str;
    }
}

