package com.founder.xy.imagefilter;

import net.coobird.thumbnailator.filters.ImageFilter;

import java.awt.image.BufferedImage;

/**
 * 黑白图
 * Created by isaac_gu on 2015/12/17.
 */
public class ToGray implements ImageFilter {

    @Override
    public BufferedImage apply(BufferedImage fromImage) {
        int width = fromImage.getWidth();
        int height = fromImage.getHeight();
        BufferedImage toImage = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        //
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int rgb = fromImage.getRGB(i, j);
                // 过滤
                int grayValue = filterRGB(i, j, rgb);
                toImage.setRGB(i, j, grayValue);
            }
        }
        return toImage;
    }

    private int filterRGB(int x, int y, int rgb) {
        int a = rgb & -16777216;
        int r = rgb >> 16 & 255;
        int g = rgb >> 8 & 255;
        int b = rgb & 255;
        rgb = r * 77 + g * 151 + b * 28 >> 8;
        return a | rgb << 16 | rgb << 8 | rgb;
    }
}
