package com.linksharing

import grails.transaction.Transactional

@Transactional
class UserDetailService {

    def springSecurityService
    def passwordEncoder
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
    def updateUser(UserDetailUpdateCO userDetailUpdateCO,flash,params,grailsApplication){
        UserDetail userDetail = UserDetail.get(springSecurityService.principal.id)
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
            springSecurityService.reauthenticate userDetail.username
            flash.message = "Hello ${userDetail.username}"
        }else {
            flash.put("error-msg", userDetail)
        }
    }

    def changeUserPassword(params,flash){
        UserDetail userDetail = UserDetail.get(springSecurityService.principal.id)
        if (passwordEncoder.isPasswordValid(userDetail.password,params.oldPassword,null)){
            if(params.password.equals(params.cnfPassword)){
                userDetail.password = params.password
                if(userDetail.save(flush: true)){
                    println("pass change")
                    springSecurityService.reauthenticate(userDetail.username,params.password)
                    flash.messege = "Password updated sucessfully"
                }
                else {
                    println("pass not change")

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

    def getDataforDashBoard(params,user){

        params.max = Math.min(params.max ? params.int('max') : 5, 100)
        //UserDetail userDetail = UserDetail.load(session.user?.id)
        List<Subscription> subscriptionList = Subscription.findAllByUserDetail(user)
        List<Topic> topicList = Topic.findAllByVisibility(Visibility.Public)
        topicList.sort { a, b ->
            a.resource.size() == b.resource.size() ? 0 : a.resource.size() > b.resource.size() ? -1 : 1
        }
        if(topicList.size()>5){
            topicList = topicList[0..4]
        }
        int totalTopic = Topic.count()
        int postCount = Resource.countByCreatedBy(user)
        List<Resource> resourceList =new ArrayList<Resource>()
        List<Subscription> subsList = Subscription.findAllByUserDetail(user)
        List<Resource> resList = Resource.findAllByTopicInList(subsList.topic as List<Topic>)
        List<Resource> resListUnread = resList.findAll{it->
            ReadingItem readingItem = ReadingItem.findByUserDetailAndResource(user,it)
            if(readingItem){
              if(readingItem.isRead == false){
                  return true
              }else{
                  return false
              }
            }else{
                return false
            }
        }
        println resListUnread

        return  [my_subscriptions: subscriptionList, postNo: postCount, topicList: topicList, totalTopic: totalTopic,userDetail:user,resourceList:resourceList,resListUnread:resListUnread]
    }

    def userProfile(UserDetail userDetail){

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
        List<Resource> resourceList = Resource.findAllByCreatedBy(userDetail)
        return [my_subscriptions: subscriptionList, postNo: postCount, topicList: topicList, totalTopic: totalTopic,userDetail: userDetail,resourceList:resourceList]
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

    def subscribeTopicService(Topic topic,flash){
        UserDetail userDetail = UserDetail.findById(springSecurityService.principal.id)
        Subscription subscription = new Subscription(seriousness: com.linksharing.Seriousness.Serious, topic: topic, userDetail: userDetail)
        if (subscription.validate()) {
            subscription.save(flush: true, failOnError: true)

        } else {
            flash.message = "Unable to subscribe"
        }
    }

    def unsubscribeTopicService(Topic topic,flash){
        UserDetail userDetail = UserDetail.load(springSecurityService.principal.id)
        Subscription subscription = Subscription.findByUserDetailAndTopic(userDetail, topic)
        userDetail.removeFromSubscription(subscription)
        topic.removeFromSubscription(subscription)
        subscription.delete(flush: true)
        flash.message = 'Unsubscribed sucessfully'
    }

    def getPostByUser(userDetail){
        List<Resource> resourceList = Resource.findAllByCreatedBy(userDetail)
        List<Topic> topicList = Topic.findAllByCreatedBy(userDetail)
        return [resourceList:resourceList,topicList:topicList,userDetail: userDetail]
    }

    def searchInboxData(userDetail,String txt){
        List<Subscription> subscriptionList = Subscription.findAllByUserDetail(userDetail)
        List<Topic> myTopicList = subscriptionList.topic as List
        List<Resource> resourceList = Resource.findAllByTopicInList(myTopicList)
        List<Resource> myResourceList= resourceList.findAll {it->
            it.description.contains(txt)
        }
        return  myResourceList
    }

}
