package com.example.team01_chintankumargajera_zippedproject_epicshophub.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.team01_chintankumargajera_zippedproject_epicshophub.R;
import com.example.team01_chintankumargajera_zippedproject_epicshophub.models.AddressModel;


import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {

    Context context;
    List<AddressModel> addressModelList;
    SelectedAddress selectedAddress;

    private RadioButton selectedRadiobtn;

    public AddressAdapter(Context context, List<AddressModel> addressModelList, SelectedAddress selectedAddress) {
        this.context = context;
        this.addressModelList = addressModelList;
        this.selectedAddress = selectedAddress;
    }

    @NonNull
    @Override
    public AddressAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.address_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AddressModel address = addressModelList.get(position);

        // Set the address text
        holder.address.setText(address.getUserAddress());

        holder.radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set all addresses as unselected
                for (AddressModel a : addressModelList) {
                    a.setSelected(false);
                }

                // Set the clicked address as selected
                address.setSelected(true);

                // Uncheck the previously selected radio button (if any)
                if (selectedRadiobtn != null) {
                    selectedRadiobtn.setChecked(false);
                }

                // Update the selected radio button
                selectedRadiobtn = (RadioButton) v;
                selectedRadiobtn.setChecked(true);

                // Update the selected address (if needed)
                selectedAddress.setAddress(address.getUserAddress());
            }
        });

        // Set the radio button's state based on the isSelected flag
        holder.radioButton.setChecked(address.isSelected());
    }

    @Override
    public int getItemCount() {
        return addressModelList.size();
    }

    public interface selectedAddress {
        void setAddAddress(String address);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView address;
        RadioButton radioButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            address = itemView.findViewById(R.id.address_add);
            radioButton = itemView.findViewById(R.id.select_address);

        }
    }

    public interface SelectedAddress {
        void setAddress(String address);
    }
}
