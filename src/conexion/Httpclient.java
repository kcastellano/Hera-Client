package conexion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;


import android.net.ParseException;
import android.util.Log;

public class Httpclient {
	private String TAG = "Foo";

	public String getServiceResult(String url) {

		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(url);
		String response = null;
		String result = null;
		try {
			ResponseHandler<String> handler = new BasicResponseHandler();
			response = httpclient.execute(httpget, handler);

			if (response != null) {
				result = response;
			} else {
				return null;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return result;
	}

	public static String request(HttpResponse response) {
		String result = "";
		try {
			InputStream in = response.getEntity().getContent();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));
			StringBuilder str = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				str.append(line + "\n");
			}
			in.close();
			result = str.toString();
		} catch (Exception ex) {
			result = "Error";
		}
		return result;
	}

	public String SendHttpPost(String URL, String jsonObjSend) {

		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(URL);
		String result = null;
		StringEntity se;
		try {
			se = new StringEntity(jsonObjSend);
			httppost.setEntity(se);
			httppost.setHeader("Accept", "application/json");
			httppost.setHeader("Content-type", "application/json");
			long t = System.currentTimeMillis();
			HttpResponse response = (HttpResponse) httpclient.execute(httppost);
			Log.i(TAG,
					"HTTPResponse received in ["
							+ (System.currentTimeMillis() - t) + "ms]");

			HttpEntity entity = response.getEntity();
			if (entity != null) {
				result = request(response);
			}

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}
	
	public String SendHttpPut(String URL, String jsonObjSend) {

		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPut httpput = new HttpPut(URL);
		String result = null;
		StringEntity se;
		try {
			se = new StringEntity(jsonObjSend);
			httpput.setEntity(se);
			httpput.setHeader("Accept", "application/json");
			httpput.setHeader("Content-type", "application/json");
			long t = System.currentTimeMillis();
			HttpResponse response = (HttpResponse) httpclient.execute(httpput);
			Log.i(TAG,
					"HTTPResponse received in ["
							+ (System.currentTimeMillis() - t) + "ms]");

			HttpEntity entity = response.getEntity();
			if (entity != null) {
				result = request(response);
			}

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}
}
