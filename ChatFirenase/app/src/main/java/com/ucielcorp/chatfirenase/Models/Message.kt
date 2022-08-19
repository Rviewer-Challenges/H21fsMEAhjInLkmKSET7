package com.ucielcorp.chatfirenase.Models

import java.util.*

data class Message (
    var id : String = "",
    var message: String = "",
    var email: String = "",
    var dob: Date = Date(),
    var nameUser : String = "",
    var imageProfile : String = "",
)