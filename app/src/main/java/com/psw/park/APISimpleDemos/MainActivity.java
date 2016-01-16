package com.psw.park.APISimpleDemos;

import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/*
*     주요 class의 사용법을 예제로 돌려본다.
*     가능한 MainActivity class에 몰아서 구현했다.
*     코딩하기 편할 수는 있어도 가독성은 나쁘다.
*     java에서 class 별로 나누어야 하는 이유를 몸소 느끼게 하기위해서 이렇게 만들었다.
*
* */
public class MainActivity extends AppCompatActivity {
    ListView lstMain = null;
    ArrayList lst    =  new <String>ArrayList();

    ArrayAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setListView();
    }

    // ListView를 화면에 구성한다.
    public void setListView(){
        lstMain = (ListView) findViewById(R.id.listview);

        // Android에서 제공해주는 템플릿으로 ArrayAdapter를 만들고 setAdapter()로 lstMain과
        // 연동한다.
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lst);
        lstMain.setAdapter(adapter);

        // 리스트에 보여줄 항목을 String 배열로 선언
        String [] EXAMPLES = {
                "File",
                "SharedPrefernece",
                "Log",
                "serializable",
                "Enviroment",
                "assets",
                "Handler",
                "AsyncTask"
        };

        // EXAMPLES 리스트의 항목을 ListView에 추가한다.
        for(int i = 0; i < EXAMPLES.length;i++){
            addListItem(EXAMPLES[i]);
        }

        lstMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 리스트를 클릭했을 때, 몇 번째인지 알려준다.
                doAction(position);
            }
        });

    }

    // 선택된 리스트에 맞는 메소드를 호출한다.
    void doAction(int nIndex){
        switch (nIndex){
            case 0:
                doFileExample();
                break;

            case 1:
                doPreferenceExample();
                break;

            case 2:
                doLogExample();
                break;

            case 3:
                doSerialExample();
                break;

            case 4:
                doEnviromentExample();
                break;

            case 5:
                doAssetExample();
                break;

            case 6:
                doHanlderExample();
                break;

            case 7:
                doAsyncTaskExample();
                break;

            default:
                doNothing();
        }
    }



    // ListView에 Item을 저장하고 리스트화면을 갱신한다.
    public void addListItem(String sMessage){
        lst.add(sMessage);

        // 화면을 갱신한다. -> 항목이 많을 때는 상당히 느려지는 원인임.
        // 일반적으로는 모든 항목을 Add 한 후에 한번 갱신하는 구조가 좋다.
        adapter.notifyDataSetChanged();
    }

    /*
    *
    * 여기서 부터 Example을 실행하는 메소드를 구현하도록 하자!!
    *
    * */

    // File을 읽고 써본다. sddcard 영역에
    private void doFileExample() {
        // 생성할 디렉토리의 절대경로명을 만든다.
        String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/campandroid";
        String file_name = file_path + "/test.txt";

        // 파일쓰기
        try {
            File dir = new File(file_path);
            if(!dir.exists())
                dir.mkdirs();

            File file = new File(file_name);

            // 파일이 존재하지 않으면 파일을 생성한다.
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("테스트파일을 저장합니다.");
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // 파일읽기
        try {
            BufferedReader br = null;
            String response = null;

            StringBuffer output = new StringBuffer();
            br = new BufferedReader(new FileReader(file_name));
            String line = "";
            while ((line = br.readLine()) != null) {
                output.append(line +"\n");
            }
            response = output.toString();
            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // 파일지우기
        File file = new File(file_name);
        if (file.exists()) {
            file.delete();
        }
    }

    // preference 예제를 보여준다.
    private void  doPreferenceExample(){
        String MY_IDENTY = "JUST_TEST_NOW";

        // SharedPrefernces를 저장하기 위해 editor 클래스를 edit() 메소드를 이용해서 가져온다.
        SharedPreferences.Editor editor = getSharedPreferences(MY_IDENTY, MODE_PRIVATE).edit();

        // 값을 저장한다.
        editor.putString("name", "DR.Dre");
        editor.putInt("age", 51);

        // commit()메소드를 호출하여 적용한다.
        editor.commit();

        // SharedPrefernces를 가져온다.
        SharedPreferences prefs = getSharedPreferences(MY_IDENTY, MODE_PRIVATE);

        // 값을 가져온다.
        String name = prefs.getString("name", "No name defined");
        int age = prefs.getInt("age", 0);

        Toast.makeText(getApplicationContext(), name + ":" + age, Toast.LENGTH_LONG).show();
    }

    // Log 예제를 보여준다. <- 왜 쓰는가? 버그잡으려고!!
    private void doLogExample() {
        for(int i = 0; i < 10; i++){
            try{
                int divdednum = 5 - i;
                int d = 0 / divdednum;
                Log.d("TEST", "index is = " + i);

            } catch(Exception e){
                e.printStackTrace();
                return;
            }
        }
    }

    // 직렬화 예제
    static int nCount = 0;
    private void doSerialExample() {
        Animal ani = null;

        // 객체를 읽어온다.
        try{
            ani = Util.LoadData(getApplicationContext());
        } catch(Exception e){
            e.printStackTrace();
        }

        // 읽어오지못했다면 새롭게 생성
        // 읽어왔다면 울어라!
        if(ani == null){
            ani = new Animal("야옹이");
        } else {
            ani.Crying(getApplicationContext());
        }

        // Animal 이름을 바꾸어본다.
        ani.sName = "야옹이-" + Integer.toString(nCount++);

        // 객체를 저장한다.
        try{
            Util.SaveData(getApplicationContext(), (Object)ani);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    // 디렉토리관련 환경설정을 가져온다.
    private void doEnviromentExample() {
        File path  = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);

        File path2 = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS);

        File path3 = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS);

        // 각 디렉토리 정보를 변수에 저장한다.
        String sPicDirectory  = path.getAbsolutePath();
        String sDownDirectory = path2.getAbsolutePath();
        String sDocDirectory  = path3.getAbsolutePath();

        // 출력할 문자열을 만든다.
        String sMessage = sPicDirectory + "\n" + sDownDirectory + "\n" + sDocDirectory;
        Toast.makeText(getApplicationContext(), sMessage, Toast.LENGTH_LONG).show();
    }

    // Assets을 이용하는 방법을 알아본다.
    private void doAssetExample() {

        AssetManager assetManager = getAssets();

        // assets 폴더의 리스트를 가져오기
        try {
            String[] files = assetManager.list("");

            String sList = "";
            for(int i=0; i<files.length; i++){
                sList = sList + files[i] + "\n";
            }
            Toast.makeText(getApplicationContext(), sList, Toast.LENGTH_LONG).show();

        } catch (IOException e1) {
            e1.printStackTrace();
        }

        // 파일읽어오기
        InputStream input;
        try {
            input = assetManager.open("simple.txt");

            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();

            String sMessage = new String(buffer);
            Toast.makeText(getApplicationContext(), sMessage, Toast.LENGTH_LONG).show();

        } catch (IOException e) {

            e.printStackTrace();
        }

    }

    // handler 예제 -> 비동기적 호출(짧은작업 : UI 액세스 처리가능함!!!!!!!!)
    Handler handler;
    private void doHanlderExample(){
        handler = new Handler() {
            // sendEmptyMessage???() 메소드실행하면 호출되는 비동기 메소드
            public void handleMessage(Message msg) {
                String sMessage = String.format("count-->%d", nCount++);
                Toast.makeText(getApplicationContext(), sMessage, Toast.LENGTH_LONG).show();

                TextView tv = (TextView)findViewById(R.id.textView);
                tv.setText(sMessage);

                if(nCount < 10){
                    // 1000이 1초: n초후 호출
                    handler.sendEmptyMessageDelayed(0,3000);
                }
            }
        };
        // 바로호출
        handler.sendEmptyMessage(0);
    }

    // AsyncTask 예제 -> 비동기호출(긴작업: UI 액세스처리 생각보다 까다로움)
    // 심지어 Activity가 종료되어도 실행됨
    private void doAsyncTaskExample() {
        new LongWork().execute();
    }

    // AyncTask class
    private class LongWork extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            for (int i = 0; i < 15; i++) {
                try {
                    Thread.sleep(1000);
                    Log.d("TEST", Integer.toString(i));
                } catch (InterruptedException e) {
                    Thread.interrupted();
                }
            }
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }

    private void doNothing() {
    }
}
