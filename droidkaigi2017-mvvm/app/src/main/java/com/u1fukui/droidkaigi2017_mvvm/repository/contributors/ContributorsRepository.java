package com.u1fukui.droidkaigi2017_mvvm.repository.contributors;

import com.u1fukui.droidkaigi2017_mvvm.model.Contributor;

import java.util.List;

import io.reactivex.Single;

public class ContributorsRepository {

    private final ContributorsRemoteDataSource remoteDataSource;

    public ContributorsRepository(ContributorsRemoteDataSource remoteDataSource) {
        this.remoteDataSource = remoteDataSource;
    }

    public Single<List<Contributor>> findAll() {
        return remoteDataSource.findAll();
    }
}
