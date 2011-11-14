package modulos;


import servicios.ServiciosExpedienteUsuario;
import servicios.ServiciosUsuario;
import vistas.MenuFamiliares;
import dominio.ExpedienteUsuario;
import dominio.Usuario;
import edu.tesis.heraproject.R;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AgregarFamiliar extends Activity {

	private TextView txt;
	private Typeface font;
	private String nombre;
	private String apellido;
	private String cedula;
	private String correo;
	private String password;
	private Button nuevoFamiliar;
	private EditText nombreFamiliar;
	private EditText cedulaFamiliar;
	private EditText apellidoFamiliar;
	private EditText correoFamiliar;
	private AlertDialog.Builder usuarioAlert1;
	private AlertDialog.Builder usuarioAlert2;
	private String expediente;
	private ProgressDialog dialogo;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.agregar_familiar);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titulo);

		txt = (TextView) findViewById(R.id.title);
		txt.setText("Hera");
		font = Typeface.createFromAsset(getAssets(), "Chunk.ttf");
		txt.setTypeface(font);

		Intent intent = getIntent();
		expediente = intent.getStringExtra("Expediente");
		nuevoFamiliar = (Button) findViewById(R.id.agregarFamiliar);

		nombreFamiliar = (EditText) findViewById(R.id.nombreFamiliar);
		cedulaFamiliar = (EditText) findViewById(R.id.cedulaFamiliar);
		apellidoFamiliar = (EditText) findViewById(R.id.apellidoFamiliar);
		correoFamiliar = (EditText) findViewById(R.id.correoFamiliar);

		usuarioAlert1 = new AlertDialog.Builder(this);
		usuarioAlert1.setTitle("Agregar familiar");
		usuarioAlert1.setCancelable(true);
		usuarioAlert1.setPositiveButton("Ok",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						Intent intent = new Intent(AgregarFamiliar.this, MenuFamiliares.class);
						intent.putExtra("Expediente",expediente);
						startActivity(intent);
					}
				});

		usuarioAlert2 = new AlertDialog.Builder(this);
		usuarioAlert2.setTitle("Agregar familiar");
		usuarioAlert2.setCancelable(true);
		usuarioAlert2.setPositiveButton("Ok", null);

		Button.OnClickListener createUserOnClickListener = new Button.OnClickListener() {

			public void onClick(View v) {
				CallWebServiceTask task = new CallWebServiceTask();
				task.applicationContext = AgregarFamiliar.this;
				nombre = nombreFamiliar.getText().toString();
				apellido = apellidoFamiliar.getText().toString();
				cedula = cedulaFamiliar.getText().toString();
				correo = correoFamiliar.getText().toString();
				task.execute(cedula, correo);
			}
		};

		dialogo = new ProgressDialog(this);
		dialogo.setIndeterminate(true);
		dialogo.setTitle("Crear familiar");
		dialogo.setMessage("Espere un momento");

		nuevoFamiliar.setOnClickListener(createUserOnClickListener);

	}

	public boolean validarUsuarioPorCedula(String cedula) {
		boolean resultado = false;
		ServiciosUsuario servicios = new ServiciosUsuario();
		Usuario usuario = new Usuario();
		usuario = servicios.obtenerUsuarioPorCedula(cedula);
		if (usuario != null) {
			usuarioAlert2.setMessage("El familiar ingresado ya existe.");
			usuarioAlert2.create().show();
		} else {
			resultado = agregarUsuario();
		}
		return resultado;
	}

	public boolean agregarUsuario() {
		boolean validate = false;
		Usuario usuario = new Usuario();
		Usuario usuarioCreado = new Usuario();
		usuario.setCedula(Integer.valueOf(cedula));
		usuario.setNombrePaciente(nombre);
		usuario.setApellidoPaciente(apellido);
		usuario.setEmail(correo);
		password = "1234";
		usuario.setPassword(password);
		usuario.setRol("Usuario");

		ServiciosUsuario servicio = new ServiciosUsuario();
		usuarioCreado = servicio.crearUsuario(usuario);
		if (usuarioCreado == null) {
			usuarioAlert2.setMessage("El familiar no pudo ser agregado.");
			usuarioAlert2.create().show();
			validate = false;
		} else {

			boolean resultado1 = agregarExpedienteUsuario();
			if (resultado1) {

				validate = true;
			} else {

				validate = false;
			}

		}
		return validate;
	}


	public boolean agregarExpedienteUsuario() {
		boolean validate = false;
		ExpedienteUsuario expedienteUsuario = new ExpedienteUsuario();
		ExpedienteUsuario expedienteUsuarioPaciente = new ExpedienteUsuario();
		ExpedienteUsuario expedienteUsuarioFinal = new ExpedienteUsuario();


		ServiciosExpedienteUsuario serviciosExpedienteUsuario = new ServiciosExpedienteUsuario();
		expedienteUsuarioFinal = serviciosExpedienteUsuario
				.crearExpedienteUsuario(expedienteUsuario);
		
		expedienteUsuarioPaciente.setFk_idExpediente(Integer.valueOf(expediente));
		expedienteUsuarioPaciente.setFk_idUsuario(Integer.valueOf(cedula));
		expedienteUsuarioPaciente.setRol_expediente("Familiar");
		expedienteUsuarioPaciente.setCompartir(0);
		
		expedienteUsuarioFinal = serviciosExpedienteUsuario
		.crearExpedienteUsuario(expedienteUsuarioPaciente);
		if (expedienteUsuarioFinal != null) {
			validate = true;
		} else {
			validate = false;
		}
		return validate;
	}



	public class CallWebServiceTask extends AsyncTask<String, Void, Integer> {

		protected Context applicationContext;
		int resultado = 0;

		@Override
		protected void onPreExecute() {
			dialogo.show();
		}

		protected void onPostExecute(Integer resultado) {
			dialogo.cancel();
			if (resultado == 1) {
				usuarioAlert1.setMessage("El familiar fue agregado con exito.");
				usuarioAlert1.create().show();
			} else {
				usuarioAlert2.setMessage("El familiar no pudo ser agregado.");
				usuarioAlert2.create().show();
			}
		}


		@Override
		protected Integer doInBackground(String... params) {
			String cedula = params[0];
			if (validarUsuarioPorCedula(cedula) == true) {
				resultado = 1;
			} else {
				resultado = -1;
			}
			return resultado;
		}

	}

	

}
