package com.fortin.shawn.teamer

import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import kotlinx.android.synthetic.main.names_holder.view.*

class MainFragment : Fragment(), AddNameDialogFragment.AddNamesListener {
    private var players = ArrayList<String>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var teamsCount: TextView

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        if (savedInstanceState != null) {
            players = savedInstanceState.getStringArrayList("Players")
        }

        setHasOptionsMenu(true)

        val view = inflater!!.inflate(R.layout.fragment_main, container, false)
        recyclerView = view.findViewById(R.id.namesRecyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(activity.applicationContext)
        recyclerView.adapter = NamesAdapter(players)

        teamsCount = view.findViewById(R.id.teamsCount)

        val teamsSlider = view.findViewById<SeekBar>(R.id.teamsSlider)

        class SeekListener: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                teamsCount.text = "${p1 + 2}"
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                /* do nothing */
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                /* do nothing */
            }
        }

        teamsSlider.setOnSeekBarChangeListener(SeekListener())

        val addButton = view.findViewById<FloatingActionButton>(R.id.addButton)
        addButton.setOnClickListener{
            // create a dialog allowing the user to enter a name
            val addNameDialog = AddNameDialogFragment()
            addNameDialog.setTarget(this)
            addNameDialog.show(fragmentManager, "add")
        }

        return view
    }

    fun createTeams() {
        val intent = Intent(activity, TeamsActivity::class.java).apply {
            putStringArrayListExtra("Players", players)
            putExtra("Teams", teamsCount.text.toString().toInt())
        }
        startActivity(intent)
    }


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId) {
            R.id.createTeams -> {
                createTeams()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putStringArrayList("Players", players)
    }

    // from AddNameDialogFragment.AddNamesListener interface
    override fun onPositiveClicked(dialog: AddNameDialogFragment, name: String) {
        addToPlayers(name)
    }

    fun addToPlayers(name: String) {
        players.add(name)
        recyclerView.adapter.notifyDataSetChanged()
    }

    // Adapter class for RecyclerView
    class NamesAdapter(private val dataset: ArrayList<String>) : RecyclerView.Adapter<NamesAdapter.NamesHolder>() {
        class NamesHolder(val view: View) : RecyclerView.ViewHolder(view)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NamesHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.names_holder, parent, false)
            return NamesHolder(view)
        }

        override fun onBindViewHolder(holder: NamesHolder, position: Int) {
            holder.view.textView.text = dataset[position]
        }

        override fun getItemCount(): Int {
            return dataset.size
        }
    }
}