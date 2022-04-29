package io.jamshid.memeng.ui.main.words.add

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import io.jamshid.memeng.R
import io.jamshid.memeng.data.local.entites.Word
import io.jamshid.memeng.databinding.AddWordFragmentBinding
import io.jamshid.memeng.utils.base.BaseFragment


@AndroidEntryPoint
class AddWordFragment : BaseFragment<AddWordViewModel>() {


    private val vm: AddWordViewModel by viewModels()
    private var binding: AddWordFragmentBinding? = null
    private val args: AddWordFragmentArgs by navArgs()
    private lateinit var title: String
    private var fr: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AddWordFragmentBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        fr = arguments?.getInt("case")!!
        title = if (fr == 1) "Add" else "Update"
        setActionBarTitle(title,true)
        if (fr == 1)
            binding!!.btnAdd.text = "Add"
        else {
            binding!!.btnAdd.text = "Update"
            binding!!.apply {
                edUzbName.setText(args.word.uzbName)
                edEngName.setText(args.word.engName)
            }
        }

        binding.apply {
            this!!.btnAdd.setOnClickListener {
                val engName = edEngName.text.toString()
                val uzbName = edUzbName.text.toString()
                if (engName.isNotEmpty() && uzbName.isNotEmpty()) {
                    if (fr == 1) {
                        vm.addWords(Word(0, uzbName, engName))
                        Snackbar.make(requireContext(),binding!!.btnAdd,"Successfully Added",Snackbar.LENGTH_SHORT).show()
                        this.edEngName.setText("")
                        this.edUzbName.setText("")
                    }else{
                        vm.updateWord(Word(args.word.id, uzbName, engName))
                        Toast.makeText(context, "Successfully Update ", Toast.LENGTH_SHORT).show()
                        findNavController().navigateUp()
                    }
                } else {
                    Snackbar.make(requireContext(),binding!!.btnAdd,"Propery has not filled",Snackbar.LENGTH_SHORT).show()
                }
            }
        }
        return binding!!.root
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.action_bar_no, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        findNavController().navigateUp()
        return true
    }

    override val viewModel: AddWordViewModel
        get() = vm


}