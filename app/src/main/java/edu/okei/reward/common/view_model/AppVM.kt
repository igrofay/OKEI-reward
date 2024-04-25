package edu.okei.reward.common.view_model

import androidx.lifecycle.ViewModel
import edu.okei.reward.common.model.UIEvent
import edu.okei.reward.common.model.UISideEffect
import edu.okei.reward.common.model.UIState
import org.orbitmvi.orbit.ContainerHost

abstract class AppVM<S: UIState, SF: UISideEffect, E: UIEvent>:
    ContainerHost<S, SF>,
    EventBase<E>,
    ViewModel()
{
    protected abstract fun onError(er: Throwable)
    protected val nameVM: String = this::class.simpleName!!
}