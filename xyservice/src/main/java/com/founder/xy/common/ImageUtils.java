package com.founder.xy.common;


import com.founder.xy.imagefilter.DrawText;
import com.founder.xy.imagefilter.ToGray;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.nutz.img.Images;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageUtils {

    private ImageUtils() {
    	throw new IllegalStateException("Utility class");
	}

	/**
     * @param destName 图片输出路径
     * @param imgType  图片类型
     * @param size     图片缩放尺寸
     * @throws Exception
     * @Description: 缩放图片
     * @author: sunj
     * @date: 2015-6-17 下午6:07:58
     */
    public static void zoomImage(String imagePath, String destName, String imgType, float size) {
        try {
            Thumbnails.of(Images.read(new File(imagePath))).scale((double) size).outputFormat(imgType).toFile(destName);
        } catch (Exception var5) {
            throw new IllegalStateException("ImageUtils:Zoom Image Error！！" + var5);
        }
    }

    public static void zoomImage(String imgPath, String destName, String imgType, int imgWidth,
                                 int imgHeight, boolean keepRatio) {
        try {
            Thumbnails.of(Images.read(new File(imgPath))).size(imgWidth, imgHeight)
                    .keepAspectRatio(keepRatio).outputFormat(imgType).outputQuality(1.0F)
                    .toFile(destName);
        } catch (Exception var5) {
            throw new IllegalStateException("ImageUtils:Zoom Image Error！！" + var5);
        }
    }

    /**
     * @param imagePath
     * @param destName
     * @param fileType
     * @param startX1
     * @param startY1
     * @param x1
     * @param y1
     * @throws Exception
     */
    public static void cutImage(String imagePath, String destName, String fileType, double startX1,
                                double startY1, double x1, double y1, Integer resizedX, Integer resizedY) {

        BufferedImage image = Images.read(new File(imagePath));
        if(image == null){
            //再尝试一次 有可能图片已经存在 只是容器中未更新
            image = Images.read(new File(imagePath));
        }
        int startX , startY ,x ,y ;
        if(startX1<=1.1 && startY1 <=1.1 && x1<=1.1 && y1<=1.1) {
            int width = image.getWidth();
            int height = image.getHeight();
             startX = (int) (width * (startX1));
            startX = Math.min(startX, width);
             startY = (int) (height * startY1);
             startY = Math.min(startY, height);
             x = (int) (width * x1);
             x = Math.min(x, width);
             y = (int) (height * y1);
             y = Math.min(y, height);
        }else {
             startX = (int) (startX1);
             startY = (int) (startY1);
             x = (int) (x1);
             y = (int) (y1);
        }
        resizedX = resizedX == null ? x : resizedX == 0 ? x : resizedX;
        resizedY = resizedY == null ? y : resizedY == 0 ? y : resizedY;
        try {
            Thumbnails.of(image).sourceRegion(startX, startY, x, y)
                    .size(resizedX, resizedY).keepAspectRatio(true).outputFormat(fileType).outputQuality(1.0F)
                    .toFile(destName);
        } catch (IOException exception) {
            throw new IllegalStateException("ImageUtils:Cut Image Error！", exception);
        }
    }


    /**
     * 加水印
     *
     * @param img          图片输入流
     * @param position     水印位置
     * @param transparency 水印透明度
     * @param destName     图片输出路径
     * @param fileType     图片类型
     * @throws Exception
     * @Description: 水印
     * @author: sunj
     * @date: 2015-6-17 下午6:11:47
     */
    public static void watermark(String img, String watermarkPath, int position,
                                 float transparency, String destName, String fileType) {
        Positions pos = getPositions(position);
        try {
            BufferedImage watermarkImage = ImageIO.read(new File(watermarkPath));
            Thumbnails.of(new File(img)).watermark(pos, watermarkImage, transparency).scale(1)
                    .outputFormat(fileType).toFile(destName);
        } catch (IOException exception) {
            throw new IllegalStateException("ImageUtils:Add watermark Image Error！", exception);
        }
    }

    private static Positions getPositions(int position) {
        Positions pos;
        switch (position) {
            case 1:
                pos = Positions.TOP_LEFT;
                break;
            case 2:
                pos = Positions.TOP_CENTER;
                break;
            case 3:
                pos = Positions.TOP_RIGHT;
                break;
            case 4:
                pos = Positions.CENTER_LEFT;
                break;
            case 5:
                pos = Positions.CENTER;
                break;
            case 6:
                pos = Positions.CENTER_RIGHT;
                break;
            case 7:
                pos = Positions.BOTTOM_LEFT;
                break;
            case 8:
                pos = Positions.BOTTOM_CENTER;
                break;
            case 9:
                pos = Positions.BOTTOM_RIGHT;
                break;
            default:
                pos = Positions.BOTTOM_RIGHT;
        }
        return pos;
    }

    /**
     * 旋转图片（最好是90度的整倍数，其他角度会出现黑框）
     *
     * @param img      图片输入流
     * @param destName 图片输出路径
     * @param fileType 图片类型
     * @param rotate   旋转角度
     * @throws Exception
     * @Description: 旋转图片（最好是90度的整倍数，其他角度会出现黑框）
     * @author: sunj
     * @date: 2015-6-17 下午6:13:00
     */
    public static void rotateImage(String img, String destName, String fileType, double rotate)
            {
        try {
            Thumbnails.of(Images.read(new File(img))).scale(1).rotate(rotate).outputFormat(fileType).toFile(destName);
        } catch (IOException exception) {
            throw new IllegalStateException("ImageUtils:Rotate Image Error！", exception);
        }
    }

    /**
     * 转黑白图片
     *
     * @param destName
     * @param imgType
     * @throws Exception
     */
    public static void toGray(String imagePath, String destName, String imgType) {
        try {
            Thumbnails.of(Images.read(new File(imagePath))).scale(1).addFilter(new ToGray()).outputFormat(imgType).toFile(destName);
        } catch (IOException exception) {
            throw new IllegalStateException("ImageUtils:Add toGray Image Error！！", exception);
        }
    }

    /**
     * 打印文字水印图片
     *
     * @param destName
     * @param fileType
     * @param content
     * @param position
     * @param transparency
     * @param font
     * @param color
     * @throws Exception
     */
    public static void drawText(String imagePath, String destName, String fileType,
           String content, int position, float transparency, Font font, Color color) {
        Positions pos = getPositions(position);
        try {
            Thumbnails.of(imagePath).scale(1)
                    .addFilter(new DrawText(content, pos, transparency, font, color))
                    .outputFormat(fileType).toFile(destName);
        } catch (IOException exception) {
            throw new IllegalStateException("ImageUtils:Add drawText Image Error！！", exception);
        }
    }

    public static Color getColor(String color) {
        if(color ==null || color.trim().equals("")){
            return null;
        }
        if (color.charAt(0) == '#') {
            color = color.substring(1);
        }
        if (color.length() != 6) {
            return null;
        }

        try {
            int r = Integer.parseInt(color.substring(0, 2), 16);
            int g = Integer.parseInt(color.substring(2, 4), 16);
            int b = Integer.parseInt(color.substring(4), 16);
            return new Color(r, g, b);

        } catch (NumberFormatException nfe) {
            return null;
        }

    }

    /**
     * 转黑白照片 - 使用的是 java image filter 滤镜
     *
     * @param imageFile
     * @param destName
     * @throws IOException
    //private static GrayscaleFilter filter = new GrayscaleFilter();
    /*public static void toGrayPro(String imageFile, String destName, String imgType) throws IOException {
        // 定义过滤器

        BufferedImage fromImage = ImageIO.read(new File(imageFile));
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
        //
        ImageIO.write(toImage, imgType, new File(destName));
    }

    public static int filterRGB(int x, int y, int rgb) {
        int a = rgb & -16777216;
        int r = rgb >> 16 & 255;
        int g = rgb >> 8 & 255;
        int b = rgb & 255;
        rgb = r * 77 + g * 151 + b * 28 >> 8;
        return a | rgb << 16 | rgb << 8 | rgb;
    }*/
    /**
     * 打印文字水印图片
     *
     * @param pressText --文字
     * @param targetImg --
     *                  目标图片
     * @param fontName  --
     *                  字体名
     * @param fontStyle -- 字体样式
     * @param color     -- 字体颜色
     * @param fontSize  -- 字体大小
     * @param x         -- 偏移量
     * @param y
    /*public static void drawText(String pressText, String targetImg, String resultImg,
                                String fontName, int fontStyle, Color color, int fontSize, Positions position) {
        try {
            File _file = new File(targetImg);
            BufferedImage src = ImageIO.read(_file);
            int wideth = src.getWidth(null);
            int height = src.getHeight(null);
            BufferedImage image = new BufferedImage(wideth, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            g.drawImage(src, 0, 0, wideth, height, null);

            g.setColor(color);
            g.setFont(new Font(fontName, fontStyle, fontSize));

            Point point = position.calculate(wideth, height, -getWatermarkLength(pressText, g), fontSize, 0, 0, 0, 0);
            //g.drawString(pressText, wideth - fontSize - x, height - fontSize / 2 - y);
            g.drawString(pressText, point.x, point.y);
            g.dispose();
            FileOutputStream out = new FileOutputStream(resultImg);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            encoder.encode(image);
            out.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void mark(String srcImgPath, String outImgPath, Color markContentColor, String waterMarkContent) {
        try {
            // 读取原图片信息
            File srcImgFile = new File(srcImgPath);
            BufferedImage srcImg = ImageIO.read(srcImgFile);
            int srcImgWidth = srcImg.getWidth(null);
            int srcImgHeight = srcImg.getHeight(null);
            // 加水印
            BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufImg.createGraphics();
            g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);
            //Font font = new Font("Courier New", Font.PLAIN, 12);
            Font font = new Font("宋体", Font.PLAIN, 50);
            g.setColor(markContentColor); //根据图片的背景设置水印颜色

            g.setFont(font);
            int x = srcImgWidth - getWatermarkLength(waterMarkContent, g) - 13;
            int y = srcImgHeight - 13;

            Positions p = Positions.BOTTOM_RIGHT;
            Point point = p.calculate(srcImgWidth, srcImgHeight, getWatermarkLength(waterMarkContent, g) + 10, 10, 0, 0, 0, 0);

            g.drawString(waterMarkContent, point.x, point.y);
            g.dispose();
            // 输出图片
            FileOutputStream outImgStream = new FileOutputStream(outImgPath);
            ImageIO.write(bufImg, "jpg", outImgStream);
            outImgStream.flush();
            outImgStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    *//**
     * 获取水印文字总长度
     *
     * @param waterMarkContent 水印的文字
     * @param g
     * @return 水印文字总长度
    public static int getWatermarkLength(String waterMarkContent, Graphics2D g) {
        return g.getFontMetrics(g.getFont()).charsWidth(waterMarkContent.toCharArray(), 0, waterMarkContent.length());
    }


    public static void drawTextPro(String text, String imgPath, String resultImgPath,
                                   String fontName, int fontStyle, int fontSize, Color color, Positions position, float opacity) {
        try {
            //获得目标对象
            File _file = new File(imgPath);
            BufferedImage targetImg = ImageIO.read(_file);
            int targetImgWidth = targetImg.getWidth();
            int targetImgHeight = targetImg.getHeight();
            int targetImgType = targetImg.getType();

            //创建结果对象
            BufferedImage resultImg = (new BufferedImageBuilder(targetImgWidth, targetImgHeight, targetImgType)).build();

            //构图
            Graphics2D graphics = resultImg.createGraphics();
            graphics.drawImage(targetImg, 0, 0, (ImageObserver) null);

            //设置字体
            Font font = new Font(fontName, fontStyle, fontSize);
            int fontWidth = getWatermarkLength(text, graphics);
            int fontHeight = fontSize;
            graphics.setFont(font);

            //计算位置
            Point point = position.calculate(targetImgWidth, targetImgHeight, fontWidth, fontHeight, 0, 0, 0, 0);

            //设置透明度
            graphics.setComposite(AlphaComposite.getInstance(3, opacity));

            //写字
            graphics.drawString(text, point.x, point.y);
            graphics.dispose();
        } catch (Exception e) {
        }
    }

    /*public static void toGray(InputStream img, String destName) throws IOException {
        FileOutputStream fos = null;
        try {
            BufferedImage image = ImageIO.read(img);
            int srcH = image.getHeight(null);
            int srcW = image.getWidth(null);
            BufferedImage bufferedImage = new BufferedImage(srcW, srcH, BufferedImage.TYPE_3BYTE_BGR);
            bufferedImage.getGraphics().drawImage(image, 0, 0, srcW, srcH, null);
            bufferedImage = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null).filter(bufferedImage, null);
            fos = new FileOutputStream(destName);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(fos);
            encoder.encode(bufferedImage);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("图片转换出错！", e);
        } finally {
            if (fos != null)
                fos.close();
        }
    }*/

}