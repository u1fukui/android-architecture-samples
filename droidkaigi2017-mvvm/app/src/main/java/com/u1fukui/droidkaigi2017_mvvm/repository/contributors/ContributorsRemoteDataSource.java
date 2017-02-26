package com.u1fukui.droidkaigi2017_mvvm.repository.contributors;

import com.u1fukui.droidkaigi2017_mvvm.api.DroidKaigiClient;
import com.u1fukui.droidkaigi2017_mvvm.model.Contributor;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class ContributorsRemoteDataSource {

    private final DroidKaigiClient client;

    public ContributorsRemoteDataSource(DroidKaigiClient client) {
        this.client = client;
    }

    public Single<List<Contributor>> findAll() {
        return client.getContributors().subscribeOn(Schedulers.io());
    }

}
