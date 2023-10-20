package app.plugin.thoughtworkscodereviewfixtools.ui.setting;

import app.plugin.thoughtworkscodereviewfixtools.intellij.store.GithubConfiguration;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBTextField;
import com.intellij.util.ui.FormBuilder;

import javax.swing.*;


public class TwCodeReviewSettingsComponent {

    private final JPanel mainPanel;
    private final JBTextField userNameTextField = new JBTextField();
    private final JBTextField repoOrganizationTextField = new JBTextField();
    private final JBTextField githubApiTokenTextField = new JBTextField();
    private final JBTextField repoNameTextField = new JBTextField();

    public TwCodeReviewSettingsComponent() {
        JBLabel trelloSettingStatusTipsLabel = new JBLabel("Setting status:");
        trelloSettingStatusTipsLabel.setVisible(false);
        mainPanel = FormBuilder.createFormBuilder()
                .addLabeledComponent(new JBLabel("Enter user name: "), userNameTextField, 1, false)
                .addLabeledComponent(new JBLabel("Enter repo organization: "), repoOrganizationTextField, 1, false)
                .addLabeledComponent(new JBLabel("Enter repo name: "), repoNameTextField, 1, false)
                .addLabeledComponent(new JBLabel("Enter github token: "), githubApiTokenTextField, 1, false)
                .addComponentFillVertically(new JPanel(), 0)
                .getPanel();
    }

    public JPanel getPanel() {
        return mainPanel;
    }

    public JComponent getPreferredFocusedComponent() {
        return githubApiTokenTextField;
    }

    public void setGithubApiToken(String githubApiToken) {
        this.githubApiTokenTextField.setText(githubApiToken);
    }
    public void setRepoOrganization(String repoOrganization) {
        this.repoOrganizationTextField.setText(repoOrganization);
    }
    public void setRepoNameText(String repoNameText) {
        this.repoNameTextField.setText(repoNameText);
    }
    public void setUserNameText(String userNameText) {
        this.userNameTextField.setText(userNameText);
    }
    public GithubConfiguration getCurrentGithubConfiguration() {
        return GithubConfiguration.builder()
                .userName(userNameTextField.getText())
                .githubApiRepo(repoNameTextField.getText())
                .githubApiOrganization(repoOrganizationTextField.getText())
                .githubApiToken(githubApiTokenTextField.getText())
                .build();
    }

}
