package com.sxb.kiven.mytest;

import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.LinkedList;

/**
 * Created by kiven on 16/3/31.
 */
public final class ULog {

    private static boolean isDebug = true;

    private static ULog myLog;

    private static boolean showAtLine = true;

    private static LinkedList<String> logs;

    /**
     * 日志记录操作
     * @param log
     */
    private static void addLog(String log){
        if (logs == null) {
            logs = new LinkedList<String>();
        }else if (logs.size() > 500) {
            logs.removeLast();
        }
        logs.addFirst(log);;
    }

    public static LinkedList<String> getLogs(){
        if (logs == null) {
            logs = new LinkedList<String>();
        }

        return logs;
    }

    /**
     * 单例ULog
     * @return
     */
    public static ULog getInstans() {

        if (myLog != null) {

            return myLog;
        } else {

            myLog = new ULog();

        }

        return myLog;

    }
    /**
     * 是否显示在哪一行
     * @param b
     */
    public static void setShowAtLine(boolean b) {
        showAtLine = b;
    }

    /**
     * 获取代码位置
     * @return
     */
    public static String findLog() {

        if (!showAtLine) {
            return "";
        }

        StackTraceElement[] sts = Thread.currentThread().getStackTrace();
        if (sts == null) {
            return null;
        }
        for (StackTraceElement st : sts) {
            if (st.isNativeMethod()) {

                continue;
            }
            if (st.getClassName().equals(Thread.class.getName())) {

                continue;
            }
            if (st.getClassName().endsWith(getInstans().getClass().getName())) {

                continue;
            }
            return " at " + st.getClassName() + "." + st.getMethodName() + "("
                    + st.getFileName() + ":" + st.getLineNumber() + ")";
        }
        return "";

    }

    public static void d(String debugInfo){
        if (isDebug){
            Log.d("ULog_default", debugInfo + findLog());
            addLog(debugInfo);
        }
    }
    public static void e(String errorInfo){
        if (isDebug){
            Log.e("ULog_default", errorInfo + findLog());
            addLog(errorInfo);
        }
    }
    public static void v(String msg){
        if (isDebug){
            Log.v("ULog_default", msg + findLog());
            addLog(msg);
        }
    }
    public static void i(String msg){
        if (isDebug){
            Log.i("ULog_default", msg + findLog());
            addLog(msg);
        }
    }
    public static void w(String msg){
        if (isDebug){
            Log.w("ULog_default", msg + findLog());
            addLog(msg);
        }
    }

    //打印异常
    public static void e (Exception e){
        if (isDebug) {
            try {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                e("\r\n" + sw.toString() + "\r\n") ;
            } catch (Exception e2) {
                e("fail getErrorInfoFromException") ;
            }
        }
    }
}
