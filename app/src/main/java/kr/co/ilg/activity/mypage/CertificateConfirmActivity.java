package kr.co.ilg.activity.mypage;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.example.capstone.R;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.android.volley.Response;


public class CertificateConfirmActivity extends AppCompatActivity implements View.OnClickListener {

    TextView nextTimeTV;
    Button uploadBtn;
    ImageButton cameraBtn;
    ImageView certificateImg;
    String worker_email, worker_pw, worker_name, worker_gender, worker_birth, worker_phonenum, worker_certicipate;
    final String TAG = getClass().getSimpleName();
    final static int TAKE_PICTURE = 1;
    boolean takePicture = false;
    String phpUrl = "http://14.63.220.50/ImageUpload.php";
    URL url;
    String mCurrentPhotoPath;
    static final int REQUEST_TAKE_PHOTO = 1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.certificate_picture);

        Intent receiver = getIntent();
        worker_email = receiver.getExtras().getString("worker_email");
        worker_pw = receiver.getExtras().getString("worker_pw");
        worker_name = receiver.getExtras().getString("worker_name");
        worker_gender = receiver.getExtras().getString("worker_gender");
        worker_birth = receiver.getExtras().getString("worker_birth");
        worker_phonenum = receiver.getExtras().getString("worker_phonenum");

        uploadBtn = findViewById(R.id.uploadBtn);
        nextTimeTV = findViewById(R.id.nextTimeTV);
        certificateImg = findViewById(R.id.certificateImg);
        cameraBtn = findViewById(R.id.cameraBtn);

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(takePicture) {
                    Intent intent = new Intent(CertificateConfirmActivity.this, LocalSelectActivity.class);
                    intent.putExtra("worker_email", worker_email);
                    intent.putExtra("worker_pw", worker_pw);
                    intent.putExtra("worker_gender", worker_gender);
                    intent.putExtra("worker_name", worker_name);
                    intent.putExtra("worker_birth", worker_birth);
                    intent.putExtra("worker_phonenum", worker_phonenum);
                    intent.putExtra("worker_certicipate", worker_certicipate);

                    //이미지 업로드 하기
                    //이미지 총 경로(서버에 쓸 거)    mCurrentPhotoPath
                    //이미지 이름(db에 넘어갈 것)      certicipate
                    atask at = new atask();
                    at.execute(mCurrentPhotoPath);

                    startActivity(intent);
                } else {
                    Toast.makeText(CertificateConfirmActivity.this, "이수증 사진을 촬영해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        nextTimeTV.setPaintFlags(nextTimeTV.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        nextTimeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CertificateConfirmActivity.this, LocalSelectActivity.class);
                intent.putExtra("worker_email", worker_email);
                intent.putExtra("worker_pw", worker_pw);
                intent.putExtra("worker_gender", worker_gender);
                intent.putExtra("worker_name", worker_name);
                intent.putExtra("worker_birth", worker_birth);
                intent.putExtra("worker_phonenum", worker_phonenum);
                intent.putExtra("worker_certicipate", "noImage");
                //사진 건너뛰기 시 worker_certicipate에는 "noImage"가 들어감;
                startActivity(intent);
            }
        });

        // 카메라 버튼에 리스너 추가
        cameraBtn.setOnClickListener(this);

        // 6.0 마쉬멜로우 이상일 경우에는 권한 체크 후 권한 요청
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "권한 설정 완료");
            } else {
                Log.d(TAG, "권한 설정 요청");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
    }

    // 권한 요청
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "onRequestPermissionsResult");
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
        }
    }

    // 버튼 onClick리스터 처리부분
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cameraBtn:
                // 카메라 앱을 여는 소스
                dispatchTakePictureIntent();
//                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(cameraIntent, TAKE_PICTURE);
                break;
        }
    }

    // 카메라로 촬영한 영상을 가져오는 부분
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        try {
            switch (requestCode) {
                case REQUEST_TAKE_PHOTO: {
                    if (resultCode == RESULT_OK) {
                        File file = new File(mCurrentPhotoPath);
                        Bitmap bitmap = MediaStore.Images.Media
                                .getBitmap(getContentResolver(), Uri.fromFile(file));

                        // 파일 크기 줄이기
//                        BitmapFactory.Options options = new BitmapFactory.Options();
//                        options.inSampleSize = 8;
//                        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
//                        imageView.setImageBitmap(bitmap);

                        if (bitmap != null) {
                            ExifInterface ei = new ExifInterface(mCurrentPhotoPath);
                            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                                    ExifInterface.ORIENTATION_UNDEFINED);

                            Bitmap rotatedBitmap = null;
                            // 각도 처리
                            switch (orientation) {

                                case ExifInterface.ORIENTATION_ROTATE_90:
                                    rotatedBitmap = rotateImage(bitmap, 90);
                                    break;

                                case ExifInterface.ORIENTATION_ROTATE_180:
                                    rotatedBitmap = rotateImage(bitmap, 180);
                                    break;

                                case ExifInterface.ORIENTATION_ROTATE_270:
                                    rotatedBitmap = rotateImage(bitmap, 270);
                                    break;

                                case ExifInterface.ORIENTATION_NORMAL:
                                default:
                                    rotatedBitmap = bitmap;
                            }
                            takePicture = true;
                            certificateImg.setImageBitmap(rotatedBitmap);
                        } else{
                            takePicture = false;
                        }
                    }
                    break;
                }
            }

        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    // 촬영한 이미지 파일로 저장
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */


        );

        // Save a file: path for use with ACTION_VIEW intents

        mCurrentPhotoPath = image.getAbsolutePath();
        String[] array = mCurrentPhotoPath.split("/");
        worker_certicipate = array[9];
        Log.d("cccccccccc", mCurrentPhotoPath);
        return image;
    }

    // 카메라 앱 실행
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.capstone.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    // 미리보기 이미지 회전
    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    private class atask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            String imgPath = strings[0]; //이미지파일 경로 받아오기

            DataOutputStream os = null;
            String border = "#";
            String two = "--";
            String nl = "\n";
            byte[] buf = null;
            int bufSz = 1024 * 1024, ret = 0;
            File imgFile = new File(imgPath);
            Log.d("ccccisfile", String.valueOf(imgFile.isFile()));
            if (imgFile.isFile()) {
                Log.d("cccc됐놔용?", "1");
                try {
                    Log.d("cccc됐냐고?", "2");
                    FileInputStream fis = new FileInputStream(imgFile);
                    url = new URL(phpUrl);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + border);
                    os = new DataOutputStream(conn.getOutputStream());
                    os.writeBytes(two + border + nl);
                    os.writeBytes("Content-Disposition: form-data; name=\"phone\";filename=\"" + imgPath + "\"" + nl);
                    os.writeBytes(nl);
                    Log.d("cccc됐냐고?", "3");
                    do {
                        if (buf == null) buf = new byte[bufSz];
                        ret = fis.read(buf, 0, bufSz); //그림파일 입력
                        os.write(buf, 0, bufSz);
                    } while (ret > 0);

                    os.writeBytes(nl + two + border + two + nl); //--#  --#--
                    conn.getResponseCode();
                    fis.close();
                    os.close();
                    Log.d("cccc됐니?", "4");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }

            return "=>imgUploading OK";
        }

        protected void onPostExecute(String string) {
            Log.d("cccccc", string);
        }
    }
}
