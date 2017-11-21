package com.example.joan.notificationmananager;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.RemoteAction;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public int idNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnNotification = findViewById(R.id.btnNotification);
        btnNotification.setOnClickListener(this);

        Button btnNotificationCompat = findViewById(R.id.btnNotificationCompat);
        btnNotificationCompat.setOnClickListener(this);

        Button btnPendingItent = findViewById(R.id.btnPendingIntent);
        btnPendingItent.setOnClickListener(this);

        Button btnNotificationCustom = findViewById(R.id.btnNotificationCustom);
        btnNotificationCustom.setOnClickListener(this);

    }

    private void createNewNotification() {
        // Create Notification
        NotificationCompat.Builder notification = new android.support.v7.app.NotificationCompat.Builder(this);

        notification.setSmallIcon(R.mipmap.ic_launcher);
        notification.setContentTitle("Mi notificación " + idNotification);

        String notificationText = "Este es el texto que aparece en mi notificación!";
        notificationText = notificationText.replace("¡", " número " + idNotification + "!");

        notification.setContentText(notificationText);
        notification.setAutoCancel(true);

        // Intent + Pending Intent
        Intent notificationIntent = new Intent(this, NotificationActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, idNotification, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        // NotificationManager enviamos Notification
        notification.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(idNotification, notification.build());
            idNotification++;
        }
    }

    private void createNewNotificationCompat() {
        // Texto notificación
        String notificationText = "Este es el texto que aparece en mi notificación!";
        notificationText = notificationText.replace("¡", " número " + idNotification + "!");

        // Icono grande notificación
        Bitmap iconoGrande = BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher);

        // Sonido notificación
        Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        // Create Notification
        NotificationCompat.Builder notification = new NotificationCompat.Builder(this);

        notification.setStyle(new NotificationCompat.BigTextStyle().bigText(notificationText));
        notification.setSmallIcon(R.mipmap.ic_launcher);
        notification.setLargeIcon(iconoGrande);
        notification.setContentTitle("Mi notificación " + idNotification);
        notification.setSound(notificationSound);

        //notification.setContentText(notificationText);
        notification.setAutoCancel(true);

        // Intent + Pending Intent
        Intent notificationIntent = new Intent(this, NotificationActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, idNotification, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        // NotificationManager enviamos Notification
        notification.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(idNotification, notification.build());
            idNotification++;

        }
    }

    private  void createNewPendingIntent() {
        Intent intent = new Intent(this, NotificationActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Service.ALARM_SERVICE);
        long timeWhen = System.currentTimeMillis() + 5000; // momento actual + 5s
        alarmManager.set(AlarmManager.RTC_WAKEUP, timeWhen, pendingIntent);

        Toast.makeText(this, "La alarma saltará en 5 segundos!", Toast.LENGTH_SHORT).show();
    }

    private void createNewNotificationCustom() {
        // Create Notification
        NotificationCompat.Builder notification = new android.support.v7.app.NotificationCompat.Builder(this);
        notification.setSmallIcon(R.mipmap.ic_launcher);
        notification.setAutoCancel(true);

        // Intent + PendingIntent
        Intent notificationIntent = new Intent(this, NotificationActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, idNotification, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        notification.setContentIntent(pendingIntent);

        // RemoteView (xml de la notificación)
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.custom_notification);
        remoteViews.setTextViewText(R.id.lblTitle, "Custom notification title");
        remoteViews.setTextViewText(R.id.lblText1, "Custom notification text1");
        remoteViews.setTextViewText(R.id.lblText2, "Custom notification text2");
        remoteViews.setImageViewResource(R.id.imgNotification, R.mipmap.ic_launcher);
        notification.setContent(remoteViews);

        // Enviar notificación mediante NotificationManager.notify()
        NotificationManager notificationManager = (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);
        if(notificationManager != null) {
            notificationManager.notify(idNotification, notification.build());
            idNotification++;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNotification:
                createNewNotification();
                break;

            case R.id.btnNotificationCompat:
                createNewNotificationCompat();
                break;

            case R.id.btnPendingIntent:
                createNewPendingIntent();
                break;

            case R.id.btnNotificationCustom:
                createNewNotificationCustom();
                break;
        }
    }
}