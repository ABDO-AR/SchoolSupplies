package com.ar.team.company.schoolsupplies.ui.activities.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ar.team.company.schoolsupplies.control.repository.MainRepository
import com.ar.team.company.schoolsupplies.model.intentions.AddToolIntentions
import com.ar.team.company.schoolsupplies.model.states.AddToolViewStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    // Channel:
    val toolChannel: Channel<AddToolIntentions> = Channel(Channel.UNLIMITED)

    // States:
    private val _state: MutableStateFlow<AddToolViewStates> = MutableStateFlow(AddToolViewStates.Failure)
    val state: StateFlow<AddToolViewStates> = _state

    // Initializing:
    init {
        // Processing:
        channelProcessing()
    }

    // Method(ChannelProcessing):
    private fun channelProcessing() {
        // Coroutines:
        viewModelScope.launch {
            // Collecting:
            toolChannel.consumeAsFlow().collect { reducing(it) }
        }
    }

    // Method(Reducing):
    private fun reducing(intention: AddToolIntentions) {
        // Coroutines:
        viewModelScope.launch {
            // Checking:
            when (intention) {
                // Reducing
                is AddToolIntentions.Upload -> repository.uploadTool(intention) {
                    viewModelScope.launch {
                        _state.emit(if (it) AddToolViewStates.Success else AddToolViewStates.Failure)
                    }
                }
            }
        }
    }
}