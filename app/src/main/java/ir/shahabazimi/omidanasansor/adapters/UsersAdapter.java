package ir.shahabazimi.omidanasansor.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;

import ir.shahabazimi.omidanasansor.R;
import ir.shahabazimi.omidanasansor.classes.DateConverter;
import ir.shahabazimi.omidanasansor.classes.Utils;
import ir.shahabazimi.omidanasansor.data.RetrofitClient;
import ir.shahabazimi.omidanasansor.models.GeneralResponse;
import ir.shahabazimi.omidanasansor.models.TrackingModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    private Context context;
    private List<TrackingModel> data;
    private List<TrackingModel> filter;

    public UsersAdapter(Context context, List<TrackingModel> data) {
        setHasStableIds(true);
        filter = new ArrayList<>();
        this.data = data;
        filter.addAll(data);

        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false));
    }


    public void search(String text) {
        text = Utils.arabicToDecimal(text);
        filter.clear();
        if (text.isEmpty()) {
            filter.addAll(data);
        } else {
            text = text.toLowerCase();
            for (TrackingModel m : data) {
                if (m.getUserCode().toLowerCase().contains(text)
                        || m.getUserName().toLowerCase().contains(text)
                        || m.getUserNumber().toLowerCase().contains(text)) {
                    filter.add(m);
                }
            }
        }
        notifyDataSetChanged();

    }

    public void update(int a) {
        filter.clear();
        switch (a) {
            case 0:
                filter.addAll(data);
                break;
            case 1:
                for (TrackingModel m : data)
                    if (m.getBuyId() == null)
                        filter.add(m);
                break;

            case 2:
                for (TrackingModel m : data)
                    if (m.getBuyId() != null)
                        filter.add(m);
                break;
        }
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int position) {
        TrackingModel model = filter.get(position);
        if (!model.getUserName().isEmpty())
            h.name.setText(model.getUserName());
        else
            h.name.setVisibility(View.GONE);


        h.number.setText("شماره تلفن: " + model.getUserNumber());
        h.wallet.setText("میزان کیف پول: " + Utils.moneySeparator(model.getWalletAmount()) + " تومان");
        h.point.setText("میزان امتیاز: " + model.getPointAmount());
        h.code.setText("کد مشتری: " + model.getUserCode());

        String date = model.getUserReg();
        DateConverter dateConverter = new DateConverter();

        dateConverter.gregorianToPersian(Integer.parseInt(date.substring(0, 4)), Integer.parseInt(date.substring(5, 7)), Integer.parseInt(date.substring(8, 10)));
        h.date.setText("تایخ ثبت نام: " + dateConverter.getYear() + "/" + dateConverter.getMonth() + "/" + dateConverter.getDay() + " " + date.substring(11, 16));


        if (model.getUserBday() != null && !model.getUserBday().isEmpty())
            h.stat.setText("تاریخ تولد: " + model.getUserBday());
        else
            h.stat.setVisibility(View.GONE);
        switch (model.getUserReason()) {
            case "1":
                h.address.setText("علت مراجعه: خرید آسانسور");
                break;
            case "2":
                h.address.setText("علت مراجعه: سایر خدمات");
                break;

        }

        h.number.setOnClickListener(v -> {
            Intent in = new Intent(Intent.ACTION_DIAL);
            in.setData(Uri.parse("tel:" + model.getUserNumber()));
            context.startActivity(in);
        });
        h.check.setOnClickListener(v -> {
            String p = h.spinner.getText().toString();


            if (!p.isEmpty()) {
                if (!p.equals(model.getPointAmount()))
                    updatePoints(model.getUserId(), p, position);

            }

        });
        h.itemView.setOnClickListener(v -> {
            if (h.more.getVisibility() == View.VISIBLE) {
                h.more.setVisibility(View.GONE);
                h.moreIcon.setImageResource(R.drawable.vector_down);
            } else {
                h.more.setVisibility(View.VISIBLE);
                h.moreIcon.setImageResource(R.drawable.vector_up);
            }


        });


    }

    @Override
    public int getItemCount() {
        return filter.size();
    }

    private void updatePoints(String id, String point, int row) {
        Utils.hideKeyboard((Activity) context);
        RetrofitClient.getInstance().getApi().UpdatePoints(id, point)
                .enqueue(new Callback<GeneralResponse>() {
                    @Override
                    public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                        if (response.isSuccessful() && response.body() != null && response.body().getMessage().equals("success")) {
                            Toast.makeText(context, "با موفقیت ثبت شد", Toast.LENGTH_SHORT).show();
                            data.get(row).setPointAmount(point);
                            notifyItemChanged(row);
                        }
                    }

                    @Override
                    public void onFailure(Call<GeneralResponse> call, Throwable t) {

                    }
                });

    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private MaterialTextView name, number, address, stat, date, wallet, point, code;
        private EditText spinner;
        private ImageView check, moreIcon;
        private ConstraintLayout more;

        public ViewHolder(@NonNull View v) {
            super(v);
            name = v.findViewById(R.id.detail_name);
            number = v.findViewById(R.id.detail_number);
            address = v.findViewById(R.id.detail_address);
            stat = v.findViewById(R.id.detail_stat);
            date = v.findViewById(R.id.detail_date);
            check = v.findViewById(R.id.detail_check);
            wallet = v.findViewById(R.id.detail_wallet);
            point = v.findViewById(R.id.detail_point);
            code = v.findViewById(R.id.detail_code);
            more = v.findViewById(R.id.detail_more);
            moreIcon = v.findViewById(R.id.detail_icon_more);

            spinner = v.findViewById(R.id.detail_spinner);
        }
    }
}
