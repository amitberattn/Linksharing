package com.linksharing

import grails.converters.JSON

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Transactional(readOnly = true)
class ResourceRatingController {
    def springSecurityService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

   def hasRating(Long id){
       int score = 0
       UserDetail userDetail = UserDetail.load(session.user.id)
       Resource resource = Resource.load(id)
       ResourceRating resourceRating = ResourceRating.findByUserDetailAndResource(userDetail,resource)
       if(resourceRating){
          score = resourceRating.score
       }
       render([score:score] as JSON)
   }

    @Secured(["ROLE_USER","ROLE_ADMIN"])
    @Transactional
    def rating(Long id,int rate){
        UserDetail userDetail = UserDetail.load(springSecurityService.principal.id)
        Resource resource = Resource.load(id)
        ResourceRating resourceRating = new ResourceRating()
        resourceRating.score=rate
        resourceRating.resource = resource
        resourceRating.userDetail = userDetail
        resourceRating.save(flush: true)
        render([flag:true] as JSON)
    }

}
