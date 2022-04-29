package io.jamshid.memeng.ui.main

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import io.jamshid.memeng.R
import io.jamshid.memeng.databinding.ChooseDialogBinding
import io.jamshid.memeng.databinding.HomeFragmentBinding
import io.jamshid.memeng.utils.Constants
import io.jamshid.memeng.utils.base.BaseFragment


@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeViewModel>() {


    private val vM: HomeViewModel by viewModels()
    private var _binding: HomeFragmentBinding? = null
    private val binding: HomeFragmentBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        binding.apply {
            cntCv1.setOnClickListener { findNavController().navigate(R.id.action_homeFragment_to_wordsFragment) }
            cntCv2.setOnClickListener {open() }
        }

        setActionBarTitle("Home",false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override val viewModel: HomeViewModel
        get() = vM


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.action_bar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add -> {
                val bundle = Bundle()
                bundle.putInt("case",1)
                Constants.case=1
                findNavController().navigate(R.id.action_homeFragment_to_addWordFragment,bundle)
            }
            R.id.serach -> {
                findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
            }
        }
        return true
    }

    @SuppressLint("ResourceType")
    private fun open() {
        val bd = ChooseDialogBinding.bind(
            LayoutInflater.from(requireContext()).inflate(R.layout.choose_dialog, null, false)
        )
        val dialog = Dialog(requireContext())
        bd.btnUzbToEng.setOnClickListener {
            Constants.pos=1
            findNavController().navigate(R.id.action_homeFragment_to_gameFragment)
            dialog.dismiss()
        }
        bd.btnEngToUzb.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_gameFragment)
            Constants.pos=0
            dialog.dismiss()
        }
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(bd.root)
        dialog.show()
    }


}