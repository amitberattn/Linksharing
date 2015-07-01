package com.linksharing

import grails.plugin.springsecurity.annotation.Secured
import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class ResourceController {
    def springSecurityService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]


    @Secured(["ROLE_USER","ROLE_ADMIN"])
    @Transactional
    def updateDesc(Long id,String desc){
        Resource resource = Resource.get(id)
        resource.description = desc
        resource.save(flush: true)
        render(true)
    }

    @Secured(["ROLE_USER","ROLE_ADMIN"])
    @Transactional
    def deleteResource(Resource resource){
        resource.delete(flush: true)
        flash.message = "Post deleted"
        redirect(controller: 'subscription',action: 'show')
    }

    @Secured(["ROLE_USER","ROLE_ADMIN"])
    def show(Resource resourceInstance) {
        params.max = Math.min(params.max ? params.int('max') : 5,100)
        List<Topic> topicList = Topic.list(params)
        int totalTopic = Topic.count()
        int score = 0
        UserDetail userDetail = UserDetail.load(springSecurityService.principal.id)
        ResourceRating resourceRating = ResourceRating.findByUserDetailAndResource(userDetail,resourceInstance)
        if(resourceRating){
            score = resourceRating.score
        }
        [resourceInstance: resourceInstance, topicList:topicList,totalTopic:totalTopic,score:score,userDetail:userDetail]
    }

}
