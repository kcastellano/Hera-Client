package vistas;

import java.util.ArrayList;

import modulos.AgregarPaciente;
import modulos.VerUsuario;

import edu.tesis.heraproject.R;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.Menu;
import android.widget.ListView;
import android.widget.TextView;

public class MenuPacientes extends ListActivity {
	private TextView txt;
	private Typeface font;
	private ArrayList<String> listaArray;
	private ArrayList<String> numberArray;
	private ArrayList<String> cedulaArray;
	private ArrayList<String> emailArray;
	private String[] listaPrimer = null;
	private String[] listaSegundo = null;
	private String[] listaTercer = null;
	private String[] listaCedulaPrimer = null;
	private String[] listaCedulaSegundo = null;
	private String[] listaCedulaTercer = null;
	private String[] listaEmailPrimer = null;
	private String[] listaEmailSegundo = null;
	private String[] listaEmailTercer = null;
	private int size;
	private String[] listaFinal;
	private String[] listaFinalEmail;
	private String cedulaDoctor;
	private String rol;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.menu_pacientes);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titulo);
		Intent intent = getIntent();
		txt = (TextView) findViewById(R.id.title);
		txt.setText("Hera");
		font = Typeface.createFromAsset(getAssets(), "Chunk.ttf");
		txt.setTypeface(font);

		listaPrimer = intent.getStringArrayExtra("PrimerTrimestre");
		listaSegundo = intent.getStringArrayExtra("SegundoTrimestre");
		listaTercer = intent.getStringArrayExtra("TercerTrimestre");
		listaCedulaPrimer = intent.getStringArrayExtra("CedulasPrimer");
		listaCedulaSegundo = intent.getStringArrayExtra("CedulasSegundo");
		listaCedulaTercer = intent.getStringArrayExtra("CedulasTercero");
		listaEmailPrimer = intent.getStringArrayExtra("EmailPrimer");
		listaEmailSegundo = intent.getStringArrayExtra("EmailSegundo");
		listaEmailTercer = intent.getStringArrayExtra("EmailTercero");
		rol = intent.getStringExtra("Rol");
		if (rol.equalsIgnoreCase("Doctor")) {
			cedulaDoctor = intent.getStringExtra("CedulaDoctor");
		}
		int sizePrimer = 0;
		int sizeSegundo = 0;
		int sizeTercero = 0;

		if ((listaPrimer != null) || (listaSegundo != null)
				|| (listaTercer != null)) {

			if (listaPrimer == null) {
				sizePrimer = 0;
				if (listaSegundo != null) {
					sizeSegundo = listaSegundo.length;
				} else {
					sizeSegundo = 0;
				}
				if (listaTercer != null) {
					sizeTercero = listaTercer.length;

				} else {
					sizeTercero = 0;
				}
			} else if (listaSegundo == null) {
				sizeSegundo = 0;
				if (listaPrimer != null) {
					sizePrimer = listaPrimer.length;
				} else {
					sizePrimer = 0;
				}
				if (listaTercer != null) {
					sizeTercero = listaTercer.length;

				} else {
					sizeTercero = 0;
				}

			} else if (listaTercer == null) {
				sizeTercero = 0;

				sizePrimer = listaPrimer.length;
				sizeSegundo = listaSegundo.length;
				if (listaPrimer != null) {
					sizePrimer = listaPrimer.length;
				} else {
					sizePrimer = 0;
				}
				if (listaSegundo != null) {
					sizeSegundo = listaSegundo.length;
				} else {
					sizeSegundo = 0;
				}
			} else {
				sizePrimer = listaPrimer.length;
				sizeSegundo = listaSegundo.length;
				sizeTercero = listaTercer.length;
			}

			size = sizePrimer + sizeSegundo + sizeTercero;

		} else {
			size = -1;
		}
		listaFinal = null;
		listaFinal = setListasOrdenadas();
		String[] listaNumeros = null;
		listaNumeros = setNumerosOrdenados();
		listaFinalEmail = setEmailOrdenados();
		boolean[] valores = null;
		if (size != -1) {
			valores = setValores(size);
		}

		if (size == -1) {
			setListAdapter(new TextAdapter(this, R.layout.filas, R.id.label, listaFinal));
		} else {
			setListAdapter(new ImageAdapter(this, R.layout.filas, R.id.label,
					R.id.label1, R.id.image1, listaFinal, listaNumeros, valores));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.add_patient:
			Intent intent = new Intent(MenuPacientes.this,
					AgregarPaciente.class);
			intent.putExtra("Cedula", cedulaDoctor);
			startActivity(intent);
			break;

		case R.id.see_profile:
			Intent intentProfile = new Intent(MenuPacientes.this,
					VerUsuario.class);
			intentProfile.putExtra("Cedula", cedulaDoctor);
			intentProfile.putExtra("Rol", rol);
			startActivity(intentProfile);
			break;
		}
		return true;
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		if (size != -1) {
			String[] listaCedulas = new String[size];
			listaCedulas = setCedulasOrdenadas();
			String cedula = listaCedulas[position];
			String nombre = l.getItemAtPosition(position).toString();
			String trim = numberArray.get(position);
			Intent intent = new Intent(MenuPacientes.this, VistaPaciente.class);
			intent.putExtra("Cedula", cedula);
			intent.putExtra("Nombre", nombre);
			intent.putExtra("Trimestre", trim);
			intent.putExtra("Rol", "Doctor");
			intent.putExtra("Lista", listaFinal);
			intent.putExtra("Emails", listaFinalEmail);
			intent.putExtra("CedulaDoctor", cedulaDoctor);
			startActivity(intent);
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

	public String[] setListasOrdenadas() {
		listaArray = new ArrayList<String>();
		String[] lista = null;
		numberArray = new ArrayList<String>();
		if (listaTercer != null) {
			for (int i = 0; i < listaTercer.length; i++) {
				listaArray.add(listaTercer[i]);
				
				numberArray.add("3¡");
			}
		}

		if (listaSegundo != null) {
			for (int i = 0; i < listaSegundo.length; i++) {
				listaArray.add(listaSegundo[i]);
				numberArray.add("2¡");
			}
		}
		if (listaPrimer != null) {
			for (int i = 0; i < listaPrimer.length; i++) {
				listaArray.add(listaPrimer[i]);
				numberArray.add("1¡");
			}
		}
		if (size == -1) {
			lista = new String[1];
			String registro = "No hay registros de pacientes";
			lista[0] = registro;
			numberArray = null;
		} else {
			lista = new String[listaArray.size()];
			for (int i = 0; i < listaArray.size(); i++) {
				lista[i] = listaArray.get(i);
			}
		}
		return lista;

	}

	public String[] setCedulasOrdenadas() {
		cedulaArray = new ArrayList<String>();
		if (listaCedulaTercer != null) {
			for (int i = 0; i < listaCedulaTercer.length; i++) {
				cedulaArray.add(listaCedulaTercer[i]);
			}
		}
		if (listaCedulaSegundo != null) {
			for (int i = 0; i < listaCedulaSegundo.length; i++) {
				cedulaArray.add(listaCedulaSegundo[i]);
			}
		}
		if (listaCedulaPrimer != null) {
			for (int i = 0; i < listaCedulaPrimer.length; i++) {
				cedulaArray.add(listaCedulaPrimer[i]);
			}
		}

		String[] lista = new String[cedulaArray.size()];
		for (int i = 0; i < cedulaArray.size(); i++) {
			lista[i] = cedulaArray.get(i);
		}

		return lista;
	}
	
	public String[] setEmailOrdenados() {
		emailArray = new ArrayList<String>();
		if (listaEmailTercer != null) {
			for (int i = 0; i < listaEmailTercer.length; i++) {
				emailArray.add(listaEmailTercer[i]);
			}
		}
		if (listaEmailSegundo != null) {
			for (int i = 0; i < listaEmailSegundo.length; i++) {
				emailArray.add(listaEmailSegundo[i]);
			}
		}
		if (listaEmailPrimer != null) {
			for (int i = 0; i < listaEmailPrimer.length; i++) {
				emailArray.add(listaEmailPrimer[i]);
			}
		}

		String[] lista = new String[emailArray.size()];
		for (int i = 0; i < emailArray.size(); i++) {
			lista[i] = emailArray.get(i);
		}

		return lista;
	}

	public String[] setNumerosOrdenados() {
		String[] lista = null;
		if (numberArray != null) {
			lista = new String[numberArray.size()];
			for (int i = 0; i < numberArray.size(); i++) {
				lista[i] = numberArray.get(i);
			}
		} else {
			lista = null;
		}

		return lista;
	}
}
