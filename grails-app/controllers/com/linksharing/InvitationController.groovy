package com.linksharing

class InvitationController {
 def mailService
    def index() {}

    def sendInvitation(){
       String email = params.email
        List<String> emailList =  email.tokenize(',')
        mailService.sendMail{
            println(params.email)
            println(params.topic)
            to emailList.toArray()
            subject "Invitation for ${params.topic}"
            html g.render(template:"mailBody")
            flash.message = "Invitation successfully send!"
        }
        redirect(controller: "userDetail", action: 'dashboard')
    }
}
