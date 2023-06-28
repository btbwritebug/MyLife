package com.sie.governance.comm.infra.util.gitlab;

import com.sie.governance.comm.api.controller.SfBugController;
import com.sie.governance.comm.infra.constant.PublicConst;
import com.sie.governance.comm.model.core.gitlab.entity.GenerateEntity;
import com.sie.governance.comm.infra.util.StringUtil;
import com.sie.iot.component.exception.ApplicationRuntimeException;
import com.sie.iot.component.exception.GushenRuntimeException;
import com.siefw.common.bean.DatabaseBean;
import com.siefw.table2entity.core.bean.InputInfoBean;
import com.siefw.table2entity.core.hibernate.GenerateHibernateEntity;
import com.siefw.table2entity.core.mybatis.GenerateMybatisEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;

@Component
public class CodeGeneratorUtil implements InitializingBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(SfBugController.class);

    private static String systemTempPath;
    private static final String systemTempPathKey = "systemTempPath";
    private static final String randomStringKey = "randomString";
    public static final String packageSuffix = ".model.entity";
    private static Integer randomDirNameLength;

    @Value("${gitlab.randomDirNameLength}")
    private String randomDirNameLengthFromConfig;

    @Value("${upload-file.temp-path}")
    private String tempFilePathFromConfig;

    private static final String fileJson = "[{\"label\":\"代码目录\",\"children\":[{\"label\":\"${fieldCode}-${serviceNameEnglish}-mybatis\",\"children\":[{\"label\":\"src\",\"children\":[{\"label\":\"main\",\"children\":[{\"label\":\"java\",\"children\":[{\"label\":\"${packageName}\",\"children\":[{\"label\":\"infra\",\"children\":[{\"label\":\"constant\",\"children\":[]},{\"label\":\"util\",\"children\":[]}]},{\"label\":\"model\",\"children\":[{\"label\":\"dao\",\"children\":[]},{\"label\":\"entity\",\"children\":[]}]}]}]},{\"label\":\"resources\",\"children\":[]}]}]}]},{\"label\":\"${appCode}-${serviceNameEnglish}-feign-api\",\"children\":[{\"label\":\"src\",\"children\":[{\"label\":\"main\",\"children\":[{\"label\":\"java\",\"children\":[{\"label\":\"${packageName}\",\"children\":[{\"label\":\"feign\",\"children\":[{\"label\":\"client\",\"children\":[]},{\"label\":\"dto\",\"children\":[]}]}]}]},{\"label\":\"resources\",\"children\":[]}]}]}]},{\"label\":\"${appCode}-${serviceNameEnglish}-model-app\",\"children\":[{\"label\":\"src\",\"children\":[{\"label\":\"main\",\"children\":[{\"label\":\"java\",\"children\":[{\"label\":\"${packageName}\",\"children\":[{\"label\":\"api\",\"children\":[{\"label\":\"controller\",\"children\":[]}]},{\"label\":\"app\",\"children\":[{\"label\":\"event\",\"children\":[]},{\"label\":\"service\",\"children\":[]}]},{\"label\":\"infra\",\"children\":[{\"label\":\"annotation\",\"children\":[]},{\"label\":\"constant\",\"children\":[]},{\"label\":\"enumeration\",\"children\":[]},{\"label\":\"feign\",\"children\":[]},{\"label\":\"util\",\"children\":[]}]}]}]},{\"label\":\"resources\",\"children\":[]}]}]}]}]}]";
    private static final String appCodeKey = "appCode";
    private static final String fieldCodeKey = "fieldCode";
    private static final String serviceNameEnglishKey = "serviceNameEnglish";
    private static final String packageNameKey = "packageName";

    public static String getGenerateFileJson(String appCode, String serviceNameEnglish, String packageName) {
        String s = fileJson;
        s = StringUtil.replace(s, appCode, appCodeKey);
        s = StringUtil.replace(s, CodeGeneratorUtil.getFieldCode(appCode), fieldCodeKey);
        s = StringUtil.replace(s, serviceNameEnglish, serviceNameEnglishKey);
        s = StringUtil.replace(s, packageName, packageNameKey);
        return s;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        systemTempPath = tempFilePathFromConfig;
        randomDirNameLength = Integer.valueOf(randomDirNameLengthFromConfig);
    }


    public static void checkInfo(GenerateEntity generateEntity) {
        InputInfoBean inputInfoBean = new InputInfoBean();
        inputInfoBean.setDataBaseType(generateEntity.getDataBaseType());
        inputInfoBean.setHostName(generateEntity.getHostName());
        inputInfoBean.setPort(generateEntity.getPort());
        inputInfoBean.setSidName(generateEntity.getSidName());
        inputInfoBean.setUserName(generateEntity.getUserName());
        inputInfoBean.setPassword(generateEntity.getPassword());
        inputInfoBean.setTableName(generateEntity.getTableName());
        inputInfoBean.setGenerateROFlag(true);
        inputInfoBean.setHisTableFlag(false);

        setClassPath(inputInfoBean, generateEntity, "randomPath");

        GenerateHibernateEntity generateHibernateEntity = null;
        try {
            generateHibernateEntity = new GenerateHibernateEntity(inputInfoBean);
        } catch (Exception e) {
            throw new GushenRuntimeException("数据库连接信息错误");
        }

        generateHibernateEntity.checkInfo();

    }

    public static void setClassPath(InputInfoBean inputInfoBean, GenerateEntity generateEntity, String randomPath) {
        String modelAppSuffix = "-model-app" + File.separator + "src" + File.separator + "main" + File.separator + "java";
        String appClassPath = randomPath + File.separator + generateEntity.getAppCode() + PublicConst.middleLineChar + generateEntity.getServiceNameEnglish() + modelAppSuffix;
        String moduleClassPath = randomPath + File.separator + generateEntity.getFieldCode() + PublicConst.middleLineChar + generateEntity.getServiceNameEnglish() + modelAppSuffix;
        inputInfoBean.setAppClassPath(appClassPath);

        inputInfoBean.setModelClassPath(moduleClassPath);
    }


    public static void hibernateCode(GenerateEntity generateEntity, String sshAddressUrl,String userName) {

        String randomPath = cloneCode(sshAddressUrl, generateEntity.getBranchName());

        LOGGER.info("随机路径：" + randomPath);
        InputInfoBean inputInfoBean = new InputInfoBean();
        inputInfoBean.setDataBaseType(generateEntity.getDataBaseType());
        inputInfoBean.setHostName(generateEntity.getHostName());
        inputInfoBean.setPort(generateEntity.getPort());
        inputInfoBean.setSidName(generateEntity.getSidName());
        inputInfoBean.setUserName(generateEntity.getUserName());
        inputInfoBean.setPassword(generateEntity.getPassword());
        inputInfoBean.setTableName(generateEntity.getTableName());

        setClassPath(inputInfoBean, generateEntity, randomPath);
        LOGGER.info("最终生成代码的路径....." + inputInfoBean.getAppClassPath());
        String packageName = generateEntity.getPackageName();
        packageName = packageName + packageSuffix;
        inputInfoBean.setPackageName(packageName);
        inputInfoBean.setGenerateROFlag(false);
        inputInfoBean.setHisTableFlag(false);
        inputInfoBean.setIsFeignApi(true);
        inputInfoBean.setAuthor(userName);
        if(generateEntity.getDataBaseType().equalsIgnoreCase("oracle")){
            inputInfoBean.setSchema(generateEntity.getUserName());
        }
        GenerateHibernateEntity generateHibernateEntity = new GenerateHibernateEntity(inputInfoBean);

        generateHibernateEntity.generateJavaClass();

        commitAndPushCode(randomPath);
    }

    private static String cloneCode(String sshAddressUrl, String branchName) {
        if (StringUtils.isEmpty(branchName)) {
            branchName = "dev";
        }

        String randomPath = "${systemTempPath}" + File.separator + "gitlab" + File.separator + "${randomString}";
        String randomString = getRandomString(randomDirNameLength);

        randomPath = StringUtil.replace(randomPath, systemTempPath, systemTempPathKey);
        randomPath = StringUtil.replace(randomPath, randomString, randomStringKey);

        File file = new File(randomPath);
        boolean flag = file.mkdirs();
        if (!flag) {
            throw new ApplicationRuntimeException("cloneCode" + randomPath + "创建文件夹失败");
        }

        CmdUtil.cloneCode(randomPath, sshAddressUrl, branchName);

        return randomPath;

    }

    private static void commitAndPushCode(String randomPath) {
        //第一步:提交更改
        CmdUtil.commitAndPush(randomPath, "auto generate");

        //第二步:删除文件夹
//        if (StringUtils.isEmpty(randomPath)) {
//            return;
//        } else if (randomPath.contains("$") || randomPath.contains("{") || randomPath.contains("}")
//                || randomPath.equals("/")) {
//            return;
//        }
//
//        delFolder(randomPath);

    }

    private static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);//再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }

    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); //删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            File myFilePath = new File(filePath);
            myFilePath.delete(); //删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getRandomString(int length) {
        String alphabetsInUpperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        String alphabetsInLowerCase = "abcdefghijklmnopqrstuvwxyz";

        String numbers = "0123456789";


        String allCharacters = alphabetsInLowerCase + alphabetsInUpperCase + numbers;


        StringBuilder randomString = new StringBuilder();

        for (int i = 0; i < length; i++) {

            int randomIndex = (int) (Math.random() * allCharacters.length());

            randomString.append(allCharacters.charAt(randomIndex));

        }

        return randomString.toString();
    }

    public static String getFieldCode(String appCode) {

        if (StringUtils.isEmpty(appCode)) {
            throw new ApplicationRuntimeException("服务号不能为空");
        }

        return "1" + appCode.substring(1);

    }

}
