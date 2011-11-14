package modulos;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dominio.Usuario;

import edu.tesis.heraproject.R;
import servicios.ServiciosUsuario;
import vistas.MenuPacientes;
import vistas.VistaPaciente;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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

public class AgregarUsuario extends Activity {

	private TextView txt;
	private Typeface font;
	private Button nuevoUsuario;
	private EditText nombreUsuario;
	private EditText cedulaUsuario;
	private EditText correoUsuario;
	private EditText apellidoUsuario;
	private EditText contrasenaUsuario;
	private String nombre;
	private String apellido;
	private String cedula;
	private String contrasena;
	private String correo;
	private String rol;
	private AlertDialog.Builder usuarioAlert1;
	private AlertDialog.Builder usuarioAlert2;
	private ProgressDialog dialog;	

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.agregar_usuario);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.titulo);

		txt = (TextView) findViewById(R.id.title);
		txt.setText("Hera");
		font = Typeface.createFromAsset(getAssets(), "Chunk.ttf");
		txt.setTypeface(font);
		nuevoUsuario = (Button) findViewById(R.id.agregarUsuario);

		nombreUsuario = (EditText) findViewById(R.id.nombreUsuario);
		cedulaUsuario = (EditText) findViewById(R.id.cedulaUsuario);
		correoUsuario = (EditText) findViewById(R.id.correoUsuario);
		apellidoUsuario = (EditText) findViewById(R.id.apellidoUsuario);
		contrasenaUsuario = (EditText) findViewById(R.id.contrasenaUsuario);

		final Spinner spinnerRol = (Spinner) findViewById(R.id.rolUsuario);

		ArrayAdapter<CharSequence> adapterRol = ArrayAdapter
				.createFromResource(this, R.array.doctor_array,
						android.R.layout.simple_spinner_item);
		adapterRol
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinnerRol.setAdapter(adapterRol);

		spinnerRol
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> adapterView,
							View view, int i, long l) {

						rol = spinnerRol.getItemAtPosition(i).toString();
					}

					public void onNothingSelected(AdapterView<?> adapterView) {
						return;
					}
				});
		usuarioAlert1 = new AlertDialog.Builder(this);
		usuarioAlert1.setTitle("Agregar usuario");
		usuarioAlert1.setCancelable(true);
		usuarioAlert1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			  @Override
			  public void onClick(DialogInterface dialog, int which) {
			    dialog.dismiss();
			    callMenupatients();
			  }
			});


		usuarioAlert2 = new AlertDialog.Builder(this);
		usuarioAlert2.setTitle("Agregar usuario");
		usuarioAlert2.setCancelable(true);
		usuarioAlert2.setPositiveButton("Ok", null)
;		Button.OnClickListener createUserOnClickListener = new Button.OnClickListener() {

			public void onClick(View v) {
				CallWebServiceTask task = new CallWebServiceTask();
				task.applicationContext = AgregarUsuario.this;
				nombre = nombreUsuario.getText().toString();
				apellido = apellidoUsuario.getText().toString();
				cedula = cedulaUsuario.getText().toString();
				correo = correoUsuario.getText().toString();
				contrasena = contrasenaUsuario.getText().toString();
				task.execute(correo);
			}
		};

		dialog = new ProgressDialog(this);
		dialog.setIndeterminate(true);
		dialog.setTitle("Crear usuario");
		dialog.setMessage("Espere un momento");
		
		nuevoUsuario.setOnClickListener(createUserOnClickListener);

	}


	public boolean validarUsuarioPorCedula(String cedula) {
		boolean resultado = false;
		ServiciosUsuario servicios = new ServiciosUsuario();
		Usuario usuario = new Usuario();
		usuario = servicios.obtenerUsuarioPorCedula(cedula);

		if (usuario != null) {
			resultado = actualizarUsuario();
			
		} else {
			resultado = agregarUsuario();

		}
		return resultado;
	}

	public boolean actualizarUsuario() {
		boolean validate = false;
		Usuario usuario = new Usuario();;
		usuario.setCedula(Integer.valueOf(cedula));
		usuario.setNombrePaciente(nombre);
		usuario.setApellidoPaciente(apellido);
		usuario.setEmail(correo);
		usuario.setPassword(contrasena);
		if (rol.equalsIgnoreCase("si")) {
			usuario.setRol("Doctor");
		} else {
			usuario.setRol("Usuario");
		}

		ServiciosUsuario servicio = new ServiciosUsuario();
		usuario = servicio.actualizarUsuario(usuario);
		if (usuario == null) {

			validate = false;
		} else {

			validate = true;

		}
		return validate;
	}

	public void callMenupatients() {
		Intent intent = new Intent(AgregarUsuario.this, MenuPacientes.class);
		String[] listaPacientesPrimer = null;
		String[] listaPacientesSegundo = null;
		String[] listaPacientesTercero = null;
		String[] cedulaPacientesPrimer = null;
		String[] cedulaPacientesSegundo = null;
		String[] cedulaPacientesTercero = null;
	

		intent.putExtra("CedulaDoctor",cedula);
		intent.putExtra("PrimerTrimestre", listaPacientesPrimer);
		intent.putExtra("SegundoTrimestre", listaPacientesSegundo);
		intent.putExtra("TercerTrimestre", listaPacientesTercero);
		intent.putExtra("CedulasPrimer", cedulaPacientesPrimer);
		intent.putExtra("CedulasSegundo", cedulaPacientesSegundo);
		intent.putExtra("CedulasTercero", cedulaPacientesTercero);
		intent.putExtra("Rol","Doctor");
		startActivity(intent);
	}

	public boolean agregarUsuario() {
		boolean validate = false;
		Usuario usuario = new Usuario();
		Usuario usuarioCreado = new Usuario();
		usuario.setCedula(Integer.valueOf(cedula));
		usuario.setNombrePaciente(nombre);
		usuario.setApellidoPaciente(apellido);
		usuario.setEmail(correo);
		usuario.setPassword(contrasena);
		if (rol.equalsIgnoreCase("si")) {
			usuario.setRol("Doctor");
		} else {
			usuario.setRol("Usuario");
		}

		ServiciosUsuario servicio = new ServiciosUsuario();
		usuarioCreado = servicio.crearUsuario(usuario);
		if (usuarioCreado == null) {
			validate = false;
		} else {
			validate = true;
		}
		return validate;
	}

	public boolean checkEmailCorrect(String Email) {

		String pttn = "^\\D.+@.+\\.[a-z]+";
		Pattern p = Pattern.compile(pttn);
		Matcher m = p.matcher(Email);

		if (m.matches()) {
			return true;
		} else {
			usuarioAlert2
					.setMessage("El formato del correo no es v‡lido.Por favor ingrese de nuevo el correo electr—nico");
			usuarioAlert2.create().show();
			return false;
		}
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
				if (rol.equalsIgnoreCase("si")) {
					usuarioAlert1.setMessage("El usuario fue agregado con exito.");
					usuarioAlert1.create().show();
				} else {
					String nombreUsuario= nombre + " " + apellido;
					Intent intent = new Intent(AgregarUsuario.this, VistaPaciente.class);
					intent.putExtra("Cedula",cedula);
					intent.putExtra("Nombre", nombreUsuario);
					intent.putExtra("Trimestre", 0);
				}	
			}
			else{
				usuarioAlert2.setMessage("El usuario no pudo ser agregado.");
				usuarioAlert2.create().show();
			}
		}

		@Override
		protected Integer doInBackground(String... params) {
			String cedula = params[0];
			if (checkEmailCorrect(correo) == true) {
					if(validarUsuarioPorCedula(cedula) == true){
						resultado = 1;
					}
					else{
						resultado = -1;
					}

			}
			return resultado;
		}

	}
}
