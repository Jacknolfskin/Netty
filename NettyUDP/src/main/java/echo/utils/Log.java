package echo.utils;

/**
 * @Author: Jacknolfskin
 * @Date: 2018/5/7 11:09
 * @Path: echo.utils
 */
import java.awt.Color;
import java.util.Date;

public class Log
{
    public static final int INFO = 1;
    public static final int PROMPT = 2;
    public static final int DEBUG = 3;
    public static final int WARN = 4;
    public static final int ERROR = 5;
    public static final int FETAL = 6;

    private int logLevel = -1;

    private static Log instance = null;

    public static Log getInstance()
    {
        if (instance == null)
            instance = new Log(null, -1);
        return instance;
    }

    public Log(Object dest, int logLevel)
    {
        this.logLevel = logLevel;
    }

    public int getLogLevel()
    {
        return this.logLevel;
    }

    public Log setLogLevel(int logLevel) {
        this.logLevel = logLevel;
        return this;
    }

    public static void i(String tag, String msg)
    {
        i(tag, msg, null);
    }

    public static void i(String tag, String msg, Exception ex) {
        getInstance().log(msg, 1, ex);
    }

    public static void p(String tag, String msg)
    {
        p(tag, msg, null);
    }

    public static void p(String tag, String msg, Exception ex) {
        getInstance().log(msg, 2, ex);
    }

    public static void d(String tag, String msg)
    {
        d(tag, msg, null);
    }

    public static void d(String tag, String msg, Exception ex) {
        getInstance().log(msg, 3, ex);
    }

    public static void w(String tag, String msg)
    {
        w(tag, msg, null);
    }

    public static void w(String tag, String msg, Exception ex) {
        getInstance().log(msg, 4, ex);
    }

    public static void e(String tag, String msg)
    {
        e(tag, msg, null);
    }

    public static void e(String tag, String msg, Exception ex) {
        getInstance().log(msg, 5, ex);
    }

    public static void f(String tag, String msg)
    {
        f(tag, msg, null);
    }

    public static void f(String tag, String msg, Exception ex) {
        getInstance().log(msg, 6, ex);
    }

    public void log(String msg, int level)
    {
        log(msg, level, null);
    }

    public void log(String msg, int level, Exception ex)
    {
        String lv="";
        Color fc=Color.black;
        switch(level)
        {
            case INFO:
                fc=new Color(153,204,0);
                lv="INFO";
                break;
            case PROMPT:
                fc=new Color(0, 255, 0);
                lv="PROMPT";
                break;
            case DEBUG:
                fc=new Color(255,204,153);
                lv="DEBUG";
                break;
            case WARN:
                fc=Color.pink;
                lv="WARN";
                break;
            case ERROR:
                fc=Color.red;
                lv="ERROR";
                break;
            case FETAL:
                fc=Color.red;
                lv="FETAL";
                break;
        }

        if(level>logLevel)
        {
            String txt=" "+lv+" - "+msg+(ex == null?"":"("+ex.getMessage()+")")+" ["+(new Date().toLocaleString())+"]\r\n";
            System.out.print(txt);
            if(ex != null)
                ex.printStackTrace();
        }
    }
}
