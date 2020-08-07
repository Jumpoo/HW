package com.example.hw3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public  class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactItemViewHolder> {
    private List<Contact> items = new ArrayList<>();
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public ContactListAdapter(List<Contact> items, OnItemClickListener mListener) {
        this.items = items;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public ContactItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact_layout, parent, false);
        return new ContactItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactItemViewHolder holder, int position) {
        holder.bind(items.get(position), mListener);
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public void addItem(Contact contact) {
        items.add(contact);
        notifyItemChanged(items.indexOf(contact));
    }

    public void removeItem(Contact contact) {
        items.remove(contact);
        notifyDataSetChanged();
    }

    public void filterList(ArrayList<Contact> filteredList) {
        items = filteredList;
        notifyDataSetChanged();
    }



    public static class ContactItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView itemIcon;
        private TextView itemLine1;
        private TextView itemLine2;

        public ContactItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemIcon = itemView.findViewById(R.id.item_icon);
            itemLine1 = itemView.findViewById(R.id.item_line_1);
            itemLine2 = itemView.findViewById(R.id.item_line_2);
        }

        public void bind(final Contact contact, final OnItemClickListener listener) {
            int icon = contact.getIcon();
            String line1 = contact.getLine1();
            String line2 = contact.getLine2();

            itemIcon.setImageResource(icon);
            itemLine1.setText(line1);
            itemLine2.setText(line2);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(getAdapterPosition());
                }
            });
        }
    }
}
