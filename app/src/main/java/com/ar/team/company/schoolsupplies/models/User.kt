package com.ar.team.company.schoolsupplies.models

class User {
    var id: String? = null
    var name: String? = null
    var nameSchool: String? = null
    var image: String? = null
    var currentYear: String? = null
    var email: String? = null
    var password: String? = null
    var phoneNumber: String? = null
    var address: String? = null

    constructor() {}
    constructor(id: String?, name: String?, nameSchool: String?, image: String?) {
        this.id = id
        this.name = name
        this.nameSchool = nameSchool
        this.image = image
    }

    constructor(
        id: String?,
        email: String?,
        password: String?,
        name: String?,
        nameSchool: String?,
        currentYear: String?,
        phoneNumber: String?,
        address: String?,
        image: String?
    ) {
        this.id = id
        this.name = name
        this.nameSchool = nameSchool
        this.currentYear = currentYear
        this.phoneNumber = phoneNumber
        this.address = address
        this.password = password
        this.email = email
        this.image = image
    }

    constructor(id: String?, name: String?) {
        this.id = id
        this.name = name
        nameSchool = ""
        image = ""
    }


}