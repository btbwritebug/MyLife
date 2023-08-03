package com.founder.xy.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.servlet.ServletRequest;
import java.io.File;
import java.util.Arrays;
import java.util.List;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Image {

    String name;
    String path;
    String wholePath;
    List<String> commands;
    String destName;
    String imgType;
    String command;
    // zoom
    Float size;
    Integer imgWidth;
    Integer imgHeight;

    Boolean keepRadio;

    // cut
    double start_x;
    double start_y;
    double end_x;
    double end_y;
    double startX;
    double startY;
    double endX;
    double endY;

    // watermark
    String watermark;
    Integer position;
    Float transparency;
    String imgList;

    // rotate
    Double rotate;

    //drawText
    String fontName;
    Integer fontStyle;
    Integer fontSize;
    String color;
    String content;
    Boolean isSameSetting;

    public Image(ServletRequest request) {
        this.name = request.getParameter("name");
        this.path = request.getParameter("path");
        this.wholePath = request.getParameter("wholePath");
        if (this.wholePath == null || this.wholePath.isEmpty()) {
            this.wholePath = this.path + File.separator + this.name;
        }
        this.destName = request.getParameter("destName");
        if (this.destName != null && !this.destName.isEmpty()) {
            int dirIndex = this.destName.replaceAll("\\\\", "/").lastIndexOf('/');
            File dirFile = new File(this.destName.substring(0, dirIndex));
            // 如果文件夹不存在则创建
            if (!dirFile.exists() && !dirFile.isDirectory()) {
                dirFile.mkdir();
            }
        }

        this.imgType = request.getParameter("imgType");

        if (this.imgType == null || "".equals(this.imgType)) {
            this.imgType = wholePath.substring(wholePath.lastIndexOf('.') + 1);
        }

        this.watermark = request.getParameter("watermark");
        String comm = request.getParameter("commands");
        if (comm != null && !comm.isEmpty()) {
            this.commands = Arrays.asList(comm.split(","));
        }

        this.size = request.getParameter("size") == null
                || request.getParameter("size").equals("null") ? null : Float.parseFloat(request
                .getParameter("size"));

        this.imgWidth = request.getParameter("imgWidth") == null
                || request.getParameter("imgWidth").equals("null") ? null : Integer
                .parseInt(request.getParameter("imgWidth"));
        this.imgHeight = request.getParameter("imgHeight") == null
                || request.getParameter("imgHeight").equals("null") ? null : Integer
                .parseInt(request.getParameter("imgHeight"));

        this.startX = request.getParameter("start_x") == null
                || request.getParameter("start_x").equals("null") ? 0 : Double.parseDouble(request
                .getParameter("start_x"));
        this.startY = request.getParameter("start_y") == null
                || request.getParameter("start_y").equals("null") ? 0 :  Double.parseDouble(request
                .getParameter("start_y"));
        this.endX = request.getParameter("end_x") == null
                || request.getParameter("end_x").equals("null") ? 0 :  Double.parseDouble(request
                .getParameter("end_x"));
        this.endY = request.getParameter("end_y") == null
                || request.getParameter("end_y").equals("null") ? 0 :  Double.parseDouble(request
                .getParameter("end_y"));

        this.position = request.getParameter("position") == null
                || request.getParameter("position").equals("null") ? null : Integer
                .parseInt(request.getParameter("position"));
        this.transparency = request.getParameter("transparency") == null
                || request.getParameter("transparency").equals("null") ? null : Float
                .parseFloat(request.getParameter("transparency"));
        this.rotate = request.getParameter("rotate") == null
                || request.getParameter("rotate").equals("null") ? null : Double
                .parseDouble(request.getParameter("rotate"));

        this.keepRadio = (request.getParameter("keepRadio") == null
                || request.getParameter("keepRadio").equals("null")) || Boolean
                .parseBoolean(request.getParameter("keepRadio"));

        this.fontName = request.getParameter("fontName");
        this.color = request.getParameter("color");

        this.fontStyle = request.getParameter("fontStyle") == null
                || request.getParameter("fontStyle").equals("null") ? null : Integer
                .parseInt(request.getParameter("fontStyle"));

        this.fontSize = request.getParameter("fontSize") == null
                || request.getParameter("fontSize").equals("null") ? null : Integer
                .parseInt(request.getParameter("fontSize"));

        content = request.getParameter("content");

        this.isSameSetting = request.getParameter("isSameSetting") != null && !request.getParameter("isSameSetting").equals("null") && Boolean.parseBoolean(request.getParameter("isSameSetting"));
    }

    public void resetImageType() {
        if (this.imgType == null || "".equals(this.imgType)) {
            if (this.wholePath != null && !"".equals(this.wholePath)) {
                this.imgType = this.wholePath.substring(this.wholePath.lastIndexOf('.') + 1);
            } else if (this.destName != null && !"".equals(this.destName)) {
                this.imgType = this.destName.substring(this.destName.lastIndexOf('.') + 1);
            }

        }
    }
}
