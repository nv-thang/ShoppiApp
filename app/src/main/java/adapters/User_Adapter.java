package adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.os.shoppiapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import models.Users;

public class User_Adapter extends RecyclerView.Adapter<User_Adapter.ViewHolder> {

    Context context;
    ArrayList<Users> users_models;

    public User_Adapter(Context context, ArrayList<Users> users_models) {
        this.context = context;
        this.users_models = users_models;
    }

    @NonNull
    @Override
    public User_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_manage, parent, false);
        return new User_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        Picasso.get().load(users_models.get(position).getImageUser()).error(R.drawable.img_loading).into(holder.imageUser);
        holder.name.setText(users_models.get(position).getName());
        holder.mail.setText(users_models.get(position).getEmail());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn có muốn xóa?");
                builder.setCancelable(false);
                builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(context, "Không xóa", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseAuth.getInstance().getCurrentUser().delete();
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                Toast.makeText(context, "Long item clicked " + users_models.get(position).getName(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return users_models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageUser;
        TextView name, mail;

        public ViewHolder(View itemView) {
            super(itemView);

            imageUser = itemView.findViewById(R.id.imgUser);
            name = itemView.findViewById(R.id.nameUser);
            mail = itemView.findViewById(R.id.mailUser);


        }
    }

    public void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có muốn xóa?");
        builder.setCancelable(false);
        builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(context, "Không xóa", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

}