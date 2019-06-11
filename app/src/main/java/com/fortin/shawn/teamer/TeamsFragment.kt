    package com.fortin.shawn.teamer

    import android.app.Fragment
    import android.os.Bundle
    import android.os.Parcel
    import android.os.Parcelable
    import android.support.v7.widget.LinearLayoutManager
    import android.support.v7.widget.RecyclerView
    import android.view.*
    import kotlinx.android.synthetic.main.player_holder.view.*
    import java.lang.Integer.MAX_VALUE
    import java.util.*

    class TeamsFragment: Fragment() {
    var teams = ArrayList<ArrayList<String>>()
    private lateinit var recycler: RecyclerView
    var numTeams = 0
    lateinit var names: ArrayList<String>

    fun createTeams() {
        // create numTeams arraylist from names provided
        var curTeam = 0
        val rand = Random()
        var tempNames = ArrayList<String>()
        tempNames.addAll(names)
        while (tempNames.size > 0) {
            val player = tempNames.get(rand.nextInt(tempNames.size))
            tempNames.remove(player)
            if (teams.size < curTeam + 1) {
                teams.add(curTeam, ArrayList())
            }
            teams[curTeam].add(player)
            curTeam += 1
            if (curTeam >= numTeams)
                curTeam = 0
        }
        recycler.adapter.notifyDataSetChanged()
    }

    private fun newTeams() {
        teams.removeAll(teams)
        createTeams()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        if (savedInstanceState != null) {
            teams = savedInstanceState.getSerializable("Teams") as ArrayList<ArrayList<String>>
        }

        setHasOptionsMenu(true)

        val view = inflater!!.inflate(R.layout.fragment_teams, container, false)

        recycler = view.findViewById(R.id.teams)
        recycler.setHasFixedSize(true)
        recycler.layoutManager = LinearLayoutManager(activity)
        recycler.adapter = TeamAdapter(teams)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_teams, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId) {
            R.id.shuffle -> {
                newTeams()
                recycler.adapter.notifyDataSetChanged()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putSerializable("Teams", teams)
    }

    class TeamAdapter(private val dataset: ArrayList<ArrayList<String>>) : RecyclerView.Adapter<TeamAdapter.PlayerHolder>() {
        class PlayerHolder(val view: View) : RecyclerView.ViewHolder(view)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerHolder {
            return PlayerHolder(LayoutInflater.from(parent.context).inflate(R.layout.player_holder, parent, false))
        }

        override fun onBindViewHolder(holder: PlayerHolder, position: Int) {
            // dataset must be sorted by team for display to work properly
            holder.view.team_name.text = "Team ${position + 1}"
            var players = ""
            for (player in dataset[position])
                players += "\n$player"

            holder.view.player.text = players.trim()
        }

        override fun getItemCount(): Int {
            return dataset.size
        }
    }
}