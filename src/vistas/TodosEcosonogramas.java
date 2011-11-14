package vistas;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import servicios.ServiciosEstudio;
import servicios.ServiciosExpedienteUsuario;
import dominio.Estudio;
import edu.tesis.heraproject.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class TodosEcosonogramas extends Activity {
	public final static String ITEM_TITLE = "title";
	public final static String ITEM_CAPTION = "caption";
	private String ced;

	// SectionHeaders
	private final static String[] trimestres = new String[] { "Primer Trimestre",
			"Segundo Trimestre", "Tercer Trimestre" };

	// Section Contents
	private String[] estudiosPrimerTrimestre = null;
	private String[] estudiosSegundoTrimestre = null;
	private String[] estudiosTercerTrimestre = null;

	// MENU - ListView
	private ListView addJournalEntryItem;

	// Adapter for ListView Contents
	private SeparadorLista adapter;

	// ListView Contents
	private ListView journalListView;

	public Map<String, ?> createItem(String title, String caption) {
		Map<String, String> item = new HashMap<String, String>();
		item.put(ITEM_TITLE, title);
		item.put(ITEM_CAPTION, caption);
		return item;
	}

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		// Sets the View Layer
		Intent intent = getIntent();
		ced = intent.getStringExtra("Cedula");
		
		setContentView(R.layout.todos_ecos);

		// Interactive Tools
		final ArrayAdapter<String> journalEntryAdapter = new ArrayAdapter<String>(
				this, R.layout.item_lista_ecos);

		setListasEstudiosPaciente(ced);
		
		int size1 = estudiosPrimerTrimestre.length;
		int size2 = estudiosSegundoTrimestre.length;
		int size3 = estudiosTercerTrimestre.length;

		String[] listaNumeros1 = setNumeros(size1);
		String[] listaNumeros2 = setNumeros(size2);
		String[] listaNumeros3 = setNumeros(size3);
		boolean[] valores1 = setValores(size1);
		boolean[] valores2 = setValores(size2);
		boolean[] valores3 = setValores(size3);
		String[] listaNumeros4 = setNumeros(size1);
		String[] listaNumeros5 = setNumeros(size2);
		String[] listaNumeros6 = setNumeros(size3);
		// Create the ListView Adapter
		adapter = new SeparadorLista(this);

		ArrayAdapter listadapter1 = new ImageAdapter(this, R.layout.filas,
				R.id.label, R.id.label1, R.id.image1, estudiosPrimerTrimestre,
				listaNumeros1, valores1);
		ArrayAdapter listadapter2 = new ImageAdapter(this, R.layout.filas,
				R.id.label, R.id.label1, R.id.image1, estudiosSegundoTrimestre,
				listaNumeros2, valores2);
		ArrayAdapter listadapter3 = new ImageAdapter(this, R.layout.filas,
				R.id.label, R.id.label1, R.id.image1, estudiosTercerTrimestre,
				listaNumeros3, valores3);
		// Add Sections

		
		
		adapter.addSection(trimestres[0], listadapter1);
		adapter.addSection(trimestres[1], listadapter2);
		adapter.addSection(trimestres[2], listadapter3);

		// Get a reference to the ListView holder
		journalListView = (ListView) this.findViewById(R.id.list_journalT);

		// Set the adapter on the ListView holder
		journalListView.setAdapter(adapter);

		// Listen for Click events
		journalListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long duration) {
				String item = (String) adapter.getItem(position);
				//Toast.makeText(getApplicationContext(), item,
					//	Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(TodosEcosonogramas.this, VistaPacienteSegundoTercero.class);
				startActivity(intent);
			}
		});
	}

	public void setListasEstudiosPaciente(String cedula) {
		ServiciosEstudio serviciosEstudio = new ServiciosEstudio();
		ServiciosExpedienteUsuario serviciosExpediente = new ServiciosExpedienteUsuario();

		int fk = serviciosExpediente.obtenerExpedientePaciente(cedula)
				.getFk_idExpediente();
		
		ArrayList<Estudio> todosEstudios = new ArrayList<Estudio>();
		ArrayList<Estudio> estudiosPacientesPrimer = new ArrayList<Estudio>();
		ArrayList<Estudio> estudiosPacientesSegundo = new ArrayList<Estudio>();
		ArrayList<Estudio> estudiosPacientesTercer = new ArrayList<Estudio>();
		
		todosEstudios = serviciosEstudio.obtenerEstudiosPorPaciente(fk);
		
		estudiosPacientesPrimer = getEstudiosPorTrimestre(1, todosEstudios);
		estudiosPacientesSegundo = getEstudiosPorTrimestre(2, todosEstudios);
		estudiosPacientesTercer = getEstudiosPorTrimestre(3, todosEstudios);
		
		if(estudiosPacientesPrimer.size() != 0){
			estudiosPrimerTrimestre = new String[estudiosPacientesPrimer.size()];
			for(int i = 0 ; i < estudiosPacientesPrimer.size();i++){
				String S = new SimpleDateFormat("dd/MM/yyyy").format(estudiosPacientesPrimer.get(i).getFechaEstudio());
				estudiosPrimerTrimestre[i] = S;
			}
		}
		else{
			estudiosPrimerTrimestre = new String[1];
			estudiosPrimerTrimestre[0] = "No hay data";
		}
		

		if(estudiosPacientesSegundo.size() != 0){
			estudiosSegundoTrimestre = new String[estudiosPacientesSegundo.size()];
			for(int i = 0 ; i < estudiosPacientesSegundo.size();i++){
				String S = new SimpleDateFormat("dd/MM/yyyy").format(estudiosPacientesSegundo.get(i).getFechaEstudio());
				estudiosSegundoTrimestre[i] = S;
			}
		}
		else{
			estudiosSegundoTrimestre = new String[1];
			estudiosSegundoTrimestre[0] = "No hay data";
		}
		
		if(estudiosPacientesTercer.size() != 0){
			estudiosTercerTrimestre = new String[estudiosPacientesTercer.size()];
			for(int i = 0 ; i < estudiosPacientesTercer.size();i++){
				String S = new SimpleDateFormat("dd/MM/yyyy").format(estudiosPacientesTercer.get(i).getFechaEstudio());
				estudiosTercerTrimestre[i] = S;
			}
		}
		else{
			estudiosTercerTrimestre = new String[1];
			estudiosTercerTrimestre[0] = "No hay data";
		}
		

	}

	public boolean[] setValores(int cantidad) {
		boolean[] valores = new boolean[cantidad];

		for (int i = 0; i < cantidad; i++) {
			boolean val = true;
			valores[i] = val;
		}
		return valores;
	}
	public String[] setNumeros(int cantidad) {
		String[] valores = new String[cantidad];

		for (int i = 0; i < cantidad; i++) {
			valores[i] = "";
		}
		return valores;
	}
	
	public ArrayList<Estudio> getEstudiosPorTrimestre(int trimestre, ArrayList<Estudio> estudios){
		ArrayList<Estudio> estudiosPaciente = new ArrayList<Estudio>();

		for(int i = 0 ; i < estudios.size() ; i ++){
			if(estudios.get(i).getTrimestre() == trimestre){
				estudiosPaciente.add(estudios.get(i));
			}
		}
		
		return estudiosPaciente;
		
	}

}
