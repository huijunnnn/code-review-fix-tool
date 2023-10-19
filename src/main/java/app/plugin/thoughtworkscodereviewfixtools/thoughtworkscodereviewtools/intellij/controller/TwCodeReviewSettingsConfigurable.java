package app.plugin.thoughtworkscodereviewfixtools.thoughtworkscodereviewtools.intellij.controller;

import app.plugin.thoughtworkscodereviewfixtools.thoughtworkscodereviewtools.intellij.store.GithubConfiguration;
import app.plugin.thoughtworkscodereviewfixtools.thoughtworkscodereviewtools.intellij.store.GithubState;
import app.plugin.thoughtworkscodereviewfixtools.thoughtworkscodereviewtools.ui.setting.TwCodeReviewSettingsComponent;
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
        GithubConfiguration trelloConfiguration = GithubState.getInstance().getState();

        twCodeReviewSettingsComponent.setGithubApiToken(trelloConfiguration.getGithubApiToken());

    }

    @Override
    public void disposeUIResources() {
        twCodeReviewSettingsComponent = null;
    }

}
