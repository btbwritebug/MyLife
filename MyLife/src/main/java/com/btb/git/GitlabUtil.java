package com.sie.governance.comm.infra.util.gitlab;

import com.sie.governance.comm.infra.constant.gitlab.GitlabServiceConst;
import com.sie.governance.comm.model.core.gitlab.config.GitlabApiManager;
import com.sie.governance.comm.model.core.gitlab.entity.GitlabBugEntity;
import com.sie.governance.comm.model.core.gitlab.entity.GitlabTaskEntity;
import com.sie.governance.comm.model.core.gitlab.entity.NginxEntity;
import com.sie.governance.comm.infra.util.StringUtil;
import com.sie.iot.component.exception.ApplicationRuntimeException;
import org.gitlab4j.api.UserApi;
import org.gitlab4j.api.models.RepositoryFile;
import org.gitlab4j.api.models.SshKey;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.yaml.snakeyaml.Yaml;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class GitlabUtil implements InitializingBean {
    @Value("${gitlab.pullOrCloneSshTitle}")
    private String pullOrCloneSshTitle;

    @Value("${jenkins.sfGitPublicKey}")
    private String sfGitPublicKey;

    private static String pullOrCloneSshTitleStatic;

    private static String sfGitPublicKeyStatic;

    private static final String flag="-";
    private static final String bugPattern = "bug\\[[0-9]+#.\\].+";
    private static final String taskPattern = "task\\[[0-9]+#[0-9]+#[0-9]+\\].+";
    private static final String selectBugIdPattern = "(bug\\[)([0-9]+)(#.\\].+)";
    private static final String selectTaskIdPattern = "(task\\[)([0-9]+)(#[0-9]+#[0-9]+\\].+)";

    private static final String nginxTemplate="server {\n" +
            "        listen       ${nginxPort};\n" +
            "        server_name  ${ipName};\n" +
            "        root         ${htmlPath};\n" +
            "        add_header 'Access-Control-Allow-Origin' '*';\n"+
            "        add_header Access-Control-Allow-Methods 'GET, POST, OPTIONS'; \n"+
            "        add_header 'Access-Control-Allow-Credentials' 'true' always; \n"+
            "        add_header Access-Control-Allow-Headers 'DNT,X-Mx-ReqToken,Keep-Alive,User-Agent,X-Requested-With,If-Modified-Since,Cache-Control,Content-Type,Authorization';\n"+
            "\n" +
            "        if ($request_method = 'OPTIONS') {\n" +
            "              return 204;\n" +
            "        } \n\n" +
            "         " +
            "location / {\n" +
            "        index  index.htm index.html;\n" +
            "        try_files $uri $uri/ /index.html;\n"+
            "        }\n" +
            "        location /nginx_status {\n" +
            "                stub_status on;\n" +
            "                access_log off;\n" +
            "           }\n"+
            "\n" +
            "        error_page 404 /404.html;\n" +
            "        location = /40x.html {\n" +
            "        }\n" +
            "\n" +
            "        error_page 500 502 503 504  /50x.html;\n" +
            "        location = /50x.html {\n" +
            "        }\n" +
            "}";


    public static Map<String, List> getBugAndTaskMessage(String commitMessage, String email, String branch, Long sfProjectId) {

        Map<String, List> returnMap=new HashMap();
        List bugList=new LinkedList();
        List taskList=new LinkedList();

        if(!commitMessage.contains("&")){
            messagePutInList(commitMessage,email,bugList,taskList,branch,sfProjectId);

            returnMap.put(GitlabServiceConst.bugPre,bugList);
            returnMap.put(GitlabServiceConst.taskPre,taskList);

            return returnMap;
        }

        String[] commitArray=commitMessage.split("&");

        for(String commit:commitArray){

            messagePutInList(commit,email,bugList,taskList,branch,sfProjectId);

        }
        returnMap.put(GitlabServiceConst.bugPre,bugList);
        returnMap.put(GitlabServiceConst.taskPre,taskList);
        return returnMap;
    }

    private static void messagePutInList(String commitMessage, String email, List bugList, List taskList, String branch, Long sfProjectId) {

        commitMessage=commitMessage.replace("\n","");
        if(!commitMessage.contains(GitlabServiceConst.bugPre)&&!commitMessage.contains(GitlabServiceConst.taskPre)){
            return;
        }

        int suffixIndex= commitMessage.indexOf("]");

        String message=commitMessage.substring(suffixIndex+1);


        if(Pattern.matches(bugPattern, commitMessage)){
            Pattern pattern = Pattern.compile(selectBugIdPattern);
            Matcher matcher = pattern.matcher(commitMessage);
            matcher.find();

            Integer id=Integer.valueOf(matcher.group(2));

            GitlabBugEntity bugEntity=new GitlabBugEntity();
            bugEntity.setAuthEmail(email);
            bugEntity.setBranch(branch);
            bugEntity.setBugId(id);
            bugEntity.setMessage(message);
            bugEntity.setSfProjectId(sfProjectId);
            bugList.add(bugEntity);
        }
        if(Pattern.matches(taskPattern, commitMessage)){
            Pattern pattern = Pattern.compile(selectTaskIdPattern);
            Matcher matcher = pattern.matcher(commitMessage);
            matcher.find();

            Integer id=Integer.valueOf(matcher.group(2));

            int preIndex=commitMessage.indexOf("#");

            String middleString=commitMessage.substring(preIndex,suffixIndex);
            String[] middleStringArray=middleString.split("#");

            Integer first=Integer.valueOf(middleStringArray[1]);
            Integer second=Integer.valueOf(middleStringArray[2]);

            GitlabTaskEntity taskEntity=new GitlabTaskEntity();
            taskEntity.setAuthEmail(email);
            taskEntity.setBranch(branch);
            taskEntity.setTaskId(id);
            taskEntity.setMessage(message);
            taskEntity.setNowConsumed(first);
            taskEntity.setLeftTimes(second);
            taskEntity.setSfProjectId(sfProjectId);
            taskList.add(taskEntity);
        }


    }

    public static void main(String[] args) throws Exception {
        String content = "task[1001#0#6]test,task";
        boolean isMatch = Pattern.matches(selectTaskIdPattern, content);

        Pattern r = Pattern.compile(selectTaskIdPattern);
        Matcher m = r.matcher(content);

        if(m.find()){
            System.out.println("Found count: " + m.groupCount());
            for(int i=0;i<m.groupCount();i++){
                System.out.println("Found value: " + m.group(i) );
            }
        }

        System.out.println(m.group(2));

        String s="#111#222";

        String[] strings=s.split("#");

        System.out.println(strings);


    }

    public static RepositoryFile getNginxConfRepositoryFile(NginxEntity nginxEntity) {
        RepositoryFile repositoryFiler=new RepositoryFile();
        //repositoryFiler.setFileName(".gitlab-ci.yml");
        repositoryFiler.setFilePath(GitlabServiceConst.nginxCondPath);
        repositoryFiler.setContent(getNginxConfContent(nginxEntity));
        //repositoryFiler.setEncoding(TEXT);
        return repositoryFiler;
    }

    private static String getNginxConfContent(NginxEntity nginxEntity) {
        String nginxTemplate=GitlabUtil.nginxTemplate;
        nginxTemplate=StringUtil.replace(nginxTemplate,nginxEntity.getPort(),"nginxPort");
        nginxTemplate=StringUtil.replace(nginxTemplate,nginxEntity.getIpName(),"ipName");
        nginxTemplate=StringUtil.replace(nginxTemplate,nginxEntity.getHtmlPath(),"htmlPath");
        return nginxTemplate;
    }

    public static void checkPrivateKey(String configType,Long sfGitlabIb,Long tenantId) {
        try {
            UserApi userApi = GitlabApiManager.getUserApiGitlabId(null, configType, sfGitlabIb,tenantId);
            List<SshKey> sshKeyList = userApi.getSshKeys();
            if (CollectionUtils.isEmpty(sshKeyList)) {
                userApi.addSshKey(GitlabUtil.pullOrCloneSshTitleStatic, sfGitPublicKeyStatic);
            } else {
                boolean setSshKeyFlag = true;
                for (SshKey sshKey : sshKeyList) {
                    if (pullOrCloneSshTitleStatic.equals(sshKey.getTitle())) {
                        setSshKeyFlag = false;
                        break;
                    }
                }
                if (setSshKeyFlag) {
                    userApi.addSshKey(pullOrCloneSshTitleStatic, sfGitPublicKeyStatic);
                }
            }
        }catch (Exception e){
            throw new ApplicationRuntimeException("检查添加gitlab公钥失败");
        }

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        GitlabUtil.pullOrCloneSshTitleStatic=pullOrCloneSshTitle;
        GitlabUtil.sfGitPublicKeyStatic=sfGitPublicKey;
    }

}
