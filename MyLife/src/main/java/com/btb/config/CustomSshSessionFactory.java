package com.btb.config;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import lombok.SneakyThrows;
import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.transport.JschConfigSessionFactory;
import org.eclipse.jgit.transport.OpenSshConfig;
import org.eclipse.jgit.util.FS;

public class CustomSshSessionFactory extends JschConfigSessionFactory {

    @Override
    protected void configure(OpenSshConfig.Host host, Session session) {

    }

    @SneakyThrows
    @Override
    protected JSch createDefaultJSch(FS fs) throws JSchException {
        JSch jsch = super.createDefaultJSch(fs);

        try {
            // 读取私钥文件内容
            String privateKey = "-----BEGIN OPENSSH PRIVATE KEY-----\n" +
                    "ZtorJhxLf1n03Qw2k7qcMMDhBcgtYgT1WhS2weZOlf4oYaUcVCd86NXjYyXT9GiU0xPjbr\n" +
                    "G5Autz54j+avsf5iHlghlW4L/AkxQnB9kpTMP+IaORRFu/GELwg3MuLcBlZg075Zh1ZxzA\n" +
                    "Dera9ZTrMp4ppMEYGf9o1v6euaoLiVvXfuu5SXAUjfG3YsYKrQ94iXAZGogy/HPEoIqn5m\n" +
                    "+QY+2H6E88o7bQ27BWzeuOqrsqqP4+wU+UsuUT+jJeiOmydgoWDmswAJC7SRkLbBVEO404\n" +
                    "Y1NhjmtaiZ8iBCG4j1qKGowF2DhBIDvBZxqfRC7NCLia5pqWkkwkikA8TMTUSeeHS5sTJa\n" +
                    "8vzvL9c2eRRwi9QDaEfVTMcGndikBj0LPMNI2mXOXiHti2IZttH2ud9Z12B48cLwLd41NC\n" +
                    "0++9uO7g4VIvqTx1pFF8vq5K/cE2+NCWW43ykRF1KZDiyIX49gN12bB8twM96egc6vBdUi\n" +
                    "8F3sl00BaX8kX120GIEXeuttdQktl7V8EgceNBQ0XUIXMpldImGtxfed7fLamKUfvdOdNu\n" +
                    "yStalRn649AZBav7PcLh+2JqxSWhjD5ieWctrV1C+y8txk2b7WRFlUv8rfYWw5yDmhL2N1\n" +
                    "fQ3kfHucujJw6L8JZS63mcHMVwrSQPdN8YtD6yQe+B0OBIQzmdL163mTZ0tHc6C+pFTTW9\n" +
                    "K3jTxsLwMB6Io7sQB/WR1F27sQmiLQxu6ZZWWBsTfsC7UQUSQ4ShNaQFaI916gDjrv1WKJ\n" +
                    "+DZeVvtR8azEN6WQbZsXVQEOMCoSkQ8Q1KdNO91vGdRetwxs+4/jUt+JmK11RKec6QrFt3\n" +
                    "uxItV1Qifi76tmwKmymWzi6rpbsJE31npdj+mGiKUaSwP13/al5uTqZQk38gHowGhi8WWs\n" +
                    "/2qO/EgvY/WLLJgMYuTcIQarxlUyC/bpFOpBr1hOEJJzdeQTwKX/HkRT5GYMZifIz3fp1T\n" +
                    "UZBpEPAQn7+W7ZG4fZHt/4IrnH9w6/rTtcwKKanyXc8N42mc5+LH2Qw9dUWOqFhkHqCJhV\n" +
                    "fs83kEK0cxYl2TlRJ69MFpGiB6tHr4h+3fRQE29FVqQMJyCEpA/3BqfRDbBeTg+5Ann19E\n" +
                    "kdgJzsmcdCBSvRkSvti/Vs1x2KftpNXRFMXL2os32M3+Ui6WZEkyAq4yBzD8IuK16Yu+EM\n" +
                    "eWwK/H8IZEcXJJJiB0rOZBI7QA7i62xpbSALKRhMNFgkJ0zcvrBZiZRk6LZ5xb0y1p+Ht+\n" +
                    "uGuxIl2tzgSP670y2S9whl8In1h1daYtY38ncj/SB+y6GJz0myOhV8xgVnN/wYaTRbVLeX\n" +
                    "jBRVU/lBBl0taneGDwWe++b8U6lE59GRk+i+xQ0EatHdVywcQHnyt9lcqCsOJaTvNXprcD\n" +
                    "uGObNnzK91oQUCqSnU0luMN+Y0Jsx1LIGsxEVbH/k11mPcTO3mtHiXYieGJ4AhU6mzf953\n" +
                    "IXk/0oSkLdQaCLBMA29xRzgpUK/vxbsNaJ/0tOMN+emfuCgfY8W32fUqib3f31hOQ+QJfd\n" +
                    "VsP4jT3+iRlgKz0fSyBK+LlPsHRSRL/1Duv3P5iSCYifMu7nArCsI08jfPfYatezOPLlnY\n" +
                    "EZenHXnAuyHKr7BryY7NxnpdJC29KkmyvLX1rjUntdRra6h7vKVjWcOu7XSypLZwoq/HCe\n" +
                    "4X9jKE5m34qiXv8P7M3ElHt+Zhjb10ZDaiIvLDev3ky79mgrxZCkCzf2eMm+f8mvKwDEQc\n" +
                    "KZ0Ov16ALr2zGrFRRzIdJsu2d/E=\n" +
                    "-----END OPENSSH PRIVATE KEY-----";

            // 添加私钥
            jsch.addIdentity("custom_key", privateKey.getBytes(), null, null);

            // 可选：设置私钥密码（如果私钥有密码保护）
            // jsch.addIdentity("custom_key", privateKey.getBytes(), null, "private_key_password".getBytes());
        } catch (JSchException e) {
            throw new TransportException("Failed to load private key", e);
        }

        return jsch;
    }
}
