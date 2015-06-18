package com.linksharing



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class ResourceController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Resource.list(params), model:[resourceInstanceCount: Resource.count()]
    }

    def show(Resource resourceInstance) {
        params.max = Math.min(params.max ? params.int('max') : 5,100)
        List<Topic> topicList = Topic.list(params)
        int totalTopic = Topic.count()
        int score = 0
        UserDetail userDetail = UserDetail.load(session.user.id)
        ResourceRating resourceRating = ResourceRating.findByUserDetailAndResource(userDetail,resourceInstance)
        if(resourceRating){
            score = resourceRating.score
        }
        [resourceInstance: resourceInstance, topicList:topicList,totalTopic:totalTopic,score:score]
    }


    def create() {
        respond new Resource(params)
    }

    @Transactional
    def save(Resource resourceInstance) {
        if (resourceInstance == null) {
            notFound()
            return
        }

        if (resourceInstance.hasErrors()) {
            respond resourceInstance.errors, view:'create'
            return
        }

        resourceInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'resource.label', default: 'Resource'), resourceInstance.id])
                redirect resourceInstance
            }
            '*' { respond resourceInstance, [status: CREATED] }
        }
    }

    def edit(Resource resourceInstance) {
        respond resourceInstance
    }

    @Transactional
    def update(Resource resourceInstance) {
        if (resourceInstance == null) {
            notFound()
            return
        }

        if (resourceInstance.hasErrors()) {
            respond resourceInstance.errors, view:'edit'
            return
        }

        resourceInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Resource.label', default: 'Resource'), resourceInstance.id])
                redirect resourceInstance
            }
            '*'{ respond resourceInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Resource resourceInstance) {

        if (resourceInstance == null) {
            notFound()
            return
        }

        resourceInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Resource.label', default: 'Resource'), resourceInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'resource.label', default: 'Resource'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
