package vistas;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
 
public class TextAdapter extends ArrayAdapter
{
    Activity context;
    String[] items;
    String[] numbers;
    boolean[] arrows;
    int layoutId;
    int textId;
    int numberId;
    int imageId;
 
    public TextAdapter(Activity context, int layoutId, int textId, String[] items)
    {
        super(context, layoutId, items);
 
        this.context = context;
        this.items = items;
        this.layoutId = layoutId;
        this.textId = textId;
    }
 
    public View getView(int pos, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater=context.getLayoutInflater();
        View row=inflater.inflate(layoutId, null);
        TextView label=(TextView)row.findViewById(textId);
        label.setText(items[pos]);
  
 
        return(row);
    }
}