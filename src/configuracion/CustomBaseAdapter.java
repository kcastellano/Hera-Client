package configuracion;

import java.util.ArrayList;

import edu.tesis.heraproject.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomBaseAdapter extends BaseAdapter {

	private static ArrayList<String> searchArrayList;
	private static ArrayList<String> titulosDiagnostico;
	private LayoutInflater mInflater;
	
	public CustomBaseAdapter(Context context, ArrayList<String> results, ArrayList<String> titulos) {
		 searchArrayList = results;
		 titulosDiagnostico = titulos;
		 mInflater = LayoutInflater.from(context);
		 }
	
	@Override
	public int getCount() {
		return searchArrayList.size();
	}

	@Override
	public Object getItem(int position) {
		 return searchArrayList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		  ViewHolder holder;
		  if (convertView == null) {
		   convertView = mInflater.inflate(R.layout.filas_personalizadas, null);
		   holder = new ViewHolder();
		   holder.txtTitle = (TextView) convertView.findViewById(R.id.titulos);
		   holder.txtResult = (TextView) convertView.findViewById(R.id.diagnosticos);

		   convertView.setTag(holder);
		  } else {
		   holder = (ViewHolder) convertView.getTag();
		  }
		  
		  holder.txtTitle.setText(titulosDiagnostico.get(position));
		  holder.txtResult.setText(searchArrayList.get(position));

		  return convertView;
	}
	
	static class ViewHolder {
		  TextView txtTitle;
		  TextView txtResult;
		 }
}
