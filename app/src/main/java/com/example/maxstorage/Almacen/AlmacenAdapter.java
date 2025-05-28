package com.example.maxstorage.Almacen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maxstorage.R;

import java.util.List;

/** Adapter para listar objetos Almacen (paquetes) */
public class AlmacenAdapter
        extends RecyclerView.Adapter<AlmacenAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Almacen item);
    }

    private final List<Almacen> list;
    private final OnItemClickListener listener;

    public AlmacenAdapter(List<Almacen> list,
                          OnItemClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                         int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_almacen_paquete,
                        parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,
                                 int position) {
        Almacen a = list.get(position);
        holder.bind(a, listener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvId, tvPeso, tvStatus;

        ViewHolder(View itemView) {
            super(itemView);
            tvId     = itemView.findViewById(R.id.item_package_id);
            tvPeso   = itemView.findViewById(R.id.item_weight);
            tvStatus = itemView.findViewById(R.id.item_status);
        }

        void bind(Almacen a, OnItemClickListener listener) {
            tvId.setText(String.valueOf(a.getPackageId()));
            tvPeso.setText(a.getWeight() + " kg");
            tvStatus.setText(a.getStatus());
            itemView.setOnClickListener(v -> listener.onItemClick(a));
        }
    }
}
