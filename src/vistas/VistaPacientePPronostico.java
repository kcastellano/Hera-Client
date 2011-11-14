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

public class VistaPacientePPronostico extends Activity {
	
	private TextView txt;
	private Typeface font;
	private String idEstudio;
	private TextView forma;
	private TextView ubicacion;
	private TextView bordes;
	private TextView diametro;
	private TextView presencia;
	private TextView visible;
	private TextView actividad;
	private TextView traslucencia;
	private TextView ductus;
	private TextView angulo;
	private TextView hueso;
	private TextView tricuspidea;
	private TextView crl;
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
		setContentView(R.layout.vista_paciente_p);
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
		
		forma = (TextView) findViewById(R.id.forma_text);
		ubicacion = (TextView) findViewById(R.id.ubicacion1_text);
		bordes = (TextView) findViewById(R.id.bordes_text);
		diametro = (TextView) findViewById(R.id.diametro_text);
		presencia = (TextView) findViewById(R.id.vesicula_text);
		visible = (TextView) findViewById(R.id.embrion_text);
		actividad = (TextView) findViewById(R.id.actividadc_text);
		traslucencia = (TextView) findViewById(R.id.traslucencia_text);
		ductus = (TextView) findViewById(R.id.ductus_text);
		angulo = (TextView)  findViewById(R.id.angulo_text);
		hueso = (TextView) findViewById(R.id.hueso_text);
		tricuspidea = (TextView) findViewById(R.id.tricuspidea_text);
		crl = (TextView) findViewById(R.id.crl_text);
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
		
		forma.setText(listaMedida.get(0).getResultadoSemanas());
		ubicacion.setText(listaMedida.get(1).getResultadoSemanas());
		bordes.setText(listaMedida.get(2).getResultadoSemanas());
		diametro.setText(String.valueOf(truncate(listaMedida.get(3).getResultadoNumerico())) + " mm");
		presencia.setText(listaMedida.get(4).getResultadoSemanas());
		visible.setText(listaMedida.get(5).getResultadoSemanas());
		actividad.setText(listaMedida.get(6).getResultadoSemanas());
		traslucencia.setText(String.valueOf(truncate(listaMedida.get(7).getResultadoNumerico())) + " mm");
		ductus.setText(listaMedida.get(8).getResultadoSemanas());
		angulo.setText(String.valueOf(truncate(listaMedida.get(9).getResultadoNumerico())) + " ¡");
		hueso.setText(listaMedida.get(10).getResultadoSemanas());
		tricuspidea.setText(listaMedida.get(11).getResultadoSemanas());
		crl.setText(String.valueOf(truncate(listaMedida.get(12).getResultadoNumerico()))+ " mm");
		
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
