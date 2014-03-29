package co.waspp.bogodata;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;

public class RandomActivity extends ActionBarActivity {
	private int count;
	Context context;
	
	private ImageView[] ib;
	private int[] idraw;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_random);
		
		context = this;
		
		count=0;
		ib = new ImageView[6];
		ib[0] = (ImageView) findViewById(R.id.education);
		ib[1] = (ImageView) findViewById(R.id.movility);
		ib[2] = (ImageView) findViewById(R.id.green);
		ib[3] = (ImageView) findViewById(R.id.jobs);
		ib[4] = (ImageView) findViewById(R.id.heath);
		ib[5] = (ImageView) findViewById(R.id.free);
		
		int[] n = { R.drawable.educacion,
					R.drawable.movilidad,
					R.drawable.ambiente,
					R.drawable.empleo,
					R.drawable.salud,
					R.drawable.libre,
					R.drawable.educacion1,
					R.drawable.movilidad1,
					R.drawable.ambiente1,
					R.drawable.empleo1,
					R.drawable.salud1,
					R.drawable.libre1
		};
		
		idraw = n;
		
		
		new Timer().schedule(new TimerTask() {
			Random rand = new Random();
			boolean red= false;
			int n, c;
			
	        @Override
	        public void run() {
	            runOnUiThread(new Runnable() {
	                public void run() {
	                	if(count == 0){
	                		n = rand.nextInt(6);
	                		c = rand.nextInt(8)+5;
	                		
	                		ib[n].setImageDrawable(getResources().getDrawable(idraw[n+6]));
	                		red= true;
	                	}else{
	                		if(red && count<c){
	                			ib[n].setImageDrawable(getResources().getDrawable(idraw[n]));
	                			//red= false;
	                			n=(n+1)%6;
	                			ib[n].setImageDrawable(getResources().getDrawable(idraw[n+6]));
	                		}else{
	                			
	                			if(count>c+1){
	                			
		                			Intent intent = new Intent(context, QtionActivity.class);
		                			intent.putExtra("type", n);
		                			startActivity(intent);
		                			
		                			cancel();
		                			finish();
	                			}
	                			
	                		}
	                	}
	                	count++;
	                }
	            });
	        }
	    }, 500, 400);
	}

}
