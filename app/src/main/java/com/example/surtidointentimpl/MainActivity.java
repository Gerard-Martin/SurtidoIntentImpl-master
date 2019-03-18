package com.example.surtidointentimpl;

import android.Manifest;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Contacts.People;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.ExtractedText;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{
	private ImageView selectedImage;
	private TextView contactName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        selectedImage = (ImageView) findViewById(R.id.selectedImage);
        contactName = (TextView) findViewById(R.id.textView);

	    Button btn1 = findViewById(R.id.button1);
	    Button btn2 = findViewById(R.id.button2);
	    Button btn3 = findViewById(R.id.button3);
	    Button btn4 = findViewById(R.id.button4);
	    Button btn5 = findViewById(R.id.button5);
	    Button btn6 = findViewById(R.id.button6);
		Button btn7 = findViewById(R.id.button7);
        Button btn8 = findViewById(R.id.button8);
        Button btn9 = findViewById(R.id.button9);
        Button btn10 = findViewById(R.id.button10);


	    btn1.setOnClickListener(this);
	    btn2.setOnClickListener(this);
	    btn3.setOnClickListener(this);
	    btn4.setOnClickListener(this);
	    btn5.setOnClickListener(this);
	    btn6.setOnClickListener(this);
		btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btn10.setOnClickListener(this);

		
	    if (Build.VERSION.SDK_INT >= 23)
	        if (! ckeckPermissions())
		   requestPermissions();	  
	}

	public void onClick (View v) {
		Intent in;
		final String lat = "41.60788";
		final String lon = "0.623333";
		final String url = "http://www.eps.udl.cat/";
		final String adressa = "Carrer de Jaume II, 69, Lleida";
		final String textoABuscar = "escuela politecnica superior";

		switch (v.getId()) {
	    case R.id.button1: 
	        Toast.makeText(this, getString(R.string.opcio1), Toast.LENGTH_LONG).show();
	        in = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:" + lat + ',' + lon));
	        startActivity(in);
	        break;
	    case R.id.button2: 
			Toast.makeText(this, getString(R.string.opcio2), Toast.LENGTH_LONG).show();
			in = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + adressa));
			startActivity(in);
	        break;
	    case R.id.button3:
	    	Toast.makeText(this, getString(R.string.opcio3), Toast.LENGTH_LONG).show();
			in = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
			startActivity(in);
	        break;
	    case R.id.button4:
	    	Toast.makeText(this, getString(R.string.opcio4), Toast.LENGTH_LONG).show();
			//in = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com/search?q=" + "escola politecnica superior UdL"));
			in = new Intent(Intent.ACTION_WEB_SEARCH);
			in.putExtra(SearchManager.QUERY, textoABuscar);
			startActivity(in);
	        break;
	    case R.id.button5:
	    	Toast.makeText(this, getString(R.string.opcio5), Toast.LENGTH_LONG).show();
			in = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + getText(R.string.telef)));
			startActivity(in);
	        break;
	    case R.id.button6:
	    	Toast.makeText(this, getString(R.string.opcio6), Toast.LENGTH_LONG).show();
			in = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
			in.setType(ContactsContract.Contacts.CONTENT_TYPE);
			startActivityForResult(in, 2);
			break;
		case R.id.button7:
			in = new Intent(Intent.ACTION_DIAL);
			//in.setData(Uri.parse("tel:666666666"));
			startActivity(in);
			break;
        case R.id.button8:
            in = new Intent(Intent.ACTION_VIEW);
            in.setData(Uri.fromParts("sms","646888777", null));
			in.putExtra("sms_body",  getResources().getString(R.string.mensaje));
            startActivity(in);
            break;
        case R.id.button9:
            in = new Intent(Intent.ACTION_SEND);
            in.setData(Uri.parse("mailto:"));
            in.setType("text/plain");
			in.putExtra(Intent.EXTRA_EMAIL, "sss@udl.cat");
            in.putExtra(Intent.EXTRA_SUBJECT, "demo");
			in.putExtra(Intent.EXTRA_TEXT,"Mensaje de prueba");
            startActivity(in);
            break;
        case R.id.button10:
            in = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			//ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            startActivityForResult(in, 1);
            break;
	    }
	}

	private boolean ckeckPermissions() {
		if (Build.VERSION.SDK_INT >= 23) {
			if (ActivityCompat.checkSelfPermission(getApplicationContext(),
					Manifest.permission.CALL_PHONE) ==
					PackageManager.PERMISSION_GRANTED)
				return true;
			else
				return false;
		    }
		else
			return true;
	}

	private void requestPermissions() {
		ActivityCompat.requestPermissions(MainActivity.this,
				new String[]{Manifest.permission.CALL_PHONE},
				0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);

		if(resultCode == RESULT_OK && intent != null){
			if(requestCode == 1) {

				Uri selectedImage = intent.getData();
				String[] filepath = {MediaStore.Images.Media.DATA};

				Cursor cursor = getContentResolver().query(selectedImage, filepath, null, null, null);
				((Cursor) cursor).moveToFirst();
				int columnIndex = cursor.getColumnIndex(filepath[0]);
				String picturePath = cursor.getString(columnIndex);

				this.selectedImage.setImageBitmap(BitmapFactory.decodeFile(picturePath));
			}else if(requestCode == 2){
				Uri contactUri = intent.getData();
				Cursor cursor = getContentResolver().query(contactUri, null, null, null, null);
				cursor.moveToFirst();
				int column = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
				String nickname = cursor.getString(column);

				contactName.setText(nickname);
			}
		}
	}
}
