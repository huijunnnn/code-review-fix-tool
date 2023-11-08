package app.plugin.thoughtworkscodereviewfixtools.service;

import app.plugin.thoughtworkscodereviewfixtools.intellij.notification.Notifier;
import app.plugin.thoughtworkscodereviewfixtools.intellij.store.GithubConfiguration;
import app.plugin.thoughtworkscodereviewfixtools.ui.FeedbackContext;
import com.intellij.openapi.project.Project;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.stereotype.Service;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;


@Service
public class GithubService {
    private final static String SWEEP_MESSAGE_TEMPLATE = "Sweep: %s - %s";
    private GithubConfiguration githubConfiguration;

    public GithubService(GithubConfiguration githubConfiguration) {
        this.githubConfiguration = githubConfiguration;
    }

    public void createIssue(FeedbackContext feedBackContext, String cardDesc, Project project) {
        String apiUrl = "https://api.github.com/repos/"
                + githubConfiguration.getGithubApiOrganization()
                + "/"
                + githubConfiguration.getGithubApiRepo()
                + "/issues";
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + githubConfiguration.getGithubApiToken());
            connection.setDoOutput(true);
            String title = String.format(SWEEP_MESSAGE_TEMPLATE, feedBackContext.getFeedback(), githubConfiguration.getUserName());
            String issueDesc = buildIssueDesc(feedBackContext, cardDesc);
            // 转义HTML特殊字符
            String escapedBody = escapeHtmlAndPreserveIndent(issueDesc);
            String requestBody = String.format("{\"title\":\"%s\", \"body\":\"%s\"}", title, escapedBody);
            byte[] requestBodyBytes = requestBody.getBytes(StandardCharsets.UTF_8);

            try (DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream())) {
                outputStream.write(requestBodyBytes);
            }
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_CREATED) {
                Notifier.notifyInfo(project, "Sweep Issue created successfully.");
            } else {
                String message = "Failed to create sweep issue. Response code: " + responseCode;
                Notifier.notifyError(project, message);
            }
            // 关闭连接
            connection.disconnect();
        } catch (IOException e) {
            Notifier.notifyError(project, e.getMessage());
        }
    }


    private static String buildIssueDesc(FeedbackContext feedBackContext, String cardDesc) {
        String issueDesc;
        String label = feedBackContext.getLabel();
        String cardDescPrefix = "请根据title的内容对以下的内容进行";
        issueDesc = switch (label) {
            case "refactor" -> cardDescPrefix + "重构：" + cardDesc;
            case "feat" -> cardDescPrefix + "功能新增：" + cardDesc;
            case "fix" -> cardDescPrefix + "bug修复：" + cardDesc;
            case "docs" -> cardDescPrefix + "文档变更：" + cardDesc;
            case "style" -> cardDescPrefix + "代码格式、样式变动，不影响代码逻辑：" + cardDesc;
            case "test" -> cardDescPrefix + "添加或修改测试代码：" + cardDesc;
            default -> cardDescPrefix + "修改：" + cardDesc;
        };
        return issueDesc;
    }
    private static String escapeHtmlAndPreserveIndent(String input) {
        StringBuilder result = new StringBuilder();
        String[] lines = input.split("\n");

        for (String line : lines) {
            // 转义HTML特殊字符
            String escapedLine = StringEscapeUtils.escapeHtml4(line.trim());
            // 添加到结果中
            result.append(escapedLine).append("\\n");
        }

        return result.toString();
    }
}

