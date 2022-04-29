package io.jamshid.memeng.ui.main.game

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import io.jamshid.memeng.R
import io.jamshid.memeng.data.local.entites.Word
import io.jamshid.memeng.databinding.GameFragmentBinding
import io.jamshid.memeng.domain.Game
import io.jamshid.memeng.utils.Constants
import io.jamshid.memeng.utils.base.BaseFragment
import io.jamshid.memeng.utils.core.CustomDialog
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class GameFragment :
    BaseFragment<GameViewModel>() {

    private val vm: GameViewModel by viewModels()
    private var _binding: GameFragmentBinding? = null
    private val binding: GameFragmentBinding get() = _binding!!
    private var currentPosition = 0
    private lateinit var convertList: List<Word>
    private lateinit var questionList: ArrayList<Game>
    private var pos = Constants.pos
    private var customDialog:CustomDialog?=null
    private var correct:Int=0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = GameFragmentBinding.inflate(inflater, container, false)

        vm.getQuestions()

        customDialog = CustomDialog(requireContext())

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            vm.questionList.collectLatest {
                convertList = it
                getList()
            }
        }

        setActionBarTitle("Game", true)

        setHasOptionsMenu(true)

        if (pos == 0) {
            binding.til1.hint = "English"
            binding.til2.hint = "Uzbek"
            binding.edName1.isEnabled = false
        }

        binding.btnStart.setOnClickListener {
            binding.btnNext.visibility = View.VISIBLE
            binding.btnStart.visibility = View.INVISIBLE
            setEdText(questionList[currentPosition])
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            customDialog!!.event.collectLatest {
                if (it){

                    findNavController().navigateUp()

                }
            }
        }


        binding.btnNext.setOnClickListener {
            binding.edName2.setText("")
            customDialog!!.openDialog(check())
            if (check()) correct++
            setEdText(questionList[currentPosition])
            if (currentPosition == questionList.size) {
                customDialog!!.openAlertDialog(correct)
                return@setOnClickListener
            }

        }

        return binding.root
    }

    override val viewModel: GameViewModel
        get() = vm


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.action_bar_game, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.sync -> {
                vm.getQuestions()
            }
            else -> {
                findNavController().navigateUp()
            }
        }
        return true
    }

    @SuppressLint("SetTextI18n")
    private fun setEdText(game: Game) {
        binding.apply {
            edName1.setText(game.name1)
            currentPosition++
        }
        binding.tvQues.text = "Question $currentPosition"
    }

    private fun check(): Boolean {
        return questionList[currentPosition - 1].name2.lowercase() == binding.edName2.text.toString()
            .lowercase()
    }

    private fun getList() {
        questionList = ArrayList()//0 eng->uzb
        if (convertList.size < 10) {
            convertList.forEach {
                if (pos == 0) {
                    questionList.add(Game(it.engName, it.uzbName))
                } else {
                    questionList.add(Game(it.uzbName, it.engName))
                }
            }
        } else {
            var i = 0
            val set = HashSet<Int>()
            while (i < 10) {
                val sw = (Math.random() * (convertList.size)).toInt()
                if (!set.contains(sw)) {
                    if (pos == 0) {
                        questionList.add(Game(convertList[sw].engName, convertList[sw].uzbName))
                    } else {
                        questionList.add(Game(convertList[sw].uzbName, convertList[sw].engName))
                    }
                    set.add(sw)
                    i++
                }
            }
        }

    }


}