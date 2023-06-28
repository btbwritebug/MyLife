package com.sie.governance.comm.infra.util.gitlab;

import cn.hutool.extra.spring.SpringUtil;
import com.sie.governance.comm.infra.constant.PublicConst;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.TransportConfigCallback;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.io.*;

@Component
public class CmdUtil {
    public static final String deletePathCommand = "rm -rf ${path} ";
    public static final String pathKey = "path";
    private static final Logger LOGGER = LoggerFactory.getLogger(CmdUtil.class);

    public static void execCommand(String cmd,boolean waitFlag) {
        try {

            Process process = Runtime.getRuntime().exec(cmd);

            if(waitFlag){
                process.waitFor();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void cloneCode(String filePath,String sshAddressUrl,String branch){
        SshSessionFactory sshSessionFactory= SpringUtil.getBean("sshSessionFactory",SshSessionFactory.class);

        try {
            LOGGER.info("文件路径：" + filePath + "  ssh仓库地址："+ sshAddressUrl + "分支名称：" + branch);
            Git.cloneRepository().setURI(sshAddressUrl).setTransportConfigCallback( new TransportConfigCallback() {
                @Override
                public void configure( Transport transport ) {
                    SshTransport sshTransport = ( SshTransport )transport;
                    sshTransport.setSshSessionFactory( sshSessionFactory );
                }
            } ).setBranch(branch).setDirectory(new File(filePath)).call();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
    }


    /**
     * 提交并推送代码至远程服务器
     * @param filePath 提交文件路径(相对路径)
     * @param desc 提交描述
     * @return
     */
    public static boolean commitAndPush(String filePath,String desc){
        SshSessionFactory sshSessionFactory = SpringUtil.getBean("sshSessionFactory",SshSessionFactory.class);

        boolean commitAndPushFlag=true;
        try  {
            Git git = Git.open( new File(filePath) );
            //git add
            git.add().addFilepattern(PublicConst.spotChar).call();
            //提交
            git.commit().setMessage(desc).call();
            //推送到远程
            git.push().setTransportConfigCallback( new TransportConfigCallback() {
                @Override
                public void configure( Transport transport ) {
                    SshTransport sshTransport = ( SshTransport )transport;
                    sshTransport.setSshSessionFactory( sshSessionFactory );
                }
            } ).call();
            LOGGER.info("Commit And Push file " + filePath + " to repository at " + git.getRepository().getDirectory());
        }catch (Exception e) {
            e.printStackTrace();
            commitAndPushFlag=false;
            LOGGER.error("Commit And Push error! \n"+e.getMessage());
        }
        return commitAndPushFlag;

    }



    public static void main(String[] args) {
        try {

            commitAndPush("D:\\gitlab\\vPiHc0Q4A3","auto 1");

//            cloneCode("D:\\gitlab\\vPiHc0Q4A3","git@124.71.23.77:test-generate/test-generate.git","dev");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
