package com.linksharing

import grails.transaction.Transactional


@Transactional
class LinkShareService {

    def springSecurityService

    def saveLink(parmas,flash) {
        UserDetail userDetail = UserDetail.load(springSecurityService.principal.id)

        Resource linkShareInstance = new LinkShare(url: parmas.url,description: parmas.description,topic:parmas.topic,createdBy: userDetail)
        ReadingItem readingItem = new ReadingItem(isRead:true,userDetail: userDetail,resource: linkShareInstance)
        //userDetail.addToReadingItem(readingItem)
        //userDetail.addToResource(linkShareInstance)
        //linkShareInstance.addToReadingItem(readingItem)
        if (linkShareInstance.hasErrors() && readingItem.hasErrors()) {
            flash.put("error-msg", linkShareInstance)
            //flash.put("error-msg", readingItem)
        } else if (linkShareInstance.save(flush: true)) {
               if(readingItem.save(flush: true))
                flash.message = "Link Resource successfully added!"
               else
                   flash.put("error-msg", readingItem)

        } else {
            flash.put("error-msg", linkShareInstance)
            //flash.put("error-msg", readingItem)
        }
    }
}
