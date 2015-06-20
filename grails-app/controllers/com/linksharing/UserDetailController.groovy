package com.linksharing

import grails.converters.JSON
import org.springframework.web.multipart.MultipartFile

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class UserDetailController {
    def userDetailService

    static allowedMethods = [save: "POST", update: "POST", delete: "DELETE"]

    @Transactional
    def changeUserStatus(Long id){
        render(userDetailService.changeStatus(id) as JSON)
    }

    def listUser(){
        List<UserDetail> userDetailList = UserDetail.findAllByAdmin(false)
        [userDetailList:userDetailList]
    }


    def dashboard() {

        render(view: 'dashboard', model: userDetailService.getDataforDashBoard(params,session))
    }

    def profile(UserDetail userDetail){
        render(view: 'profile', model: userDetailService.userProfile(userDetail,session))
    }

    def trendingTopicsPagination() {
        render(template: 'topicsAdmin', userDetailService.trendingTopicsPerPage(params))
    }

    @Transactional
    def subscribeTopic(Topic topic) {
        userDetailService.subscribeTopicService(topic,session,flash)
        redirect(action: 'dashboard')
    }

    @Transactional
    def unsubscribeTopic(Topic topic) {
        userDetailService.unsubscribeTopicService(topic,session,params)
        redirect(action: 'dashboard')
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond UserDetail.list(params), model: [userDetailInstanceCount: UserDetail.count()]
    }

    def show(UserDetail userDetailInstance) {
        respond userDetailInstance
    }

    def create() {
        respond new UserDetail(params)
    }

    @Transactional
    def save(UserDetail userDetailInstance) {
        if (userDetailInstance == null) {
            notFound()
            return
        }

        if (userDetailInstance.hasErrors()) {
            respond userDetailInstance.errors, view: 'create'
            return
        }

        userDetailInstance.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'userDetail.label', default: 'UserDetail'), userDetailInstance.id])
                redirect userDetailInstance
            }
            '*' { respond userDetailInstance, [status: CREATED] }
        }
    }

    def edit() {
        List<Subscription> subscriptionList = Subscription.findAllByUserDetail(UserDetail.load(session.user?.id))
        List<Topic> topicList = Topic.list()
        int postCount = Resource.countByCreatedBy(session.user)
        [my_subscriptions: subscriptionList, postNo: postCount, topicList: topicList]
    }

    @Transactional
    def update(UserDetailUpdateCO userDetailUpdateCO) {

        userDetailService.updateUser(userDetailUpdateCO, session, flash, params, grailsApplication)
        redirect(controller: 'userDetail', action: 'dashboard')
    }

    @Transactional
    def changePassword() {
        userDetailService.changeUserPassword(session, params, flash)
        redirect(controller: 'userDetail', action: 'edit')

    }

    @Transactional
    def delete(UserDetail userDetailInstance) {

        if (userDetailInstance == null) {
            notFound()
            return
        }

        userDetailInstance.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'UserDetail.label', default: 'UserDetail'), userDetailInstance.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'userDetail.label', default: 'UserDetail'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}


class UserDetailUpdateCO {
    String email
    String username
    String firstName
    String lastName
    MultipartFile photo
    Boolean admin = false
    Boolean active = true

    static constraints = {

        email(email: true, unique: true, blank: false)
        username(unique: true, blank: false)
        firstName()
        lastName()
    }

}