package com.founder.xy.service;

import com.founder.xy.common.ImageUtils;
import net.sf.json.JSONArray;
import org.springframework.stereotype.Service;

import java.awt.*;

@Service
public class ImageService {
    /**
     * 缩放
     *
     * @param imagePath
     * @param destName
     * @param imgType
     * @param size
     * @throws Exception
     */
    public void zoomImage(String imagePath, String destName, String imgType, float size) {
        ImageUtils.zoomImage(imagePath, destName, imgType, size);
    }

    /**
     * 缩放 - 保持比例
     *
     * @param imgPath
     * @param destName
     * @param imgType
     * @param imgWidth
     * @param imgHeight
     * @param keepRadio
     * @throws Exception
     */
    public void zoomImage(String imgPath, String destName, String imgType, int imgWidth,
                          int imgHeight, boolean keepRadio) {
        ImageUtils.zoomImage(imgPath, destName, imgType, imgWidth, imgHeight, keepRadio);
    }

    /**
     * 旋转
     *
     * @param imagePath
     * @param destName
     * @param imgType
     * @param rotate
     * @throws Exception
     */
    public void rotateImage(String imagePath, String destName, String imgType, double rotate) {
        ImageUtils.rotateImage(imagePath, destName, imgType, rotate);
    }

    /**
     * 水印
     *
     * @param imagePath
     * @param watermarkImg
     * @param position
     * @param transparency
     * @param destName
     * @param imgType
     * @throws Exception
     */
    public void watermark(String imagePath, String watermarkImg, int position,
                          float transparency, String destName, String imgType) {
        ImageUtils.watermark(imagePath, watermarkImg, position, transparency, destName, imgType);
    }

    /**
     * 水印 批量
     *
     * @param imgList
     * @param watermarkImg
     * @param position
     * @param transparency
     * @return
     * @throws Exception
     */
    public String watermarkList(String imgList, String watermarkImg, int position, float transparency) {
        JSONArray jsonArray = JSONArray.fromObject(imgList);
        JSONArray resultArray = new JSONArray();
        String path0;
        for (Object path : jsonArray) {
            path0 = (String) path;
            String imgType = path0.substring(path0.lastIndexOf('.') + 1);
            String destName = path0.substring(0, path0.lastIndexOf('.')) + "_batchwm" + "." + imgType;
            ImageUtils.watermark(path0, watermarkImg, position, transparency, destName, imgType);
            resultArray.add(destName);
        }
        return resultArray.toString();

    }

    /**
     * 转黑白图片
     *
     * @param imgPath
     * @param destName
     * @param imgType
     * @throws Exception
     */
    public void toGray(String imgPath, String destName, String imgType) {
        ImageUtils.toGray(imgPath, destName, imgType);
    }

    /**
     * 文字水印
     *
     * @param imgPath
     * @param destName
     * @param imgType
     * @param content
     * @param position
     * @param transparency
     * @param fontName
     * @param fontStyle
     * @param fontSize
     * @param color
     * @throws Exception
     */
    public String drawText(String imgPath, String destName, String imgType, String content, int position,
                           float transparency, String fontName, int fontStyle, int fontSize, String color, Boolean isSameSetting, String imgList) {
        JSONArray resultArray = new JSONArray();
        if (isSameSetting != null && isSameSetting) {
            JSONArray jsonArray = JSONArray.fromObject(imgList);
            String path0;
            for (Object path : jsonArray) {
                path0 = (String) path;
                imgType = path0.substring(path0.lastIndexOf('.') + 1);
                destName = path0.substring(0, path0.lastIndexOf('.')) + "_txt" + "." + imgType;
                ImageUtils.drawText(path0, destName, imgType, content, position, transparency,
                        new Font(fontName, fontStyle, fontSize), ImageUtils.getColor(color));
                resultArray.add(destName);
            }

        } else {
            ImageUtils.drawText(imgPath, destName, imgType, content, position, transparency,
                    new Font(fontName, fontStyle, fontSize), ImageUtils.getColor(color));
        }
        return resultArray.toString();
    }
}
