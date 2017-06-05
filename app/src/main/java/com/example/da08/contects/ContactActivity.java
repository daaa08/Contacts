package com.example.da08.contects;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.da08.contects.domain.PhoneData;

import java.util.ArrayList;
import java.util.List;


// 데이터를 가져옴
public class ContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        for(PhoneData phoneData : getContacts()){
            Log.i("Contacts", "이름" + phoneData.getName() + ", tel=" + phoneData.getTel());
        }
    }

    public List<PhoneData> getContacts() {

        // 데이터베이스 혹은 content resolve를 통해 가져온 데이터를 적재할 데이터 저장소를 먼저 만든다
        List<PhoneData> datas = new ArrayList<>();

        // 일종의 db 관리툴
        // 전화번호부에 이미 만들어져있는 Content Provider를 불러옴
        // 데이터를 가져올 수 있다
        ContentResolver resolver = getContentResolver();

        // 1 데이터 컨텐츠의 URI (자원의 주소)를 정의  ( 전화번호 Uri는 따로있음)
        Uri phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;  // 테이블 명

        // 2 데이터에서 가져올 컬럼명을 정의
        String projections[] = {ContactsContract.CommonDataKinds.Phone.CONTACT_ID, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER};    // id와 화면에 표시되는 이름 전화번호

        // 3 Content Resolver로 쿼리르 날려서 데이터를 가져온다
        Cursor cursor = resolver.query(phoneUri, projections, null, null, null); // 데이터를 요구할때 날리는 요청문같은것 (질의)

        // 4 반복문을 통해 cursor에 담겨있는 데이터를 하나씩 추출한다
        if (cursor != null) {
            while (cursor.moveToNext()) {  // cursor는 내가 가져올 데이터를 가르키고있는것 (cursor.moveToNext는 cursor를 row로 움직여 줌)
                // 4.1 위에 정의한 프로젝션의 컬렴명으로 cursor에 있는 인덱스값을 조회하고
                int idIndex = cursor.getColumnIndex(projections[0]);
                // 4.2 해당 index를 사용해서 실제값을 가져온다
                int id = cursor.getInt(idIndex);

                int nameIndex = cursor.getColumnIndex(projections[1]);
                String name = cursor.getString(nameIndex);


                int telIndex = cursor.getColumnIndex(projections[2]);
                String tel = cursor.getString(telIndex);  // 이름으로 실제 값을 꺼냄

                // 5 내가 설계한 데이터 클래스에 담아준다
                PhoneData phoneData = new PhoneData();
                phoneData.setId(id);
                phoneData.setName(name);
                phoneData.setTel(tel);

                // 6 여러개의 객체를 담을 수있는 저장소에 적재한다

                datas.add(phoneData);

            }

        }
            return datas;
    }

}



