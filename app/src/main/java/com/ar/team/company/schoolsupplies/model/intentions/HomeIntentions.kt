package com.ar.team.company.schoolsupplies.model.intentions

sealed class HomeIntentions {

    // Intention(GetTools):
    data class GetTools(val filtrationType: String) : HomeIntentions()
}
