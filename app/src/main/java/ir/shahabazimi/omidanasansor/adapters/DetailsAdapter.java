package ir.shahabazimi.omidanasansor.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.textview.MaterialTextView;
import java.util.List;

import ir.shahabazimi.omidanasansor.R;
import ir.shahabazimi.omidanasansor.classes.DateConverter;
import ir.shahabazimi.omidanasansor.classes.Utils;
import ir.shahabazimi.omidanasansor.models.DetailsModel;


public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.ViewHolder> {
    private List<DetailsModel> data;

    public DetailsAdapter(List<DetailsModel> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int position) {
        DetailsModel model = data.get(position);
        h.name.setText("نام: " + model.getUserName());
        h.number.setText("شماره تلفن: " + model.getUserNumber());
        h.code.setText("کد مشتری: " + model.getUserCode());
        h.price.setText("مبلغ فاکتور: " + Utils.moneySeparator(model.getBuyPrice())+" تومان");
        h.wallet.setText("مبلغ استفاده شده از کیف پول: " + Utils.moneySeparator(model.getBuyWallet())+" تومان");
        h.pay.setText("مبلغ پرداختی: " + Utils.moneySeparator(model.getBuyPay())+" تومان");
        if(model.getBuyTitle().equals("1"))
            h.type.setText("عنوان فاکتور: خرید آسانسور");
        else
            h.type.setText("عنوان فاکتور: سایر خدمات");
        String date = model.getBuyDate();
        DateConverter dateConverter = new DateConverter();

        dateConverter.gregorianToPersian(Integer.parseInt(date.substring(0,4)),Integer.parseInt(date.substring(5,7)),Integer.parseInt(date.substring(8,10)));
        h.date.setText("تایخ ثبت: "+dateConverter.getYear()+"/"+dateConverter.getMonth()+"/"+dateConverter.getDay()+" "+date.substring(11,16));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private MaterialTextView name, number, code, price, wallet, pay,type, date;

        ViewHolder(@NonNull View v) {
            super(v);
            name = v.findViewById(R.id.detail_name);
            number = v.findViewById(R.id.detail_number);
            code = v.findViewById(R.id.detail_code);
            price = v.findViewById(R.id.detail_price);
            wallet = v.findViewById(R.id.detail_wallet);
            pay = v.findViewById(R.id.detail_pay);
            type = v.findViewById(R.id.detail_type);
            date = v.findViewById(R.id.detail_date);
        }
    }
}
