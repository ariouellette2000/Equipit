package com.ouellette.equipit.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ouellette.equipit.R;
import com.ouellette.equipit.model.Product;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<Product> productList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Product product);
    }

    public RecyclerViewAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    public RecyclerViewAdapter(List<Product> productList, OnItemClickListener listener) {
        this.productList = productList;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        Product product = productList.get(position);

        //put that from the contact to the view
        holder.p_cat_row.setText(product.getP_cat_name());

        holder.bind(productList.get(position), listener);
    }


    @Override
    public int getItemCount() {
        return productList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView p_cat_row;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            p_cat_row = itemView.findViewById(R.id.p_cat_row);
        }

        public void bind(final Product product, final OnItemClickListener listener) {
            p_cat_row.setText(product.getP_cat_name());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(product);
                    Context context = view.getContext();
                    Intent intent = new Intent(context, com.ouellette.equipit.Product.class);
                    intent.putExtra("cat_name", product.getP_cat_name());
                    context.startActivity(intent);
                }
            });
        }
    }
}
