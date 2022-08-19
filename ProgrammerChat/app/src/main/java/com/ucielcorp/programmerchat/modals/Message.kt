package com.ucielcorp.programmerchat.modals

data class Message(
    var message : String? = null,
    var senderId : String? = null,
    var timestamp : Long? = null,
    var currentTime : String? = null
)
