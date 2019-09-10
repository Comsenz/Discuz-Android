package cn.tencent.DiscuzMob.manager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;

import cn.tencent.DiscuzMob.base.RedNetApp;
import cn.tencent.DiscuzMob.model.BaseModel;
import cn.tencent.DiscuzMob.model.SecureVariables;
import cn.tencent.DiscuzMob.net.Api;
import cn.tencent.DiscuzMob.model.CheckPostModelVariables;

/**
 * Created by kurt on 15-6-8.
 */
public class TThreadManager {

    public static void checkPost(String fid, final DataLoaderListener dataLoaderListener) {
        Api.getInstance().requestCheckPost(fid, new DataLoaderCallback(new InterDataLoaderListener() {
            @Override
            public void onLoadFinished(Object object) {
                try {
                    BaseModel<CheckPostModelVariables> vriablesBaseModel = new Gson().fromJson((String) object
                            , new TypeToken<BaseModel<CheckPostModelVariables>>() {
                            }.getType());
                    if (vriablesBaseModel.getVariables() != null) {
                        RedNetApp.setFormHash(vriablesBaseModel.getVariables().getFormhash());
                        if (dataLoaderListener != null && vriablesBaseModel.getVariables().getAllowperm() != null) {
                            HashMap<String, Object> data = new HashMap<String, Object>();
                            data.put("checkpost", vriablesBaseModel.getVariables().getAllowperm());
                            data.put("cookiepre", vriablesBaseModel.getVariables().getCookiepre());
                            dataLoaderListener.onLoadDataFinished(data);
                        }
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (dataLoaderListener != null) {
                    dataLoaderListener.onError(object);
                }
            }

            @Override
            public void onError(Object object) {
                if (dataLoaderListener != null) {
                    dataLoaderListener.onError(object);
                }
            }
        }));

    }

    public static void checkSecure(String type, final DataLoaderListener loaderListener) {
        Api.getInstance().requestSecure(type, new DataLoaderCallback(new InterDataLoaderListener() {
            @Override
            public void onLoadFinished(Object object) {
                try {
                    BaseModel<SecureVariables> vriablesBaseModel = new Gson().fromJson((String) object, new TypeToken<BaseModel<SecureVariables>>() {
                    }.getType());
                    SecureVariables secure = vriablesBaseModel.getVariables();
                    if (vriablesBaseModel.getVariables() != null) {
                        RedNetApp.setFormHash(vriablesBaseModel.getVariables().getFormhash());
                        if (loaderListener != null) {
                            String cookiepre = vriablesBaseModel.getVariables().getCookiepre();
                            HashMap<String, Object> data = new HashMap<String, Object>();
                            data.put("secure", secure);
                            data.put("cookiepre", cookiepre);
                            loaderListener.onLoadDataFinished(data);
                        }
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (loaderListener != null) {
                    loaderListener.onError(null);
                }
            }

            @Override
            public void onError(Object object) {
                if (loaderListener != null) {
                    loaderListener.onError(null);
                }
            }
        }));

    }

}
