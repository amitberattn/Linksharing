package com.linksharing

import grails.transaction.Transactional

import java.text.SimpleDateFormat

@Transactional
class LoginService {

    def serviceMethod() {

    }

    def registerUser(UserDetailCO userDetailCOInstance, flash, session, grailsApplication, params) {
        UserDetail userDetail = new UserDetail(userDetailCOInstance)
        if (userDetailCOInstance.hasErrors()) {
            flash.put("error-msg", userDetailCOInstance)
            return false

        } else if (userDetail.save(flush: true)) {
            String path = grailsApplication.mainContext.servletContext.getRealPath("images/profile")
            File image = new File("${path}/${userDetail.username}")
            image.bytes = params.photo.bytes
            flash.message = "Hallo ${userDetail.username}"
            session.user = userDetail
            return true
        } else {
            flash.put("error-msg", userDetail)
            return true
        }

    }

    def getPublicTrendingTopics() {
        Calendar calendar = Calendar.getInstance()
        Date d1 = calendar.getTime()
        calendar.add(Calendar.YEAR, -1)
        Date preViousYear = calendar.getTime()
        List<Topic> topicListYear = Topic.findAllByVisibilityAndDateCreatedBetween(Visibility.Public, preViousYear, d1)
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-ddd")
        List<Topic> todayTopicList = topicListYear.findAll {
            simpleDateFormat.format(it.dateCreated).equals(simpleDateFormat.format(d1))
        }
        calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, -1)
        List<Topic> monthTopicList = topicListYear.findAll {
            it.dateCreated <= d1 && it.dateCreated >= calendar.getTime()
        }
        calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, -10)
        List<Topic> tenDaysTopicList = topicListYear.findAll {
            it.dateCreated <= d1 && it.dateCreated >= calendar.getTime()
        }

        return [todayTopicList: todayTopicList, tenDaysTopicList: tenDaysTopicList, monthTopicList: monthTopicList, topicListYear: topicListYear]
    }

}
