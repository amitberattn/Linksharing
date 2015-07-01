package com.linksharing

import grails.plugin.springsecurity.annotation.Secured
class SearchController {
    def searchService
    def springSecurityService
    def index() {}

    @Secured(["ROLE_USER","ROLE_ADMIN"])
    def search(){
     searchService.searchResource(params)
    }
}
