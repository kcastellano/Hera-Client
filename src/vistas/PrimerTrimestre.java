package vistas;

import java.util.ArrayList;

import configuracion.nombreMedicionesPT;

import servicios.ServiciosEstudio;
import servicios.ServiciosExpedienteUsuario;
import servicios.ServiciosMedidas;
import dominio.Estudio;
import dominio.Medida;
import edu.tesis.heraproject.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class PrimerTrimestre extends Activity {

	private TextView txt;
	private Typeface font;
	private EditText traslucencia;
	private EditText angulo;
	private EditText longitud;
	private EditText diametro;
	private String forma;
	private String ubicacion;
	private String bordes;
	private String visible;
	private String actividad;
	private String presencia;
	private String ductus;
	private String hueso;
	private String tricuspidea;
	private AlertDialog.Builder emailAlert;
	private Button enviar;
	private String ced;
	private String nombre;
	private String trimestre;
	private int cedulaPaciente;
	private ProgressDialog dialog;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.primer_trimestre);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.titulo);

		txt = (TextView) findViewById(R.id.title);
		txt.setText("Hera");
		font = Typeface.createFromAsset(getAssets(), "Chunk.ttf");
		txt.setTypeface(font);
		
		enviar = (Button) findViewById(R.id.enviar_datos_primer);
		
		Intent intent = getIntent();
		ced = intent.getStringExtra("Cedula");
		nombre = intent.getStringExtra("Nombre");
		trimestre = intent.getStringExtra("Trimestre");
		
		dialog = new ProgressDialog(this);
		dialog.setIndeterminate(true);
		dialog.setMessage("Espere un momento.");
		dialog.setTitle("Enviando archivo");

		diametro = (EditText) findViewById(R.id.diametro);
		final Spinner spinnerForm = (Spinner) findViewById(R.id.forma);
		final Spinner spinnerLocation = (Spinner) findViewById(R.id.ubicacion);
		final Spinner spinnerBorder = (Spinner) findViewById(R.id.bordes);
		final Spinner spinnerVisible = (Spinner) findViewById(R.id.visible);
		final Spinner spinnerActivity = (Spinner) findViewById(R.id.actividad);
		final Spinner spinnerPresencia = (Spinner) findViewById(R.id.presencia);
		final Spinner spinnerDuctus = (Spinner) findViewById(R.id.ductus);
		final Spinner spinnerBone = (Spinner) findViewById(R.id.hueso);
		final Spinner spinnerTricuspidea = (Spinner) findViewById(R.id.tricuspidea);

		traslucencia = (EditText) findViewById(R.id.traslucencia);
		angulo = (EditText) findViewById(R.id.angulo);
		longitud = (EditText) findViewById(R.id.longitud);
		
		emailAlert = new AlertDialog.Builder(this);
		emailAlert.setTitle("Envio de archivo");
		emailAlert.setPositiveButton("ok", null);
		emailAlert.setCancelable(true);

		ArrayAdapter<CharSequence> adapterForm = ArrayAdapter
				.createFromResource(this, R.array.form_array,
						android.R.layout.simple_spinner_item);

		adapterForm
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		ArrayAdapter<CharSequence> adapterLocation = ArrayAdapter
				.createFromResource(this, R.array.location_array,
						android.R.layout.simple_spinner_item);
		adapterLocation
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		ArrayAdapter<CharSequence> adapterBorder = ArrayAdapter
				.createFromResource(this, R.array.border_array,
						android.R.layout.simple_spinner_item);
		adapterBorder
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		ArrayAdapter<CharSequence> adapterVisible = ArrayAdapter
				.createFromResource(this, R.array.visible_array,
						android.R.layout.simple_spinner_item);
		adapterVisible
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		ArrayAdapter<CharSequence> adapterActivity = ArrayAdapter
				.createFromResource(this, R.array.actividad_array,
						android.R.layout.simple_spinner_item);
		adapterActivity
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		ArrayAdapter<CharSequence> adapterPresencia = ArrayAdapter
				.createFromResource(this, R.array.presencia_array,
						android.R.layout.simple_spinner_item);
		adapterPresencia
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		ArrayAdapter<CharSequence> adapterDuctus = ArrayAdapter
				.createFromResource(this, R.array.ductus_array,
						android.R.layout.simple_spinner_item);
		adapterDuctus
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		ArrayAdapter<CharSequence> adapterBone = ArrayAdapter
				.createFromResource(this, R.array.hueso_array,
						android.R.layout.simple_spinner_item);
		adapterBone
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		ArrayAdapter<CharSequence> adapterTricuspidea = ArrayAdapter
				.createFromResource(this, R.array.tricuspidea_array,
						android.R.layout.simple_spinner_item);
		adapterTricuspidea
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinnerForm.setAdapter(adapterForm);
		spinnerLocation.setAdapter(adapterLocation);
		spinnerBorder.setAdapter(adapterBorder);
		spinnerVisible.setAdapter(adapterVisible);
		spinnerActivity.setAdapter(adapterActivity);
		spinnerPresencia.setAdapter(adapterPresencia);
		spinnerDuctus.setAdapter(adapterDuctus);
		spinnerBone.setAdapter(adapterBone);
		spinnerTricuspidea.setAdapter(adapterTricuspidea);

		spinnerForm
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> adapterView,
							View view, int i, long l) {

						forma = spinnerForm.getItemAtPosition(i).toString();
					}

					public void onNothingSelected(AdapterView<?> adapterView) {
						return;
					}
				});

		spinnerLocation
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> adapterView,
							View view, int i, long l) {
						ubicacion = spinnerLocation.getItemAtPosition(i)
								.toString();
					}

					public void onNothingSelected(AdapterView<?> adapterView) {
						return;
					}
				});

		spinnerBorder
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> adapterView,
							View view, int i, long l) {
						bordes = spinnerBorder.getItemAtPosition(i).toString();
					}

					public void onNothingSelected(AdapterView<?> adapterView) {
						return;
					}
				});

		spinnerVisible
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> adapterView,
							View view, int i, long l) {

						visible = spinnerVisible.getItemAtPosition(i)
								.toString();
					}

					public void onNothingSelected(AdapterView<?> adapterView) {
						return;
					}
				});

		spinnerActivity
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> adapterView,
							View view, int i, long l) {

						actividad = spinnerActivity.getItemAtPosition(i)
								.toString();
					}

					public void onNothingSelected(AdapterView<?> adapterView) {
						return;
					}
				});

		spinnerPresencia
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> adapterView,
							View view, int i, long l) {

						presencia = spinnerPresencia.getItemAtPosition(i)
								.toString();
					}

					public void onNothingSelected(AdapterView<?> adapterView) {
						return;
					}
				});

		spinnerDuctus
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> adapterView,
							View view, int i, long l) {

						ductus = spinnerDuctus.getItemAtPosition(i).toString();
					}

					public void onNothingSelected(AdapterView<?> adapterView) {
						return;
					}
				});

		spinnerBone
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> adapterView,
							View view, int i, long l) {

						hueso = spinnerBone.getItemAtPosition(i).toString();
					}

					public void onNothingSelected(AdapterView<?> adapterView) {
						return;
					}
				});

		spinnerTricuspidea
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> adapterView,
							View view, int i, long l) {

						tricuspidea = spinnerTricuspidea.getItemAtPosition(i)
								.toString();
					}

					public void onNothingSelected(AdapterView<?> adapterView) {
						return;
					}
				});
		
		Button.OnClickListener enviarDatosOnClickListener = new Button.OnClickListener() {

			public void onClick(View v) {
				CallWebServiceTask task = new CallWebServiceTask();
				task.applicationContext = PrimerTrimestre.this;
				task.execute(nombre, ced);
			}
		};
		
		enviar.setOnClickListener(enviarDatosOnClickListener);

	}
	
	public class CallWebServiceTask extends AsyncTask<String, Void, Integer> {

		protected Context applicationContext;
		int resultado = 0;

		@Override
		protected void onPreExecute() {
			dialog.show();
		}

		protected void onPostExecute(Integer resultado) {

		/*	try {
				//Thread.sleep(12000L);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			dialog.cancel();
			if (resultado == 1) {
				emailAlert.setMessage("Las medidas fueron enviadas con Žxito");
				emailAlert.create().show();
				Intent intent = new Intent(PrimerTrimestre.this,
						VistaPaciente.class);
				intent.putExtra("Cedula", ced);
				intent.putExtra("Nombre", nombre);
				startActivity(intent);
			} else {
				emailAlert
						.setMessage("Las medidas fueron enviadas con Žxito");
				emailAlert.create().show();
				Intent intent = new Intent(PrimerTrimestre.this,
						VistaPaciente.class);
				intent.putExtra("Cedula", ced);
				intent.putExtra("Nombre", nombre);
				intent.putExtra("Trimestre", trimestre);
				startActivity(intent);
			}

		}

		@Override
		protected Integer doInBackground(String... params) {
			cedulaPaciente = Integer.valueOf(params[1]);
			int resultado = 0;
			enviarMedidas();
			return resultado;
		}
		
		public int enviarMedidas(){
			int resultado = 0;
			ArrayList<String> medidas = new ArrayList<String>();
			medidas = setValoresMedidas();
			ArrayList<Medida> todasMedidas = new ArrayList<Medida>();
			todasMedidas = getMedidas(medidas,ced);
			ServiciosMedidas servicio = new ServiciosMedidas();
			for(int i =0; i < todasMedidas.size(); i++){
				servicio.enviarMediciones(todasMedidas.get(i));
			}
			resultado = 1;
			return resultado;
			
		}
		
		public ArrayList<String> setValoresMedidas(){
			ArrayList<String> medidas = new ArrayList<String>();
			medidas.add(forma);
			medidas.add(ubicacion);
			medidas.add(bordes);
			medidas.add(diametro.toString());
			medidas.add(presencia);
			medidas.add(visible);
			medidas.add(actividad);
			medidas.add(traslucencia.toString());
			medidas.add(ductus);
			medidas.add(angulo.toString());
			medidas.add(hueso);
			medidas.add(longitud.toString());
			return medidas;
		}
		
		public  ArrayList<Medida> getMedidas(ArrayList<String> lista,String cedula) {
	        ArrayList<Medida> listaMedidas = new ArrayList<Medida>();
	        ArrayList<String> nombreMediciones = new ArrayList<String>();
	        nombreMediciones = getNombresMediciones();
	        ServiciosEstudio servicioEstudio = new ServiciosEstudio();
	        ServiciosExpedienteUsuario expediente = new ServiciosExpedienteUsuario();
	        int fk = expediente.obtenerExpedientePaciente(cedula).getFk_idExpediente();
	        crearEstudio(fk,1);
	        int estudioFK = servicioEstudio.obtenerUltimoEstudio().getIdEstudio();
	        for (int i = 0; i < lista.size(); i++) {
	        	Medida medida = new Medida();
	            medida.setNombreMedida(nombreMediciones.get(i));
	            medida.setResultadoSemanas(lista.get(i));
	            listaMedidas.add(medida);
	            medida.setFk_idEstudio(estudioFK);
	            medida.setFk_idExpediente(fk);
	        }
	        return listaMedidas;
	    }
		
	    public ArrayList<String> getNombresMediciones() {
	        ArrayList<String> nombreMediciones = new ArrayList<String>();

	        for (nombreMedicionesPT n : nombreMedicionesPT.values()) {
	            String nombre = n.name();
	            nombreMediciones.add(nombre);
	        }
	        return nombreMediciones;
	    }
	    
	    public Estudio crearEstudio(int idExpediente, int trimestre) {
	       Estudio estudio = new Estudio();
	       estudio.setFk_idExpediente(idExpediente);
	       estudio.setTrimestre(trimestre);
	       ServiciosEstudio servicio = new ServiciosEstudio();
	       servicio.enviarMediciones(estudio);
	       return estudio;
	    }

	}
	
}
