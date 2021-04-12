package com.hins.jdbc.utils;

import com.hins.jdbc.aop.IdHintInfo;
import org.apache.shardingsphere.api.hint.HintManager;

/**
 * @author : chenqixuan
 * @date : 2021/4/12
 */
public class ThreadLocalUtils {

    private static ThreadLocal<IdHintInfo> threadLocal = new ThreadLocal<IdHintInfo>();

    public static IdHintInfo getIdHintInfo() { return (IdHintInfo) threadLocal.get(); }

    public static void setIdHintInfo(IdHintInfo entity) { threadLocal.set(entity); }

    public static HintManager getHintManager() {
        HintManager.clear();
        HintManager hintManager = HintManager.getInstance();
        return hintManager;
    }

}
