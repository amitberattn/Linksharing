package com.linksharing

import com.linksharing.UserDetailCO
import org.springframework.web.multipart.MultipartFile

class UserDetail extends User {
    UserDetail(UserDetailCO userDetailCO) {
        super()
        this.username = userDetailCO.username
        this.email = userDetailCO.email
        this.password = userDetailCO.password
        this.confirmPassword = userDetailCO.confirmPassword
        this.firstName = userDetailCO.firstName
        this.lastName = userDetailCO.lastName
        this.active = userDetailCO.active
    }

    UserDetail(UserDetailUpdateCO userDetailUpdateCO) {
        super(userDetailUpdateCO.username)
        this.email = userDetailUpdateCO.email
        this.firstName = userDetailUpdateCO.firstName
        this.lastName = userDetailUpdateCO.lastName
    }

    String confirmPassword
    String firstName
    String lastName
    MultipartFile photo
    Boolean active = true
    Date dateCreated
    Date lastUpdated
    String forgotPassId = "default"
    static transients = ['confirmPassword', 'photo']

    static mapping = {
        sort dateCreated: 'desc'
//        topic fetch: 'join'
//        subscription fetch: 'join'
//        resource lazy: false
    }
    static hasMany = [
            topic         : Topic,
            subscription  : Subscription,
            resource      : Resource,
            readingItem   : ReadingItem,
            resourceRating: ResourceRating
    ]

    static constraints = {
        firstName()
        lastName()
        confirmPassword bindable: true, nullable: true
        active()
        dateCreated(format: 'yyyy-MM-dd')
        lastUpdated(format: 'yyyy-MM-dd')
        forgotPassId(nullable: true)
    }

}


