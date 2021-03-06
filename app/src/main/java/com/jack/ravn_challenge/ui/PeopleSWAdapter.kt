package com.jack.ravn_challenge.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jack.ravn_challenge.R
import com.jack.ravn_challenge.data.model.PersonModel
import com.jack.ravn_challenge.databinding.ItemPeopleBinding
import java.util.stream.Collectors

class PeopleSWAdapter(private val context: Context,
                      private val itemClickListener: OnItemClickListener
                      ):RecyclerView.Adapter<PeopleSWAdapter.ViewHolder>() {

    private var onEndOfListReached: (() -> Unit)? = null
    private var peopleList:MutableList<PersonModel> = mutableListOf()
    private var dataList:MutableList<PersonModel> = mutableListOf()

    fun setList(newList: MutableList<PersonModel>){
        peopleList.addAll(newList)
        dataList.addAll(newList)
        notifyDataSetChanged()
    }

    fun removeList(){
        peopleList.clear()
        notifyDataSetChanged()
    }

    interface OnItemClickListener{
        fun onPersonClick(person:PersonModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater:View = LayoutInflater.from(context).inflate(R.layout.item_people,parent,false)
        return ViewHolder(inflater)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val person = peopleList[position]
        holder.binding.peopleName.text = person.name
        val species:String? = person.species?.nameSM
        val homeworl:String = person.homeworld?.nameHM!!
        if (species==null){
            holder.binding.species.text = "Human from $homeworl"
        }else{
            holder.binding.species.text = "$species from $homeworl"
        }
        if (position == peopleList.size - 1) {
            onEndOfListReached?.invoke()
        }
        holder.binding.root.setOnClickListener {
            itemClickListener.onPersonClick(person)
        }
    }

    override fun getItemCount(): Int = peopleList.size

    fun fiterPerson(name: String?) {
        val long = name?.length
        if (long==0){
            peopleList.clear()
            peopleList.addAll(dataList)
        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                Log.d("Letter","$name")
                val collection = peopleList.stream()
                    .filter({i->i.name!!.lowercase().contains(name!!.lowercase())})
                    .collect(Collectors.toList())
                peopleList.clear()
                peopleList.addAll(collection)
            }else{
                for (person in dataList){
                    if (person.name!!.lowercase().contains(name!!.lowercase())){
                        peopleList.add(person)
                    }
                }
            }
        }
        notifyDataSetChanged()
    }


    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val binding = ItemPeopleBinding.bind(itemView)
    }
}