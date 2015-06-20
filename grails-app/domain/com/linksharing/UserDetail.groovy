package com.linksharing

import org.springframework.web.multipart.MultipartFile

class UserDetail {
    UserDetail(UserDetailCO userDetailCO) {
        this.email = userDetailCO.email
        this.username = userDetailCO.username
        this.password = userDetailCO.password
        this.confirmPassword = userDetailCO.confirmPassword
        this.firstName = userDetailCO.firstName
        this.lastName = userDetailCO.lastName
        this.admin = userDetailCO.admin
        this.active = userDetailCO.active
    }

   UserDetail(UserDetailUpdateCO userDetailUpdateCO){
       this.email = userDetailUpdateCO.email
       this.username = userDetailUpdateCO.username
       this.firstName = userDetailUpdateCO.firstName
       this.lastName = userDetailUpdateCO.lastName
   }

    String email
    String username
    String password
    String confirmPassword
    String firstName
    String lastName
    MultipartFile photo
    Boolean admin = false
    Boolean active = true
    Date dateCreated
    Date lastUpdated
    String forgotPassId ="default"
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
        email(email: true, unique: true, blank: false)
        username(unique: true)
        password(password: true,nullable: true)
        confirmPassword bindable: true , nullable: true
        admin()
        active()
        dateCreated(format: 'yyyy-MM-dd')
        lastUpdated(format: 'yyyy-MM-dd')
        forgotPassId(nullable: true)
    }

}
