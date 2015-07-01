package com.linksharing
import grails.plugin.springsecurity.annotation.Secured
class HeaderMenuController {

    def headerMenuService
    def springSecurityService

    def index() {}
   @Secured(['ROLE_USER','ROLE_ADMIN'])
    def invite(){
        render(headerMenuService.getSubscribedTopic(springSecurityService.principal.id))
    }
}
