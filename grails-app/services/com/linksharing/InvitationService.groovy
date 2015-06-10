package com.linksharing

import grails.transaction.Transactional

@Transactional
class InvitationService {
    def mailService

    boolean sendInvitationEmail(params,g){
        String email = params.email
        List<String> emailList =  email.tokenize(',')
        mailService.sendMail{
            to emailList.toArray()
            subject "Invitation for ${params.topic}"
            html g.render(template:"mailBody")
        }
        return true
    }
}
