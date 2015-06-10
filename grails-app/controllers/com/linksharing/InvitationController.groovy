package com.linksharing

class InvitationController {

    def invitationService

    def sendInvitation(){
        if(invitationService.sendInvitationEmail(params,g))
            flash.message = "Invitation successfully send!"
        else
            flash.message = "Invitation sending fail!"
        redirect(controller: "userDetail", action: 'dashboard')
    }
}
