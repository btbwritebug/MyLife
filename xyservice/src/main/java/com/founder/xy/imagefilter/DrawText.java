package com.founder.xy.imagefilter;

import net.coobird.thumbnailator.builders.BufferedImageBuilder;
import net.coobird.thumbnailator.filters.ImageFilter;
import net.coobird.thumbnailator.geometry.Position;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;


public class DrawText implements ImageFilter {

    private final Position position;

    private final Font font;

    private final Color color;

    private final float opacity;
    private final String content;

    public DrawText(String content, Position position, float opacity, Font font, Color color) {
        this.font = font;
        if (position == null) {
            throw new NullPointerException("Position is null.");
        } else if (font == null) {
            throw new NullPointerException("font is null.");
        } else if (content == null) {
            throw new NullPointerException("content is null.");
        }else if (color == null) {
            throw new NullPointerException("color is null.");
        }
        else if (opacity <= 1.0F && opacity >= 0.0F) {
            this.position = position;
            this.opacity = opacity;
            this.content = content;
            this.color = color;
        } else {
            throw new IllegalArgumentException("Opacity is out of range of between 0.0f and 1.0f.");
        }
    }

    @Override
    public BufferedImage apply(BufferedImage image) {
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();
        int imageType = image.getType();
        BufferedImage createdImage = (new BufferedImageBuilder(imageWidth, imageHeight, imageType)).build();

        Graphics2D graphics = createdImage.createGraphics();
        graphics.setFont(this.font);
        graphics.setColor(color);

        int fontWidth = getWatermarkLength(this.content, graphics);
        int fontHeight = font.getSize();

        Point point = this.position.calculate(imageWidth, imageHeight, fontWidth, 0, 5, 5, fontHeight, 5);

        graphics.drawImage(image, 0, 0, (ImageObserver) null);
        graphics.setComposite(AlphaComposite.getInstance(3, this.opacity));
        graphics.drawString(this.content, point.x, point.y);
        graphics.dispose();
        return createdImage;
    }

    public static int getWatermarkLength(String content, Graphics2D graphics) {
        return graphics.getFontMetrics(graphics.getFont()).charsWidth(content.toCharArray(), 0, content.length());
    }
}
