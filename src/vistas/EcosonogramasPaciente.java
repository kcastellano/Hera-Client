package vistas;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import servicios.ServiciosEstudio;
import servicios.ServiciosExpedienteUsuario;
import servicios.ServiciosUsuario;

import dominio.Estudio;
import dominio.Usuario;
import edu.tesis.heraproject.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class EcosonogramasPaciente extends Activity {

	public final static String ITEM_TITLE = "title";
	public final static String ITEM_CAPTION = "caption";
	private String ced;
	private int idEstudio;

	// SectionHeaders
	private final static String[] trimestres = new String[] {
			"Primer Trimestre", "Segundo Trimestre", "Tercer Trimestre" };

	// Section Contents
	private String[] estudiosPrimerTrimestre = null;
	private String[] estudiosSegundoTrimestre = null;
	private String[] estudiosTercerTrimestre = null;
	private Integer[] idEstudiosPrimerTrimestre = null;
	private Integer[] idEstudiosSegundoTrimestre = null;
	private Integer[] idEstudiosTercerTrimestre = null;
	private ArrayList<Estudio> estudiosPacientesPrimer;
	private ArrayList<Estudio> estudiosPacientesSegundo;
	private ArrayList<Estudio> estudiosPacientesTercer;

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

		setContentView(R.layout.ecos_paciente);

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
		journalListView = (ListView) this.findViewById(R.id.list_journal);

		// Set the adapter on the ListView holder
		journalListView.setAdapter(adapter);

		// Listen for Click events
		journalListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long duration) {
				Integer item = (Integer) adapter.getPosition(position) + 1;
				String trimestre = (String) adapter.getSection(position);
				String idEstudio = String.valueOf(getNumeroEstudio(trimestre,
						item));
				String fecha = (String) adapter.getItem(position);

				if (!fecha.equalsIgnoreCase("No hay data")) {
					Intent intent = new Intent(EcosonogramasPaciente.this,
							VistaPacienteSegundoTercero.class);
					intent.putExtra("idEstudio", idEstudio);
					intent.putExtra("fecha", fecha);
					startActivity(intent);
				}
			}
		});
	}

	public void setListasEstudiosPaciente(String cedula) {
		ServiciosEstudio serviciosEstudio = new ServiciosEstudio();
		ServiciosExpedienteUsuario serviciosExpediente = new ServiciosExpedienteUsuario();

		int fk = serviciosExpediente.obtenerExpedientePaciente(cedula)
				.getFk_idExpediente();

		ArrayList<Estudio> todosEstudios = new ArrayList<Estudio>();
		estudiosPacientesPrimer = new ArrayList<Estudio>();
		estudiosPacientesSegundo = new ArrayList<Estudio>();
		estudiosPacientesTercer = new ArrayList<Estudio>();

		todosEstudios = serviciosEstudio.obtenerEstudiosPorPaciente(fk);

		estudiosPacientesPrimer = getEstudiosPorTrimestre(1, todosEstudios);
		estudiosPacientesSegundo = getEstudiosPorTrimestre(2, todosEstudios);
		estudiosPacientesTercer = getEstudiosPorTrimestre(3, todosEstudios);

		if (estudiosPacientesPrimer.size() != 0) {
			estudiosPrimerTrimestre = new String[estudiosPacientesPrimer.size()];
			idEstudiosPrimerTrimestre = new Integer[estudiosPacientesPrimer
					.size()];
			for (int i = 0; i < estudiosPacientesPrimer.size(); i++) {
				String S = new SimpleDateFormat("dd/MM/yyyy")
						.format(estudiosPacientesPrimer.get(i)
								.getFechaEstudio());
				int id = estudiosPacientesPrimer.get(i).getIdEstudio();
				estudiosPrimerTrimestre[i] = S;
				idEstudiosPrimerTrimestre[i] = id;
			}
		} else {
			estudiosPrimerTrimestre = new String[1];
			idEstudiosPrimerTrimestre = new Integer[1];
			estudiosPrimerTrimestre[0] = "No hay data";
			idEstudiosPrimerTrimestre[0] = 0;
		}

		if (estudiosPacientesSegundo.size() != 0) {
			estudiosSegundoTrimestre = new String[estudiosPacientesSegundo
					.size()];
			idEstudiosSegundoTrimestre = new Integer[estudiosPacientesSegundo
					.size()];
			for (int i = 0; i < estudiosPacientesSegundo.size(); i++) {
				String S = new SimpleDateFormat("dd/MM/yyyy")
						.format(estudiosPacientesSegundo.get(i)
								.getFechaEstudio());
				int id = estudiosPacientesSegundo.get(i).getIdEstudio();
				estudiosSegundoTrimestre[i] = S;
				idEstudiosSegundoTrimestre[i] = id;
			}
		} else {
			estudiosSegundoTrimestre = new String[1];
			idEstudiosSegundoTrimestre = new Integer[1];
			estudiosSegundoTrimestre[0] = "No hay data";
			idEstudiosSegundoTrimestre[0] = 0;
		}

		if (estudiosPacientesTercer.size() != 0) {
			estudiosTercerTrimestre = new String[estudiosPacientesTercer.size()];
			idEstudiosTercerTrimestre = new Integer[estudiosPacientesTercer
					.size()];
			for (int i = 0; i < estudiosPacientesTercer.size(); i++) {
				String S = new SimpleDateFormat("dd/MM/yyyy")
						.format(estudiosPacientesTercer.get(i)
								.getFechaEstudio());
				int id = estudiosPacientesTercer.get(i).getIdEstudio();
				estudiosTercerTrimestre[i] = S;
				idEstudiosTercerTrimestre[i] = id;
			}
		} else {
			estudiosTercerTrimestre = new String[1];
			idEstudiosTercerTrimestre = new Integer[1];
			estudiosTercerTrimestre[0] = "No hay data";
			idEstudiosTercerTrimestre[0] = 0;
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

	public ArrayList<Estudio> getEstudiosPorTrimestre(int trimestre,
			ArrayList<Estudio> estudios) {
		ArrayList<Estudio> estudiosPaciente = new ArrayList<Estudio>();

		for (int i = 0; i < estudios.size(); i++) {
			if (estudios.get(i).getTrimestre() == trimestre) {
				estudiosPaciente.add(estudios.get(i));
			}
		}

		return estudiosPaciente;

	}

	public Integer getNumeroEstudio(String trimestre, Integer item) {
		Integer idEstudio = 0;
		if (trimestre.equalsIgnoreCase("Primer Trimestre")) {
			int posicion = item;
			idEstudio = idEstudiosPrimerTrimestre[posicion - 1];
		} else if (trimestre.equalsIgnoreCase("Segundo Trimestre")) {
			int posicion = item;
			idEstudio = idEstudiosSegundoTrimestre[posicion - 1];
		} else {
			int posicion = item;
			idEstudio = idEstudiosTercerTrimestre[posicion - 1];
		}

		return idEstudio;
	}
}
