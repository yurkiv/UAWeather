package com.yurkiv.weatherforecast;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		
		
		TextView feedback = (TextView) findViewById(R.id.mailto);
		feedback.setText(Html.fromHtml("<a href=\"mailto:jurkiw.misha@gmail.com\">jurkiw.misha@gmail.com</a>"));
		feedback.setMovementMethod(LinkMovementMethod.getInstance());
		
		TextView sourse = (TextView) findViewById(R.id.sourse);
		sourse.setText(Html.fromHtml("<a href=\"https://github.com/zloysalat/UAWeather\">https://github.com/zloysalat/UAWeather</a>"));
		sourse.setMovementMethod(LinkMovementMethod.getInstance());
		
		ImageView img = (ImageView)findViewById(R.id.data_sourse);
		img.setOnClickListener(new View.OnClickListener(){
		    public void onClick(View v){
		        Intent intent = new Intent();
		        intent.setAction(Intent.ACTION_VIEW);
		        intent.addCategory(Intent.CATEGORY_BROWSABLE);
		        intent.setData(Uri.parse("http://www.weather.ua/"));
		        startActivity(intent);
		    }
		});
		
	}
}
