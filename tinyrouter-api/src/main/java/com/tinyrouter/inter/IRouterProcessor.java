package com.tinyrouter.inter;

import android.content.Context;
import android.net.Uri;

public interface IRouterProcessor {

    void process(Context context, Uri uri);
}