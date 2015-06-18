package com.linksharing

import grails.converters.JSON

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class ResourceRatingController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond ResourceRating.list(params), model:[resourceRatingInstanceCount: ResourceRating.count()]
    }

    def show(ResourceRating resourceRatingInstance) {
        respond resourceRatingInstance
    }

    def create() {
        respond new ResourceRating(params)
    }

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

    @Transactional
    def rating(Long id,int rate){
        println("Score"+rate)
        println("Rid"+id)

        UserDetail userDetail = UserDetail.load(session.user.id)
        Resource resource = Resource.load(id)
        ResourceRating resourceRating = new ResourceRating()
/*        ResourceRating resourceRating = ResourceRating.findByUserDetailAndResource(userDetail,resource)
        if(!resourceRating){
            resourceRating=new ResourceRating()
        }*/
        resourceRating.score=rate
        resourceRating.resource = resource
        resourceRating.userDetail = userDetail
        resourceRating.save(flush: true)

        render([flag:true] as JSON)

    }


    @Transactional
    def save(ResourceRating resourceRatingInstance) {
        if (resourceRatingInstance == null) {
            notFound()
            return
        }

        if (resourceRatingInstance.hasErrors()) {
            respond resourceRatingInstance.errors, view:'create'
            return
        }

        resourceRatingInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'resourceRating.label', default: 'ResourceRating'), resourceRatingInstance.id])
                redirect resourceRatingInstance
            }
            '*' { respond resourceRatingInstance, [status: CREATED] }
        }
    }

    def edit(ResourceRating resourceRatingInstance) {
        respond resourceRatingInstance
    }

    @Transactional
    def update(ResourceRating resourceRatingInstance) {
        if (resourceRatingInstance == null) {
            notFound()
            return
        }

        if (resourceRatingInstance.hasErrors()) {
            respond resourceRatingInstance.errors, view:'edit'
            return
        }

        resourceRatingInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'ResourceRating.label', default: 'ResourceRating'), resourceRatingInstance.id])
                redirect resourceRatingInstance
            }
            '*'{ respond resourceRatingInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(ResourceRating resourceRatingInstance) {

        if (resourceRatingInstance == null) {
            notFound()
            return
        }

        resourceRatingInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'ResourceRating.label', default: 'ResourceRating'), resourceRatingInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'resourceRating.label', default: 'ResourceRating'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
