## Runtime Permission

- 특정 기능을 사용하기위해 해주는 권한
- 안드로이드 6.0 마시멜로우버전 이상부터는 앱에서 해당 권한이 필요할때마다 사용자로부터 권한을 허가받도록 변경 됨

```java
/*Build.VERSION.SDK_INT :설치 안드로이드폰의 api level 가져오기
             Build.VERSION_CODES 아래이 상수로 각 버전별 api level 을 적어주면 됨
             */
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // api레벨이 23(마시멜로우)이상일 경우만 실행
            checkPermission();
        }else{
            // 아니면 그냥 run
            run();
        }
private final int REQ_PERMISSION = 100; // 요청 코드 세팅
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
@Override
public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if(requestCode == REQ_PERMISSION){
        // 3.1 사용자가 승인했을 경우
        if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
            run();
            // 3.2 사용자가 승인을 거절했을 경우
        }else{
            cancel();
        }
    }
}
```

![enter image description here](http://cfile27.uf.tistory.com/image/25320A3356C6767F0C170D)

> Manifest에 uses-permission 등록해줘야 함
```java
<uses-permission android:name="android.permission.READ_CONTACTS" />  // 전화번호부 읽기 권한
```

## ContentResolver

- 구조화된 데이터 세트의 액세스를 관리
- 한 프로세스의 데이터에 다른 프로세스에서 실행 중인 코드를 연결
- Context에 있는 ContentResolver 객체를 사용

```java
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
```
