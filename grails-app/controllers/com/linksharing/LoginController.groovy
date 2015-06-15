package com.linksharing

import grails.transaction.Transactional
import grails.validation.Validateable
import org.springframework.web.multipart.MultipartFile

import java.text.SimpleDateFormat

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND

class LoginController {
    def loginService

    static allowedMethods = [login: 'POST', register: 'POST', logout: 'GET']

    def index() {
        Calendar calendar = Calendar.getInstance()
        Date d1 = calendar.getTime()
        calendar.add(Calendar.YEAR,-1)
        Date preViousYear = calendar.getTime()
        List<Topic> topicListYear=Topic.findAllByVisibilityAndDateCreatedBetween(Visibility.Public,preViousYear,d1)
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-ddd")
        List<Topic> todayTopicList = topicListYear.findAll {simpleDateFormat.format(it.dateCreated).equals(simpleDateFormat.format(d1))}
        calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH,-1)
        List<Topic> monthTopicList = topicListYear.findAll{it.dateCreated <= d1 && it.dateCreated >= calendar.getTime()}
        calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE,-10)
        List<Topic> tenDaysTopicList = topicListYear.findAll {it.dateCreated<=d1 && it.dateCreated>=calendar.getTime()}

        [todayTopicList:todayTopicList,tenDaysTopicList:tenDaysTopicList,monthTopicList:monthTopicList,topicListYear:topicListYear]
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

        password nullable: false
        confirmPassword bindable: true , nullable: false

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