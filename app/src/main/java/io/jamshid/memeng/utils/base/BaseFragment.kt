package io.jamshid.memeng.utils.base

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

abstract class BaseFragment<VH:BaseViewModel>() : Fragment(){

    protected abstract val viewModel:VH

    fun setActionBarTitle(title:String,isNavigate:Boolean){
        (activity as AppCompatActivity).supportActionBar?.apply {
            show()
            setTitle(title)
            setDisplayHomeAsUpEnabled(isNavigate)
        }
    }
}