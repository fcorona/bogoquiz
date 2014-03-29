package co.waspp.bogodata;

import java.util.Calendar;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class QtionActivity extends ActionBarActivity {
	int type;
	TextView question;
	TextView[] answer;
	ImageView[] imgAnswer;
	
	Random rand;
	boolean charge;
	
	int[] num = new int[3]; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_qtion);
		
		question =(TextView)findViewById(R.id.question);
		answer = new TextView[3];
		
		answer[0]=(TextView)findViewById(R.id.answer1);
		answer[1]=(TextView)findViewById(R.id.answer2);
		answer[2]=(TextView)findViewById(R.id.answer3);
		
		imgAnswer = new ImageView[3];
		
		imgAnswer[0]=(ImageView)findViewById(R.id.imageAns1);
		imgAnswer[1]=(ImageView)findViewById(R.id.imageAns2);
		imgAnswer[2]=(ImageView)findViewById(R.id.imageAns3);
		
		Bundle bundle = getIntent().getExtras();
		type = bundle.getInt("type");	
		charge = false;		
		
		num[0]=num[1]=num[2]= -1;
		
		rand = new Random();
		num[0] = rand.nextInt(3);
		
		HttpHandler handler = new HttpHandler(this,"0");
		
		switch(type){
		case 0:
			handler.execute("http://api.bogotacomovamos.org/api/datas/120/?key=comovamos/");
			break;
			
		case 1:
			handler.execute("http://api.bogotacomovamos.org/api/datas/131/?key=comovamos/");
			break;
		
		//Medio Ambiemte
		case 2:
			handler.execute("http://api.bogotacomovamos.org/api/datas/120/?key=comovamos/");
			break;
		case 3:
		default:
			
			handler.execute("hhttp://api.bogotacomovamos.org/api/datas/82/?key=comovamos/");
			break;
		}
		
		//Toast.makeText(this, "n: "+ type, Toast.LENGTH_LONG).show();
	}
	
	public void onBackgroundTaskCompleted(String jsonStr){
		JSONObject data = null;
		
		charge = true;
		//((LinearLayout)findViewById(R.id.chargeLine)).setVisibility(View.GONE);
		//((LinearLayout)findViewById(R.id.QueLine)).setVisibility(View.VISIBLE);
		
		try {
			JSONObject jsonObj = new JSONObject(jsonStr);
			
	        
	        // Getting JSON Array node
			data = jsonObj.getJSONObject("datas");
			
		}catch (JSONException e) {
            e.printStackTrace();
        }
			switch(type){
			case 0:
				selectEducation(data);
				break;
			case 1:
				selectMovility(data);
				break;
			
			//Medio Ambiente
			case 2:
				selectGreen(data);
				break;
				
			case 5:
			default:
				selectSocial(data);			
				
				break;
			}
	}
	
	public void selectGreen(JSONObject data){
		//Para Arboles por cada 100.000 hab
		int year = Calendar.getInstance().get(Calendar.YEAR);
		
		String fdata = null;
		//HashMap<String, String> answer = new HashMap<String, String>();
		
		do{
			try {
				fdata = data.getString(Integer.toString(year));
				fdata = Float.toString(Float.parseFloat(fdata)/1000); 
				//answer.put(Integer.toString(year), fdata);
			} catch (JSONException e) { e.printStackTrace();}
			finally {
				year--;
			}
		}while(year>=1998 && fdata==null);// && answer.size()<1);
		
		question.setText("¿Cuántos arboles hay por cada 100 habitantes? ");
		
		//answers
		this.answer[num[0]].setText( Integer.toString(Math.round(Float.parseFloat(fdata))));
		
		for(int i=1;i<this.answer.length;i++){
			boolean done;
			
			do{
				done=false;
				num[i] = this.rand.nextInt(3);
				for(int j=0;j<i;j++){
					if(num[j]==num[i]){
						done=true;
					}
				}
				
			}while(done);
			
			int m = Math.round(Float.parseFloat(fdata));
			Random rand = new Random();
			
			int r2 = rand.nextInt(m) + (int) Math.round(m*0.4);
			
			//Log.d("Data", "n: "+num[i]+", data: "+r2);
			this.answer[num[i]].setText(Integer.toString(r2));
			
		}
	}
	
	public void selectEducation(JSONObject data){
		//Para analfabetismo
		int year = Calendar.getInstance().get(Calendar.YEAR);
		
		String fdata = null;
		//HashMap<String, String> answer = new HashMap<String, String>();
		
		float max = 0;
		int maxYear = year;
		
		do{
			try {
				fdata = data.getString(Integer.toString(year));
				if(Float.parseFloat(fdata)>max){
					maxYear = year;
				}
				//answer.put(Integer.toString(year), fdata);
			} catch (JSONException e) { e.printStackTrace();}
			  catch (NumberFormatException e) { e.printStackTrace();}
			finally {
				year--;
			}
		}while(year>=1998);// && answer.size()<1);
		
		question.setText("¿En qué año se presentó la mayor tasa de analfabetismo? ");
		
		//answers
		this.answer[num[0]].setText( Integer.toString(maxYear));
		
		for(int i=1;i<this.answer.length;i++){
			boolean done;
			
			do{
				done=false;
				num[i] = this.rand.nextInt(3);
				for(int j=0;j<i;j++){
					if(num[j]==num[i]){
						done=true;
					}
				}
				
			}while(done);
			
			Random rand = new Random();
				
			int r2 = maxYear - rand.nextInt(10) -5;
			
			//Log.d("Data", "n: "+num[i]+", data: "+r2);
			this.answer[num[i]].setText(Integer.toString(r2));
			
		}
	}
	
	public void selectMovility(JSONObject data){
		//
		int year = Calendar.getInstance().get(Calendar.YEAR);
		
		String fdata = null;
		//HashMap<String, String> answer = new HashMap<String, String>();
		
		do{
			try {
				fdata = data.getString(Integer.toString(year));
				//answer.put(Integer.toString(year), fdata);
			} catch (JSONException e) { e.printStackTrace();}
			  catch (NumberFormatException e) { e.printStackTrace();}
			finally {
				year--;
			}
		}while(year>=1998 && fdata==null);// && answer.size()<1);
		
		question.setText("¿Que porcentaje de pasajeros de Transporte publico se movilizados en Transmilenio?  ");
		
		//answers
		this.answer[num[0]].setText( Float.toString(Float.parseFloat(fdata)*100)+"%" );
		
		for(int i=1;i<this.answer.length;i++){
			boolean done;
			
			do{
				done=false;
				num[i] = this.rand.nextInt(3);
				for(int j=0;j<i;j++){
					if(num[j]==num[i]){
						done=true;
					}
				}
				
			}while(done);
			
			Random rand = new Random();
			
			
			float m = Float.parseFloat(fdata)*100;
			float r2 = m - rand.nextInt(10) - 5;
			
			//Log.d("Data", "n: "+num[i]+", data: "+r2);
			this.answer[num[i]].setText(Float.toString(r2)+"%");
			
		}
	}
	
	public void selectSocial(JSONObject data){
		//hurto
		int year = 2010;//Calendar.getInstance().get(Calendar.YEAR);
		
		String fdata = null;
		//HashMap<String, String> answer = new HashMap<String, String>();
		
		float max = 0;
		int maxYear = year;
		
		do{
			try {
				fdata = data.getString(Integer.toString(year));
				if(Integer.parseInt(fdata)>max){
					maxYear = year;
				}
				//answer.put(Integer.toString(year), fdata);
			} catch (Exception e) { e.printStackTrace();}
			  //catch (NumberFormatException e) { e.printStackTrace();}
			finally {
				year--;
			}
		}while(year>=1998);// && answer.size()<1);
		
		question.setText("¿En qué año se presentó el mayor hurto a personas? ");
		
		//answers
		this.answer[num[0]].setText( Integer.toString(maxYear));
		
		for(int i=1;i<this.answer.length;i++){
			boolean done;
			
			do{
				done=false;
				num[i] = this.rand.nextInt(3);
				for(int j=0;j<i;j++){
					if(num[j]==num[i]){
						done=true;
					}
				}
				
			}while(done);
			
			Random rand = new Random();
				
			int r2 = maxYear - rand.nextInt(10) -5;
			
			//Log.d("Data", "n: "+num[i]+", data: "+r2);
			this.answer[num[i]].setText(Integer.toString(r2));
			
		}
	}
	
	public void select(View view){
		
		if(charge){
			String mess;
			if(Integer.parseInt(view.getTag().toString()) == num[0] ){
				mess="Bien";
			}else{
				mess="Mal";
			}
			
			imgAnswer[num[0]].setImageDrawable(getResources().getDrawable(R.drawable.check));
			
			for(int i=1;i<imgAnswer.length;i++){
				imgAnswer[num[i]].setImageDrawable(getResources().getDrawable(R.drawable.x));
			}
			
			Toast.makeText(this, mess, Toast.LENGTH_SHORT).show();
			 
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {e.printStackTrace();}
			finally{finish();}
		}
	}

}
