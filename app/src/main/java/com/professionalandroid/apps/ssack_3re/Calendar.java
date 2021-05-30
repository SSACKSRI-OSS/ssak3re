package com.professionalandroid.apps.ssack_3re;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Calendar extends AppCompatActivity implements Button.OnClickListener {

    public String fname = null;
    public String str = null;
    public CalendarView calendarView;
    public Button r_button,d_button,s_button;
    public TextView dateTextView,memoView,calName;
    public EditText editText;
    ImageView Abutton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        calendarView = findViewById(R.id.calendarView);
        dateTextView = findViewById(R.id.dateTextView);
        s_button = findViewById(R.id.s_button);
        d_button = findViewById(R.id.d_button);
        r_button = findViewById(R.id.r_button);
        memoView = findViewById(R.id.memoView);
        calName = findViewById(R.id.calName);
        editText = findViewById(R.id.editText);
        Abutton = (ImageView) findViewById(R.id.alarmButton);
        Abutton.setOnClickListener(this);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override   // 날짜가 변경될 때 이벤트를 받기 위한 리스너
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                // 리스너로부터 받은 날짜값을 날짜정보를 보여주는 dianryDateText에 넣어준다.
                dateTextView.setText(String.format("%d / %d / %d", year, month+1, dayOfMonth));
                editText.setText(""); // null값 오류방지

                                                      // 날짜눌렀을때     (가시성)
                dateTextView.setVisibility(View.VISIBLE);  // 날짜텍스트칸 ON
                s_button.setVisibility(View.VISIBLE);       // 저장버튼    ON
                editText.setVisibility(View.VISIBLE);       // 메모작성칸   ON
                memoView.setVisibility(View.INVISIBLE);     // 메모기록칸   OFF
                r_button.setVisibility(View.INVISIBLE);     // 수정버튼     OFF
                d_button.setVisibility(View.INVISIBLE);     // 삭제버튼     OFF


                checkDay(year , month, dayOfMonth);
            }
        });

        s_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDiary(fname);
                str = editText.getText().toString(); //
                memoView.setText(str);
                s_button.setVisibility(View.INVISIBLE);
                editText.setVisibility(View.INVISIBLE);
                r_button.setVisibility(View.VISIBLE);
                d_button.setVisibility(View.VISIBLE);
                memoView.setVisibility(View.VISIBLE);

            }
        });
    }

    public void checkDay(int cYear, int cMonth, int cDay) {
        fname = cYear + "-" + (cMonth+1) + "-" + cDay + ".txt"; // 저장할 파일이름을 날짜로 설정
        FileInputStream fis = null; // FileStream fis 변수

        try{
            fis = openFileInput(fname);

            byte[] fileData = new byte[fis.available()]; // 입력 스트림에서 읽을 수 있는 바이트 개수만큼 배열 생성
            fis.read(fileData); // fileData 배열에 저장된 데이터를 읽는다.
            fis.close();

            str = new String(fileData); // 메모장에 표현하기 위해 string type 객체 생성

            editText.setVisibility(View.INVISIBLE); // 메모 작성칸 보기 X
            memoView.setText(str);                  // str값을 갖는 새로운 문자열 memoView에 넣어준다.
            memoView.setVisibility(View.VISIBLE);   // 메모칸 보기  O

            s_button.setVisibility(View.INVISIBLE); // 저장버튼 보기 X
            r_button.setVisibility(View.VISIBLE);   // 수정버튼 보기 O
            d_button.setVisibility(View.VISIBLE);   // 삭제버튼 보기 O

            r_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {            // 수정버튼 누를 시
                    editText.setVisibility(View.VISIBLE);   // 메모 작성칸 보기 O
                    editText.setText(str);                  //
                    memoView.setVisibility(View.INVISIBLE); // 메모기록칸 보기 X

                    s_button.setVisibility(View.VISIBLE);
                    r_button.setVisibility(View.INVISIBLE);
                    d_button.setVisibility(View.INVISIBLE);
                    memoView.setText(editText.getText());
                }

            });

            d_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {            // 삭제버튼 누를 시
                    memoView.setVisibility(View.INVISIBLE);
                    editText.setText("");
                    editText.setVisibility(View.VISIBLE);
                    s_button.setVisibility(View.VISIBLE);
                    r_button.setVisibility(View.INVISIBLE);
                    d_button.setVisibility(View.INVISIBLE);
                    removeDiary(fname);
                }
            });

            if( memoView.getText() == null ) {
                memoView.setVisibility(View.INVISIBLE);
                dateTextView.setVisibility(View.VISIBLE);
                s_button.setVisibility(View.VISIBLE);
                r_button.setVisibility(View.INVISIBLE);
                d_button.setVisibility(View.INVISIBLE);
                editText.setVisibility(View.VISIBLE);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @SuppressLint("WrongConstant")
    public void removeDiary(String readDay){
        FileOutputStream fos = null;

        try {
            fos = openFileOutput(readDay, MODE_NO_LOCALIZED_COLLATORS);
            String content = null;
            fos.write((content).getBytes());
            fos.close();

        } catch (Exception e){
            e.printStackTrace();
        }

        if ( memoView.getText().toString().equals("") );
    }

    @SuppressLint("WrongConstant")
    public void saveDiary(String readDay){
        FileOutputStream fos = null;

        try {
            fos = openFileOutput(readDay, MODE_NO_LOCALIZED_COLLATORS);
            String content = editText.getText().toString();
            fos.write((content).getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        Intent Aintent = new Intent(Calendar.this, AlarmSet.class);
        startActivity(Aintent);
    }
}