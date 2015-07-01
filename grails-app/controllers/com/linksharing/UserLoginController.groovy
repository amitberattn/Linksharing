package com.linksharing


import grails.converters.JSON
import grails.plugin.springsecurity.LoginController
import grails.plugin.springsecurity.SpringSecurityUtils
import org.springframework.web.multipart.MultipartFile

import javax.servlet.http.HttpServletResponse

import org.springframework.security.access.annotation.Secured
import org.springframework.security.authentication.AccountExpiredException
import org.springframework.security.authentication.CredentialsExpiredException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.LockedException
import org.springframework.security.core.context.SecurityContextHolder as SCH
import org.springframework.security.web.WebAttributes

import com.linksharing.UserDetail
import grails.transaction.Transactional
import java.sql.Timestamp
import java.util.Date

import java.sql.DriverManager

@Secured('permitAll')
class UserLoginController extends LoginController {

    def authenticationTrustResolver

    /**
     * Dependency injection for the springSecurityService.
     */
    def springSecurityService

    /**
     * Default action; redirects to 'defaultTargetUrl' if logged in, /login/auth otherwise.
     */
    def loginService



    def demoFacebook() {
        loginService.facebookLogin(flash, params)
        render(true)

    }

    def register(UserDetailCO userDetailCOInstance) {
        println("inside register form")
        withForm {
            if (loginService.registerUser(userDetailCOInstance, flash, session, grailsApplication, params)) {
                println("login service true")
                redirect(url: '/')
            } else {
                println("login service false")
                redirect(url: '/')
            }
        }
    }


    def forgotPassword() {
        render(view: 'forgotPassword')
    }

    def resetPasswordPage() {
        String forgotPasswordId = params.id
        boolean isRequestForReest = loginService.checkForgetPasswordId(params.id)
        if (isRequestForReest) {
            render(view: 'resetPasswordPage', model: [forgotPasswordId: forgotPasswordId])

        } else {
            flash.message = "Invalid url"
            redirect(url: '/')
        }

    }


    def forgotPasswordEmailSet() {
        loginService.forgotPasswordSendMail(params, request, flash)
        redirect(action: 'forgotPassword')
    }


    @Transactional
    def forgotPasswordReset() {
        if (loginService.forgotUserPasswordReset(params, flash, session, request)) {
            redirect(controller: 'userDetail', action: 'dashboard')
        } else {
            redirect(url: passwordResetUrl)
        }
    }



    def index() {
        if (springSecurityService.isLoggedIn()) {
            println(SpringSecurityUtils.securityConfig.successHandler.defaultTargetUrl)
            redirect uri: SpringSecurityUtils.securityConfig.successHandler.defaultTargetUrl
        } else {
            redirect action: 'auth', params: params
        }
    }

/*My coustom actions*/

    /**
     * Show the login page.
     */
    def auth() {

        def config = SpringSecurityUtils.securityConfig

        if (springSecurityService.isLoggedIn()) {
            redirect uri: config.successHandler.defaultTargetUrl
            return
        }

        String view = 'auth'
        String postUrl = "${request.contextPath}${config.apf.filterProcessesUrl}"
        render view: view, model: [postUrl            : postUrl,
                                   rememberMeParameter: config.rememberMe.parameter] + loginService.getPublicTrendingTopics()
    }

    /**
     * The redirect action for Ajax requests.
     */
    def authAjax() {
        response.setHeader 'Location', SpringSecurityUtils.securityConfig.auth.ajaxLoginFormUrl
        response.sendError HttpServletResponse.SC_UNAUTHORIZED
    }

    /**
     * Show denied page.
     */
    def denied() {
        if (springSecurityService.isLoggedIn() &&
                authenticationTrustResolver.isRememberMe(SCH.context?.authentication)) {
            // have cookie but the page is guarded with IS_AUTHENTICATED_FULLY
            redirect action: 'full', params: params
        }
    }

    /**
     * Login page for users with a remember-me cookie but accessing a IS_AUTHENTICATED_FULLY page.
     */
    def full() {
        def config = SpringSecurityUtils.securityConfig
        render view: 'auth', params: params,
                model: [hasCookie: authenticationTrustResolver.isRememberMe(SCH.context?.authentication),
                        postUrl  : "${request.contextPath}${config.apf.filterProcessesUrl}"]
    }

    /**
     * Callback after a failed login. Redirects to the auth page with a warning message.
     */
    def authfail() {

        String msg = ''
        def exception = session[WebAttributes.AUTHENTICATION_EXCEPTION]
        if (exception) {
            if (exception instanceof AccountExpiredException) {
                msg = g.message(code: "springSecurity.errors.login.expired")
            } else if (exception instanceof CredentialsExpiredException) {
                msg = g.message(code: "springSecurity.errors.login.passwordExpired")
            } else if (exception instanceof DisabledException) {
                msg = g.message(code: "springSecurity.errors.login.disabled")
            } else if (exception instanceof LockedException) {
                msg = g.message(code: "springSecurity.errors.login.locked")
            } else {
                msg = g.message(code: "springSecurity.errors.login.fail")
            }
        }

        if (springSecurityService.isAjax(request)) {
            render([error: msg] as JSON)
        } else {
            flash.message = msg
            redirect action: 'auth', params: params
        }
    }

    /**
     * The Ajax success redirect url.
     */
    def ajaxSuccess() {
        render([success: true, username: springSecurityService.authentication.name] as JSON)
    }

    /**
     * The Ajax denied redirect url.
     */
    def ajaxDenied() {
        render([error: 'access denied'] as JSON)
    }

}


class UserDetailCO {
    String email
    String username
    String password
    String confirmPassword
    String firstName
    String lastName
    MultipartFile photo
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
