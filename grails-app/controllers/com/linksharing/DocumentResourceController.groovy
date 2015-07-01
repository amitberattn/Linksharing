package com.linksharing

import javax.print.Doc

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Transactional(readOnly = true)
class DocumentResourceController {
    def springSecurityService
    def documentResourceService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    @Secured(["ROLE_USER","ROLE_ADMIN"])
    def download(Long id){
        Resource documentResource = Resource.load(id)
        String path = grailsApplication.mainContext.servletContext.getRealPath("images/topic")
        File file = new File("${path}/${documentResource.fileName}")

        if ( documentResource == null) {
            flash.message = "Document not found."
            redirect (action:'list')
        } else {
            response.setContentType("APPLICATION/OCTET-STREAM")
            response.setHeader("Content-Disposition", "Attachment;Filename=\"${documentResource.fileName}\"")
            def fileInputStream = new FileInputStream(file)
            def outputStream = response.getOutputStream()
            byte[] buffer = new byte[4096];
            int len;
            while ((len = fileInputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, len);
            }
            outputStream.flush()
            outputStream.close()
            fileInputStream.close()
        }
       // redirect(controller: 'userDetail',action: 'dashboard')

    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond DocumentResource.list(params), model:[documentResourceInstanceCount: DocumentResource.count()]
    }

    def show(DocumentResource documentResourceInstance) {
        respond documentResourceInstance
    }

    def create() {
        respond new DocumentResource(params)
    }
    @Secured(["ROLE_USER","ROLE_ADMIN"])
    @Transactional
    def save(DocumentResource documentsResourceInstance) {
        UserDetail userDetail = UserDetail.get(springSecurityService.principal.id)
        withForm {
            documentResourceService.saveDocResource(grailsApplication,params,flash,userDetail,request)
        }
        redirect(controller: "userDetail", action: 'dashboard')
    }

    def edit(DocumentResource documentResourceInstance) {
        respond documentResourceInstance
    }


    @Transactional
    def update(DocumentResource documentResourceInstance) {
        if (documentResourceInstance == null) {
            notFound()
            return
        }

        if (documentResourceInstance.hasErrors()) {
            respond documentResourceInstance.errors, view:'edit'
            return
        }

        documentResourceInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'DocumentResource.label', default: 'DocumentResource'), documentResourceInstance.id])
                redirect documentResourceInstance
            }
            '*'{ respond documentResourceInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(DocumentResource documentResourceInstance) {

        if (documentResourceInstance == null) {
            notFound()
            return
        }

        documentResourceInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'DocumentResource.label', default: 'DocumentResource'), documentResourceInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'documentResource.label', default: 'DocumentResource'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
