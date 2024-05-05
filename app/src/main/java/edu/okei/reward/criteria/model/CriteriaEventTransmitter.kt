package edu.okei.reward.criteria.model

import edu.okei.reward.common.model.UIEvent

object CriteriaEventTransmitter {
    private var observer: Observer? = null

    fun subscribe(observer: Observer) {
        if (this.observer != null) return
        this.observer = observer
    }

    fun unsubscribe(observer: Observer) {
        if (this.observer != observer) return
        this.observer = null
    }
    fun sendMessage(message: UIEvent){
        observer?.messageReceived(message)
    }
    interface Observer {
        fun messageReceived(message: UIEvent)
    }
}