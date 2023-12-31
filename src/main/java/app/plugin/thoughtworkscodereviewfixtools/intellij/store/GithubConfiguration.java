package app.plugin.thoughtworkscodereviewfixtools.intellij.store;

import com.intellij.util.xmlb.annotations.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Builder
public class GithubConfiguration {
    @Tag("githubApiToken")
    private String githubApiToken;

    @Tag("githubApiRepo")
    private String githubApiRepo;

    @Tag("githubApiOrganization")
    private String githubApiOrganization;

    @Tag("userName")
    private String userName;


    public boolean isAnyBlank() {
        return githubApiToken.isBlank()||
                githubApiOrganization.isBlank()||
                githubApiRepo.isBlank()||
                userName.isBlank();
    }
}
