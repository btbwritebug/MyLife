package com.founder.xy.controller;

import com.founder.xy.common.ImageUtils;
import com.founder.xy.model.Image;
import com.founder.xy.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class ImageController {
    private static final long serialVersionUID = -7907695207451579355L;

    @Autowired
    ImageService imageService;

    @PostMapping("/servlet/ImageServlet")
    public void imageHandle(HttpServletRequest request, HttpServletResponse response) {
        String jsonStr = request.getParameter("json");
        log.info("[Image.service] param: " + jsonStr);

        //把接收的数据转成image对象
        Image image = initImage(request, jsonStr);
        // 返回的json数据
        JSONObject json = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONObject element;
        try {
            List<String> commandList = image.getCommands();
            // 文件的全路径
            if (commandList != null && !commandList.isEmpty()) {
                for (String command : commandList) {
                    // 如果没有设置目标路径，初始化一个
                    checkDestName(image, command);

                    String resultArray = "";
                    // 判断该走哪一个命令
                    switch (command) {
                        case "zoom":
                            imageService.zoomImage(image.getWholePath(), image.getDestName(), image.getImgType(),
                                    image.getSize());
                            break;
                        case "zsize":
                            imageService.zoomImage(image.getWholePath(), image.getDestName(), image.getImgType(),
                                    image.getImgWidth(), image.getImgHeight(), image.getKeepRadio());
                            break;
                        case "rotate":
                            imageService.rotateImage(image.getWholePath(), image.getDestName(), image.getImgType(),
                                    image.getRotate());
                            break;
                        case "cut":
                            ImageUtils.cutImage(image.getWholePath(), image.getDestName(), image.getImgType(),
                                    image.getStartX(), image.getStartY(), image.getEndX(),
                                    image.getEndY(), image.getImgWidth(), image.getImgHeight());
                            break;
                        case "watermark":
                            imageService.watermark(image.getWholePath(), image.getWatermark(), image.getPosition(),
                                    image.getTransparency(), image.getDestName(), image.getImgType());
                            break;
                        case "batchwm":
                            resultArray = imageService.watermarkList(image.getImgList(), image.getWatermark(), image.getPosition(),
                                    image.getTransparency());
                            break;
                        case "gray":
                            imageService.toGray(image.getWholePath(), image.getDestName(), image.getImgType());
                            break;
                        case "drawtext":
                            resultArray = imageService.drawText(image.getWholePath(), image.getDestName(), image.getImgType(), image.getContent(), image.getPosition()
                                    , image.getTransparency(), image.getFontName(), image.getFontStyle(), image.getFontSize(), image.getColor(), image.getIsSameSetting(), image.getImgList());
                            break;
                        default:
                            break;
                    }
                    element = new JSONObject();
                    element.put("command", command);
                    element.put("destPath", image.getDestName());
                    element.put("resultImgArray", resultArray);
                    jsonArray.add(element);

                }

                json.put("status", "success");
                json.put("result", jsonArray);
            }
        } catch (Exception e) {
            log.error(image.toString(), e);
            json.put("status", "failure");
            json.put("image", image.toString());
            json.put("exception", e.getMessage());
        } finally {
            log.info("json: " + json);
            PrintWriter out = null;
            try {
                response.setContentType("application/json; charset=UTF-8");
                out = response.getWriter();
                out.write(json.toString());
                out.flush();
            } catch (IOException e) {
                log.error("out error:", e);
            } finally {
                if (out != null) {
                    out.close();
                }
            }
        }
    }

    private Image initImage(HttpServletRequest request, String jsonStr) {
        Image image;
        if (jsonStr != null && !"".equals(jsonStr)) {
            JSONObject json = JSONObject.fromObject(jsonStr);
            image = (Image) JSONObject.toBean(json, Image.class);

            List<String> list = new ArrayList<>();
            list.add(json.getString("command"));

            image.setCommands(list);
        } else {
            image = new Image(request);
        }
        return image;
    }

    /**
     * 如果没有设置目标路径，初始化一个
     *
     * @param image
     * @param command
     */

    private void checkDestName(Image image, String command) {
        String destName = image.getDestName();
        if (destName == null || destName.isEmpty()) {
            // 路径的位置
            int pathPosition = image.getWholePath().lastIndexOf('/') == -1 ? image
                    .getWholePath().lastIndexOf('\\') + 1 : image.getWholePath()
                    .lastIndexOf('/') + 1;
            // 原文件名
            destName = image.getWholePath().substring(pathPosition,
                    image.getWholePath().length());
            // 把文件名后面添加命令结尾
            destName = destName.substring(0, destName.lastIndexOf('.')) + "_" + command + "_" + System.currentTimeMillis()
                    + destName.substring(destName.lastIndexOf('.'));
            destName = image.getWholePath().substring(0, pathPosition) + destName;
            image.setDestName(destName);
        } else {
            File dir = new File(destName.substring(0, destName.lastIndexOf(File.separator)));
            if (!dir.exists()) {
                if (!dir.mkdirs())
                    log.error("创建目录失败 " + dir);
            }
        }
        image.resetImageType();
    }
}
