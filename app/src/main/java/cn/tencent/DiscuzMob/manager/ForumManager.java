package cn.tencent.DiscuzMob.manager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import cn.tencent.DiscuzMob.base.RedNetApp;
import cn.tencent.DiscuzMob.model.BaseModel;
import cn.tencent.DiscuzMob.model.ForumThreadType;
import cn.tencent.DiscuzMob.model.ForumTypeVariables;
import cn.tencent.DiscuzMob.net.Api;

/**
 * Created by kurt on 15-6-4.
 */
public class ForumManager {

    public static void getForumThreadType(final String fid, boolean queryDB, final DataLoaderListener dataLoaderListener) {
        Api.getInstance().requestForumThreadType(fid, new DataLoaderCallback(new InterDataLoaderListener() {
            @Override
            public void onLoadFinished(Object object) {
                String reslut = (String) object;
                BaseModel<ForumTypeVariables> data = new Gson().fromJson(reslut, new TypeToken<BaseModel<ForumTypeVariables>>() {
                }.getType());
                if (data != null && data.getVariables() != null) {
                    RedNetApp.setFormHash(data.getVariables().getFormhash());
                }
                if (data.getVariables().getThreadtypes() != null) {
                    HashMap<String, String> typesMap = data.getVariables().getThreadtypes().getTypes();
                    if (typesMap != null) {
                        ArrayList<ForumThreadType> typeList = new ArrayList<ForumThreadType>();
                        if (typesMap.size() > 0) {
                            Iterator iter = typesMap.entrySet().iterator();
                            while (iter.hasNext()) {
                                Map.Entry entry = (Map.Entry) iter.next();
                                String typeId = (String) entry.getKey();
                                String typeName = (String) entry.getValue();
                                ForumThreadType type = new ForumThreadType();
                                type.setTypeId(typeId);
                                type.setTypeName(typeName);
                                type.setFid(fid);
                                typeList.add(type);
                            }
                            if (dataLoaderListener != null) {
                                dataLoaderListener.onLoadDataFinished(typeList);
                            }
                        }
                    }
                }
            }

            @Override
            public void onError(Object object) {
                if (dataLoaderListener != null) {
                    String message = (String) object;
                    dataLoaderListener.onError(message);
                }
            }
        }));
    }

}
