package com.linksharing

import grails.transaction.Transactional

@Transactional
class SearchService {

    def serviceMethod() {

    }
    def searchResource(params,session){
        String searchText = params.search
        println(searchText)
        UserDetail userDetail = UserDetail.load(session.user.id)
        List<Subscription> subscriptionList = Subscription.findAllByUserDetail(userDetail)
        List<Resource> resourceList = (subscriptionList.topic.resource.flatten() as List<Resource>).findAll{it->
            it.description.contains(searchText)
        }
        resourceList.each {it->
            println(it.description)
        }

        List<Topic> topicList = Topic.findAllByVisibility(Visibility.Public)
        topicList.sort { a, b ->
            a.resource.size() == b.resource.size() ? 0 : a.resource.size() > b.resource.size() ? -1 : 1
        }
        if(topicList.size()>5){
            topicList = topicList[0..4]
        }
        int totalTopic = Topic.count()
       return  [resourceList:resourceList,topicList:topicList]
    }
}
