package com.ar.team.company.schoolsupplies.ui.activitys.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ar.team.company.schoolsupplies.control.repository.MainRepository
import com.ar.team.company.schoolsupplies.model.intentions.HomeIntentions
import com.ar.team.company.schoolsupplies.model.models.Tool
import com.ar.team.company.schoolsupplies.model.states.HomeViewStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    // Channel:
    val homeChannel: Channel<HomeIntentions> = Channel(Channel.UNLIMITED)

    // States:
    private val _state: MutableStateFlow<HomeViewStates> = MutableStateFlow(HomeViewStates.Failure)
    val state: StateFlow<HomeViewStates> = _state

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
            homeChannel.consumeAsFlow().collect { reducing(it) }
        }
    }

    // Method(Reducing):
    private suspend fun reducing(intention: HomeIntentions) {
        // Coroutines:
        viewModelScope.launch {
            // Checking:
            when (intention) {
                // Reducing
                is HomeIntentions.GetTools -> {
                    repository.getTools {
                        viewModelScope.launch {
                            _state.emit(if (it.isNotEmpty()) HomeViewStates.Tools(it) else HomeViewStates.Failure)
                        }
                    }
                }
            }
        }
    }
}