/**
  * Copyright 2019 bejson.com 
  */
package cn.npe1348.testrecyclerview.http.bean;
import java.util.List;

/**
 * Auto-generated: 2019-07-18 14:35:50
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class DataRootBean {

    private int code;
    private String msg;
    private List<Data> data;
    public void setCode(int code) {
         this.code = code;
     }
     public int getCode() {
         return code;
     }

    public void setMsg(String msg) {
         this.msg = msg;
     }
     public String getMsg() {
         return msg;
     }

    public void setData(List<Data> data) {
         this.data = data;
     }
     public List<Data> getData() {
         return data;
     }

    @Override
    public String toString() {
        return "DataRootBean{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data.toString() +
                '}';
    }
}