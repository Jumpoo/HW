package com.example.hw90;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactItemViewHolder> {
    private List<Contact> items = new ArrayList<>();
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(Contact contact);
    }

    @NonNull
    @Override
    public ContactItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact_layout, parent, false);
        return new ContactItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactItemViewHolder holder, int position) {
        Contact currentContact = items.get(position);
        holder.itemIcon.setImageResource(currentContact.getIcon());
        holder.itemLine1.setText(currentContact.getLine1());
        holder.itemLine2.setText(currentContact.getLine2());
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public void setContacts(List<Contact> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void setMListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }

    public void filterList(ArrayList<Contact> filteredList) {
        items = filteredList;
        notifyDataSetChanged();
    }

    public class ContactItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView itemIcon;
        private TextView itemLine1;
        private TextView itemLine2;

        public ContactItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemIcon = itemView.findViewById(R.id.item_icon);
            itemLine1 = itemView.findViewById(R.id.item_line_1);
            itemLine2 = itemView.findViewById(R.id.item_line_2);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (mListener != null && position != RecyclerView.NO_POSITION) {
                        mListener.onItemClick(items.get(position));
                    }
                }
            });
        }
    }
}
