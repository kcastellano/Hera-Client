package vistas;

import java.util.ArrayList;
import modulos.AgregarFamiliar;
import dominio.ExpedienteUsuario;
import dominio.Usuario;
import servicios.ServiciosExpedienteUsuario;
import servicios.ServiciosUsuario;
import edu.tesis.heraproject.R;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class MenuFamiliares extends ListActivity {
	private TextView txt;
	private Typeface font;
	private ArrayList<Usuario> listaArray;
	private int size;
	private String[] listaFinal;
	private Button agregarFamiliar;
	private String expediente;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.menu_familiares);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titulo);
		Intent intent = getIntent();
		txt = (TextView) findViewById(R.id.title);
		txt.setText("Hera");
		font = Typeface.createFromAsset(getAssets(), "Chunk.ttf");
		txt.setTypeface(font);
		agregarFamiliar = (Button) findViewById(R.id.agregarFamiliar);
		expediente = intent.getStringExtra("Expediente");
		
		listaFinal = null;
		listaFinal = setListasOrdenadas(expediente);

		if (size == -1) {
			setListAdapter(new TextAdapter(this, R.layout.filas, R.id.label, listaFinal));
		} else {
			setListAdapter(new TextAdapter(this, R.layout.filas, R.id.label, listaFinal));
		}
		
		Button.OnClickListener addRelativeOnClickListener = new Button.OnClickListener() {

			public void onClick(View v) {
				ServiciosExpedienteUsuario servicios = new ServiciosExpedienteUsuario();
				ExpedienteUsuario expedienteUsuario = new ExpedienteUsuario();
				
				expedienteUsuario = servicios.obtenerPacientePorExpediente(expediente);
				Intent intent = new Intent(MenuFamiliares.this, AgregarFamiliar.class);
				intent.putExtra("Cedula",String.valueOf(expedienteUsuario.getFk_idUsuario()));
				intent.putExtra("Expediente",expediente);
				startActivity(intent);
			}
		};

		agregarFamiliar.setOnClickListener(addRelativeOnClickListener);
	}


	public boolean[] setValores(int cantidad) {
		boolean[] valores = new boolean[cantidad];

		for (int i = 0; i < cantidad; i++) {
			boolean val = true;
			valores[i] = val;
		}
		return valores;
	}

	public String[] setListasOrdenadas(String expediente) {
		ServiciosUsuario servicio = new ServiciosUsuario();
		listaArray = servicio.obtenerFamiliares(expediente);
		String[] lista = null;
		
		if (listaArray == null) {
			lista = new String[1];
			String registro = "No hay registros de familiares";
			lista[0] = registro;
		} else {
			lista = new String[listaArray.size()];
			for (int i = 0; i < listaArray.size(); i++) {
				lista[i] = listaArray.get(i).getNombrePaciente() + " " + listaArray.get(i).getApellidoPaciente();
			}
		}
		return lista;

	}

	
}
