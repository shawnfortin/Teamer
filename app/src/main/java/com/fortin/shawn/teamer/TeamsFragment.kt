package com.fortin.shawn.teamer

import android.app.Fragment
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.player_holder.view.*

class TeamsFragment: Fragment() {
    lateinit var players: ArrayList<Player>

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    class Player(val name: String, val team: Int)

    class TeamAdapter(private val dataset: ArrayList<Player>): RecyclerView.Adapter<TeamAdapter.PlayerHolder>() {
        class PlayerHolder(val view: View): RecyclerView.ViewHolder(view)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerHolder {
            return PlayerHolder(LayoutInflater.from(parent.context).inflate(R.layout.player_holder, parent, false))
        }

        override fun onBindViewHolder(holder: PlayerHolder, position: Int) {
            // dataset must be sorted by team for display to work properly
            holder.view.team_name.text = dataset[position].team as String
            holder.view.player.text = dataset[position].name

            if (position > 0 && dataset[position - 1].team == dataset[position].team) {
                holder.view.team_name.visibility = View.INVISIBLE
            } else {
                holder.view.team_name.visibility = View.VISIBLE
            }
        }

        override fun getItemCount(): Int {
            return dataset.size
        }
    }
}