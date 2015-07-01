package com.linksharing
import grails.plugin.springsecurity.annotation.Secured
class HeaderMenuController {

    def headerMenuService
    def springSecurityService

    def index() {}
   @Secured(['ROLE_USER','ADMIN_ROLE'])
    def invite(){
        render(headerMenuService.getSubscribedTopic(springSecurityService.principal.id))
    }
}
