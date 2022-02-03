package com.ar.team.company.schoolsupplies.model.states

sealed class AddToolViewStates {
    // State(Failure):
    object Failure : AddToolViewStates()

    // State(Success):
    object Success : AddToolViewStates()
}
