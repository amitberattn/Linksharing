package com.linksharing
import grails.plugin.springsecurity.annotation.Secured

class InvitationController {

    def invitationService
@Secured(["ROLE_USER","ROLE_ADMIN"])
    def sendInvitation(){
        if(invitationService.sendInvitationEmail(params,g))
            flash.message = "Invitation successfully send!"
        else
            flash.message = "Invitation sending fail!"
        redirect(controller: "userDetail", action: 'dashboard')
    }
}
