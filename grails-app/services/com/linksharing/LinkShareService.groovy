package com.linksharing

import grails.transaction.Transactional

@Transactional
class LinkShareService {

    def saveLink(LinkShare linkShareInstance, flash, session) {
        UserDetail userDetail = UserDetail.load(session.user.id)
        ReadingItem readingItem = new ReadingItem(isRead:true)
        userDetail.addToReadingItem(readingItem)
        linkShareInstance.addToReadingItem(readingItem)
        if (linkShareInstance.hasErrors()) {
            flash.put("error-msg", linkShareInstance)
            //flash.put("error-msg", readingItem)
        } else if (linkShareInstance.save(flush: true)) {
                flash.message = "Link Resource successfully added!"

        } else {
            flash.put("error-msg", linkShareInstance)
            //flash.put("error-msg", readingItem)
        }
    }
}
