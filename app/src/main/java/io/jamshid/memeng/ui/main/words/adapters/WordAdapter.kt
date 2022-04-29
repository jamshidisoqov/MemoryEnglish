package io.jamshid.memeng.ui.main.words.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.jamshid.memeng.R
import io.jamshid.memeng.data.local.entites.Word
import io.jamshid.memeng.databinding.RcvWordItemBinding
import io.jamshid.memeng.utils.OnItemClickListener

class WordAdapter(private val onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<WordAdapter.ViewHolder>() {

    private lateinit var binding: RcvWordItemBinding
    private var wordList: List<Word> = emptyList()

    inner class ViewHolder(private val binding: RcvWordItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(word: Word) {
            binding.apply {
                tvWord.text = "${word.engName}->${word.uzbName}"

                btnTrash.setOnClickListener {
                    onItemClickListener.onClick(word)
                }
                root.setOnClickListener {
                    onItemClickListener.onItemClick(word)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = RcvWordItemBinding.bind(
            LayoutInflater.from(parent.context).inflate(R.layout.rcv_word_item, parent, false)
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(wordList[position])

    }

    override fun getItemCount(): Int = wordList.size


    fun setData(list: List<Word>) {
        wordList = list
        notifyDataSetChanged()
    }
}