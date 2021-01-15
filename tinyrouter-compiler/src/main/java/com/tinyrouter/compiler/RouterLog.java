package com.tinyrouter.compiler;

public class RouterLog {
    public static final String LOG = "Router Compiler : ";
//    private Messager mMessager;
//
//    public RouterLog(Messager mMessager) {
//        this.mMessager = mMessager;
//    }

    public static void i(CharSequence info) {
        System.out.println(LOG + info);
    }
}
