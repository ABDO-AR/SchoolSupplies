package com.ar.team.company.schoolsupplies.ui.activities.sign

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ar.team.company.schoolsupplies.control.repository.MainRepository
import com.ar.team.company.schoolsupplies.model.intentions.SignIntentions
import com.ar.team.company.schoolsupplies.model.states.SignViewStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    // Channel:
    val signChannel: Channel<SignIntentions> = Channel(Channel.UNLIMITED)

    // States:
    private val _state: MutableStateFlow<SignViewStates> = MutableStateFlow(SignViewStates.Failure)
    val state: StateFlow<SignViewStates> = _state

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
            signChannel.consumeAsFlow().collect { reducing(it) }
        }
    }

    // Method(Reducing):
    private fun reducing(intention: SignIntentions) {
        // Coroutines:
        viewModelScope.launch {
            // Checking:
            when (intention) {
                // Reducing
                is SignIntentions.SignIn -> repository.signIn(intention) {
                    viewModelScope.launch {
                        _state.emit(if (it) SignViewStates.Success else SignViewStates.Failure)
                    }
                }
                is SignIntentions.SignUp -> repository.signUp(intention) {
                    viewModelScope.launch {
                        _state.emit(if (it) SignViewStates.Success else SignViewStates.Failure)
                    }
                }
            }
        }
    }
}