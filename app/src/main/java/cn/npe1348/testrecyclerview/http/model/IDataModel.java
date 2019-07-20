package cn.npe1348.testrecyclerview.http.model;

import cn.npe1348.testrecyclerview.http.bean.DataRootBean;

public abstract interface IDataModel {
    public abstract void loadData(int page, ILoadDataListener iLoadDataListener);

    public interface ILoadDataListener{
        public void onSuccess(DataRootBean dataRootBean);
        public void onFailure(String error);
    }
}
