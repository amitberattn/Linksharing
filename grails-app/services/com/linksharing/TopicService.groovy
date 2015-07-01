package com.linksharing

import grails.transaction.Transactional

@Transactional
class TopicService {

    def springSecurityService
    def serviceMethod() {

    }

    def saveTopic(topicInstance,flash){
        Subscription subscription = new Subscription(seriousness: Seriousness.Serious)
        topicInstance.addToSubscription(subscription)

        UserDetail user = UserDetail.load(springSecurityService.principal.id)
        user.addToTopic(topicInstance)
        user.addToSubscription(subscription)

        if(user.hasErrors()){
            println("in error of save topic111111111111111111111111")
            flash.put("error-msg", user)
        }else if (user.validate()) {
            if(user.save(flush: true))
            flash.message = "Topic successfully added!"
            else
                flash.put("error-msg", user)
        }else {
            println("in error of save topic")
            flash.put("error-msg", user)
        }
    }

}
