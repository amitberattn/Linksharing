package com.linksharing

import grails.converters.JSON

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class ReadingItemController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond ReadingItem.list(params), model: [readingItemInstanceCount: ReadingItem.count()]
    }

    def show(ReadingItem readingItemInstance) {
        respond readingItemInstance
    }

    def create() {
        respond new ReadingItem(params)
    }

    @Transactional
    def markAsRead(Long id) {
        println("id:::::${id}")
        Resource resource = Resource.get(id)
        println("resource:::::${resource}")
        UserDetail userDetail = UserDetail.load(session.user.id)
        ReadingItem readingItem = ReadingItem.findByUserDetailAndResource(userDetail, resource)
        boolean flag = true

        if (!readingItem) {
            readingItem = new ReadingItem()
            readingItem.isRead = true
            userDetail.addToReadingItem(readingItem)
            resource.addToReadingItem(readingItem)
            readingItem.save(flush: true, failOnError: true)
            flag = true
        } else {
            if (readingItem.isRead) {
                readingItem.isRead = false
                flag = false
            }
            else {
                readingItem.isRead = true
                flag = true
            }

        }
        render([isreadItem: flag] as JSON)
    }

    @Transactional
    def save(ReadingItem readingItemInstance) {
        if (readingItemInstance == null) {
            notFound()
            return
        }

        if (readingItemInstance.hasErrors()) {
            respond readingItemInstance.errors, view: 'create'
            return
        }

        readingItemInstance.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'readingItem.label', default: 'ReadingItem'), readingItemInstance.id])
                redirect readingItemInstance
            }
            '*' { respond readingItemInstance, [status: CREATED] }
        }
    }

    def edit(ReadingItem readingItemInstance) {
        respond readingItemInstance
    }

    @Transactional
    def update(ReadingItem readingItemInstance) {
        if (readingItemInstance == null) {
            notFound()
            return
        }

        if (readingItemInstance.hasErrors()) {
            respond readingItemInstance.errors, view: 'edit'
            return
        }

        readingItemInstance.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'ReadingItem.label', default: 'ReadingItem'), readingItemInstance.id])
                redirect readingItemInstance
            }
            '*' { respond readingItemInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(ReadingItem readingItemInstance) {

        if (readingItemInstance == null) {
            notFound()
            return
        }

        readingItemInstance.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'ReadingItem.label', default: 'ReadingItem'), readingItemInstance.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'readingItem.label', default: 'ReadingItem'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}
