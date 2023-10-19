// Copyright 2000-2020 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
// lihui change the file start 2021

package app.plugin.thoughtworkscodereviewfixtools.thoughtworkscodereviewtools.ui.setting;

import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBTextField;
import com.intellij.util.ui.FormBuilder;
import app.plugin.thoughtworkscodereviewfixtools.thoughtworkscodereviewtools.intellij.store.GithubConfiguration;

import javax.swing.*;

/**
 * Supports creating and managing a {@link JPanel} for the Settings Dialog.
 */

public class TwCodeReviewSettingsComponent {

    private final JPanel mainPanel;
    private final JBTextField githubApiTokenTextField = new JBTextField();



    public TwCodeReviewSettingsComponent() {
        JBLabel trelloSettingStatusTipsLabel = new JBLabel("Setting status:");
        trelloSettingStatusTipsLabel.setVisible(false);
        mainPanel = FormBuilder.createFormBuilder()
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



    public GithubConfiguration getCurrentGithubConfiguration() {
        return GithubConfiguration.builder()
                .githubApiToken(githubApiTokenTextField.getText())
                .build();
    }

}
