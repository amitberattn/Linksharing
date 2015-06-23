package com.linksharing


import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class SubscriptionController {

    static allowedMethods = [save: "POST", update: "POST", delete: "DELETE"]



    def postSearch(String txt,Long resId){
        println("text= "+txt)
        println("-------------------------rid="+resId)
        Resource resource = Resource.get(resId)
        Topic topic = resource.topic
        List<Resource> resourceList = Resource.findAllByTopic(topic)
        List<Resource> myResourceList= resourceList.findAll {it->
            it.description.contains(txt)
        }
        println("myResourceList size="+myResourceList.size())
        render(template: '/topic/post',model: [resourceList: myResourceList])
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Subscription.list(params), model: [subscriptionInstanceCount: Subscription.count()]
    }

    def show() {
        params.max = Math.min(params.max ? params.int('max') : 5, 100)
        UserDetail userDetail = UserDetail.load(session.user?.id)
        List<Topic> topicInstanceList = Subscription.findAllByUserDetail(userDetail, params).topic as List<Topic>
        int topicInstanceTotal = Subscription.countByUserDetail(userDetail)
        render(view: 'show', model: [topicInstanceList: topicInstanceList, topicInstanceTotal: topicInstanceTotal])
    }

    def remotePagination() {
        params.max = Math.min(params.max ? params.int('max') : 5, 100)
        UserDetail userDetail = UserDetail.load(session.user.id)
        List<Topic> topicInstanceList = Subscription.findAllByUserDetail(userDetail, params).topic as List<Topic>
        int topicInstanceTotal = Subscription.countByUserDetail(userDetail)
        render(template: 'subscriptionList', model: [topicInstanceList: topicInstanceList, topicInstanceTotal: topicInstanceTotal])
    }

    def postDetails(Long id) {
        Topic topic = Topic.load(id)
        List<Resource> resourceList = Resource.findAllByTopic(topic)
        println resourceList.size()
        render(template: '/topic/post', model: [resourceList: resourceList])

    }

    def create() {
        respond new Subscription(params)
    }

    @Transactional
    def save(Subscription subscriptionInstance) {
        if (subscriptionInstance == null) {
            notFound()
            return
        }

        if (subscriptionInstance.hasErrors()) {
            respond subscriptionInstance.errors, view: 'create'
            return
        }

        subscriptionInstance.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'subscription.label', default: 'Subscription'), subscriptionInstance.id])
                redirect subscriptionInstance
            }
            '*' { respond subscriptionInstance, [status: CREATED] }
        }
    }

    def edit(Subscription subscriptionInstance) {
        respond subscriptionInstance
    }

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

    @Transactional
    def delete(Subscription subscriptionInstance) {

        if (subscriptionInstance == null) {
            notFound()
            return
        }

        subscriptionInstance.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Subscription.label', default: 'Subscription'), subscriptionInstance.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'subscription.label', default: 'Subscription'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}
