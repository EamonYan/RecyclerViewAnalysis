package cn.npe1348.testrecyclerview.http;

import cn.npe1348.testrecyclerview.http.bean.DataRootBean;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("satinApi")
    Call<DataRootBean> getData(@Query("type") int type,@Query("page") int page);
}
