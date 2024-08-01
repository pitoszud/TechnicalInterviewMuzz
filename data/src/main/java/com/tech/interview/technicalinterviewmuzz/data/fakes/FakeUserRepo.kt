package com.tech.interview.technicalinterviewmuzz.data.fakes

import com.tech.interview.technicalinterviewmuzz.data.UserRepo

class FakeUserRepo : UserRepo {

    override fun getUserId(): String {
        return "fakeUserId"
    }

    override fun getUserName(): String {
        return "fakeUserName"
    }
}