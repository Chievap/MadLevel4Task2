package com.example.madlevel4task2.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.madlevel4task2.R
import com.example.madlevel4task2.model.Game
import com.example.madlevel4task2.model.GameMove
import com.example.madlevel4task2.model.GameResult
import kotlinx.android.synthetic.main.item_game_history.view.*

class GameHistoryAdapter(private val gamesList: List<Game>) : RecyclerView.Adapter<GameHistoryAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(game: Game) {
            itemView.tv_game_outcome_title.text = game.gameResult.getResultAsString()
            itemView.tv_game_date.text = game.date.toString()
            itemView.iv_selected_hand_computer.setImageResource(getDrawableFromGameMove(game.computerMove))
            itemView.iv_selected_hand_you.setImageResource(getDrawableFromGameMove(game.playerMove))
        }
    }

    private fun getDrawableFromGameMove (move: GameMove): Int {
        return when (move) {
            GameMove.ROCK -> R.drawable.rock
            GameMove.PAPER -> R.drawable.paper
            GameMove.SCISSORS -> R.drawable.scissors
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_game_history, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(gamesList[position])
    }

    override fun getItemCount(): Int = this.gamesList.size
}