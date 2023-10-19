package app.plugin.thoughtworkscodereviewfixtools.thoughtworkscodereviewtools.intellij;

import app.plugin.thoughtworkscodereviewfixtools.thoughtworkscodereviewtools.intellij.exception.BaseException;
import app.plugin.thoughtworkscodereviewfixtools.thoughtworkscodereviewtools.intellij.notification.Notifier;
import app.plugin.thoughtworkscodereviewfixtools.thoughtworkscodereviewtools.intellij.store.GithubConfiguration;
import app.plugin.thoughtworkscodereviewfixtools.thoughtworkscodereviewtools.intellij.store.GithubState;
import app.plugin.thoughtworkscodereviewfixtools.thoughtworkscodereviewtools.service.GithubService;
import app.plugin.thoughtworkscodereviewfixtools.thoughtworkscodereviewtools.ui.CodeReviewFeedbackDialog;
import app.plugin.thoughtworkscodereviewfixtools.thoughtworkscodereviewtools.ui.FeedbackContext;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;

import static java.util.Objects.isNull;

public class CodeReviewFeedbackAction extends AnAction {

    private static final String SET_UP_NOTIFICATION = "您尚未配置 Github 信息，请补全 Github 配置信息 设置路径 Preferences -> Tw Code Review Fix Tools 中设置";
    private static final String USER_SELECT_INFO_DESCRIPTION_TEMPLATE = "Project: %s, File Path: %s, Selected Text: %s";
    private static final String ISSUE_CREATED_SUCCESSFULLY_MESSAGE_TEMPLATE ="Issue created successfully, %s:%s";
    private static final String ISSUE_CREATED_FAILED_MESSAGE_TEMPLATE ="Failed to create issue, %s:%s";

    private GithubService githubService;

    @Override
    public void actionPerformed(AnActionEvent actionEvent) {
        Project project = actionEvent.getProject();
        try {
            doAction(actionEvent);
        } catch (Exception exception) {

            Notifier.notifyError(project, exception.getMessage());
        }
    }

    private void doAction(AnActionEvent actionEvent) {
        initCodeReviewBoardService();

        UserSelectedInfo userSelectedInfo = new UserSelectedInfo(actionEvent);
        String cardDesc = buildFileDesc(userSelectedInfo);

        FeedbackContext feedbackContext = showFeedbackDialog(userSelectedInfo.getProject());
        if (isNull(feedbackContext)) {
            return;
        }

        createCodeReviewFeedbackCard(feedbackContext, cardDesc);
        Notifier.notifyInfo(userSelectedInfo.getProject(), String.format(ISSUE_CREATED_SUCCESSFULLY_MESSAGE_TEMPLATE, feedbackContext.getFeedback(), cardDesc));
    }

    private void initCodeReviewBoardService() {
        GithubConfiguration githubConfiguration = GithubState.getInstance().getState();
        if (githubConfiguration.isAnyBlank()) {
            throw new BaseException(SET_UP_NOTIFICATION);
        }
         githubService = new GithubService(githubConfiguration);
    }

    private void createCodeReviewFeedbackCard(FeedbackContext feedbackContext, String cardDesc) {
        try {
            githubService.createIssue(feedbackContext,cardDesc);
        } catch (Exception e) {
            throw new BaseException(String.format(ISSUE_CREATED_FAILED_MESSAGE_TEMPLATE, feedbackContext.getFeedback(), cardDesc));
        }
    }

    private FeedbackContext showFeedbackDialog(Project project) {
        CodeReviewFeedbackDialog codeReviewFeedbackDialog = new CodeReviewFeedbackDialog(project);
        boolean isCommitFeedback = codeReviewFeedbackDialog.showAndGet();
        if (!isCommitFeedback) {
            return null;
        }

        return codeReviewFeedbackDialog.getFeedbackContext();
    }

    private String buildFileDesc(UserSelectedInfo userSelectedInfo) {
        return String.format(USER_SELECT_INFO_DESCRIPTION_TEMPLATE,
                userSelectedInfo.getProjectName(),
                userSelectedInfo.getSelectedFilePath(),
                userSelectedInfo.getSelectedText());
    }
}
