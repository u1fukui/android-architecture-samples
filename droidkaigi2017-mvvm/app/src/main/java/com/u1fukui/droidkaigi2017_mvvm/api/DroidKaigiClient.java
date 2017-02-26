package com.u1fukui.droidkaigi2017_mvvm.api;

import com.u1fukui.droidkaigi2017_mvvm.model.Contributor;

import java.util.List;

import io.reactivex.Single;

public class DroidKaigiClient {

    private GitHubApi gitHubApi;

    private static final int INCLUDE_ANONYMOUS = 1;

    private static final int MAX_PER_PAGE = 100;

    public DroidKaigiClient(GitHubApi gitHubApi) {
        this.gitHubApi = gitHubApi;
    }

    public Single<List<Contributor>> getContributors() {
        return gitHubApi.getContributors("DroidKaigi", "conference-app-2017", INCLUDE_ANONYMOUS, MAX_PER_PAGE);
    }
}
