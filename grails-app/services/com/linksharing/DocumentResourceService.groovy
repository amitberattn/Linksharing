package com.linksharing

import grails.transaction.Transactional

@Transactional
class DocumentResourceService {

    def serviceMethod() {

    }

    def saveDocResource(grailsApplication,params,flash,userDetail,request){
        def doc = request.getFile('filePath')
        DocumentResource documentResource = new DocumentResource(fileName: doc.originalFilename,description:params.description , topic: params.topic,createdBy: userDetail)
        if (documentResource.hasErrors()) {
            flash.put("error-msg", documentResource)
        }else if(documentResource.save(flush: true)) {
            String path= grailsApplication.mainContext.servletContext.getRealPath("images/topic")
            doc.transferTo(new File("${path}/${doc.originalFilename}"))
            flash.message = "File Resource successfully added!"
        }else {
            flash.put("error-msg", documentResource)
        }
    }
}
