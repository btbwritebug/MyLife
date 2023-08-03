package com.founder.xy.sensitive;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class SensitiveResult {
    private Set<SensitiveWord> resultSensitiveSet = new HashSet<>();
    private Set<SensitiveWord> resultIllegalSet = new HashSet<>();
    private int senSize = 0;
    private int illSize = 0;

    public SensitiveResult() {}

    public SensitiveResult(String content, String res) {
        getWords(content, res);
    }

    private void getWords(String content, String res){
        int index1 = res.indexOf("<USIF><![CDATA[");
        int index2 = res.indexOf("]]></USIF>", index1);
        int index3 = res.indexOf("<ILLEGALIDS><![CDATA[");
        int index4 = res.indexOf("]]></ILLEGALIDS>", index3);
        //敏感词
        handle(content, res, index1, index2, "Sensitive", 15);
        //非法词
        handle(content, res, index3, index4, "Illegal", 21);
    }

    private void handle(String content, String res, int index1, int index2, String str, int index) {
        if (index1 != -1 && index2 != -1) {
            String sif = res.substring(index1 + index, index2);
            String[] split = sif.split("#");
            if (split.length == 3) {
                SensitiveWord sw;
                //存放规则id的数组
                String ids = split[0];
                //存放各个规则命中次数的数组
                String nums = split[1];
                //存放各个敏感词位置的数组
                String pos = split[2];
                String[] id = ids.split(";");
                String[] num = nums.split(";");
                String[] poss = pos.split(";");
                //遍历各个敏感词命中的规则
                for (int i = 0; i < id.length; i++) {
                    sw = new SensitiveWord(content, id[i], num[i], poss[i]);
                    if (str.equals("Sensitive")) {
                        resultSensitiveSet.add(sw);
                        senSize++;
                    }else {
                        resultIllegalSet.add(sw);
                        illSize++;
                    }
                }
            }
        }
    }
}
