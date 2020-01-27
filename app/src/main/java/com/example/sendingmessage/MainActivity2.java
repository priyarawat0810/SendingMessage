package com.example.sendingmessage;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity2 extends Activity {
    private final static int SEND_SMS_PERMISSION_CODE = 111;
    private Button sendMessage;

    public MainActivity2() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        sendMessage = findViewById(R.id.send_message);
        final EditText phone = findViewById(R.id.phone_no);
        final EditText message = findViewById(R.id.message);
        sendMessage.setEnabled(false);

        if (checkPermission(Manifest.permission.SEND_SMS)) {
            sendMessage.setEnabled(true);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_PERMISSION_CODE);
        }
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = message.getText().toString();
                String phoneNumber = phone.getText().toString();

                if (!TextUtils.isEmpty(msg) && !TextUtils.isEmpty(phoneNumber)) {
                    if (checkPermission(Manifest.permission.SEND_SMS)) {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(phoneNumber, null, msg, null, null);
                    } else {
                        Toast.makeText(MainActivity2.this, "permission denied", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity2.this, "Enter a message and a phone number", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }


        private boolean checkPermission (String permission){
            int checkPermission = ContextCompat.checkSelfPermission(this, permission);
            return checkPermission == PackageManager.PERMISSION_GRANTED;
        }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case SEND_SMS_PERMISSION_CODE :
                if (grantResults.length > 0 && (grantResults[0]== PackageManager.PERMISSION_GRANTED));{
                    sendMessage.setEnabled(true);
            }
                break;
        }
    }
}
