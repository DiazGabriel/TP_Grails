package com.mbds.annonces
import grails.validation.ValidationException
import org.apache.catalina.connector.RequestFacade

import javax.servlet.http.HttpServletRequest

import static org.springframework.http.HttpStatus.*
class AnnonceController {
    AnnonceService annonceService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond annonceService.list(params), model:[annonceCount: annonceService.count()]
    }

    def show(Long id) {
        respond annonceService.get(id)
    }

    def create() {
        respond new Annonce(params).addToIllustration(filename: params.myFile)
    }

    def save() {

        HttpServletRequest req = request
        // Upload de l'image
        def file = req.getFile("myFile")

        // Construction d'un nom unique pour l'image
        int i = 1
        def fileName = params.title + "_image_" + i + ".png"
        File image = new File(grailsApplication.config.maconfig.assets_path + fileName)
        if (image.exists()) {
            while (image.exists()) {
                i += 1
                fileName = params.title + "_image_" + i + ".png"
                image = new File(grailsApplication.config.maconfig.assets_path + fileName)
            }
        }

        // On charge l'image dans le dossier asserts/image
        file.transferTo(image)

        def annonce = new Annonce(params).addToIllustration(filename: fileName)

        try {
            annonceService.save(annonce)
        } catch (ValidationException e) {
            respond annonce.errors, view:'create'
            return
        }
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'annonce.label', default: 'Annonce'), annonce.id])
                redirect annonce
            }
            '*' { respond annonce, [status: CREATED] }
        }
    }
    def edit(Long id) {
        respond annonceService.get(id)
    }
    def update(Annonce annonce) {
        if (annonce == null) {
            notFound()
            return
        }
        try {
            annonceService.save(annonce)
        } catch (ValidationException e) {
            respond annonce.errors, view:'edit'
            return
        }
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'annonce.label', default: 'Annonce'), annonce.id])
                redirect annonce
            }
            '*'{ respond annonce, [status: OK] }
        }
    }
    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }
        annonceService.delete(id)
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'annonce.label', default: 'Annonce'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }
    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'annonce.label', default: 'Annonce'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
