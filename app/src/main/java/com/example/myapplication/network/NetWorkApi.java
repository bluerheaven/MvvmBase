package com.example.myapplication.network;

import android.annotation.SuppressLint;
import android.os.SystemClock;

import com.example.myapplication.network.base.INetworkRequiredInfo;
import com.example.myapplication.network.interceptor.CommonRequestInterceptor;
import com.example.myapplication.network.interceptor.CommonResponseInterceptor;
import com.example.myapplication.utils.TecentUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetWorkApi {
    private static NetWorkApi api;
    private static INetworkRequiredInfo iNetworkRequiredInfo;
    private Retrofit retrofit;
    private String mBaseUrl;
    private OkHttpClient mOkHttpClient;
    private static boolean mIsFormal = true;

    private NetWorkApi() {
        if (!mIsFormal) {
            mBaseUrl = getTest();
        }
        mBaseUrl = getFormal();
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder();
        retrofitBuilder.baseUrl(mBaseUrl);
        retrofitBuilder.client(getOkHttpClient());
        retrofitBuilder.addConverterFactory(GsonConverterFactory.create());
        retrofitBuilder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        retrofit = retrofitBuilder.build();
    }

    public static NetWorkApi getInstance() {
        if (api == null) {
            synchronized (NetWorkApi.class) {
                if (api == null) {
                    api = new NetWorkApi();
                }
            }
        }
        return api;
    }

    public static void init(INetworkRequiredInfo networkRequiredInfo) {
        iNetworkRequiredInfo = networkRequiredInfo;
        mIsFormal = true;
    }

    private OkHttpClient getOkHttpClient() {
        if (mOkHttpClient == null) {
            OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
            if (getInterceptor() != null) {
                okHttpClientBuilder.addInterceptor(getInterceptor());
            }
            int cacheSize = 100 * 1024 * 1024; // 10MB
            okHttpClientBuilder.cache(new Cache(iNetworkRequiredInfo.getApplicationContext().getCacheDir(), cacheSize));
            okHttpClientBuilder.addInterceptor(new CommonRequestInterceptor(iNetworkRequiredInfo));
            okHttpClientBuilder.addInterceptor(new CommonResponseInterceptor());
            if (iNetworkRequiredInfo != null && (iNetworkRequiredInfo.isDebug())) {
                HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
                httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                okHttpClientBuilder.addInterceptor(httpLoggingInterceptor);
            }
            mOkHttpClient = okHttpClientBuilder.build();
        }
        return mOkHttpClient;
    }

    protected Interceptor getInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                String timeStr = TecentUtil.getTimeStr();
                Request.Builder builder = chain.request().newBuilder();
                builder.addHeader("Source", "source");
                builder.addHeader("Authorization", TecentUtil.getAuthorization(timeStr));
                builder.addHeader("Date", timeStr);
                return chain.proceed(builder.build());
            }
        };
    }

    @SuppressLint("CheckResult")
    public void getChannels(final CommonCallback<NewsChannelsBean> callback) {
        NewsApiInterface newsApiInterface = retrofit.create(NewsApiInterface.class);
        newsApiInterface.getNewsChannels().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<NewsChannelsBean>() {
            @Override
            public void accept(NewsChannelsBean newsChannelsBean) throws Exception {
                callback.onSuccess(newsChannelsBean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                callback.onError(throwable.toString());
            }
        });
    }

    public String getFormal() {
        return "http://service-o5ikp40z-1255468759.ap-shanghai.apigateway.myqcloud.com/";
    }

    public String getTest() {
        return "http://service-o5ikp40z-1255468759.ap-shanghai.apigateway.myqcloud.com/";
    }

}
