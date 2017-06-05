package com.example.da08.contects;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.da08.contects.domain.PhoneData;

import java.util.List;


public class ListViewActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        ContactActivity contactActivity = new ContactActivity();

        List<PhoneData> datas = contactActivity.getContacts();
        CustomAdapter adapter = new CustomAdapter(datas);
        ((RecyclerView)findViewById(R.id.recycler)).setAdapter(adapter);

    }
}

class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.Holder> {

    List<PhoneData> datas;

    public CustomAdapter(List<PhoneData> datas) {
        this.datas = datas;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, null);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        PhoneData data = datas.get(position);


        holder.name.setText(data.getName());
        holder.id.setText(data.getId());
        holder.tel.setText(data.getTel());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class Holder extends RecyclerView.ViewHolder {

        private TextView id, name, tel;

        public Holder(View itemView) {
            super(itemView);

            id = (TextView) itemView.findViewById(R.id.txtId);
            name = (TextView) itemView.findViewById(R.id.txtName);
            tel = (TextView) itemView.findViewById(R.id.txtTel);
        }

        public TextView getId() {
            return id;
        }

        public void setId(TextView id) {
            this.id = id;
        }

        public TextView getName() {
            return name;
        }

        public void setName(TextView name) {
            this.name = name;
        }

        public TextView getTel() {
            return tel;
        }

        public void setTel(TextView tel) {
            this.tel = tel;
        }
    }

}