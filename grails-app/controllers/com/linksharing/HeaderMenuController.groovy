package com.linksharing

class HeaderMenuController {

    def headerMenuService

    def index() {}

    def invite(){
        render(headerMenuService.getSubscribedTopic(session.user.id))
    }
}
