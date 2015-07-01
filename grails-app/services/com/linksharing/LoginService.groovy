package com.linksharing

import com.linksharing.UserDetailCO
import grails.transaction.Transactional

import java.security.MessageDigest
import java.sql.Timestamp
import java.text.SimpleDateFormat

@Transactional
class LoginService {

    def mailService
    def springSecurityService


    def serviceMethod() {

    }

    def checkForgetPasswordId(String id) {
        boolean flag = false
        UserDetail userDetail = UserDetail.findByForgotPassId(id)

        if (userDetail) {
            flag = true
        } else {
            flag = false
        }
        return flag
    }

    def forgotPasswordSendMail(params, request, flash) {

        String email = params.loginid
        UserDetail userDetail = UserDetail.findByEmail(email)

        if (userDetail) {

            String baseUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()
            println(baseUrl)
            String passwordResetUrl = ""

            MessageDigest messageDigest = MessageDigest.getInstance("SHA1")
            messageDigest.update(userDetail.username.getBytes())
            String sha1Hex = new BigInteger(1, messageDigest.digest()).toString(16).padLeft(40, '0')
            println(sha1Hex)
            userDetail.forgotPassId = sha1Hex
            UserDetail user = userDetail.save(flush: true)

            if (user) {
                passwordResetUrl = baseUrl + "/userLogin/resetPasswordPage?id=${sha1Hex}"
            }
            println("userdeat")
            mailService.sendMail {
                to email
                subject "Reset password"
                //body "Password : ${userDetail.password}
                body passwordResetUrl
            }


        } else {
            flash.message = "Email does not exits to any account"
        }
    }

    def forgotUserPasswordReset(params, flash, session, request) {
        boolean flag = true
        String forgotPasswordId = params.forgotPasswordId
        UserDetail userDetail = UserDetail.findByForgotPassId(forgotPasswordId)
        String password = params.password
        String confirmPassword = params.confirmPassword
        String baseUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()
        String passwordResetUrl = baseUrl + "/login/resetPasswordPage?id=${forgotPasswordId}"
        if (password.equals(confirmPassword)) {
            userDetail.password = password
            if (userDetail.save(flush: true)) {
                flash.message = "Hallo ${userDetail.username}"
                session.user = userDetail
                //render(view: '/userDetail/dashboard')
                flag = true
            } else {
                flag = false

            }

        } else {
            flag = false
            flash.messege = "Password and confirm Password must be same"
        }
    }


    def facebookLogin(flash, params) {
        java.util.Date date = new java.util.Date()
        String pass = params.uid + new Timestamp(date.getTime())
        UserDetailCO userDetailCO = new UserDetailCO()
        userDetailCO.email = params.fbEmail
        userDetailCO.username = params.uid
        userDetailCO.password = pass
        userDetailCO.confirmPassword = pass
        userDetailCO.firstName = params.firstName
        userDetailCO.lastName = params.lastName
        userDetailCO.active = true
        User user = new UserDetail(userDetailCO)
        Role role = Role.findByAuthority("ROLE_USER") ?: (new Role("ROLE_USER").save(flush: true))

        User preLoginUser = UserDetail.findByUsername(params.uid)

        if (preLoginUser) {
            preLoginUser.password = pass
            preLoginUser.save(flush: true)
            springSecurityService.reauthenticate(preLoginUser.username, pass)
        } else {
            if (userDetailCO.hasErrors()) {
                flash.put("error-msg", userDetailCOInstance)
                return false
            } else if (user.save(flush: true)) {
                UserRole userRole = new UserRole(user, role).save(flush: true)
                flash.message = "Hallo ${user.username}"
                springSecurityService.reauthenticate(user.username, user.password)
                return true
            } else {
                flash.put("error-msg", user)
                return true
            }
        }
    }

    def registerUser(UserDetailCO userDetailCOInstance, flash, session, grailsApplication, params) {
        User user = new UserDetail(userDetailCOInstance)
        Role role = Role.findByAuthority("ROLE_USER") ?: (new Role("ROLE_USER").save(flush: true))
        println("authority : " + role?.authority)
        if (userDetailCOInstance.hasErrors()) {
            flash.put("error-msg", userDetailCOInstance)
            return false

        } else if (user.save(flush: true)) {
            UserRole userRole = new UserRole(user, role).save(flush: true)
            String path = grailsApplication.mainContext.servletContext.getRealPath("images/profile")
            File image = new File("${path}/${user.username}")
            image.bytes = params.photo.bytes
            flash.message = "Hallo ${user.username}"
            springSecurityService.reauthenticate(user.username, user.password)
            return true
        } else {
            flash.put("error-msg", user)
            return true
        }

    }

    def getPublicTrendingTopics() {
        Calendar calendar = Calendar.getInstance()
        Date d1 = calendar.getTime()
        calendar.add(Calendar.YEAR, -1)
        Date preViousYear = calendar.getTime()
        List<Topic> topicListYear = Topic.findAllByVisibilityAndDateCreatedBetween(Visibility.Public, preViousYear, d1)
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-ddd")
        List<Topic> todayTopicList = topicListYear.findAll {
            simpleDateFormat.format(it.dateCreated).equals(simpleDateFormat.format(d1))
        }
        calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, -1)
        List<Topic> monthTopicList = topicListYear.findAll {
            it.dateCreated <= d1 && it.dateCreated >= calendar.getTime()
        }
        calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, -10)
        List<Topic> tenDaysTopicList = topicListYear.findAll {
            it.dateCreated <= d1 && it.dateCreated >= calendar.getTime()
        }

        return [todayTopicList: todayTopicList, tenDaysTopicList: tenDaysTopicList, monthTopicList: monthTopicList, topicListYear: topicListYear]
    }

}
