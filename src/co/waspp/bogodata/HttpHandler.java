package co.waspp.bogodata;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;

public class HttpHandler extends AsyncTask<String, Void, String> {
	QtionActivity caller;
	String id;
	
	public HttpHandler(QtionActivity caller, String id){
		this.id = id;
		this.caller = caller;
	}

	@Override
	protected String doInBackground(String... urls) {
		String respuesta;
		
		//List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		//nameValuePairs.add(new BasicNameValuePair("id", id));
    	
        try {
        	DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(urls[0]);
 
            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
			
			respuesta = EntityUtils.toString(httpEntity);
        } catch (Exception e) {
        	respuesta = "No se pudo conectar con el servidor";
        }
        
        return respuesta;
	}

    protected void onPostExecute(String resp) {
    	
    	caller.onBackgroundTaskCompleted(resp);
    }
 }