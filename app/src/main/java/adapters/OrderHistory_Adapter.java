package adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.os.shoppiapp.R;

import java.util.ArrayList;

import models.Order;

public class OrderHistory_Adapter extends RecyclerView.Adapter<OrderHistory_Adapter.ViewHolder> {

    Context context;
    ArrayList<Order> order_models;

    public OrderHistory_Adapter(Context context, ArrayList<Order> order_models) {
        this.context = context;
        this.order_models = order_models;
    }

    @NonNull
    @Override
    public OrderHistory_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_history, parent, false);
        return new OrderHistory_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final OrderHistory_Adapter.ViewHolder holder, final int position) {
        holder.name.setText(order_models.get(position).getSanpham());
        holder.date.setText(order_models.get(position).getDate());
        holder.total.setText(order_models.get(position).getTongtien());
        holder.tenuser.setText(order_models.get(position).getHoTen());
        holder.diachiuser.setText(order_models.get(position).getDiachi());

//        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (onItemClickedListener != null) {
//                    onItemClickedListener.onItemClick(cart_models.get(position).getId());
//                    Toast.makeText(v.getContext(), (position)+" ", Toast.LENGTH_SHORT).show();
//                    cart_models.remove(position);
//                    cart_models.clear();
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return order_models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, date, total, tenuser, diachiuser;

        public ViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tv_orderName);
            tenuser = itemView.findViewById(R.id.tv_userName);
            diachiuser = itemView.findViewById(R.id.tv_diachi);
            date = itemView.findViewById(R.id.tv_orderDate);
            total = itemView.findViewById(R.id.tv_orderTotal);

        }
    }

    public interface OnItemClickedListener {
        void onItemClick(String id);
    }

    private Cart_Adapter.OnItemClickedListener onItemClickedListener;

    public void setOnItemClickedListener(Cart_Adapter.OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }
}