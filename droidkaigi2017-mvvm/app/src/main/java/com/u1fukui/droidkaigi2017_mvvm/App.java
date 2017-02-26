package com.u1fukui.droidkaigi2017_mvvm;

import android.app.Application;

import com.google.gson.GsonBuilder;
import com.u1fukui.droidkaigi2017_mvvm.api.DroidKaigiClient;
import com.u1fukui.droidkaigi2017_mvvm.api.GitHubApi;

import io.reactivex.disposables.CompositeDisposable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {

    private DroidKaigiClient apiClient;

    private CompositeDisposable compositeDisposable;

    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();

        initApiClient();
        initCompositeDisposable();

        instance = this;
    }

    private void initApiClient() {
        GitHubApi api = new Retrofit.Builder()
                .client(new OkHttpClient.Builder().build())
                .baseUrl("https://api.github.com")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .build()
                .create(GitHubApi.class);

        apiClient = new DroidKaigiClient(api);
    }

    private void initCompositeDisposable() {
        compositeDisposable = new CompositeDisposable();
    }

    public static DroidKaigiClient getApiClient() {
        return instance.apiClient;
    }

    public static CompositeDisposable getCompositeDisposable() {
        return instance.compositeDisposable;
    }
}
