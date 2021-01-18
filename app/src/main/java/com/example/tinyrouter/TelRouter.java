package com.example.tinyrouter;

import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import com.tinyrouter.annotation.Router;
import com.tinyrouter.inter.IRouterProcessor;

/**
 * Author: HEFENG959
 * Date: 2021-1-18 15:48
 * Description: 打电话路由
 */
@Router(paths = {"myrouter://tel"}, desc = "打电话路由")
public class TelRouter implements IRouterProcessor {
    @Override
    public void process(Context context, Uri uri) {
        Toast.makeText(context, "success:" + uri, Toast.LENGTH_SHORT).show();

        /*IUriInjector injector = ModularHelper.uriInjector(uri);
        String num = injector.getParam("num");
        VLog.d("vipabc://tel" + num);
        try {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + num));
            context.startActivity(intent);
        } catch (Exception e) {
        }*/
    }
}
