package org.androidtown.cok;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    ImageButton mbutton;
    String phoneNum;
    int[] arr = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private static final int REQUEST_READ_PHONE_STATE_PERMISSION = 225;
    Server server = new Server();
    public static Map<String, Integer> Ala = new HashMap<String, Integer>();
    String setCurDate;
    int cnt = 0;
    public static HashMap<String, String> location = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startActivity(new Intent(this, SplachActivity.class));
        locationSetup();
        phoneNum = getPhoneNum();
        setDate();
        new Thread() {
            @Override
            public void run() {
                System.out.println("!!!");
                HttpURLConnection con = server.getConnection("GET", "/phonenum/" + phoneNum);
                System.out.println("Connection done");
                try {
                    System.out.println("codeasd " + con.getResponseCode());
                    arrayToobject(server.readJson(con));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }.start();


        mbutton = (ImageButton) findViewById(R.id.m_button);
        mbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                Bundle bundle = new Bundle();
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);
            }
        });
    }

    private void setDate() {
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat SettingFormat = new SimpleDateFormat("yyyy-MM-dd");
        setCurDate = SettingFormat.format(date);
    }

    public String getPhoneNum() {
        TelephonyManager telephonyManager = (TelephonyManager) getApplicationContext().getSystemService(getApplicationContext().TELEPHONY_SERVICE);
        return telephonyManager.getLine1Number();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String outName = data.getStringExtra("title");
            String people = data.getStringExtra("people");
            String num = data.getStringExtra("number");
            String start = data.getStringExtra("start");
            String finish = data.getStringExtra("finish");

            int cur = calculate(start, setCurDate);
            if (outName.length() > 0 && num.length() > 0 && people.length() > 0) {
                InsertMap(start, finish);
                makefragment(phoneNum, outName, people, num, calculate(start, finish) + "", cur, finish);
                server.Insertproject(phoneNum, phoneNum, outName, num, start, finish, 0, Integer.parseInt(people));
                String title = phoneNum.replace("+", outName);
                server.maketable(title, VoteActivtiy.data);
            }
        }
    }

    public void InsertMap(String start, String finish) {
        String[] arr1 = start.split("-");
        String str;
        int tem = calculate(start, finish);
        int year = Integer.parseInt(arr1[0]), mon = Integer.parseInt(arr1[1]), day = Integer.parseInt(arr1[2]);
        for (int j = 0; j < tem; j++) {
            if (mon < 10) {
                if (day < 10)
                    str = year + "-" + "0" + mon + "-" + "0" + day;
                else
                    str = year + "-" + "0" + mon + "-" + day;
            } else {
                if (day < 10)
                    str = year + "-" + mon + "-" + "0" + day;
                else
                    str = year + "-" + mon + "-" + day;
            }
            VoteActivtiy.data.put(str, 0);

            if (mon == 2 && day == 28) {
                mon += 1;
                day = 1;
            } else if ((mon == 4 || mon == 6 || mon == 9 || mon == 11) && day == 30) {
                mon += 1;
                day = 1;
            } else if ((mon == 1 || mon == 3 || mon == 5 || mon == 7 || mon == 8 || mon == 10 || mon == 12) && day == 31) {
                if (mon == 12)
                    mon = 1;
                else
                    mon += 1;
                day = 1;
            } else
                day++;
        }
    }

    public int calculate(String start, String finish) {
        String[] arr1 = start.split("-");
        String[] arr2 = finish.split("-");
        int stem = 0, ftem = 0;
        for (int i = 0; i < Integer.parseInt(arr1[1]) - 1; i++) {
            stem += arr[i];
        }
        stem += Integer.parseInt(arr1[2]);
        for (int i = 0; i < Integer.parseInt(arr2[1]) - 1; i++) {
            ftem += arr[i];
        }
        ftem += Integer.parseInt(arr2[2]);
        if (ftem - stem >= 0)
            return ftem - stem;
        else
            return -1;
    }

    public void makefragment(final String master, final String outName, String peo, String num, String day, int cur, String finish) {
        new Thread() {
            @Override
            public void run() {
                HttpURLConnection conn = server.getConnection("GET", "/galarm/" + master + "/" + phoneNum + "/" + outName);
                try {
                    System.out.println("codef" + conn.getResponseCode());
                    setalarm(server.readJson(conn));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
        android.app.FragmentManager fm = getFragmentManager();
        android.app.FragmentTransaction tr = fm.beginTransaction();
        MainFragment cf = new MainFragment(MainActivity.this, Ala);
        Bundle bundle = new Bundle();
        bundle.putString("master", master);
        bundle.putString("Project", outName);
        bundle.putString("mCount", peo);
        bundle.putString("mcount", num);
        bundle.putString("day", day);
        bundle.putInt("cur", cur);
        bundle.putString("finish", finish);
        cf.setArguments(bundle);
        tr.add(R.id.frame, cf, "counter");
        tr.commit();
    }

    public void makeAlarm(String name, String finish) {
        int dis = 1;
        String[] spl = finish.split("-");
        Calendar calendar = Calendar.getInstance();
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent receiverIntent = new Intent(MainActivity.this, AlarmReceive.class);
        PendingIntent pendingIntent;
        int year = Integer.parseInt(spl[0]);
        int mon = Integer.parseInt(spl[1]);
        int date = Integer.parseInt(spl[2]);
        for (String key : Ala.keySet()) {
            if (Ala.get(key) == 1) {
                pendingIntent = PendingIntent.getBroadcast(MainActivity.this, cnt, receiverIntent, 0);
                calendar.set(year,mon-1,date-dis,13,0,0);
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                dis += 2;
                cnt++;
            }
        }
    }


    private void setalarm(JSONArray jsonArray) throws JSONException {
        JSONObject order = jsonArray.getJSONObject(0);
        Ala.put("1", order.getInt("alarm1"));
        Ala.put("3", order.getInt("alarm3"));
        Ala.put("5", order.getInt("alarm5"));
        Ala.put("7", order.getInt("alarm7"));
        makeAlarm(order.getString("project"), order.getString("finish"));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_READ_PHONE_STATE_PERMISSION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("TAG", "Permission Granted");
                    //Proceed to next steps
                } else {
                    Log.e("TAG", "Permission Denied");
                }
                return;
            }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void arrayToobject(JSONArray jsonArray) throws JSONException {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject order = jsonArray.getJSONObject(i);
            makefragment(order.getString("master"), order.getString("project"), order.getInt("people") + "", order.getInt("meeting") + "", calculate(order.getString("start"), order.getString("finish")) + "", calculate(order.getString("start"), setCurDate), order.getString("finish"));
        }
    }
    public void locationSetup(){
        location.put("문산","37.8547, 126.7874");
        location.put("파주"," 37.8154, 126.7924");
        location.put("양평중앙선", "37.5339, 127.5047");
        location.put("월릉","37.7962, 126.7926");
        location.put("금촌","37.7663, 126.7745");
        location.put("금릉","37.7514, 126.7654");
        location.put("운정","37.7256, 126.7671");
        location.put("탄현", "37.6944, 126.7611");
        location.put("일산", "37.6825, 126.7695");
        location.put("풍산", "37.6723, 126.7863");
        location.put("백마", "37.6585, 126.7944");
        location.put("곡산", "37.6459, 126.8019");
        location.put("대곡", "37.6316, 126.8112");
        location.put("능곡", "37.6187, 126.8208");
        location.put("행신", "37.6121, 126.8340");
        location.put("화정", "37.6346, 126.8326");
        location.put("수색", "37.5808, 126.8956");
        location.put("디지털미디어시티" ,"37.5779, 126.9003");
        location.put("가좌", "37.5686, 126.914844");
        location.put("홍대입구", "37.5580, 126.9254");
        location.put("서강대", "37.5521, 126.9353");
        location.put("공덕", "37.5426, 126.9521");
        location.put("신촌", "37.5598, 126.9423");
        location.put("삼각지", "37.5345, 126.9729");
        location.put("서울역", "37.5547, 126.9707");
        location.put("용산", "37.5299, 126.9647");
        location.put("이촌", "37.5225, 126.9738");
        location.put("서빙고" ,"37.5196, 126.9883");
        location.put("한남","37.5293, 127.0089");
        location.put("옥수", "37.5405, 127.0186");
        location.put("응봉", "37.5504, 127.0348");
        location.put("왕십리", "37.5611, 127.0354");
        location.put("청량리", "37.5803, 127.0469");
        location.put("회기", "37.5898, 127.0579");
        location.put("중랑", "37.5949, 127.0761");
        location.put("상봉", "37.5925, 127.0858");
        location.put("망우", "37.5993, 127.0923");
        location.put("양원", "37.6066, 127.1080");
        location.put("구리", "37.6032, 127.1432");
        location.put("도농", "37.6087, 127.1611");
        location.put("양정", "37.6042, 127.1946");
        location.put("덕소", "37.5869, 127.2088");
        location.put("도심", "37.5796, 127.2228");
        location.put("팔당", "37.5472, 127.2438");
        location.put("운길산", "37.5546, 127.3101");
        location.put("양수", "37.5460, 127.3288");
        location.put("신원", "37.5258, 127.3727");
        location.put("국수", "37.5162, 127.3994");
        location.put("아신", "37.5139, 127.4432");
        location.put("오빈", "37.5061, 127.4740");
        location.put("양평중앙선", "37.5339, 127.5047");
        location.put("원덕", "37.4685, 127.5474");
        location.put("용문", "37.4820, 127.5942");
        location.put("대화", "37.6759, 126.7477");
        location.put("주엽", "37.6701, 126.7612");
        location.put("정발산", "37.6597, 126.7732");
        location.put("마두","37.6521, 126.7776");
        location.put("백석", "37.6429, 126.7881");
        location.put("화정", "37.6346, 126.8326");
        location.put("원당", "37.6531, 126.8428");
        location.put("삼송", "37.6531, 126.8955");
        location.put("지축", "37.6480, 126.9136");
        location.put("구파발", "37.6365, 126.9188");
        location.put("연신내", "37.6190, 126.9212");
        location.put("불광", "37.6100, 126.9302");
        location.put("녹번", "37.6008, 126.9357");
        location.put("홍제", "37.5888, 126.9440");
        location.put("무악재", "37.5826, 126.9501");
        location.put("독립문", "37.5744, 126.9578");
        location.put("경복궁", "37.5758, 126.9735");
        location.put("안국", "37.5765, 126.9854");
        location.put("종로3가", "37.5704, 126.9921");
        location.put("을지로3가", "37.5662, 126.9926");
        location.put("충무로", "37.5609, 126.9935");
        location.put("동대입구", "37.5590, 127.0050");
        location.put("약수", "37.5649, 127.0146");
        location.put("금호", "37.5482, 127.0158");
        location.put("압구정", "37.5261, 127.0284");
        location.put("신사", "37.5162, 127.0196");
        location.put("잠원", "37.5129, 127.0115");
        location.put("고속터미널", "37.5049, 127.0049");
        location.put("교대", "37.4929, 127.0137");
        location.put("남부터미널", "37.4849, 127.0162");
        location.put("양재", "37.4845, 127.0340");
        location.put("매봉", "37.4870, 127.0469");
        location.put("도곡", "37.4909, 127.0553");
        location.put("대치", "37.4945, 127.0634");
        location.put("학여울", "37.4960, 127.0705");
        location.put("대청", "37.4935, 127.0794");
        location.put("일원", "37.4838, 127.0841");
        location.put("수서", "37.4876, 127.1011");
        location.put("가락시장", "37.4924, 127.1187");
        location.put("경찰병원", "37.4956, 127.1241");
        location.put("오금", "37.5020, 127.1282");
        location.put("암사", "37.5501, 1271275");
        location.put("천호", "37.5386, 1271235");
        location.put("강동구청", "37.5304, 127.1205");
        location.put("몽촌토성", "37.5176, 127.1127");
        location.put("잠실", "37.5132, 127.1001");
        location.put("석촌", "37.5054, 127.1069");
        location.put("송파", "37.4997, 127.1122");
        location.put("문정","37.4860, 127.1224");
        location.put("장지", "37.4787, 127.1261");
        location.put("복정", "37.4697, 127.1266");
        location.put("산성", "37.4565, 127.1500");
        location.put("남한산성입구", "37.4515, 127.1598");
        location.put("단대오거리", "37.4450, 127.1567");
        location.put("신흥", "37.4409, 127.1474");
        location.put("수진", "37.4376, 127.1409");
        location.put("모란", "37.4321, 127.1290");
        location.put("전대에버랜드", "37.2854, 127.2194");
        location.put("둔전", "37.2672, 127.2135");
        location.put("보평", "37.2589, 127.2185");
        location.put("고진", "37.2446, 127.2141");
        location.put("운동장송담대", "37.2379, 127.2090");
        location.put("김량장", "37.2372, 127.1986");
        location.put("명지대", "37.2380, 127.1902");
        location.put("시청용인대", "37.2394, 127.1788");
        location.put("삼가", "37.2420, 127.1681");
        location.put("초당", "37.2610, 127.1590");
        location.put("동백", "37.2691, 127.1527");
        location.put("어정", "37.2753, 127.1437");
        location.put("지석", "37.2696, 127.1366");
        location.put("강남대", "37.2701, 127.1260");
        location.put("기흥", "37.2757, 127.1158");
        location.put("서울숲", "37.5436, 127.0446");
        location.put("압구정로데오", "37.5274, 127.0405");
        location.put("강남구청", "37.5171, 127.0412");
        location.put("선정릉", "37.5103, 127.0437");
        location.put("선릉", "37.5045, 127.0489");
        location.put("합정" ,"37.5495, 126.9140");
        location.put("한티", "37.4961,127.0528");
        location.put("구룡", "37.4870, 127.0594");
        location.put("개포동", "37.4891, 127.0663");
        location.put("대모산입구", "37.4913, 127.0727");
        location.put("가천대", "37.4487, 127.1267");
        location.put("태평" ,"37.4398, 127.1277");
        location.put("야탑", "37.4113, 127.1286");
        location.put("이매", "37.3958, 127.1282");
        location.put("서현", "37.3849, 127.1232");
        location.put("수내", "37.3784, 127.1141");
        location.put("정자", "37.3660, 127.1080");
        location.put("미금", "37.3500, 127.1089");
        location.put("오리", "37.3398, 127.1089");
        location.put("죽전", "37.3245, 127.1073");
        location.put("보정", "37.3127, 127.1081");
        location.put("구성", "37.2990, 127.1056");
        location.put("신갈", "37.2861, 127.1112");
        location.put("상갈", "37.2618, 127.1087");
        location.put("청명", "37.2594, 127.0789");
        location.put("영통", "37.2538, 127.0738");
        location.put("망포", "37.2458, 127.0573");
        location.put("매탄권선", "37.2524, 127.0408");
        location.put("수원시청", "37.2619, 127.0306");
        location.put("매교", "37.2654, 127.0156");
        location.put("수원", "37.2661, 126.9998");
        location.put("강남", "37.4979, 127.0275");
        location.put("양재시민의숲", "37.4700, 127.0383");
        location.put("청계산입구", "37.4482, 127.0547");
        location.put("판교" ,"37.3948, 127.1111");
        location.put("춘천", "37.8844, 127.7165");
        location.put("남춘천", "37.8640, 127.7237");
        location.put("김유정", "37.8184, 127.7142");
        location.put("강촌", "37.8056, 127.6339");
        location.put("백양리", "37.8308, 127.5890");
        location.put("굴봉산", "37.8321, 127.5577");
        location.put("가평", "37.8145, 127.5107");
        location.put("상천", "37.7704, 127.4542");
        location.put("청평", "37.7355, 127.4265");
        location.put("대성리", "37.6839, 127.3791");
        location.put("마석", "37.6523, 127.3117");
        location.put("천마산", "37.6590, 127.2857");
        location.put("평내호평", "37.6531, 127.2444");
        location.put("금곡", "37.6374, 127.2079");
        location.put("사릉", "37.6511, 127.1768");
        location.put("퇴계원", "37.6483, 127.1438");
        location.put("별내", "37.6422, 127.1272");
        location.put("갈매", "37.6341, 127.1147");
        location.put("신내", "37.6128, 127.1032");
    }
}