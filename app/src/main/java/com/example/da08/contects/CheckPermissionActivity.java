package com.example.da08.contects;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

// 권한처리
public class CheckPermissionActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_permission);

        /*Build.VERSION.SDK_INT :설치 안드로이드폰의 api level 가져오기
             Build.VERSION_CODES 아래이 상수로 각 버전별 api level 을 적어주면 됨
             */
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // api레벨이 23 이상일 경우만 실행
            checkPermission();
        }else{
            // 아니면 그냥 run
            run();
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void checkPermission(){
        // 1  권한 체크 - 특정 권한이 있는지 시스템에 물어본다
        if (checkSelfPermission(Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_GRANTED) {
            run();
        }else{
            // 2 권한이 없으면 사용자의 권한을 달라고 요청
            String permissions[] = {Manifest.permission.READ_CONTACTS};
            requestPermissions(permissions ,REQ_PERMISSION );  // 권한을 요구하는 팝업이 사용자 화면에 노출 됨

        }
    }

    private final int REQ_PERMISSION = 100;
    // 3 사용자가 권한 체크 팝업에서 권한을 승인 또는 거절하면 아래 메소드가 호출
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQ_PERMISSION){
            // 3.1 사용자가 승인
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                run();
                // 3.2 사용자가 승인을 거절
            }else{
                cancel();
            }
        }
    }

    public void run(){
        Intent intent = new Intent(this, ContactActivity.class);
        startActivity(intent);
    }

    public void cancel() {
        Toast.makeText(this,"권한요청을 승인해줘야 앱 사용 가능함", Toast.LENGTH_SHORT).show();
        finish();
    }
}
