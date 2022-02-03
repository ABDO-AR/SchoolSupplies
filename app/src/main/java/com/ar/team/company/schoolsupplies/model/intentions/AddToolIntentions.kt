package com.ar.team.company.schoolsupplies.model.intentions

import com.ar.team.company.schoolsupplies.model.models.Tool

sealed class AddToolIntentions {

    // Intention(Upload):
    data class Upload(val tool: Tool) : AddToolIntentions()
}
