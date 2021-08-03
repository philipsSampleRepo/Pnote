package com.palo.palonote.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.palo.palonote.R;
import com.palo.palonote.model.PaloNotesModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteHolder> {
    private List<PaloNotesModel> notes = new ArrayList<>();
    private OnItemClickedListener itemListener;

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item_view, parent, false);
        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        PaloNotesModel currentNote = notes.get(position);
        holder.txt_title.setText(currentNote.getNote_title());
        if (currentNote.getNote_content() == null || currentNote.getNote_content().length() == 0) {
            holder.txt_content.setVisibility(View.GONE);
        } else {
            holder.txt_content.setText(currentNote.getNote_content());
        }
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(List<PaloNotesModel> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    public List<PaloNotesModel> getNotes() {
        return this.notes;
    }

    class NoteHolder extends RecyclerView.ViewHolder {
        private TextView txt_title;
        private TextView txt_content;

        public NoteHolder(View itemView) {
            super(itemView);
            txt_title = itemView.findViewById(R.id.txt_title);
            txt_content = itemView.findViewById(R.id.txt_content);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (itemListener != null && RecyclerView.NO_POSITION != position) {
                        itemListener.onItemClicked(notes.get(position));
                    }
                }
            });
        }
    }

    public void removeItem(int position) {
        notes.remove(position);
        notifyItemRemoved(position);
    }

    public interface OnItemClickedListener {
        void onItemClicked(PaloNotesModel model);
    }

    public void setOnItemClickedListener(OnItemClickedListener listener) {
        this.itemListener = listener;
    }
}
