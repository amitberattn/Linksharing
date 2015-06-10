package com.linksharing


import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class UserDetailController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def dashboard() {

        List<Subscription> subscriptionList = Subscription.findAllByUserDetail(UserDetail.load(session.user?.id))
        println subscriptionList.topic.asList()
        List<Topic> topicList = Topic.list()
        int postCount = Resource.countByCreatedBy(session.user)
        [my_subscriptions: subscriptionList, postNo: postCount, topicList: topicList]
    }

    @Transactional
    def subscribeTopic(Topic topic) {
        UserDetail userDetail = UserDetail.findById(session.user.id)
        Subscription subscription = new Subscription(seriousness: com.linksharing.Seriousness.Serious, topic: topic, userDetail:userDetail)
        if (subscription.validate()) {
            subscription.save(flush: true, failOnError: true)

        } else {
            flash.message = "Unable to subscribe"
        }
        redirect(action: 'dashboard')
    }

    @Transactional
    def unsubscribeTopic(Topic topic){
        UserDetail userDetail =UserDetail.load(session.user.id)
        Subscription subscription = Subscription.findByUserDetailAndTopic(userDetail,topic)
        userDetail.removeFromSubscription(subscription)
        topic.removeFromSubscription(subscription)
        subscription.delete(flush: true)
        flash.message = 'Unsubscribed sucessfully'
        redirect(action: 'dashboard')
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond UserDetail.list(params), model: [userDetailInstanceCount: UserDetail.count()]
    }

    def show(UserDetail userDetailInstance) {
        respond userDetailInstance
    }

    def create() {
        respond new UserDetail(params)
    }

    @Transactional
    def save(UserDetail userDetailInstance) {
        if (userDetailInstance == null) {
            notFound()
            return
        }

        if (userDetailInstance.hasErrors()) {
            respond userDetailInstance.errors, view: 'create'
            return
        }

        userDetailInstance.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'userDetail.label', default: 'UserDetail'), userDetailInstance.id])
                redirect userDetailInstance
            }
            '*' { respond userDetailInstance, [status: CREATED] }
        }
    }

    def edit(UserDetail userDetailInstance) {
        respond userDetailInstance
    }

    @Transactional
    def update(UserDetail userDetailInstance) {
        if (userDetailInstance == null) {
            notFound()
            return
        }

        if (userDetailInstance.hasErrors()) {
            respond userDetailInstance.errors, view: 'edit'
            return
        }

        userDetailInstance.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'UserDetail.label', default: 'UserDetail'), userDetailInstance.id])
                redirect userDetailInstance
            }
            '*' { respond userDetailInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(UserDetail userDetailInstance) {

        if (userDetailInstance == null) {
            notFound()
            return
        }

        userDetailInstance.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'UserDetail.label', default: 'UserDetail'), userDetailInstance.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'userDetail.label', default: 'UserDetail'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}
