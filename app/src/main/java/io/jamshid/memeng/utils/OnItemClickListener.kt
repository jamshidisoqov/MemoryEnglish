package io.jamshid.memeng.utils

import io.jamshid.memeng.data.local.entites.Word

interface OnItemClickListener {
    fun onClick(word: Word)

    fun onItemClick(word: Word)
}