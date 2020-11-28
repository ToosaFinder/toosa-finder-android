package com.toosafinder.eventCreation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.DialogFragment
import com.toosafinder.R

class AddTagFragment : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_tag, container, false)

        val adapter = ArrayAdapter<String>(requireActivity(), android.R.layout.simple_list_item_1)
        view.findViewById<ListView>(R.id.listViewTags).adapter = adapter

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    /*class TagViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView? = null
        init {
            textView = itemView.findViewById(R.id.text)
        }
    }

    class TagAdapter(private val tags: List<String>) : RecyclerView.Adapter<TagViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.fragment_add_tag, parent, false)
            return TagViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
            holder.textView?.text = tags[position]
        }

        override fun getItemCount(): Int {
            return tags.size
        }
    }*/
}