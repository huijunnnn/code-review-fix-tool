package app.plugin.thoughtworkscodereviewfixtools.intellij.controller;

import app.plugin.thoughtworkscodereviewfixtools.intellij.store.GithubConfiguration;
import app.plugin.thoughtworkscodereviewfixtools.intellij.store.GithubState;
import app.plugin.thoughtworkscodereviewfixtools.ui.setting.TwCodeReviewSettingsComponent;
import com.intellij.openapi.options.Configurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;


public class TwCodeReviewSettingsConfigurable implements Configurable {

    private static final String CODE_REVIEW_PLUGIN_DISPLAY_NAME = "TW Code Review Fix Tools";
    private TwCodeReviewSettingsComponent twCodeReviewSettingsComponent;


    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return CODE_REVIEW_PLUGIN_DISPLAY_NAME;
    }

    @Override
    public JComponent getPreferredFocusedComponent() {
        return twCodeReviewSettingsComponent.getPreferredFocusedComponent();
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        twCodeReviewSettingsComponent = new TwCodeReviewSettingsComponent();
        return twCodeReviewSettingsComponent.getPanel();
    }

    @Override
    public boolean isModified() {
        GithubConfiguration githubConfiguration = GithubState.getInstance().getState();

        return !githubConfiguration.equals(twCodeReviewSettingsComponent.getCurrentGithubConfiguration());
    }

    @Override
    public void apply() {
        updateLocalState();
    }


    private void updateLocalState() {
        GithubState githubState = GithubState.getInstance();
        githubState.setState(twCodeReviewSettingsComponent.getCurrentGithubConfiguration());
    }

    @Override
    public void reset() {
        GithubConfiguration githubConfiguration = GithubState.getInstance().getState();
        twCodeReviewSettingsComponent.setGithubApiToken(githubConfiguration.getGithubApiToken());
        twCodeReviewSettingsComponent.setRepoOrganization(githubConfiguration.getGithubApiOrganization());
        twCodeReviewSettingsComponent.setRepoNameText(githubConfiguration.getGithubApiRepo());
        twCodeReviewSettingsComponent.setUserNameText(githubConfiguration.getUserName());

    }

    @Override
    public void disposeUIResources() {
        twCodeReviewSettingsComponent = null;
    }

}
