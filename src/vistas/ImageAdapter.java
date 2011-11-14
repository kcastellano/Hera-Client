package vistas;


import edu.tesis.heraproject.R;
import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
public class ImageAdapter extends ArrayAdapter
{
    Activity context;
    String[] items;
    String[] numbers;
    boolean[] arrows;
    int layoutId;
    int textId;
    int numberId;
    int imageId;
 
    public ImageAdapter(Activity context, int layoutId, int textId, int numberId, int imageId, String[] items, String[] numbers, boolean[] arrows)
    {
        super(context, layoutId, items);
 
        this.context = context;
        this.items = items;
        this.arrows = arrows;
        this.layoutId = layoutId;
        this.textId = textId;
        this.numberId = numberId;
        this.numbers = numbers;
        this.imageId = imageId;
    }
 
    public View getView(int pos, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater=context.getLayoutInflater();
        View row=inflater.inflate(layoutId, null);
        TextView label=(TextView)row.findViewById(textId);
        TextView number = (TextView) row.findViewById(numberId);
        Typeface font = Typeface.createFromAsset(context.getAssets(), "Chunk.ttf");
		number.setTypeface(font);
        label.setText(items[pos]);
        number.setText(numbers[pos]);

        if (arrows[pos])
        {
         ImageView icon=(ImageView)row.findViewById(imageId); 
            icon.setImageResource(R.drawable.arrow);
        }   
 
        return(row);
    }
}