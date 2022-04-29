package io.jamshid.memeng.ui.main.words

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import io.jamshid.memeng.R
import io.jamshid.memeng.data.local.entites.Word
import io.jamshid.memeng.databinding.WordsFragmentBinding
import io.jamshid.memeng.ui.main.HomeFragment
import io.jamshid.memeng.ui.main.words.adapters.WordAdapter
import io.jamshid.memeng.utils.Constants
import io.jamshid.memeng.utils.OnItemClickListener
import io.jamshid.memeng.utils.base.BaseFragment
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class WordsFragment : BaseFragment<WordsViewModel>() {


    private val vm: WordsViewModel by viewModels()
    private var _binding: WordsFragmentBinding? = null
    private val binding: WordsFragmentBinding get() = _binding!!
    private lateinit var adapter: WordAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = WordsFragmentBinding.inflate(inflater, container, false)

        setActionBarTitle("Words", true)

        adapter = WordAdapter(object : OnItemClickListener {
            override fun onClick(word: Word) {
                val dialog = AlertDialog.Builder(context)
                dialog.setMessage("Are you sure?")
                dialog.setTitle("Delete")
                dialog.setPositiveButton(
                    "Yes"
                ) { p0, p1 ->
                    vm.deleteWord(word)
                }
                dialog.setNegativeButton("No") { p0, p1 ->
                }
                dialog.show()
            }

            override fun onItemClick(word: Word) {
                Constants.case = 2
                val action =
                    WordsFragmentDirections.actionWordsFragmentToAddWordFragment(word)
                findNavController().navigate(action)
            }
        })
        setHasOptionsMenu(true)
        vm.getAllWords()

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            vm.allWords.collectLatest {
                adapter.setData(it)
                if (it.isNotEmpty()) {
                    binding.tvWordSize.visibility = View.INVISIBLE
                }
            }
        }

        binding.rcvWord.adapter = adapter


        return binding.root
    }

    override val viewModel: WordsViewModel
        get() = vm

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.action_bar_no, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        findNavController().navigateUp()
        return true
    }


}