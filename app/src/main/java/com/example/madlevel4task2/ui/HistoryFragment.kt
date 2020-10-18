package com.example.madlevel4task2.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.madlevel4task2.R
import com.example.madlevel4task2.model.Game
import com.example.madlevel4task2.repository.GameRepository
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_history.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistoryFragment : Fragment() {
    private lateinit var gameRepository: GameRepository
    private val gamesList = arrayListOf<Game>()
    private val gameHistoryAdapter = GameHistoryAdapter(gamesList)

    private val mainScope = CoroutineScope(Dispatchers.Main)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle? ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeHistoryRecyclerView()

        // Get a support ActionBar corresponding to this toolbar and enable the Up button
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        gameRepository = GameRepository(requireContext())
        getGameListFromDatabase()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.findItem(R.id.iv_action_delete_history).isVisible = true
        requireActivity().toolbar.title = getString(R.string.game_history)
        // If Up button is clicked, pop the fragment off the Backstack
        requireActivity().toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }
    // Deletes the history
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.iv_action_delete_history -> {
                deleteGamesList()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initializeHistoryRecyclerView() {

        rv_game_history_list.addItemDecoration(
            DividerItemDecoration(
                activity,
                DividerItemDecoration.VERTICAL
            )
        )

        rv_game_history_list.apply {
            setHasFixedSize(true) // true because the ViewHolder's do not affect the RecyclerView's size
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = gameHistoryAdapter
        }
    }

    private fun getGameListFromDatabase() {
        mainScope.launch {
            val gamesList = withContext(Dispatchers.IO) {
                gameRepository.getAllGames()
            }
            this@HistoryFragment.gamesList.clear()
            this@HistoryFragment.gamesList.addAll(gamesList)
            this@HistoryFragment.gameHistoryAdapter.notifyDataSetChanged()
        }
    }

    private fun deleteGamesList() {
        mainScope.launch {
            withContext(Dispatchers.IO) {
                gameRepository.deleteAllGames()
            }
            getGameListFromDatabase()
        }
    }
}