package org.ayo.component.core;

import android.app.Application;


/**
 * Created by Administrator on 2016/9/20.
 */
public class Core {
    public static Application app;

    public static void init(Application app){
        Core.app = app;
//        MobclickAgent.setDebugMode(true);
        // SDK在统计Fragment时，需要关闭Activity自带的页面统计，
        // 然后在每个页面中重新集成页面统计的代码(包括调用了 onResume 和 onPause 的Activity)。
//        MobclickAgent.openActivityDurationTrack(false);
        // MobclickAgent.setAutoLocation(true);
        // MobclickAgent.setSessionContinueMillis(1000);
        // MobclickAgent.startWithConfigure(
        // new UMAnalyticsConfig(mContext, "4f83c5d852701564c0000011", "Umeng",
        // EScenarioType.E_UM_NORMAL));
//        MobclickAgent.setScenarioType(app, MobclickAgent.EScenarioType.E_UM_NORMAL);

        /*
        @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(mPageName);
        MobclickAgent.onResume(mContext);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(mPageName);
        MobclickAgent.onPause(mContext);
    }


    List<String> keyPath = new ArrayList<String>();
            keyPath.add("one");
            keyPath.add("two");
            keyPath.add("tree");
            MobclickAgent.onEvent(mContext, keyPath, 20, "label");

             MobclickAgent.onEvent(mContext, "click");
            MobclickAgent.onEvent(mContext, "click", "button");

            Map<String, String> map_ekv = new HashMap<String, String>();
            map_ekv.put("type", "popular");
            map_ekv.put("artist", "JJLin");
            MobclickAgent.onEvent(mContext, "music", map_ekv);


             Map<String, String> map_value = new HashMap<String, String>();
            map_value.put("type", "popular");
            map_value.put("artist", "JJLin");
            MobclickAgent.onEventValue(this, "music", map_value, 12000);

            UMPlatformData platform = new UMPlatformData(UMedia.SINA_WEIBO, "user_id");
            platform.setGender(GENDER.MALE); // optional
            platform.setWeiboId("weiboId"); // optional
            MobclickAgent.onSocialEvent(this, platform);

             MobclickAgent.onProfileSignIn("example_id");

             MobclickAgent.onProfileSignOff();

             MobclickAgent.onKillProcess(mContext);
             int pid = android.os.Process.myPid();
                android.os.Process.killProcess(pid);
         */
    }


}
