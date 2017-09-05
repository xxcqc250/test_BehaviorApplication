package com.behavior.ming_yi.AppParser;

import android.content.Context;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;

import com.behavior.ming_yi.AppTemplete.AppTempleteParser;

import java.util.List;


/**
 * Created by ming-yi on 2017/5/10.
 */

public class EBCnewsParser extends AppTempleteParser {
    private String TAG = "App_EBCnews";

    public EBCnewsParser(Context context, String appname, String event){
        super(context,appname,event);
    }


    @Override
    public String AppTempleteParser(AccessibilityNodeInfo mAccessibilityNodeInfo) {
        if (mAccessibilityNodeInfo == null) return null;
        StringBuilder data = new StringBuilder();
        List<AccessibilityNodeInfo> CacheNodes = mAccessibilityNodeInfo.findAccessibilityNodeInfosByViewId("com.ebc.news:id/coordinatorLayout");


        String hh = Integer.toString(CacheNodes.get(0).getChild(0).getChildCount());
        //Log.e("NUM",CacheNodes.get(0).getChild(0).getText().toString() + ":" + Integer.toString(CacheNodes.size()));
        Log.e("NUM",CacheNodes.toString());

        AccessibilityNodeInfo FinalNode = SerachClassName(CacheNodes.get(0),"android.webkit.WebView");
        if(FinalNode == null) return null;
        if(FinalNode.getChildCount() == 0) return null;
        if(FinalNode.getChild(0).getChildCount() < 6);
        StringBuilder title = new StringBuilder() ;
        StringBuilder str_context = new StringBuilder();

        if(FinalNode.getChild(0).getContentDescription() != null)
        {
            Log.e("APP_OO",FinalNode.getChild(0).toString());
            title.append(FinalNode.getChild(0).getContentDescription().toString());

        }

        if(FinalNode.getChild(0).getChild(6) != null)
            str_context.append(FinalNode.getChild(0).getChild(6) .getContentDescription()+"\n");
        for(int i=0 ; i<FinalNode.getChild(0).getChild(6).getChildCount();i++){
            AccessibilityNodeInfo tmpNode = FinalNode.getChild(0).getChild(6).getChild(i);
            if (tmpNode.getContentDescription()!=null && tmpNode.getContentDescription().length()!=0){
                if (tmpNode.getContentDescription().toString().contains("【今日最熱門】"))
                    break;
                str_context.append(tmpNode.getContentDescription().toString()+"\n");
            }
        }
        if(title !=null && str_context != null){
            data.append(title+"\n");
            data.append(str_context);
        }

        Log.i(TAG,data.toString());
        return data.toString();
    }

    private AccessibilityNodeInfo SerachClassName(AccessibilityNodeInfo CacheNode,String name){
        AccessibilityNodeInfo data = null;

        int CacheNodechildcount = CacheNode.getChildCount();
        String vv = Integer.toString(CacheNodechildcount);


        Log.e("HAAA",vv);
        if(CacheNode.getClassName() != null && CacheNode.getClassName().equals(name)) {
            Log.e("BBBB",CacheNode.toString());
            return CacheNode;
        }

        for(int i=0; i<CacheNodechildcount ; i++){
            if(data!=null) break;
            data = SerachClassName(CacheNode.getChild(i),name);
        }

        return data;
    }
}
