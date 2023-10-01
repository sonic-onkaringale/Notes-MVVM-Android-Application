package com.onkaringale.notes.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.onkaringale.notes.R
import com.onkaringale.notes.data.NoteModel
import com.onkaringale.notes.ui.CreateNote

class NotesAdapter(private val allNotes: ArrayList<NoteModel>,val context: Context) :
    RecyclerView.Adapter<NotesAdapter.ViewHolder>()
{
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
    {
        val card: MaterialCardView
        val title: TextView
        val description: TextView

        init
        {
            card = view.findViewById(R.id.cardview_list_item)
            title = view.findViewById(R.id.title_list_item)
            description = view.findViewById(R.id.description_list_item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int
    {
        return allNotes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        holder.title.text = allNotes[position].title
        holder.description.text = allNotes[position].description
        holder.card.setOnClickListener {
            if (allNotes[position].id!=null)
                context.startActivity(Intent(context,CreateNote::class.java).putExtra("id",allNotes[position].id ))
        }
    }
}