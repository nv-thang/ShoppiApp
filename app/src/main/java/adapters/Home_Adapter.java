package adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.QuickContactBadge;
import android.widget.TextView;
import android.widget.Toast;

import com.example.os.shoppiapp.DetailActivity;
import com.example.os.shoppiapp.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import models.Model_Home;

public class Home_Adapter extends RecyclerView.Adapter<Home_Adapter.ViewHolder> {

    Context context;
    ArrayList<Model_Home> home_models;

    public Home_Adapter(Context context, ArrayList<Model_Home> home_models) {
        this.context = context;
        this.home_models = home_models;
    }

    @NonNull
    @Override
    public Home_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_home, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Home_Adapter.ViewHolder holder, int position) {

        Picasso.get().load(home_models.get(position).getImage()).error(R.drawable.img_loading).into(holder.image, new Callback() {
            @Override
            public void onSuccess() {
                holder.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {
                holder.progressBar.setVisibility(View.GONE);
            }
        });
        holder.phantram.setText(home_models.get(position).getPhantram());
        holder.gia.setText(home_models.get(position).getGia());
        holder.giakm.setText(home_models.get(position).getGiakm());
        holder.ten.setText(home_models.get(position).getTen());

        final String ten = home_models.get(position).getTen();
        final String thongtin = home_models.get(position).getThongtin();
        final String id = home_models.get(position).getId();
        final String gia =home_models.get(position).getGia();
        final String giakm= home_models.get(position).getGiakm();


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailActivity.class);
                intent.putExtra("ten", ten);
                intent.putExtra("thongtin", thongtin);
                intent.putExtra("ID", id);
                intent.putExtra("gia", gia);
                intent.putExtra("giakm", giakm);

                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return home_models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView phantram, ten, gia, giakm;
        ProgressBar progressBar;

        public ViewHolder(View itemView) {
            super(itemView);

            progressBar = itemView.findViewById(R.id.progress);
            image = itemView.findViewById(R.id.imageshose);
            phantram = itemView.findViewById(R.id.discount);
            ten = itemView.findViewById(R.id.ten);
            gia = itemView.findViewById(R.id.gia);
            giakm = itemView.findViewById(R.id.giakm);

        }
    }

}
