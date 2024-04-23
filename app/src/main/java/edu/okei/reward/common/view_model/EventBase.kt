package edu.okei.reward.common.view_model

import edu.okei.reward.common.model.UIEvent

interface EventBase<T: UIEvent> {
    fun onEvent(event: T)
}