package com.linksharing

import grails.transaction.Transactional
import grails.validation.Validateable
import org.springframework.web.multipart.MultipartFile

import java.text.SimpleDateFormat

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import java.security.MessageDigest

class LoginController {
    def loginService
    def mailService

    static allowedMethods = [login: 'POST', register: 'POST', logout: 'GET']

    def index() {
        loginService.getPublicTrendingTopics()
    }

    def forgotPassword() {}

    def resetPasswordPage(){
        String forgotPasswordId = params.id
        UserDetail userDetail = UserDetail.findByForgotPassId(forgotPasswordId)
        if(userDetail){
              render(view: 'resetPasswordPage',model: [forgotPasswordId:forgotPasswordId])

        }else {
            flash.message = "Invalid url"
            redirect(url: '/')
        }

    }


    def forgotPasswordEmailSet() {
        loginService.forgotPasswordSendMail(params,request,flash)
        redirect(action: 'forgotPassword')
    }


    @Transactional
    def forgotPasswordReset(){
        if (loginService.forgotUserPasswordReset(params,flash,session,request)){
            redirect(controller: 'userDetail',action: 'dashboard')
        }else {
            redirect(url:passwordResetUrl)
        }
    }



    def login = {
        withForm {
            def user = UserDetail.findByPasswordAndUsername(params.password, params.loginid) ?: UserDetail.findByPasswordAndEmail(params.password, params.loginid)
            if (user) {
                if (user.active) {
                    session.user = user
                    flash.message = "Hello ${user.firstName + " " + user.lastName}!"
                    redirect(controller: 'userDetail', action: 'dashboard')
                }else{
                    flash.message = "Your account has been deactivated"
                    redirect(controller: 'login',action: 'index')
                }

            } else {
                flash.error = "The email/username and password you entered don't match. "
                redirect(controller: 'login',action: 'index')
            }
        }
    }


    def logout = {
        def user = session.user
        flash.message = "Goodbye ${user.username}"
        session.invalidate()
        redirect(controller: 'login', action: 'index')
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

        password nullable: false
        confirmPassword bindable: true, nullable: false

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