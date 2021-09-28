package com.knf.dev.demo.kotlinspringvaadincrud.backend.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class User {
    @Id
    @GeneratedValue
    var id: Long? = null
    var firstName: String? = null
    var lastName: String? = null
    var email: String? = null

    protected constructor() {}
    constructor(firstName: String?, lastName: String?, email: String?) {
        this.firstName = firstName
        this.lastName = lastName
        this.email = email
    }
}