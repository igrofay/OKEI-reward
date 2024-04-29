package edu.okei.reward.common.view_model

import edu.okei.reward.common.model.UIEvent

fun interface EventBase<T: UIEvent> {
    fun onEvent(event: T)
}