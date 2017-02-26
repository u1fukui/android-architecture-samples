package com.u1fukui.droidkaigi2017_mvvm.api;

import android.support.annotation.NonNull;

import com.u1fukui.droidkaigi2017_mvvm.model.Contributor;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GitHubApi {

    @GET("/repos/{owner}/{repo}/contributors")
    Single<List<Contributor>> getContributors(@Path("owner") @NonNull String owner, @Path("repo") @NonNull String repo,
                                              @Query("anon") int anon, @Query("per_page") int perPage);
}
