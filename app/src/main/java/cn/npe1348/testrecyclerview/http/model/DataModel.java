package cn.npe1348.testrecyclerview.http.model;

import cn.npe1348.testrecyclerview.http.Api;
import cn.npe1348.testrecyclerview.http.ApiHelper;
import cn.npe1348.testrecyclerview.http.bean.DataRootBean;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataModel implements IDataModel {
    @Override
    public void loadData(int page, final ILoadDataListener iLoadDataListener) {
        ApiHelper apiHelper = new ApiHelper(Api.HOST);
        apiHelper.getData(page).enqueue(new Callback<DataRootBean>() {
            @Override
            public void onResponse(Call<DataRootBean> call, Response<DataRootBean> response) {
                iLoadDataListener.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<DataRootBean> call, Throwable t) {
                iLoadDataListener.onFailure(t.getMessage());
            }
        });
    }
}
