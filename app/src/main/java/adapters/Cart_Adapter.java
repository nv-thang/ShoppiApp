package adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.os.shoppiapp.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import models.Model_Cart;

public class Cart_Adapter extends RecyclerView.Adapter<Cart_Adapter.ViewHolder> {

    Context context;
    ArrayList<Model_Cart> cart_models;

    public Cart_Adapter(Context context, ArrayList<Model_Cart> cart_models) {
        this.context = context;
        this.cart_models = cart_models;
    }

    @NonNull
    @Override
    public Cart_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new Cart_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Cart_Adapter.ViewHolder holder, final int position) {
        Picasso.get().load(cart_models.get(position).getImage()).error(R.drawable.img_loading).into(holder.imageCart, new Callback() {
            @Override
            public void onSuccess() {
                holder.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {
                holder.progressBar.setVisibility(View.GONE);
            }
        });
        holder.nameCart.setText(cart_models.get(position).getTen());
        holder.gia.setText(cart_models.get(position).getGia());

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickedListener != null) {
                    onItemClickedListener.onItemClick(cart_models.get(position).getId());
                    Toast.makeText(v.getContext(), (position)+" ", Toast.LENGTH_SHORT).show();
                    cart_models.remove(position);
                    cart_models.clear();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cart_models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageCart, imgDelete;
        TextView gia, nameCart;
        LinearLayout ln;
        ProgressBar progressBar;

        public ViewHolder(View itemView) {
            super(itemView);

            ln = itemView.findViewById(R.id.ln);
            imageCart = itemView.findViewById(R.id.cart_image);
            imgDelete = itemView.findViewById(R.id.car_delete);
            nameCart = itemView.findViewById(R.id.cart_name);
            gia = itemView.findViewById(R.id.cart_gia);
            progressBar = itemView.findViewById(R.id.progressbarCart);

        }
    }
    public interface OnItemClickedListener {
        void onItemClick(String id);
    }

    private OnItemClickedListener onItemClickedListener;

    public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }
}