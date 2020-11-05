package kr.co.ilg.activity.findwork;

import android.content.Context;
import android.content.SharedPreferences;

public class Sharedpreference {
    public static final String PREFERENCES_NAME = "memberinfo";

    private static SharedPreferences getPreferences(Context context) {

        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);

    }
    public static void clear(Context context) {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();

    }public static void remove(Context context) {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove("jobname1");
        editor.remove("jobname2");
        editor.remove("jobname3");
        editor.commit();

    }
    public static void setNone_email(Context context, String key, String value) {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();

    }
    public static String getNone_email(Context context, String key) {
        SharedPreferences prefs = getPreferences(context);
        String value = prefs.getString(key, "memberinfo");
        return value;
    } // 회원번호 이메일 저장

    public static void set_email(Context context, String key, String value) {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }
    public static String get_email(Context context, String key) {
        SharedPreferences prefs = getPreferences(context);
        String value = prefs.getString(key, "memberinfo");
        return value;
    } // 일반이메일 저장
    public static void set_Password(Context context, String key, String value) {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();

    }
    public static String get_Password(Context context, String key) {
        SharedPreferences prefs = getPreferences(context);
        String value = prefs.getString(key, "memberinfo");
        return value;
    } // 비밀번호 이메일 저장

    public static void set_Nickname(Context context, String key, String value) {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();

    }public static String get_Nickname(Context context, String key) {
        SharedPreferences prefs = getPreferences(context);
        String value = prefs.getString(key, "memberinfo");
        return value;
    } // 닉네임 저장
    public static void set_Birth(Context context, String key, String value) {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();

    }public static String get_Birth(Context context, String key) {
        SharedPreferences prefs = getPreferences(context);
        String value = prefs.getString(key, "memberinfo");
        return value;
    } // 생년월일 저장

    public static void set_Gender(Context context, String key, String value) {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();

    }
    public static String get_Getgender(Context context, String key) {
        SharedPreferences prefs = getPreferences(context);
        String value = prefs.getString(key, "memberinfo");
        return value;
    } // 성별 저장

    public static void set_Phonenum(Context context, String key, String value) {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();

    }public static String get_Phonenum(Context context, String key) {
        SharedPreferences prefs = getPreferences(context);
        String value = prefs.getString(key, "memberinfo");
        return value;
    } // 휴대전화번호 저장

    public static void set_Bankaccount(Context context, String key, String value) {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();

    }public static String get_bankaccount(Context context, String key) {
        SharedPreferences prefs = getPreferences(context);
        String value = prefs.getString(key, "memberinfo");
        return value;
    } // 은행계좌 저장
    public static void set_Bankname(Context context, String key, String value) {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();

    }public static String get_bankname(Context context, String key) {
        SharedPreferences prefs = getPreferences(context);
        String value = prefs.getString(key, "memberinfo");
        return value;
    } // 은행이름 저장


    public static void set_introduce(Context context, String key, String value) {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();

    }public static String get_introduce(Context context, String key) {
        SharedPreferences prefs = getPreferences(context);
        String value = prefs.getString(key, "memberinfo");
        return value;
    } // 한줄소개 저장
    public static void set_Jobname(Context context, String key, String value) {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();

    }public static String get_Jobname(Context context, String key) {
        SharedPreferences prefs = getPreferences(context);
        String value = prefs.getString(key, "memberinfo");
        return value;
    } // 한줄소개 저장
    public static void set_Jobcareer(Context context, String key, String value) {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();

    }public static String get_Jobcareer(Context context, String key) {
        SharedPreferences prefs = getPreferences(context);
        String value = prefs.getString(key, "memberinfo");
        return value;
    } // 한줄소개 저장
    public static void set_Hope_local_sido(Context context, String key, String value) {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();

    }public static String get_Hope_local_sido(Context context, String key) {
        SharedPreferences prefs = getPreferences(context);
        String value = prefs.getString(key, "memberinfo");
        return value;
    } // 한줄소개 저장
    public static void set_Hope_local_sigugun(Context context, String key, String value) {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();

    }public static String get_Hope_local_sigugun(Context context, String key) {
        SharedPreferences prefs = getPreferences(context);
        String value = prefs.getString(key, "memberinfo");
        return value;
    } // 한줄소개 저장

}
