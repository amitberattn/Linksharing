package com.linksharing

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional


@Transactional(readOnly = true)
class ReadingItemController {

    def springSecurityService
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    @Secured(["ROLE_USER","ROLE_ADMIN"])
    @Transactional
    def markAsRead(Long id) {
        Resource resource = Resource.get(id)
        UserDetail userDetail = UserDetail.load(springSecurityService.principal.id)
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

}
