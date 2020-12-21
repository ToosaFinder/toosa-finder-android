package com.toosafinder.eventCreation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LiveData
import co.lujun.androidtagview.TagContainerLayout
import com.toosafinder.R
import com.toosafinder.login.afterTextChanged
import com.toosafinder.utils.Option

class AddTagFragment(
    private val viewModel: EventCreationViewModel,
    private val tags: LiveData<MutableList<String>>,
    private val tagView: TagContainerLayout,
    private val editTag: Int? = null
) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_tag, container, false)

        val adapter = ArrayAdapter<String>(requireActivity(), android.R.layout.simple_list_item_1)
        val tagsList = view.findViewById<ListView>(R.id.listViewTags)
        val tagField = view.findViewById<EditText>(R.id.textFieldTagName)
        val error = view.findViewById<TextView>(R.id.textErrorMessage)
        val buttonAdd = view.findViewById<Button>(R.id.buttonAddTag)
        val buttonCancel = view.findViewById<Button>(R.id.buttonDelete)
        tagsList.adapter = adapter

        viewModel.getTagsResult.observe(this, {
            when(it) {
                is Option.Success -> {
                    adapter.clear()
                    adapter.addAll(it.data)
                }
                is Option.Error -> {
                    error.visibility = View.VISIBLE
                    error.text = it.error?.toString()
                }
            }
        })

        tagField.afterTextChanged {
            val tag = tagField.text.toString()
            buttonAdd.isEnabled = when (tags.value) {
                is MutableList<String> -> !tags.value!!.contains(tag) || editTag != null
                else -> true
            }
        }

        if(editTag != null) {
            tagField.setText(tagView.getTagText(editTag))
            buttonAdd.text = getString(R.string.button_edit_tag)
        }

        tagsList.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            tagField.setText(adapter.getItem(position))
        }

        buttonCancel.setOnClickListener {
            close()
        }

        buttonAdd.setOnClickListener {
            val tag = tagField.text.toString()
            if(editTag != null) {
                tagView.removeTag(editTag)
            }
            tagView.addTag(tag)
            tags.value?.add(tag)
            close()
        }

        viewModel.getPopularTags()

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()

        viewModel.getTagsResult.removeObservers(this)
    }

    private fun close() {
        parentFragmentManager.beginTransaction().remove(this).commit()
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