package com.mbds.annonces
import grails.validation.ValidationException

import javax.servlet.http.HttpServletRequest

import static org.springframework.http.HttpStatus.*
class AnnonceController {

    AnnonceService annonceService

    static allowedMethods = [save: "POST", update: "POST", delete: "DELETE"]
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

    def save(Annonce annonce) {

        annonceBD(annonce, "SAVE")

    }

    def edit(Long id) {
        respond annonceService.get(id)
    }

    def update(Annonce annonce) {

        annonceBD(annonce, "UPDATE")

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

    def annonceBD(Annonce annonce, String action) {

        // Les méthodes save et update effectue un traitement similaire.
        // Pour éviter la redondance de code, on appel cette méthode
        // Upload de l'image
        def file = request.getFile("myFile")
        def fileName

        if (file != null) {
            // Construction d'un nom unique pour l'image
            // Nom = titre de l'anonce + _image_ + un numéro de réferencement unique
            int i = 1
            fileName = params.title + "_image_" + i + ".png"
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
        } else {
            fileName = null
        }

        // Deux options :
        // On est en mode création : l'annonce données en entrée est redéfini avec les paramètres du formulaire puis créé dans la base
        // On est en mode modif : l'annonce est mise à jour (avec ajout de l'image si il y en a une)
        if (action == "SAVE") {
            if (fileName == null) {
                annonce = new Annonce(params)
            } else {
                annonce = new Annonce(params).addToIllustration(filename: fileName)
            }
            try {
                annonceService.save(annonce)
            } catch (ValidationException e) {
                respond annonce.errors, view:'create'
                return
            }
        } else {
            try {
                if (fileName == null) {
                    annonceService.save(annonce)
                } else {
                    annonceService.save(annonce.addToIllustration(filename: fileName))
                }
            } catch (ValidationException e) {
                respond annonce.errors, view:'create'
                return
            }
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'annonce.label', default: 'Annonce'), annonce.id])
                redirect annonce
            }
            '*' { respond annonce, [status: CREATED] }
        }
    }

    def deleteIllustration() {
        def illustrationId = params.illustrationId
        def annonceId = params.annonceId
        def annonceInstance = Annonce.get(annonceId)
        def illustrationInstance = Illustration.get(illustrationId)
        annonceInstance.removeFromIllustration(illustrationInstance)
        annonceInstance.save(flush: true)
        illustrationInstance.delete(flush: true)
    }

}




