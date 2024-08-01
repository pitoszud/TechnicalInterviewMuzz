package com.tech.interview.technicalinterviewmuzz.data

import javax.inject.Inject

class UserRepository @Inject constructor() : UserRepo {

    /*
    * In production User repository would provide some info about the current app's user
    * */
    override fun getUserId(): String {
        return "usr_001"
    }

    override fun getUserName(): String {
        return "Sarah"
    }
}



interface UserRepo {
    fun getUserId(): String
    fun getUserName(): String
}