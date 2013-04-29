package com.example.fragmentpractice;

import java.util.ArrayList;

import android.app.ListActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SendMessage extends ListActivity{
	
	private ArrayList<String> texts = new ArrayList<String>();
	private ListView listview;
	private AdapterView.OnItemClickListener clicklistener;
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
	    setContentView(R.layout.activity_send_message);
	    
	    texts.add("Sample Text");
	    
	    if (texts == null) {
			String[] textsStrings = new String[] { "No Texts"};
			setListAdapter(new ArrayAdapter<String>(this, R.layout.list_layout,	textsStrings));
		} else {
			String[] textsStrings = new String[texts.size()];
			for (int i = 0; i < texts.size(); ++i) {
				textsStrings[i] = texts.get(i);
			}
			setListAdapter(new ArrayAdapter<String>(this, R.layout.list_layout,	textsStrings));
			listview = (ListView) findViewById(android.R.id.list);
			clicklistener = new AdapterView.OnItemClickListener() {
				
				public void onItemClick(AdapterView<?>parent, View view, int position, long id){
					String item = ((TextView)view).getText().toString();
					
					sendSMS("13158063563",item);
					Toast.makeText(getApplicationContext(), "Message Sent", Toast.LENGTH_SHORT).show();
				}
			};
			listview.setOnItemClickListener(clicklistener);
		}
	}
	
	private void sendSMS(String phoneNumber, String message){
		SmsManager sms = SmsManager.getDefault();
	    sms.sendTextMessage(phoneNumber, null, message, null, null);
	}
}
