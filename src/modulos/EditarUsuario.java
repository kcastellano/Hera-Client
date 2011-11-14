package modulos;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dominio.Usuario;

import edu.tesis.heraproject.R;
import servicios.ServiciosUsuario;
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

public class EditarUsuario extends Activity {

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
		setContentView(R.layout.editar_usuario);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.titulo);

		txt = (TextView) findViewById(R.id.title);
		txt.setText("Hera");
		font = Typeface.createFromAsset(getAssets(), "Chunk.ttf");
		txt.setTypeface(font);
		nuevoUsuario = (Button) findViewById(R.id.btnEditarUsuario);

		nombreUsuario = (EditText) findViewById(R.id.nombreUsuarioEditar);
		cedulaUsuario = (EditText) findViewById(R.id.cedulaUsuarioEditat);
		correoUsuario = (EditText) findViewById(R.id.correoUsuarioEditar);
		apellidoUsuario = (EditText) findViewById(R.id.apellidoUsuarioEditar);
		contrasenaUsuario = (EditText) findViewById(R.id.contrasenaUsuarioEditar);

		
		Intent intent = getIntent();
		String cedula1 = intent.getStringExtra("Cedula");
		rol = intent.getStringExtra("Rol");
		Usuario usuario = new Usuario();
		usuario = obtenerUsuario(cedula1);
		
		nombreUsuario.setText(usuario.getNombrePaciente());
		cedulaUsuario.setText(String.valueOf(usuario.getCedula()));
		correoUsuario.setText(usuario.getEmail());
		apellidoUsuario.setText(usuario.getApellidoPaciente());
		
		
		usuarioAlert1 = new AlertDialog.Builder(this);
		usuarioAlert1.setTitle("Editar usuario");
		usuarioAlert1.setCancelable(true);
		usuarioAlert1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			  @Override
			  public void onClick(DialogInterface dialog, int which) {
			    dialog.dismiss();
			    Intent intent = new Intent(EditarUsuario.this, VerUsuario.class);
			    if(rol.equalsIgnoreCase("Familiar")){
			    	intent.putExtra("Rol",rol);
			    	intent.putExtra("CedulaFamiliar",cedula);
			    }
			    else{
				intent.putExtra("Cedula",cedula);
				intent.putExtra("Rol",rol);
			    }
				startActivity(intent);
			  }
			});


		usuarioAlert2 = new AlertDialog.Builder(this);
		usuarioAlert2.setTitle("Editar usuario");
		usuarioAlert2.setCancelable(true);
		usuarioAlert2.setPositiveButton("Ok", null)
;		Button.OnClickListener createUserOnClickListener = new Button.OnClickListener() {

			public void onClick(View v) {
				CallWebServiceTask task = new CallWebServiceTask();
				task.applicationContext = EditarUsuario.this;
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
		dialog.setTitle("Editar usuario");
		dialog.setMessage("Espere un momento");
		
		nuevoUsuario.setOnClickListener(createUserOnClickListener);

	}

	public boolean validarUsuarioPorCorreo(String email) {
		boolean validate = false;
		ServiciosUsuario servicios = new ServiciosUsuario();
		Usuario usuario = new Usuario();
		usuario = servicios.obtenerUsuarioPorEmail(email);

		if (usuario != null) {
			usuarioAlert2.setMessage("El correo ya existe por favor elija otro correo.");
			usuarioAlert2.create().show();
			validate = false;
		} else {
			validate = true;
		}

		return validate;
	}


	public boolean actualizarUsuario() {
		boolean validate = false;
		Usuario usuario = new Usuario();;
		usuario.setCedula(Integer.valueOf(cedula));
		usuario.setNombrePaciente(nombre);
		usuario.setApellidoPaciente(apellido);
		usuario.setEmail(correo);
		usuario.setPassword(contrasena);
		usuario.setRol(rol);

		ServiciosUsuario servicio = new ServiciosUsuario();
		usuario = servicio.actualizarUsuario(usuario);
		if (usuario == null) {

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
				usuarioAlert1.setMessage("El usuario fue editado con exito.");
				usuarioAlert1.create().show();
			}
			else{
				usuarioAlert2.setMessage("El usuario no pudo ser editado.");
				usuarioAlert2.create().show();
			}
		}

		@Override
		protected Integer doInBackground(String... params) {			
			if (checkEmailCorrect(correo) == true) {
					if(actualizarUsuario() == true){
						resultado = 1;
					}
					else{
						resultado = -1;
					}
			}
			return resultado;
		}

	}
	
	public Usuario obtenerUsuario(String cedula) {
		Usuario usuario = new Usuario();
		ServiciosUsuario servicio = new ServiciosUsuario();
		usuario = servicio.obtenerUsuarioPorCedula(cedula);

		return usuario;
	}
}
