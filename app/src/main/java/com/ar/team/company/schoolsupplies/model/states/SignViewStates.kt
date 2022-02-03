package com.ar.team.company.schoolsupplies.model.states

sealed class SignViewStates {

    // State(Failure):
    object Failure : SignViewStates()

    // State(Success):
    object Success : SignViewStates()
}
