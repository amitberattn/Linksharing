package com.linksharing

import grails.transaction.Transactional

@Transactional
class UserDetailService {

    def serviceMethod() {

    }
    def changeStatus(Long id){
        boolean statusFlag = true
        UserDetail userDetail = UserDetail.load(id)
        if(userDetail.active) {
            userDetail.active = false
            statusFlag = false
        }
        else {
            userDetail.active = true
            statusFlag = true
        }
        userDetail.save(flush: true)
        return [statusFlag:statusFlag]
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

    def getDataforDashBoard(params,session){

        params.max = Math.min(params.max ? params.int('max') : 5, 100)
        UserDetail userDetail = UserDetail.load(session.user?.id)
        List<Subscription> subscriptionList = Subscription.findAllByUserDetail(userDetail)
        List<Topic> topicList = Topic.findAllByVisibility(Visibility.Public)
        topicList.sort { a, b ->
            a.resource.size() == b.resource.size() ? 0 : a.resource.size() > b.resource.size() ? -1 : 1
        }
        if(topicList.size()>5){
            topicList = topicList[0..4]
        }
        int totalTopic = Topic.count()
        int postCount = Resource.countByCreatedBy(session.user)
        List<Resource> resourceList =new ArrayList<Resource>()
        if(userDetail) {
            if (userDetail.admin) {
                topicList = Topic.list(params)
                resourceList = Resource.list()
            }
        }


        return  [my_subscriptions: subscriptionList, postNo: postCount, topicList: topicList, totalTopic: totalTopic,userDetail:userDetail,resourceList:resourceList]
    }

    def userProfile(UserDetail userDetail,session){

        List<Subscription> subscriptionList = Subscription.findAllByUserDetail(userDetail)
        List<Topic> topicList = Topic.findAllByCreatedByAndVisibility(userDetail,Visibility.Public)
        topicList.sort { a, b ->
            a.resource.size() == b.resource.size() ? 0 : a.resource.size() > b.resource.size() ? -1 : 1
        }
        if(topicList.size()>5){
            topicList = topicList[0..4]
        }
        int totalTopic = Topic.count()
        int postCount = Resource.countByCreatedBy(userDetail)
        return [my_subscriptions: subscriptionList, postNo: postCount, topicList: topicList, totalTopic: totalTopic,userDetail: userDetail]
    }

    def trendingTopicsPerPage(params){

        println("inside trendingTopicsPagination")
        params.max = Math.min(params.max ? params.int('max') : 5, 100)
        List<Topic> topicList = Topic.list(params)
        int totalTopic = Topic.count()
        println("TpoicList:" + topicList)
        println(params.max)
        println(params.offset)
        return  [topicList: topicList, totalTopic: totalTopic]
    }

    def subscribeTopicService(Topic topic,session,flash){
        UserDetail userDetail = UserDetail.findById(session.user.id)
        Subscription subscription = new Subscription(seriousness: com.linksharing.Seriousness.Serious, topic: topic, userDetail: userDetail)
        if (subscription.validate()) {
            subscription.save(flush: true, failOnError: true)

        } else {
            flash.message = "Unable to subscribe"
        }
    }

    def unsubscribeTopicService(Topic topic,session,flash){
        UserDetail userDetail = UserDetail.load(session.user.id)
        Subscription subscription = Subscription.findByUserDetailAndTopic(userDetail, topic)
        userDetail.removeFromSubscription(subscription)
        topic.removeFromSubscription(subscription)
        subscription.delete(flush: true)
        flash.message = 'Unsubscribed sucessfully'
    }

}
