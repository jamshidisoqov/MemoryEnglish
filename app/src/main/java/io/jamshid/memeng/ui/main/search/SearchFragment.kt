package io.jamshid.memeng.ui.main.search

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import io.jamshid.memeng.R
import io.jamshid.memeng.data.local.entites.Word
import io.jamshid.memeng.databinding.SearchFragmentBinding
import io.jamshid.memeng.ui.main.words.adapters.WordAdapter
import io.jamshid.memeng.utils.OnItemClickListener
import io.jamshid.memeng.utils.base.BaseFragment
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class SearchFragment : BaseFragment<SearchViewModel>() {

    private val vm: SearchViewModel by viewModels()
    private var _binding: SearchFragmentBinding? = null
    private val binding: SearchFragmentBinding get() = _binding!!
    private lateinit var adapter: WordAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = SearchFragmentBinding.inflate(inflater, container, false)

        setHasOptionsMenu(true)
        setActionBarTitle("Search", true)

        vm.getAllWords()

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
                val action =
                    SearchFragmentDirections.actionSearchFragmentToAddWordFragment(word)
                findNavController().navigate(action)
            }

        })

        binding.rcvSearchWord.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            vm.allWords.collectLatest {
                adapter.setData(it)
            }
        }
        return binding.root
    }

    override val viewModel: SearchViewModel
        get() = vm

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_bar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
        val findItem = menu.findItem(R.id.serach)
        (findItem.actionView as SearchView).setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                vm.adapterChange(query!!)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                vm.adapterChange(newText!!)
                return true
            }

        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            findNavController().navigateUp()
        return true
    }

}