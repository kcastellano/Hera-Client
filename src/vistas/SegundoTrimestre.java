package vistas;

import java.util.ArrayList;

import servicios.ServiciosArchivo;
import servicios.ServiciosEstudio;
import servicios.ServiciosExpedienteUsuario;
import servicios.ServiciosMedidas;

import configuracion.nombreMedicionesST;
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
import android.widget.Spinner;
import android.widget.TextView;


public class SegundoTrimestre extends Activity {

	private TextView txt;
	private Typeface font;
	private Button file;
	private Button enviar;
	private String fileChosen;
	private ProgressDialog dialog;
	private String ced;
	private AlertDialog.Builder emailAlert;
	private int cedulaPaciente;
	private String presentacion;
	private String situacion;
	private String posicion;
	private String estomago;
	private String vejiga;
	private String rinones;
	private String sexo;
	private String cordon;
	private String placenta;
	private String nombre;
	private String trimestre;
	private String rol;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.segundo_tercer_trimestre);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.titulo);

		txt = (TextView) findViewById(R.id.title);
		txt.setText("Hera");
		font = Typeface.createFromAsset(getAssets(), "Chunk.ttf");
		txt.setTypeface(font);
		file = (Button) findViewById(R.id.file);
		enviar = (Button) findViewById(R.id.enviar);
		Intent intent = getIntent();
		ced = intent.getStringExtra("Cedula");
		nombre = intent.getStringExtra("Nombre");
		trimestre = intent.getStringExtra("Trimestre");
		rol = intent.getStringExtra("Rol");
		dialog = new ProgressDialog(this);
		dialog.setIndeterminate(true);
		dialog.setMessage("Espere un momento.");
		dialog.setTitle("Enviando archivo");

		emailAlert = new AlertDialog.Builder(this);
		emailAlert.setTitle("Envio de archivo");
		emailAlert.setPositiveButton("ok", null);
		emailAlert.setCancelable(true);

		final Spinner spinnerPresentacion = (Spinner) findViewById(R.id.presentacion);
		final Spinner spinnerSituacion = (Spinner) findViewById(R.id.situacion);
		final Spinner spinnerPosicion = (Spinner) findViewById(R.id.posicion);
		final Spinner spinnerEstomago = (Spinner) findViewById(R.id.estomago);
		final Spinner spinnerVejiga = (Spinner) findViewById(R.id.vejiga);
		final Spinner spinnerRinones = (Spinner) findViewById(R.id.rinones);
		final Spinner spinnerSexo = (Spinner) findViewById(R.id.sexo);
		final Spinner spinnerCordon = (Spinner) findViewById(R.id.cordon);
		final Spinner spinnerPlacenta = (Spinner) findViewById(R.id.placenta);

		ArrayAdapter<CharSequence> adapterPresentacion = ArrayAdapter
				.createFromResource(this, R.array.presentacion_array,
						android.R.layout.simple_spinner_item);

		adapterPresentacion
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		ArrayAdapter<CharSequence> adapterSituacion = ArrayAdapter
				.createFromResource(this, R.array.situacion_array,
						android.R.layout.simple_spinner_item);

		adapterSituacion
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		ArrayAdapter<CharSequence> adapterPosicion = ArrayAdapter
				.createFromResource(this, R.array.posicion_array,
						android.R.layout.simple_spinner_item);

		adapterPosicion
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		ArrayAdapter<CharSequence> adapterEstomago = ArrayAdapter
				.createFromResource(this, R.array.estomago_array,
						android.R.layout.simple_spinner_item);

		adapterEstomago
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		ArrayAdapter<CharSequence> adapterVejiga = ArrayAdapter
				.createFromResource(this, R.array.vejiga_array,
						android.R.layout.simple_spinner_item);

		adapterVejiga
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		ArrayAdapter<CharSequence> adapterRinones = ArrayAdapter
				.createFromResource(this, R.array.rinones_array,
						android.R.layout.simple_spinner_item);

		adapterRinones
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		ArrayAdapter<CharSequence> adapterSexo = ArrayAdapter
				.createFromResource(this, R.array.sexo_array,
						android.R.layout.simple_spinner_item);

		adapterSexo
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		ArrayAdapter<CharSequence> adapterCordon = ArrayAdapter
				.createFromResource(this, R.array.cordon_array,
						android.R.layout.simple_spinner_item);

		adapterCordon
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		ArrayAdapter<CharSequence> adapterPlacenta = ArrayAdapter
				.createFromResource(this, R.array.placenta_array,
						android.R.layout.simple_spinner_item);

		adapterPlacenta
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinnerPresentacion.setAdapter(adapterPresentacion);
		spinnerSituacion.setAdapter(adapterSituacion);
		spinnerPosicion.setAdapter(adapterPosicion);
		spinnerEstomago.setAdapter(adapterEstomago);
		spinnerVejiga.setAdapter(adapterVejiga);
		spinnerRinones.setAdapter(adapterRinones);
		spinnerSexo.setAdapter(adapterSexo);
		spinnerCordon.setAdapter(adapterCordon);
		spinnerPlacenta.setAdapter(adapterPlacenta);
		
		spinnerPresentacion
		.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> adapterView,
					View view, int i, long l) {

				presentacion = spinnerPresentacion.getItemAtPosition(i)
						.toString();
			}

			public void onNothingSelected(AdapterView<?> adapterView) {
				return;
			}
		});
		
		spinnerSituacion
		.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> adapterView,
					View view, int i, long l) {

				situacion = spinnerSituacion.getItemAtPosition(i)
						.toString();
			}

			public void onNothingSelected(AdapterView<?> adapterView) {
				return;
			}
		});

		spinnerPosicion
		.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> adapterView,
					View view, int i, long l) {

				posicion = spinnerPosicion.getItemAtPosition(i)
						.toString();
			}

			public void onNothingSelected(AdapterView<?> adapterView) {
				return;
			}
		});

		spinnerEstomago
		.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> adapterView,
					View view, int i, long l) {

				estomago = spinnerEstomago.getItemAtPosition(i)
						.toString();
			}

			public void onNothingSelected(AdapterView<?> adapterView) {
				return;
			}
		});
		
		spinnerVejiga
		.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> adapterView,
					View view, int i, long l) {

				vejiga = spinnerVejiga.getItemAtPosition(i)
						.toString();
			}

			public void onNothingSelected(AdapterView<?> adapterView) {
				return;
			}
		});
		
		spinnerRinones
		.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> adapterView,
					View view, int i, long l) {

				rinones = spinnerRinones.getItemAtPosition(i)
						.toString();
			}

			public void onNothingSelected(AdapterView<?> adapterView) {
				return;
			}
		});
		
		spinnerSexo
		.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> adapterView,
					View view, int i, long l) {

				sexo = spinnerSexo.getItemAtPosition(i)
						.toString();
			}

			public void onNothingSelected(AdapterView<?> adapterView) {
				return;
			}
		});
		
		spinnerCordon
		.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> adapterView,
					View view, int i, long l) {

				cordon = spinnerCordon.getItemAtPosition(i)
						.toString();
			}

			public void onNothingSelected(AdapterView<?> adapterView) {
				return;
			}
		});
		
		spinnerPlacenta
		.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> adapterView,
					View view, int i, long l) {

				placenta = spinnerPlacenta.getItemAtPosition(i)
						.toString();
			}

			public void onNothingSelected(AdapterView<?> adapterView) {
				return;
			}
		});
		
		Button.OnClickListener fileExplorerOnClickListener = new Button.OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(SegundoTrimestre.this,
						VisorArchivo.class);
				int ACTIVITY_ID_SUBMIT_SCORE = 1;
				startActivityForResult(intent, ACTIVITY_ID_SUBMIT_SCORE);
			}
		};
		Button.OnClickListener enviarDatosOnClickListener = new Button.OnClickListener() {

			public void onClick(View v) {
				CallWebServiceTask task = new CallWebServiceTask();
				task.applicationContext = SegundoTrimestre.this;
				task.execute(fileChosen, ced);
			}
		};
		
		file.setOnClickListener(fileExplorerOnClickListener);
		enviar.setOnClickListener(enviarDatosOnClickListener);
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		fileChosen = (String) data.getCharSequenceExtra("Value");

	}

	public class CallWebServiceTask extends AsyncTask<String, Void, Integer> {

		protected Context applicationContext;
		int resultado = 0;

		@Override
		protected void onPreExecute() {
			dialog.show();
		}

		protected void onPostExecute(Integer resultado) {

			dialog.cancel();
			if (resultado == 1) {
				emailAlert.setMessage("Las medidas fueron enviadas con Žxito");
				emailAlert.create().show();
				Intent intent = new Intent(SegundoTrimestre.this,
						VistaPaciente.class);
				intent.putExtra("Cedula", ced);
				intent.putExtra("Nombre", nombre);
				intent.putExtra("Rol", rol);
				startActivity(intent);
			} else {
				emailAlert
						.setMessage("Las medidas fueron enviadas con Žxito");
				emailAlert.create().show();
				Intent intent = new Intent(SegundoTrimestre.this,
						VistaPaciente.class);
				intent.putExtra("Cedula", ced);
				intent.putExtra("Nombre", nombre);
				intent.putExtra("Trimestre", trimestre);
				intent.putExtra("Rol", rol);
				startActivity(intent);
			}

		}

		@Override
		protected Integer doInBackground(String... params) {
			String username = params[0];
			cedulaPaciente = Integer.valueOf(params[1]);
			int resultado = 0;
			enviarMedidas();
			  sendFile(username, cedulaPaciente);
			return resultado;
		}

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
		medidas.add(presentacion);
		medidas.add(situacion);
		medidas.add(posicion);
		medidas.add(estomago.replaceAll(" ", ""));
		medidas.add(vejiga.replaceAll(" ", ""));
		medidas.add(rinones.replaceAll(" ", ""));
		medidas.add(sexo);
		medidas.add(cordon.replaceAll(" ", ""));
		medidas.add(placenta);
		return medidas;
	}

	public  ArrayList<Medida> getMedidas(ArrayList<String> lista,String cedula) {
        ArrayList<Medida> listaMedidas = new ArrayList<Medida>();
        ArrayList<String> nombreMediciones = new ArrayList<String>();
        nombreMediciones = getNombresMediciones();
        ServiciosEstudio servicioEstudio = new ServiciosEstudio();
        ServiciosExpedienteUsuario expediente = new ServiciosExpedienteUsuario();
        int fk = expediente.obtenerExpedientePaciente(cedula).getFk_idExpediente();
        crearEstudio(fk,2);
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
	
    public static ArrayList<String> getNombresMediciones() {
        ArrayList<String> nombreMediciones = new ArrayList<String>();

        for (nombreMedicionesST n : nombreMedicionesST.values()) {
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
    
	public int sendFile(String usuario, int cedula) {
		int lista = 0;
		ServiciosArchivo servicio = new ServiciosArchivo();
		lista = servicio.enviarArchivo(usuario, cedula);
		return lista;
	}
}
