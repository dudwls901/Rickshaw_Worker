package kr.co.ilg.activity.findwork;

import android.content.Context;
import android.content.SharedPreferences;

public class Sharedpreference {
    //public static final String PREFERENCES_NAME = "memberinfo";

    private static SharedPreferences getPreferences(String name,Context context) {

        return context.getSharedPreferences(name, Context.MODE_PRIVATE);

    }
    public static void clear(Context context, String name) {
        SharedPreferences prefs = getPreferences(name, context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();

    }public static void removeinfo(Context context, int jobnum, String name) {
        SharedPreferences prefs = getPreferences(name, context);
        SharedPreferences.Editor editor = prefs.edit();
        for(int i=0; i<jobnum; i++){
            editor.remove("jobname"+i);
            editor.remove("jobcode"+i);
            editor.remove("jobcareer"+i);
        }

        editor.commit();

    }
    public static void set_state(Context context, String key, Boolean value, String name) {
        SharedPreferences prefs = getPreferences(name,context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(key, value);
        editor.commit();

    }
    public static Boolean get_state(Context context, String key, String name) {
        SharedPreferences prefs = getPreferences(name,context);
        Boolean value = prefs.getBoolean(key, false);
        return value;
    }
    public static void set_state1(Context context, String key, Boolean value, String name) {
        SharedPreferences prefs = getPreferences(name,context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(key, value);
        editor.commit();

    }
    public static Boolean get_state1(Context context, String key, String name) {
        SharedPreferences prefs = getPreferences(name,context);
        Boolean value = prefs.getBoolean(key, true);
        return value;
    }
    public static void set_id(Context context, String key, String value, String name) {
        SharedPreferences prefs = getPreferences(name,context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();

    }
    public static String get_id(Context context, String key, String name) {
        SharedPreferences prefs = getPreferences(name,context);
        String value = prefs.getString(key, null);
        return value;
    }
    public static void set_token(Context context, String key, String value, String name) {
        SharedPreferences prefs = getPreferences(name,context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();

    }
    public static String get_token(Context context, String key, String name) {
        SharedPreferences prefs = getPreferences(name,context);
        String value = prefs.getString(key, null);
        return value;
    }
    public static void set_pw(Context context, String key, String value, String name) {
        SharedPreferences prefs = getPreferences(name,context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();

    }
    public static String get_pw(Context context, String key, String name) {
        SharedPreferences prefs = getPreferences(name,context);
        String value = prefs.getString(key, null);
        return value;
    }
    public static void setNone_email(Context context, String key, String value, String name) {
        SharedPreferences prefs = getPreferences(name,context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();

    }
    public static String getNone_email(Context context, String key, String name) {
        SharedPreferences prefs = getPreferences(name,context);
        String value = prefs.getString(key, "memberinfo");
        return value;
    } // 회원번호 이메일 저장

    public static void set_email(Context context, String key, String value, String name) {
        SharedPreferences prefs = getPreferences(name,context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }
    public static String get_email(Context context, String key, String name) {
        SharedPreferences prefs = getPreferences(name,context);
        String value = prefs.getString(key, null);
        return value;
    } // 일반이메일 저장
    public static void set_Password(Context context, String key, String value, String name) {
        SharedPreferences prefs = getPreferences(name,context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();

    }
    public static String get_Password(Context context, String key, String name) {
        SharedPreferences prefs = getPreferences(name,context);
        String value = prefs.getString(key, "memberinfo");
        return value;
    } // 비밀번호 이메일 저장

    public static void set_Nickname(Context context, String key, String value, String name) {
        SharedPreferences prefs = getPreferences(name,context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();

    }public static String get_Nickname(Context context, String key, String name) {
        SharedPreferences prefs = getPreferences(name,context);
        String value = prefs.getString(key, "memberinfo");
        return value;
    } // 닉네임 저장
    public static void set_Birth(Context context, String key, String value, String name) {
        SharedPreferences prefs = getPreferences(name,context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();

    }public static String get_Birth(Context context, String key, String name) {
        SharedPreferences prefs = getPreferences(name,context);
        String value = prefs.getString(key, "memberinfo");
        return value;
    } // 생년월일 저장

    public static void set_Gender(Context context, String key, String value, String name) {
        SharedPreferences prefs = getPreferences(name,context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();

    }
    public static String get_Getgender(Context context, String key, String name) {
        SharedPreferences prefs = getPreferences(name,context);
        String value = prefs.getString(key, "memberinfo");
        return value;
    } // 성별 저장

    public static void set_Phonenum(Context context, String key, String value, String name) {
        SharedPreferences prefs = getPreferences(name,context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();

    }public static String get_Phonenum(Context context, String key, String name) {
        SharedPreferences prefs = getPreferences(name,context);
        String value = prefs.getString(key, null);
        return value;
    } // 휴대전화번호 저장

    public static void set_Bankaccount(Context context, String key, String value, String name) {
        SharedPreferences prefs = getPreferences(name,context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();

    }public static String get_bankaccount(Context context, String key, String name) {
        SharedPreferences prefs = getPreferences(name,context);
        String value = prefs.getString(key, null);
        return value;
    } // 은행계좌 저장
    public static void set_Bankname(Context context, String key, String value, String name) {
        SharedPreferences prefs = getPreferences(name,context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();

    }public static String get_bankname(Context context, String key, String name) {
        SharedPreferences prefs = getPreferences(name,context);
        String value = prefs.getString(key, "memberinfo");
        return value;
    } // 은행이름 저장


    public static void set_introduce(Context context, String key, String value, String name) {
        SharedPreferences prefs = getPreferences(name,context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();

    }public static String get_introduce(Context context, String key, String name) {
        SharedPreferences prefs = getPreferences(name,context);
        String value = prefs.getString(key, null);
        return value;
    } // 한줄소개 저장
    public static void set_Jobname(Context context, String key, String value, String name) {
        SharedPreferences prefs = getPreferences(name,context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();

    }public static String get_Jobname(Context context, String key, String name) {
        SharedPreferences prefs = getPreferences(name,context);
        String value = prefs.getString(key, "memberinfo");
        return value;
    } // 한줄소개 저장
    public static void set_Jobcareer(Context context, String key, String value, String name) {
        SharedPreferences prefs = getPreferences(name,context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();

    }public static String get_Jobcareer(Context context, String key, String name) {
        SharedPreferences prefs = getPreferences(name,context);
        String value = prefs.getString(key, "memberinfo");
        return value;
    } // 한줄소개 저장
    public static void set_Jobcode(Context context, String key, String value, String name) {
        SharedPreferences prefs = getPreferences(name,context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();

    }public static String get_Jobcode(Context context, String key, String name) {
        SharedPreferences prefs = getPreferences(name,context);
        String value = prefs.getString(key, "0");
        return value;
    } // 한줄소개 저장
    public static void set_Hope_local_sido(Context context, String key, String value, String name) {
        SharedPreferences prefs = getPreferences(name,context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();

    }public static String get_Hope_local_sido(Context context, String key, String name) {
        SharedPreferences prefs = getPreferences(name,context);
        String value = prefs.getString(key, "memberinfo");
        return value;
    } // 한줄소개 저장
    public static void set_Hope_local_sigugun(Context context, String key, String value, String name) {
        SharedPreferences prefs = getPreferences(name,context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();

    }public static String get_Hope_local_sigugun(Context context, String key, String name) {
        SharedPreferences prefs = getPreferences(name,context);
        String value = prefs.getString(key, "memberinfo");
        return value;
    } // 한줄소개 저장
    public static void set_numofjob(Context context, String key, String value, String name) {
        SharedPreferences prefs = getPreferences(name,context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();

    }public static String get_numofjob(Context context, String key, String name) {
        SharedPreferences prefs = getPreferences(name,context);
        String value = prefs.getString(key, "memberinfo");
        return value;
    } // 한줄소개 저장

}
