package com.linksharing

import grails.transaction.Transactional

@Transactional
class LoginService {

    def serviceMethod() {

    }

    def registerUser(UserDetailCO userDetailCOInstance,flash,session,grailsApplication,params){
        UserDetail userDetail = new UserDetail(userDetailCOInstance)
            if (userDetailCOInstance.hasErrors()) {
                flash.put("error-msg", userDetailCOInstance)
                return false

            } else if (userDetail.save(flush: true)) {
                String path = grailsApplication.mainContext.servletContext.getRealPath("images/profile")
                File image = new File("${path}/${userDetail.username}")
                image.bytes = params.photo.bytes
                flash.message = "Hallo ${userDetail.username}"
                session.user = userDetail
                return true
            } else {
                flash.put("error-msg", userDetail)
                return true
            }

    }

}
