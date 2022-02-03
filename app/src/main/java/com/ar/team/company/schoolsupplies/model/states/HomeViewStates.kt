package com.ar.team.company.schoolsupplies.model.states

import com.ar.team.company.schoolsupplies.model.models.Tool

sealed class HomeViewStates {

    // State(Tools):
    data class Tools(val tools: ArrayList<Tool>) : HomeViewStates()

    // State(Failure):
    object Failure : HomeViewStates()
}
