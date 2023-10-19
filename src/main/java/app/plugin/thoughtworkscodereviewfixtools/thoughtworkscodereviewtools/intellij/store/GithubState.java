// Copyright 2000-2020 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
// lihui change the file start 2021

package app.plugin.thoughtworkscodereviewfixtools.thoughtworkscodereviewtools.intellij.store;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(
        name = "app.plugin.thoughtworkscodereviewfixtools.settings.GithubState",
        storages = @Storage("$APP_CONFIG$/TwCodeReviewFixToolsSetting.xml")
)
public class GithubState implements PersistentStateComponent<GithubConfiguration> {

    private GithubConfiguration githubConfiguration = new GithubConfiguration();

    public static GithubState getInstance() {
        return ApplicationManager.getApplication().getService(GithubState.class);
    }

    public void setState(GithubConfiguration githubConfiguration) {
        this.githubConfiguration = githubConfiguration;
    }

    @Nullable
    @Override
    public GithubConfiguration getState() { // idea 保存时调用，拿到数据，保存到文件中
        return githubConfiguration;
    }

    @Override
    public void loadState(@NotNull GithubConfiguration state) {
        XmlSerializerUtil.copyBean(state, githubConfiguration);
    }
}
