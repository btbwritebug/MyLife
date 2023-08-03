package com.btb.util;

import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.Properties;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.JschConfigSessionFactory;
import org.eclipse.jgit.transport.OpenSshConfig;
import org.eclipse.jgit.transport.SshSessionFactory;
import org.eclipse.jgit.transport.SshTransport;
import org.eclipse.jgit.util.FS;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.springframework.stereotype.Component;
/**
 * git操作工具类
 *
 * @author B
 */
@Component
public class GitUtil {

    public static void main(String[] args) {
        String localCodeDir = "/Users/baotingbo/Desktop/sie-gushen-dynamicformcenter-v1.0"; //本地文件夹
        String remoteRepoPath = "ssh://git@git.sieiot.com:6022/gushen-LCDP/sie-gushen-dynamicformcenter-v1.0.git"; //git地址
        String keyPath = "/Users/baotingbo/IdeaProjects/MyLife/src/main/resources/id_rsa";  //私钥文件
        //String privateKey = "-----BEGIN RSA PRIVATE KEY-----&&MIIJJwIBAAKCAgEA4Dcsf3V2vXtLpvTSjUd/+75kJdcWb9qGNBLU3X05vTiYi4u/&&hn1x6DLJi+NmxDS1FHgYTiczzilbP5+P+TWj5r/MQ3ij7ZmdXK/9hPa+iN27j0Wy&&+M2pE0CtbwsZPyzTpTpaU5FnW+LYQVe3dhzrhEOoWI5cxv0QjJL7xDXb4Nwc+OOi&&ID/IMkIFtm+BFUZBNDvWiLk/vWVc2oR/XE0+Trd5jc9jvIryy6KGXkWtZ3Btjhmb&&tUQL0y75/ubVcPZRugVbk6XmirzuVt23tDtfG3xG38ZEKvqWXcydQ/uIcdacrp9t&&2pvNxKj8t7dcgUI/Vr/hbNIB/0RgK/v9lFk2+ZP8WyFSVsCVrvr30j8PZLm4Q/uk&&ael6ZNXadlWDyVC8V3UX1eSn2XV28l8tDrpkXTi9JXTMWg4O3S81L2fRjHIrYbUe&&Uyp4oqIEbpiNkzJMj0Pp6HpOjxVh+x62LU4FSBjIgoVzvugBbhQjUarVCa3TZ82X&&6WHAI7YtPFiMVcdCTdCcMvRpErOp3eWpp/wXxR+ZASeZYW3Red7SJnWmHFJ51Jbf&&Aug5A0ExRSU6nOCBTVqApV3fjdsXxurLm/8fugVXuEaNU82479LVS9WUZYAmORaL&&WbFlZlcSIqmhTDtmavD1XdJMIHnc2c5UxmJltQ70TJYUhsrvwGcANr+TjdcCAwEA&&AQKCAgBdKo/y5nxQf8sPtOtIiU1E7ffvQVCxabwaJzkMT9yOI9Y/JfKPj9ClC3V0&&CynInZ4gw0ZWMzPzlnu0FDle9EsGtuvJnXeAYzRgL0DI3z6M4JXvEqcre0mmFln6&&qkHzXk0TewvNbb3lEH2LqUcybRHwA60QHxeuF7mor+qtvTOAW/Yt9WiIvfgR6h01&&kV+x11+AtNyQM2mo6VWL90J0uCQL0XFQ27w1vAbnVsE+qvVC5hSf10Q8Leo79mkq&&4gAO0EtlYJSM5YLGY3sl3TtYSNNcOGe61Ee/FCEnGL7AYORxaRvx841SKSt3bECW&&ljp5+20WPuUMsrbhuye7HxpfajLM+t/6pJR9fRA2nKusffAAu/IQR6XfAZo32h+k&&pFQcKCZk0RrKjKPxEcsSqJguo7BxNhChWE5CUd4e4JkfV1v8OdmMbz0DgB3AOuXE&&ZyvocBfmdlUtGsHt1ige0Hz8sJU5XiYCZmX9GBr3BMqyNrGtTz6qPDNGRk5Z3fBF&&RgBZBXrqsAfYwr1gYziPBmGqNk7VY8LkIgvxuAHFfUIxT5aB5sW6164k4WR2dlw7&&dNF7hUuSoDfLrJoJJtSHO32k1yo3Q3xVfRKDE1NebodW+VURiYczugLfu/xPdpEH&&NdLVabU0bNOCwQSKgpt6iPnYPGBv+I2RDrB0jUhDE3ebRjbXAQKCAQEA9r5ity81&&JRgL5ytTZLIDCkXtvoSnXD7v/5wRa4YLr4hE9fqEvKHm7KNQQayVi2EYzinODtB5&&OvVuem1ovPoQDDoAe6GEs9Uy1aCIIU7NI9PUYZ8uwUrReZwgvmebkrwUHi79eUkh&&h7lDyf1yVDnme4KhQ3BO1mzIxaxGLehw38p8a0NnAp5cOLYq6gK2aCvx74BpoLRL&&l1hYuPUk8cc2aq38Th65nlgevUF75xX/bsJ7HY2FsSdMcmDsNT0DMX89QopxKBj4&&VsDQafzFKFQ7t1H4ff2oAr8GqObL/O0A3LWful0IvyRTYBEEWHOn0Lv8yKp8MGjm&&xCS2GEfUpTKCywKCAQEA6KBwGV/gw/kXEt/2fXGMlzRAo38O4+7O5HZb2h2flSWC&&yITXQNrziI/oBaKMeaAZo2nonJZALyWb13M+12hMXLapCd5lzd1+AZRW2g//2iAT&&fiizQXJc0Wnnro9sTVE7UT4QgzadZN5vLifDDEQpgVG9Qy8Z2j9/0zqJ9j6GsSHu&&eQbj6SkkRl/S4VLbsXeY9E4QaUNbl2sDrSlHI4iyyot0InZQ9sN9FMHkwhUelXoC&&QkQ76SGjxRavPXukVvYWDTi8zXVCJAgzPvoqnmWp8rYyJSBA4m22I8lKk8rAHx4C&&i6c6G8+NQlnZVEvmaDfkj7ZSFj0xWFiS68yzWHqjpQKCAQAEPUUo7JC3MHCT6jSm&&mEBhHs505YaSmSo6dA4AkO8GdwroRwOc0hNLoYqxdiujl2l7sVAvBPnwP2NEiziv&&doRsEVsMxLuo81LKRNXR7K6tnOgbPUbqX0S2GC6f1tvfa4fU9O259b2zvUUi5U3f&&kJcbLCVlKTrRiFDyxID4LPQzhCEbHq9YZiwjdU3Lc3x60bZ4bPZjonViBkyP8PZP&&iSKwNokNNo3t4Im2qrdcQw5+cqz21wWjA4/Wrz+fsoqpgQwmZoaHL9akdu+dwpAI&&n1nfTnM8hu0qDuooYL7Ee7X2FNDHvO13i0I7dk9izanmdjRjpjo4ArVkTVUrmaBX&rkR9AoIBABd3T7ePj2nVb7lstD21PZxnHfogMA1YIrCzK9LV2CpAbW/yzo/xRk3J&&Tnt7CTafqtZsMZXSJklbPtw9+OHb2JAlsOmY8KYSqMpZ/2Th5kpHoA/C5Uo90hUh&&3MgkGaW3AwfP/fRuhvQM6CQ8yJUtLw6ngzMKxk3Yx/kz14Tqi8XEiXzV8BmuF8Wt&&luU56YV+bgR+93LW459joSxPlKiF+psp9B4h10r9L1nvsTbO0JX9h8rNfUmoJhAU&&mLrYBRzsmA7fYVRMds6RsTGyJKhz0u/z5x0Not30eUIk5QwBkN3StikGRntVtYcy&&7xRsz6JFuR2yI5ewtpCV5sQ4kwLi5C0CggEAXMc3ura0lWWoy1wxVc6ShwNpwKu7&&zlS0AtGqG+3oG6wKDMy26Pq/N0e57PuOGTvKdmdX/fVvPxREbOGbuSrUBIumwI9o&&BjiJimwT5gj/IOUtrfeZA2jSVwFzLTpeYenaRacMpYT0AFD00JUT0qgDcR+7rtjS&&0kd6yu+Ch9arvyhlX+LeDjhNaVGtKd4jLkMoeQMOvEwk1o3jwsAK5EX00QvsnJ3r&&czRzxx0NrTOt+QKprPYYO25OP8sRbVcdy6W3dXJQETdhGpdzeJzGdxQ9eUSEwfoP&&hTAeURg8a5jlFLOsoTEdKLh1f2NSI/2Mm23d1b7f5qRxruwJWtlfmFXXOg==&&-----END RSA PRIVATE KEY-----";
        gitClone(remoteRepoPath, localCodeDir, keyPath);
    }

    //localRepoPath 为本地文件夹
    //keyPath 私钥文件 path
    //remoteRepoPath 为 ssh git远端仓库地址
    protected static void gitClone(String remoteRepoPath, String localRepoPath, String keyPath) {
        //ssh session的工厂,用来创建密匙连接
        SshSessionFactory sshSessionFactory = new JschConfigSessionFactory() {
            @Override
            protected void configure(OpenSshConfig.Host host, Session session) {
                session.setConfig("StrictHostKeyChecking", "no");
            }
            @Override
            protected JSch createDefaultJSch(FS fs) throws JSchException {
                JSch sch = super.createDefaultJSch(fs);
                sch.addIdentity(keyPath); //添加私钥文件
                return sch;
            }
        };
        //克隆代码库命令
        CloneCommand cloneCommand = Git.cloneRepository();
        Git git = null;
        File tempFile = null;
        try {
            git = cloneCommand.setURI(remoteRepoPath) //设置远程URI
                    .setTransportConfigCallback(transport -> {
                        SshTransport sshTransport = (SshTransport) transport;
                        sshTransport.setSshSessionFactory(sshSessionFactory);
                    })
                    .setDirectory(new File(localRepoPath)) //设置下载存放路径
                    .call();
            List<Ref> branches = git.branchList().setListMode(ListBranchCommand.ListMode.REMOTE).call();
            for (Ref branch : branches) {
                System.out.println(branch.getName());
            }
            // 拉取最新代码
            git.pull().call();

            // 切换到指定分支
            git.branchCreate().setName("feature/test").setStartPoint("origin/feature/test").call();
            git.checkout().setName("feature/test").call();

            // 在本地仓库中生成临时测试文件
            tempFile = new File(localRepoPath + "/temp.txt");
            FileWriter writer = new FileWriter(tempFile);
            writer.write("This is a temporary file for testing.");
            writer.close();
            // 将临时文件添加到暂存区
            git.add().addFilepattern("temp.txt").call();

            // 提交更改
            git.commit().setMessage("Bao test first commit!!!!!!!!").call();

            // 推送到远程仓库
            git.push().call();

            System.out.println("success");
        } catch (Exception e) {
            System.out.println("fail");
            e.printStackTrace();
        } finally {
            if (git != null) {
                git.close();
            }
            if (tempFile != null) {
                tempFile.delete();
            }
        }
    }
}