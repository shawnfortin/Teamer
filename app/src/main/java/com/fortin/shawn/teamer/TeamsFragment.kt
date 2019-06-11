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
    var teams = ArrayList<ArrayList<Player>>()
    private lateinit var recycler: RecyclerView
    private var numTeams = 0

    fun createTeams(names: ArrayList<String>, numTeams: Int) {
        // create numTeams arraylist from names provided
        this.numTeams = numTeams
        var curTeam = 0
        val rand = Random()
        while (names.size > 0) {
            val player = names.get(rand.nextInt(names.size))
            names.remove(player)
            if (teams.size < curTeam + 1) {
                teams.add(curTeam, ArrayList<Player>())
            }
            this.teams[curTeam].add(Player(player, curTeam))
            curTeam += 1
            if (curTeam >= numTeams)
                curTeam = 0
        }
        recycler.adapter.notifyDataSetChanged()
    }

//    private fun newTeams() {
//        var newPlayers = ArrayList<Player>()
//        var curTeam = 1
//        val rand = Random()
//        while (teams.size > 0) {
//            val player = teams.get(rand.nextInt(teams.size))
//            teams.remove(player)
//            player.team = curTeam
//            newPlayers.add(player)
//            curTeam += 1
//            if (curTeam > numTeams)
//                curTeam = 1
//        }
//        teams = newPlayers
//        sortPlayers(teams)
//        recycler.adapter.notifyDataSetChanged()
//    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        if (savedInstanceState != null) {
//            teams = savedInstanceState.getParcelableArrayList("Teams")
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
                teams.shuffle()
                recycler.adapter.notifyDataSetChanged()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
//        outState?.putParcelableArrayList("Teams", teams)
    }

    class Player(var name: String, var team: Int) : Parcelable{
        private constructor(parcel: Parcel) : this(
            name = parcel.readString(),
            team = parcel.readInt()
        )

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(name)
            parcel.writeInt(team)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Player> {
            override fun createFromParcel(parcel: Parcel): Player {
                return Player(parcel)
            }

            override fun newArray(size: Int): Array<Player?> {
                return arrayOfNulls(size)
            }
        }
    }

    class TeamAdapter(private val dataset: ArrayList<ArrayList<Player>>) : RecyclerView.Adapter<TeamAdapter.PlayerHolder>() {
        class PlayerHolder(val view: View) : RecyclerView.ViewHolder(view)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerHolder {
            return PlayerHolder(LayoutInflater.from(parent.context).inflate(R.layout.player_holder, parent, false))
        }

        override fun onBindViewHolder(holder: PlayerHolder, position: Int) {
            // dataset must be sorted by team for display to work properly
            holder.view.team_name.text = "Team ${position + 1}"
            var players = ""
            for (player in dataset[position])
                players += "\n${player.name}"

            holder.view.player.text = players.trim()
        }

        override fun getItemCount(): Int {
            return dataset.size
        }
    }
}