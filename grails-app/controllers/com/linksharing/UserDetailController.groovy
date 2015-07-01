package com.linksharing

import grails.converters.JSON
import org.springframework.web.multipart.MultipartFile

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Transactional(readOnly = true)
class UserDetailController {
    def userDetailService
    def springSecurityService

    static allowedMethods = [save: "POST", update: "POST", delete: "DELETE"]


    @Secured(['ROLE_ADMIN'])
    def getUserByType(String type){
        println("type="+type)
        List<UserDetail> userList
        List<UserDetail> userDetailList
        Role role
        if(type.equals("all")){
            userDetailList = UserDetail.findAll()
            role = new Role(authority: "ROLE_USER")
            userList = userDetailList.findAll() { it ->
                List<Role> roleList = it.getAuthorities() as List<Role>
                roleList.contains(role)

            }
        }else if(type.equals("active")){

            userDetailList = UserDetail.findAllByActive(true)
            role = new Role(authority: "ROLE_USER")
            userList = userDetailList.findAll() { it ->
                List<Role> roleList = it.getAuthorities() as List<Role>
                roleList.contains(role)

            }

        }else{
            userDetailList = UserDetail.findAllByActive(false)
            role = new Role(authority: "ROLE_USER")
            userList = userDetailList.findAll() { it ->
                List<Role> roleList = it.getAuthorities() as List<Role>
                roleList.contains(role)

            }
        }
        println("userlist==== "+userList)
        render(template: 'listUserTable',model: [userDetailList:userList])

    }

    @Secured(["ROLE_USER", "ROLE_ADMIN"])
    def searchInbox(String txt) {
        UserDetail userDetail = UserDetail.load(springSecurityService.principal.id)
        List<Resource> myResourceList = userDetailService.searchInboxData(userDetail,txt)
        render(template: '/topic/post', model: [resourceList: myResourceList, userDetail: userDetail])
    }

    @Secured(['ROLE_ADMIN'])
    @Transactional
    def changeUserStatus(Long id) {
        render(userDetailService.changeStatus(id) as JSON)
    }

    @Secured(['ROLE_ADMIN'])
    def listUser() {
        List<UserDetail> userDetailList = UserDetail.findAll()
        Role role = new Role(authority: "ROLE_USER")
        List<UserDetail> userList = userDetailList.findAll() { it ->
            List<Role> roleList = it.getAuthorities() as List<Role>
            roleList.contains(role)

        }
        [userDetailList: userList]
    }

    @Secured(["ROLE_USER", "ROLE_ADMIN"])
    def postUser(UserDetail userDetail) {
        render(view: 'userPost', model: userDetailService.getPostByUser(userDetail))
    }

    @Secured(['ROLE_USER', 'ROLE_ADMIN'])
    def dashboard() {
        def user = User.get(springSecurityService.principal.id)
        render(view: 'dashboard', model: userDetailService.getDataforDashBoard(params, user))
    }

    @Secured('permitAll')
    def profile(UserDetail userDetail) {
        render(view: 'profile', model: userDetailService.userProfile(userDetail))
    }

    def trendingTopicsPagination() {
        render(template: 'topicsAdmin', userDetailService.trendingTopicsPerPage(params))
    }

    @Secured(["ROLE_USER", "ROLE_ADMIN"])
    @Transactional
    def subscribeTopic(Topic topic) {
        userDetailService.subscribeTopicService(topic, flash)
        redirect(action: 'dashboard')
    }

    @Secured(["ROLE_USER", "ROLE_ADMIN"])
    @Transactional
    def unsubscribeTopic(Topic topic) {
        userDetailService.unsubscribeTopicService(topic, params)
        redirect(action: 'dashboard')
    }

    @Secured(["ROLE_USER", "ROLE_ADMIN"])
    def edit() {
        UserDetail userDetail = UserDetail.load(springSecurityService.principal.id)
        List<Subscription> subscriptionList = Subscription.findAllByUserDetail(UserDetail.load(springSecurityService.principal.id))
        List<Topic> topicList = Topic.list()
        int postCount = Resource.countByCreatedBy(userDetail)
        [my_subscriptions: subscriptionList, postNo: postCount, topicList: topicList, userDetail: userDetail]
    }

    @Secured(["ROLE_USER", "ROLE_ADMIN"])
    @Transactional
    def updateSeriousness(String ser, Long id) {
        Subscription subscription = Subscription.get(id)
        if (ser.equals("Serious")) {
            subscription.seriousness = Seriousness.Serious
        } else if (ser.equals("Casual")) {
            subscription.seriousness = Seriousness.Casual
        } else {
            subscription.seriousness = Seriousness.VerySerious
        }
        subscription.save(flush: 'true')
        render(true)
    }

    @Secured(["ROLE_USER", "ROLE_ADMIN"])
    @Transactional
    def updateVisibility(String visibility, Long id) {
        Topic topic = Topic.get(id)
        if (visibility.equals("Private")) {
            topic.visibility = Visibility.Private
        } else {
            topic.visibility = Visibility.Public
        }
        topic.save(flush: true)
        render(true)

    }

    @Secured(["ROLE_USER", "ROLE_ADMIN"])
    @Transactional
    def update(UserDetailUpdateCO userDetailUpdateCO) {

        userDetailService.updateUser(userDetailUpdateCO, flash, params, grailsApplication)
        redirect(controller: 'userDetail', action: 'dashboard')
    }

    @Secured(["ROLE_USER", "ROLE_ADMIN"])
    @Transactional
    def changePassword() {
        userDetailService.changeUserPassword(params, flash)
        redirect(controller: 'userDetail', action: 'edit')

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