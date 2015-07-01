package com.linksharing

import org.springframework.validation.Errors

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Transactional(readOnly = true)
class TopicController {
def topicService
    def springSecurityService
    static allowedMethods = [save: "POST", update: "PUT"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Topic.list(params), model:[topicInstanceCount: Topic.count()]
    }

    @Secured(['permitAll'])
    def show(Topic topicInstance) {
        List<Resource> resourceList = Resource.findAllByTopic(topicInstance)
        List<Subscription> subscriptionList = Subscription.findAllByTopic(topicInstance)
        [resourceList : resourceList, subscriptionList:subscriptionList, topicInstance :topicInstance]

    }

    @Secured(["ROLE_USER","ROLE_ADMIN"])
    @Transactional
    def save(Topic topicInstance) {
        withForm {
            topicService.saveTopic(topicInstance,flash)
        }
        redirect(controller: "userDetail", action: 'dashboard')

    }

    @Secured(["ROLE_USER","ROLE_ADMIN"])
    @Transactional
    def delete(Topic topicInstance) {

        if (topicInstance == null) {
            notFound()
            return
        }

        topicInstance.delete(flush: true)
        flash.message = "Deleted successfully"
        redirect(controller: "userDetail",action: "dashboard")

    }
}
