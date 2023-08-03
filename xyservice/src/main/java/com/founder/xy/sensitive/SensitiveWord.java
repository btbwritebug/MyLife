package com.founder.xy.sensitive;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SensitiveWord {
    private long length;
    private String id;
    private List<String> keyword = new ArrayList<>();
    private List<String> position = new ArrayList<>();

    public SensitiveWord() {}

    public SensitiveWord(String content, String id, String num, String poss) {
        handle(poss, content);
        this.id = id;
        this.length = Integer.parseInt(num);
    }

    private void handle( String poss, String content) {
        String[] posArr = poss.split(",");
        for (String po:posArr) {
            String[] sp = po.split("-");
            //取出对应位置的文字信息
            String cont = content.substring(Integer.parseInt(sp[0]), Integer.parseInt(sp[1]) + 1);
            keyword.add(cont);
            position.add(po);
        }
    }
}
