package cn.npe1348.testrecyclerview.http;

import java.util.concurrent.TimeUnit;

import cn.npe1348.testrecyclerview.http.bean.DataRootBean;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiHelper {
    private static OkHttpClient mOkHttpClient;
    private ApiService mApiService;

    public ApiHelper(String host) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(host)
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApiService = retrofit.create(ApiService.class);
    }

    private OkHttpClient getOkHttpClient() {
        if (mOkHttpClient == null){
            mOkHttpClient = new OkHttpClient.Builder()
                    .retryOnConnectionFailure(true)
                    .callTimeout(30, TimeUnit.SECONDS)
                    .build();
        }
        return mOkHttpClient;
    }

    public Call<DataRootBean> getData(int page){
        return mApiService.getData(1,page);
    }
}
