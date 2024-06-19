package com.mertadali.retrofitproject.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mertadali.retrofitproject.databinding.RowLayoutBinding
import com.mertadali.retrofitproject.model.CryptoModel


class RecyclerAdapter(private val cryptoList : ArrayList<CryptoModel>,private val listener : Listener) : RecyclerView.Adapter<RecyclerAdapter.RowHolder>()  {

    interface Listener{
        fun onItemClicked(cryptoModel: CryptoModel)
    }

    private val colors : Array<String> = arrayOf("#4b534e","#001e00","#5b7658","#5b3d7a","#3f4a99","#802200","#267105")

    class RowHolder(val binding: RowLayoutBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val binding = RowLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return RowHolder(binding)

    }

    override fun getItemCount(): Int {
      return cryptoList.size

    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        holder.itemView.setOnClickListener {
            listener.onItemClicked(cryptoList.get(position))
        }
        holder.itemView.setBackgroundColor(Color.parseColor(colors[position %6]))
        holder.binding.textPrice.text = cryptoList.get(position).price
        holder.binding.textName.text = cryptoList.get(position).currency


    }
}