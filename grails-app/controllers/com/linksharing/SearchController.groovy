package com.linksharing

class SearchController {
def searchService
    def index() {}
    def search(){
     searchService.searchResource(params,session)
    }
}
