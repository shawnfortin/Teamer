package com.fortin.shawn.teamer

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class TeamsActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teams)
        val players = intent.getStringArrayListExtra("Players")
        val teams = intent.getIntExtra("Teams", 4)
        val tFrag = fragmentManager.findFragmentById(R.id.teams_fragment) as TeamsFragment
        tFrag.createTeams(players, teams)
    }
}