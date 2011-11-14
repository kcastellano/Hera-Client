package vistas;

import java.text.DecimalFormat;
import java.util.ArrayList;

import servicios.ServiciosDiagnostico;
import servicios.ServiciosMedidas;

import dominio.Diagnostico;
import dominio.Medida;

import edu.tesis.heraproject.R;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class VistaPacienteSegundoTercero extends Activity {
	
	private TextView txt;
	private Typeface font;
	private String idEstudio;
	private TextView presentacion;
	private TextView situacion;
	private TextView posicion;
	private TextView estomago;
	private TextView vejiga;
	private TextView rinon;
	private TextView sexo;
	private TextView cordon;
	private TextView ubicacion;
	private TextView bdp;
	private TextView hc;
	private TextView ac;
	private TextView fl;
	private TextView peso;
	private TextView header;
	private TextView fca;
	private TextView fpp;
	private TextView set;
	private TextView sef;
	private TextView diagnostico;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.vista_paciente_st);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.titulo);
		txt = (TextView) findViewById(R.id.title);
		txt.setText("Hera");
		font = Typeface.createFromAsset(getAssets(), "Chunk.ttf");
		txt.setTypeface(font);
		
		presentacion = (TextView) findViewById(R.id.presentacion_text);
		situacion = (TextView) findViewById(R.id.situacion_text);
		posicion = (TextView) findViewById(R.id.posicion_text);
		estomago = (TextView) findViewById(R.id.estomago_text);
		vejiga = (TextView) findViewById(R.id.vejiga_text);
		rinon = (TextView) findViewById(R.id.rinones_text);
		sexo = (TextView) findViewById(R.id.sexo_text);
		cordon = (TextView) findViewById(R.id.cordon_text);
		ubicacion = (TextView) findViewById(R.id.placenta_text);
		bdp = (TextView)  findViewById(R.id.dbp_text);
		hc = (TextView) findViewById(R.id.cc_text);
		ac = (TextView) findViewById(R.id.ca_text);
		fl = (TextView) findViewById(R.id.fl_text);
		peso = (TextView) findViewById(R.id.peso_text);
		header = (TextView) findViewById(R.id.vistaHeader);
		fca = (TextView) findViewById(R.id.fur_text);
		fpp = (TextView) findViewById(R.id.fpp_text);
		set = (TextView) findViewById(R.id.st_text);
		sef = (TextView) findViewById(R.id.sfe_text);
		diagnostico = (TextView) findViewById(R.id.diagnostico_text);
		
		Intent intent = getIntent();
		idEstudio = intent.getStringExtra("idEstudio");
		String fecha = intent.getStringExtra("fecha");
		
		header.setText("Semana: " + fecha);
		Button btnMedidas= (Button) findViewById(R.id.btnMedidas);
		Button btnDiagnostico= (Button) findViewById(R.id.btnDiagnostico);
	
		View panelMedidas= findViewById(R.id.panelMedidas);
		panelMedidas.setVisibility(View.GONE);

		View panelDiagnostico= findViewById(R.id.panelDiagnostico);
		panelDiagnostico.setVisibility(View.GONE);


		btnMedidas.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// DO STUFF
				View panelMedidas = findViewById(R.id.panelMedidas);			
				View panelDiagnostico = findViewById(R.id.panelDiagnostico);	
				
				if(panelMedidas.getVisibility() == View.VISIBLE){
					panelMedidas.setVisibility(View.GONE);
				}
				else{
					panelMedidas.setVisibility(View.VISIBLE);
					panelDiagnostico.setVisibility(View.GONE);
				}

			}
		});
		
		btnDiagnostico.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				View panelMedidas = findViewById(R.id.panelMedidas);
				View panelDiagnostico = findViewById(R.id.panelDiagnostico);			

				
				if(panelDiagnostico.getVisibility() == View.VISIBLE){
					panelDiagnostico.setVisibility(View.GONE);
				}
				else{
					panelDiagnostico.setVisibility(View.VISIBLE);
					panelMedidas.setVisibility(View.GONE);
				}

			}
		});

		setMedidasValues(idEstudio);
	}
	
	public void setMedidasValues(String idStudy){
		ArrayList<Medida> listaMedida = new ArrayList<Medida>();
		ServiciosMedidas servicios = new ServiciosMedidas();
		
		listaMedida = servicios.obtenerMedidasPorEstudio(idStudy);
		
		presentacion.setText(listaMedida.get(0).getResultadoSemanas());
		situacion.setText(listaMedida.get(1).getResultadoSemanas());
		posicion.setText(listaMedida.get(2).getResultadoSemanas());
		estomago.setText(listaMedida.get(3).getResultadoSemanas());
		vejiga.setText(listaMedida.get(4).getResultadoSemanas());
		rinon.setText(listaMedida.get(5).getResultadoSemanas());
		sexo.setText(listaMedida.get(6).getResultadoSemanas());
		cordon.setText(listaMedida.get(7).getResultadoSemanas());
		ubicacion.setText(listaMedida.get(8).getResultadoSemanas());
		bdp.setText(String.valueOf(truncate(listaMedida.get(9).getResultadoNumerico())) + " mm");
		hc.setText(String.valueOf(truncate(listaMedida.get(10).getResultadoNumerico()))+ " mm");
		ac.setText(String.valueOf(truncate(listaMedida.get(11).getResultadoNumerico()))+ " mm");
		fl.setText(String.valueOf(truncate(listaMedida.get(12).getResultadoNumerico()))+ " mm");
		peso.setText(String.valueOf(truncate(listaMedida.get(13).getResultadoNumerico()))+ " g");
	}
	
	public void setDiagnostico(String idStudy){
		int idEstudio = Integer.valueOf(idStudy);
		Diagnostico diagnostico = new Diagnostico();
		ServiciosDiagnostico servicios = new ServiciosDiagnostico();
		
		diagnostico = servicios.obtenerDiagnosticoPorEstudio(idEstudio);
		
		
	}

    private double truncate(double x) {
    	x = x *1000;
        DecimalFormat df = new DecimalFormat("0.#");
        String d = df.format(x);
        d = d.replaceAll(",", ".");
        Double dbl = new Double(d);
        return dbl.doubleValue();
    }
}
