package app.plugin.thoughtworkscodereviewfixtools.thoughtworkscodereviewtools.service;

import app.plugin.thoughtworkscodereviewfixtools.thoughtworkscodereviewtools.intellij.store.GithubConfiguration;
import app.plugin.thoughtworkscodereviewfixtools.thoughtworkscodereviewtools.ui.FeedbackContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


@Service
public class GithubService {
    private final static String SWEEP_MESSAGE_TEMPLATE = "Sweep: {}, {}";
    private GithubConfiguration githubConfiguration;

    public GithubService(GithubConfiguration githubConfiguration) {
        this.githubConfiguration = githubConfiguration;
    }

    public ResponseEntity<String> createIssue(FeedbackContext feedBackContext, String cardDesc) {
        RestTemplate restTemplate = new RestTemplate();

        // https://github.com/otr-performance/tasking-tool-BE/issues
        String url = "https://api.github.com/"
                + githubConfiguration.getGithubApiOrganization()
                + "/"
                + githubConfiguration.getGithubApiRepo()
                + "/issues";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + githubConfiguration.getGithubApiToken());

        Map<String, Object> requestBody = new HashMap<>();

        requestBody.put("title", String.format(SWEEP_MESSAGE_TEMPLATE, feedBackContext.getFeedback(), cardDesc));
        requestBody.put("body_template_name", "sweep-template.yml");


        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity, String.class);
        return responseEntity;
    }

}

