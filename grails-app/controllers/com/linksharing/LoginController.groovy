package com.linksharing

import grails.transaction.Transactional
import grails.validation.Validateable
import org.springframework.web.multipart.MultipartFile

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND

class LoginController {
    def loginService

    static allowedMethods = [login: 'POST', register: 'POST', logout: 'GET']

    def index() {
        //forward( action:"login")
    }

    def login = {
        withForm {
            def user = UserDetail.findByPasswordAndUsername(params.password, params.loginid) ?: UserDetail.findByPasswordAndEmail(params.password, params.loginid)
            if (user) {
                session.user = user
                flash.message = "Hello ${user.firstName + " " + user.lastName}!"

            } else {
                flash.error = "The email/username and password you entered don't match. "
            }
        }
    }


    def logout = {
        def user = session.user
        flash.message = "Goodbye ${user.username}"
        session.invalidate()
        redirect(url: '/')
    }
    def register(UserDetailCO userDetailCOInstance) {
        withForm {
            if (loginService.registerUser(userDetailCOInstance, flash, session, grailsApplication, params)) {
                render(view: 'index')
            } else {
                render(view: '/userDetail/dashboard')
            }
        }
    }

}

@Validateable
class UserDetailCO {
    String email
    String username
    String password
    String confirmPassword
    String firstName
    String lastName
    MultipartFile photo
    Boolean admin = false
    Boolean active = true

    static constraints = {

        confirmPassword validator: { value, user, errors ->
            if (!(value?.equals(user?.password))) {
                errors.rejectValue("confirmPassword", "some.text", "Confirm password must be same as password")
                return false
            }
            return true
        }
        importFrom(UserDetail)
    }

}