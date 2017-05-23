package c.baloncesto;

        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.Filter;
        import android.widget.Filterable;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;
        import android.widget.Filter;

        import java.util.List;


public class CustomAdapter extends BaseAdapter implements Filterable{
    String [] result;
    Context context;
    int [] imageId;
    private List<String> filteredItems;
    //private ItemFilter mFilter = new ItemFilter();

    private static LayoutInflater inflater=null;
    public CustomAdapter(MainActivity mainActivity, String[] prgmNameList, int[] prgmImages) {
        // TODO Auto-generated constructor stub
        result=prgmNameList;
        context=mainActivity;
        imageId=prgmImages;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public Filter getFilter() {
        return null;
    }


    public class Holder
    {
        TextView tv;
        ImageView img;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.list_image_text, null);

        holder.tv=(TextView) rowView.findViewById(R.id.textView1);
        holder.img=(ImageView) rowView.findViewById(R.id.imageView1);
        holder.tv.setText(result[position]);
        holder.img.setImageResource(imageId[position]);
        return rowView;

    }



}