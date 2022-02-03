package com.ar.team.company.schoolsupplies.ui.activitys.sign

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ar.team.company.schoolsupplies.control.repository.SignRepository
import com.ar.team.company.schoolsupplies.model.intentions.SignIntentions
import com.ar.team.company.schoolsupplies.model.states.SignViewStates
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignViewModel @Inject constructor(private val repository: SignRepository) : ViewModel() {

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
                is SignIntentions.SignIn -> _state.emit(if (authCheck(repository.signIn(intention))) SignViewStates.Success else SignViewStates.Failure)
                is SignIntentions.SignUp -> _state.emit(if (voidCheck(repository.signUp(intention))) SignViewStates.Success else SignViewStates.Failure)
            }
        }
    }

    // Method(TaskCheck):
    private fun authCheck(task: Task<AuthResult>): Boolean = task.isComplete && task.isSuccessful
    private fun voidCheck(task: Task<Void>?): Boolean = task!!.isComplete && task.isSuccessful
}