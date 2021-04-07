package com.hins.core.config.shard;

import com.hins.core.store.entity.SkuStore;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SkuStoreShard implements ShardStrategy {

    @Override
    public String getTableName(String tableName, Object param, String mapperId) {
        String hash = "";
        if(param instanceof SkuStore) {
            SkuStore uc = (SkuStore) param;
            hash = StringUtils.replace(uc.getStoreId()," ","");
        } else if(param instanceof HashMap) {
            HashMap<String, String> map = (HashMap) param;
            String storeId = null;
            try {
                storeId = map.get("storeId");
            } catch (Exception e) {
                e.printStackTrace();
            }
            hash = StringUtils.replace(storeId," ","");
        }
        if(StringUtils.length(hash)>10|| StringUtils.contains(hash,";")){
            //替换空格后防注入
            hash="";
        }
        return tableName+"_"+hash;
    }

}
