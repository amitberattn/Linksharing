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
        String email = params.loginid
        UserDetail userDetail = UserDetail.findByEmail(email)

        if (userDetail) {

            String baseUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()
            println(baseUrl)
            String passwordResetUrl = ""

            MessageDigest messageDigest = MessageDigest.getInstance("SHA1")
            messageDigest.update( userDetail.username.getBytes())
            String sha1Hex = new BigInteger(1, messageDigest.digest()).toString(16).padLeft( 40, '0' )
            println(sha1Hex)
            userDetail.forgotPassId =sha1Hex
            UserDetail user = userDetail.save(flush: true)

              if(user){
                 passwordResetUrl = baseUrl+"/login/resetPasswordPage?id=${sha1Hex}"
              }
            println("userdeat")
            mailService.sendMail {
                to email
                subject "Reset password"
                //body "Password : ${userDetail.password}
                body passwordResetUrl
            }
            redirect(action: 'forgotPassword')

        } else {
            flash.message = "Email does not exits to any account"
            redirect(action: 'forgotPassword')
        }
    }


    @Transactional
    def forgotPasswordReset(){
        String forgotPasswordId = params.forgotPasswordId
        UserDetail userDetail = UserDetail.findByForgotPassId(forgotPasswordId)
        String password = params.password
        String confirmPassword = params.confirmPassword
        String baseUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()
        String passwordResetUrl = baseUrl+"/login/resetPasswordPage?id=${forgotPasswordId}"
        if(password.equals(confirmPassword)){
            userDetail.password=password
            if(userDetail.save(flush: true)) {
                flash.message = "Hallo ${userDetail.username}"
                session.user = userDetail
                //render(view: '/userDetail/dashboard')
                redirect(controller: 'userDetail',action: 'dashboard')
            }else {
                redirect(url:passwordResetUrl)
            }

        }else{
            flash.messege = "Password and confirm Password must be same"
            redirect(url:passwordResetUrl)
        }
    }



    def login = {
        withForm {
            def user = UserDetail.findByPasswordAndUsername(params.password, params.loginid) ?: UserDetail.findByPasswordAndEmail(params.password, params.loginid)
            if (user) {
                session.user = user
                flash.message = "Hello ${user.firstName + " " + user.lastName}!"
                redirect(controller: 'userDetail',action: 'dashboard')
               // render(view: '/userDetail/dashboard')

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