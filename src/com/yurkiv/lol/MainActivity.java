package com.yurkiv.lol;
//package com.yurkiv.lol;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.Spinner;
//import android.widget.Toast;
//
//public class MainActivity extends Activity implements OnClickListener {
//
//	String[] data = {"one", "two", "three", "four", "five"};
//
//    /** Called when the activity is first created. */
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main);
//
//        // �������
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        Spinner spinner = (Spinner) findViewById(R.id.spinner);
//        spinner.setAdapter(adapter);
//        // ���������
//        spinner.setPrompt("Title");
//        // �������� �������
//        spinner.setSelection(2);
//        // ������������� ���������� �������
//        spinner.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view, int position,
//					long id) {
//				// ���������� ������� �������� ��������
//		        Toast.makeText(getBaseContext(), "Position = " + position, Toast.LENGTH_SHORT).show();
//			}
//	    });
//
//        Button button =(Button) findViewById(R.id.button);
//        button.setOnClickListener(this);
//
//
//    }
//
//	@Override
//	public void onClick(View arg0) {
//		Intent intent=new Intent("com.lol.getforecast");
//		startActivity(intent);
//	}
//}
