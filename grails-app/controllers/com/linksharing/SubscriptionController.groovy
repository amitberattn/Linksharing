package com.linksharing


import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Transactional(readOnly = true)
class SubscriptionController {

    def springSecurityService

    static allowedMethods = [save: "POST", update: "POST", delete: "DELETE"]

    @Secured(["ROLE_USER","ROLE_ADMIN"])
    def postSearch(String txt,Long resId){
        Resource resource = Resource.get(resId)
        Topic topic = resource.topic
        List<Resource> resourceList = Resource.findAllByTopic(topic)
        List<Resource> myResourceList= resourceList.findAll {it->
            it.description.contains(txt)
        }
        render(template: '/topic/post',model: [resourceList: myResourceList])
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Subscription.list(params), model: [subscriptionInstanceCount: Subscription.count()]
    }

    @Secured(["ROLE_USER","ROLE_ADMIN"])
    def show() {
        params.max = Math.min(params.max ? params.int('max') : 5, 100)
        UserDetail userDetail = UserDetail.load(springSecurityService.principal.id)
        List<Topic> topicInstanceList = Subscription.findAllByUserDetail(userDetail, params).topic as List<Topic>
        int topicInstanceTotal = Subscription.countByUserDetail(userDetail)
        render(view: 'show', model: [topicInstanceList: topicInstanceList, topicInstanceTotal: topicInstanceTotal])
    }
    @Secured(["ROLE_USER","ROLE_ADMIN"])
    def remotePagination() {
        params.max = Math.min(params.max ? params.int('max') : 5, 100)
        UserDetail userDetail = UserDetail.load(springSecurityService.principal.id)
        List<Topic> topicInstanceList = Subscription.findAllByUserDetail(userDetail, params).topic as List<Topic>
        int topicInstanceTotal = Subscription.countByUserDetail(userDetail)
        render(template: 'subscriptionList', model: [topicInstanceList: topicInstanceList, topicInstanceTotal: topicInstanceTotal])
    }

    @Secured(["ROLE_USER","ROLE_ADMIN"])
    def postDetails(Long id) {
        Topic topic = Topic.load(id)
        List<Resource> resourceList = Resource.findAllByTopic(topic)
        println resourceList.size()
        render(template: '/topic/post', model: [resourceList: resourceList])

    }

    @Secured(["ROLE_USER","ROLE_ADMIN"])
    @Transactional
    def update(Subscription subscriptionInstance) {

        if (subscriptionInstance.hasErrors()) {
            flash.put("error-msg", subscriptionInstance)
        } else if (subscriptionInstance.save(flush: true)) {
            flash.message = "successfully updated!"
        } else {
            flash.put("error-msg", subscriptionInstance)
        }
        redirect(controller: "userDetail", action: 'dashboard')
    }

}
