package com.linksharing

import grails.transaction.Transactional

@Transactional
class UserDetailService {

    def serviceMethod() {

    }
    def updateUser(UserDetailUpdateCO userDetailUpdateCO,session,flash,params,grailsApplication){
        UserDetail userDetail = UserDetail.get(session.user.id)
        userDetail.firstName = userDetailUpdateCO.firstName
        userDetail.lastName = userDetailUpdateCO.lastName
        userDetail.email = userDetailUpdateCO.email
        userDetail.username = userDetailUpdateCO.username

        if (userDetailUpdateCO.hasErrors()) {
            flash.put("error-msg", userDetailUpdateCO)
        }else if (userDetail.save(flush: true)) {
            String path = grailsApplication.mainContext.servletContext.getRealPath("images/profile")
            File image = new File("${path}/${userDetail.username}")
            image.bytes = params.photo.bytes
            session.user = userDetail
            flash.message = "Hello ${userDetail.username}"
        }else {
            flash.put("error-msg", userDetail)
        }
    }

    def changeUserPassword(session,params,flash){
        UserDetail userDetail = UserDetail.get(session.user.id)


        if (userDetail.password.equals(params.oldPassword)){
            if(params.password.equals(params.cnfPassword)){
                userDetail.password = params.password
                if(userDetail.save()){
                    flash.messege = "Password updated sucessfully"
                }
                else {
                    flash.messege = "Password not updated"
                }
            }
            else {
                flash.messge = "Password and confirm password does not match"
            }
        }else{
            flash.messege = "Current password is wrong"
        }
    }
}
