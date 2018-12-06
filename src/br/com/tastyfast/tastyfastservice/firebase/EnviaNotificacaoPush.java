package br.com.tastyfast.tastyfastservice.firebase;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.codehaus.jettison.json.JSONObject;

public class EnviaNotificacaoPush {

		// Chave do servidor Firebase
		public final static String AUTH_KEY_FCM = "AAAA3i23QUI:APA91bGs-CPUKKfZ7dL_ezjZWlIalGvCend964CQHa_PhNHwK5TyvTR9mmkdNqKX4CDeXcR9W4QxwFsExeW7xlhYMdDO5L4WPtdA-xfnQiDKqIkxQm2HK2qGaStHncDXRuJGv_8tym5V";
		public final static String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";

		public static void pushFCMNotification(String mensagem, String userDeviceIdKey) throws Exception{

		   String authKey = AUTH_KEY_FCM; // You FCM AUTH key
		   String FMCurl = API_URL_FCM; 

		   URL url = new URL(FMCurl);
		   HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		   conn.setUseCaches(false);
		   conn.setDoInput(true);
		   conn.setDoOutput(true);

		   conn.setRequestMethod("POST");
		   conn.setRequestProperty("Authorization","key="+authKey);
		   conn.setRequestProperty("Content-Type","application/json");

		   JSONObject json = new JSONObject();
		   json.put("to",userDeviceIdKey.trim());
		   JSONObject info = new JSONObject();
		   info.put("title", "Tasty Fast"); // Notification title
		   info.put("body", mensagem); // Notification body
		   json.put("notification", info);

		   OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		   wr.write(json.toString());
		   wr.flush();
		   conn.getInputStream();
		}
	
}
