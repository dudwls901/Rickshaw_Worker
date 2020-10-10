package kr.co.ilg.activity.login;

import android.content.Intent;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.kakao.auth.ISessionCallback;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;

import kr.co.ilg.activity.findwork.MainActivity;

public class SessionCallback extends AppCompatActivity implements ISessionCallback {
    // 로그인에 성공한 상태


    @Override

    public void onSessionOpened() {

        requestMe();

    }



    // 로그인에 실패한 상태

    @Override

    public void onSessionOpenFailed(KakaoException exception) {


        Log.e("SessionCallback :: ", "onSessionOpenFailed : " + exception.getMessage());

    }



    // 사용자 정보 요청

    public void requestMe() {

        // 사용자정보 요청 결과에 대한 Callback

        UserManagement.requestMe(new MeResponseCallback() {

            // 세션 오픈 실패. 세션이 삭제된 경우,

            @Override

            public void onSessionClosed(ErrorResult errorResult) {

                Log.e("SessionCallback :: ", "onSessionClosed : " + errorResult.getErrorMessage());

            }



            // 회원이 아닌 경우,

            @Override

            public void onNotSignedUp() {

                Log.e("SessionCallback :: ", "onNotSignedUp");

            }



            // 사용자정보 요청에 성공한 경우,

            @Override

            public void onSuccess(UserProfile userProfile) {

                Log.e("SessionCallback :: ", "onSuccess");



                String nickname = userProfile.getNickname();

                String email = userProfile.getEmail();

                String profileImagePath = userProfile.getProfileImagePath();

                String thumnailPath = userProfile.getThumbnailImagePath();

                String UUID = userProfile.getUUID();

                long id = userProfile.getId();



                Log.e("Profile : ", nickname + "");

                Log.e("Profile : ", email + "");

                Log.e("Profile : ", profileImagePath  + "");

                Log.e("Profile : ", thumnailPath + "");

                Log.e("Profile : ", UUID + "");

                Log.e("Profile : ", id + "");


            }



            // 사용자 정보 요청 실패

            @Override

            public void onFailure(ErrorResult errorResult) {

                Log.e("SessionCallback :: ", "onFailure : " + errorResult.getErrorMessage());

            }

        });

    }
}
