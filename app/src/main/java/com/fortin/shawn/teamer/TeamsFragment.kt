package com.fortin.shawn.teamer

import android.app.Fragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.player_holder.view.*
import java.lang.Integer.MAX_VALUE
import java.util.*

class TeamsFragment: Fragment() {
    var players = ArrayList<Player>()
    private lateinit var recycler: RecyclerView

    fun initializePlayers(names: ArrayList<String>) {
        // create players arraylist from names provided
        val teams = 4
        var curTeam = 1
        val rand = Random()
        while(names.size > 0) {
            val player = names.get(rand.nextInt(names.size))
            names.remove(player)
            players.add(Player(player, curTeam))
            curTeam += 1
            if (curTeam > teams)
                curTeam = 1
        }
        sortPlayers(players)
        recycler.adapter.notifyDataSetChanged()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater!!.inflate(R.layout.fragment_teams, container, false)

        recycler = view.findViewById(R.id.teams)
        recycler.layoutManager = LinearLayoutManager(activity)
        recycler.adapter = TeamAdapter(players)

        return view
    }

    class Player(val name: String, val team: Int)

    private fun sortPlayers(players: ArrayList<Player>): ArrayList<Player> {
        // selection sorts the list of players based on team
        for (x in 0 until players.size) {
            var min = MAX_VALUE
            var minIndex = 0
            for (i in x until players.size) {
                if (players[i].team < min) {
                    min = players[i].team
                    minIndex = i
                }
            }
            // swap position x with position minIndex
            val tmp = players[x]
            players[x] = players[minIndex]
            players[minIndex] = tmp
        }
        return players
    }

    class TeamAdapter(private val dataset: ArrayList<Player>): RecyclerView.Adapter<TeamAdapter.PlayerHolder>() {
        class PlayerHolder(val view: View): RecyclerView.ViewHolder(view)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerHolder {
            return PlayerHolder(LayoutInflater.from(parent.context).inflate(R.layout.player_holder, parent, false))
        }

        override fun onBindViewHolder(holder: PlayerHolder, position: Int) {
            // dataset must be sorted by team for display to work properly
            holder.view.team_name.text = "Team ${dataset[position].team}"
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