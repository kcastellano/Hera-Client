package vistas;

import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

//import servicios.ServiciosDiagnostico;
import servicios.ServiciosDiagnostico;
import servicios.ServiciosMedidas;

//import dominio.Diagnostico;
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

public class VistaPacienteSTPronostico extends Activity {
	
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
	private String fecha1;
	private String fecha2;
	private String id;
	private String header1;
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
		
		Intent intent = getIntent();
		fecha1 = intent.getStringExtra("fecha1");
		fecha2 = intent.getStringExtra("fecha2");
		id = intent.getStringExtra("id");
		header1 = intent.getStringExtra("header");
		
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
		
		
		header.setText(header1);
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

		setMedidasValues();
		setDiagnostico(id);
	}
	
	//El embarazo esta acorde con la fecha de la ultima regla
	
	public void setMedidasValues(){
		ArrayList<Medida> listaMedida = new ArrayList<Medida>();
		ServiciosMedidas servicios = new ServiciosMedidas();
		
		listaMedida = servicios.obtenerMedidasPorPronostico(fecha1,fecha2,id);
		
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
		Diagnostico diagnostico1 = new Diagnostico();
		ServiciosDiagnostico servicios = new ServiciosDiagnostico();
		
		diagnostico1 = servicios.obtenerDiagnosticoPorEstudio(idEstudio);
		String fur = fechaAproxConcepcion(diagnostico1.getEdadGestacional());
		
		fca.setText(fur);
		
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat("yy-MM-dd");
		String fpp1 = formatter.format(diagnostico1.getFechaProbableParto());
		
		fpp.setText(fpp1);
		
		set.setText(fecha2);
		
		int faltante = 40 - Integer.valueOf(fecha2);
		sef.setText(String.valueOf(faltante));
		
		diagnostico.setText(diagnostico1.getResultado());
		
		
	}

    private double truncate(double x) {
        DecimalFormat df = new DecimalFormat("0.#");
        String d = df.format(x);
        d = d.replaceAll(",", ".");
        Double dbl = new Double(d);
        return dbl.doubleValue();
    }
    
    public Date fechaProbableDeParto(double GA) {

		int semana = (int) GA;
		int dias = (int) (GA - (int) GA);
		Calendar cal = Calendar.getInstance();

		cal.add(Calendar.WEEK_OF_YEAR, -semana);
		cal.add(Calendar.DAY_OF_YEAR, -dias);

		// int mes = (cal.get(Calendar.MONTH) + 1) -3;
		cal.add(Calendar.WEEK_OF_YEAR, 1);
		cal.add(Calendar.WEEK_OF_MONTH, -13);
		int dia = cal.get(Calendar.DATE);
		int mes = (cal.get(Calendar.MONTH) + 1);
		int anio = cal.get(Calendar.YEAR) + 1;

		Calendar fecha = Calendar.getInstance();
		fecha.set(Calendar.YEAR, anio);
		fecha.set(Calendar.MONTH, mes);
		fecha.set(Calendar.DATE, dia);
		System.out.println("Fecha probable de parto: " + dia + "-" + mes + "-"
				+ anio);
		java.sql.Date date = new java.sql.Date(fecha.getTime().getTime());
		return date;
	}

	public String fechaAproxConcepcion(double GA) {

		int semana = (int) GA;
		int dias = (int) (GA - (int) GA);
		Calendar cal = Calendar.getInstance();
	
		cal.add(Calendar.WEEK_OF_YEAR, -semana);
		cal.add(Calendar.DAY_OF_YEAR, -dias);

		String FUR = cal.get(Calendar.DATE) + "-"
				+ (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.YEAR);

		return FUR;
	}
}
