package ir.shahabazimi.omidanasansor.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.textview.MaterialTextView;
import java.util.List;

import ir.shahabazimi.omidanasansor.R;
import ir.shahabazimi.omidanasansor.activities.DetailsActivity;
import ir.shahabazimi.omidanasansor.classes.Utils;
import ir.shahabazimi.omidanasansor.models.StatsModel;


public class StatsAdapter extends RecyclerView.Adapter<StatsAdapter.ViewHolder> {

    private List<StatsModel> data;
    private Context context;
    private String year;
    private String eyear;

    public StatsAdapter(Context context, List<StatsModel> data, String year){
        this.data=data;
        this.context=context;
        this.year=year;
        this.eyear = String.valueOf(Integer.valueOf(year)+621);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_year,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int position) {

        StatsModel model = data.get(position);

        h.count.setText("تعداد: "+model.getCount());
        h.price.setText("مبلغ: "+ Utils.moneySeparator(model.getSum())+" تومان");

        switch (model.getId()){
            case 1:
                h.title.setText("فروردین");
                break;
            case 2:
                h.title.setText("اردیبهشت");
                break;
            case 3:
                h.title.setText("خرداد");
                break;
            case 4:
                h.title.setText("تیر");
                break;
            case 5:
                h.title.setText("مرداد");
                break;
            case 6:
                h.title.setText("شهریور");
                break;
            case 7:
                h.title.setText("مهر");
                break;
            case 8:
                h.title.setText("آبان");
                break;
            case 9:
                h.title.setText("آذر");
                break;
            case 10:
                h.title.setText("دی");
                break;
            case 11:
                h.title.setText("بهمن");
                break;
            case 12:
                h.title.setText("اسفند");
                break;
            case 13:
                h.title.setText("امسال");
                break;

            case 14:
                h.title.setText("کل");
                break;
        }

        h.itemView.setOnClickListener(w->{
            if(Integer.parseInt(model.getCount())>0 && model.getId()<13) {
                    Intent in = new Intent(context, DetailsActivity.class);
                    in.putExtra("month",model.getId()+"");
                    in.putExtra("year",year);
                    in.putExtra("eyear",eyear);
                    context.startActivity(in);
            }

        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        private MaterialTextView count,price,title;


        ViewHolder(@NonNull View v) {
            super(v);
            count = v.findViewById(R.id.year_count);
            price = v.findViewById(R.id.year_price);
            title = v.findViewById(R.id.year_title);
        }
    }
}

