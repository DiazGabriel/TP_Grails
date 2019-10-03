package com.mbds.annonces

import grails.converters.JSON
import grails.converters.XML
import java.text.SimpleDateFormat

class ApiController {

    AnnonceService annonceService
    def pattern = "dd-MM-yyyy"

    def annonce() {
        switch (request.getMethod()) {

            case "GET":
                if (!params.id)
                    return response.status = 400
                def annonceInstance = Annonce.get(params.id)
                if (!annonceInstance)
                    return response.status = 404
                response.withFormat {
                    json { render annonceInstance as JSON }
                    xml { render annonceInstance as XML }
                }
                break


            case "PUT":
                if (!params.id)
                    return response.status = 400
                def annonceInstance = Annonce.get(params.id)
                if (!annonceInstance)
                    return response.status = 404
                if (!params.title || !params.description || !params.dateCreated || !params.validTill || !params.state)
                    return response.status = 404

                annonceInstance.setTitle(params.title)
                annonceInstance.setDescription(params.description)
                annonceInstance.setDateCreated(new SimpleDateFormat(pattern).parse(params.dateCreated))
                annonceInstance.setValidTill(new SimpleDateFormat(pattern).parse(params.validTill))
                annonceInstance.setState(new Boolean(params.state))
                annonceService.save(annonceInstance)
                return response.status = 200

                break


            case "PATCH":
                if (!params.id)
                    return response.status = 400
                def annonceInstance = Annonce.get(params.id)
                if (!annonceInstance)
                    return response.status = 404

                if (params.dateCreated)
                    annonceInstance.setDateCreated(new SimpleDateFormat(pattern).parse(params.dateCreated))
                if (params.validTill)
                    annonceInstance.setValidTill(new SimpleDateFormat(pattern).parse(params.validTill))
                if (params.state)
                    annonceInstance.setState(new Boolean(params.state))
                if (params.description)
                    annonceInstance.setDescription(params.description)
                if (params.title)
                    annonceInstance.setTitle(params.title)
                annonceService.save(annonceInstance)

                return response.status = 200


            case "DELETE":
                if (!params.id)
                    return response.status = 400
                def annonce = Annonce.get(params.id)

                if (!annonce)
                    return response.status = 404
                annonceService.delete(annonce.id)


                return response.status = 200
            default:
                return response.status = 405
                break

        }
        return response.status = 406
    }

    def annonces() {
        switch(request.getMethod()) {
            case "GET" :
                def annonces = Annonce.getAll()
                if (!annonces)
                    return response.status = 404
                response.withFormat {
                    json { render annonces as JSON }
                    xml { render annonces as XML }
                }
                break
            case "POST" :
                if(!params.title || !params.description || !params.validTill)
                    return response.status = 404

                def newAnnonce = new Annonce(
                        title: params.title,
                        description: params.description,
                        validTill: new SimpleDateFormat(pattern).parse(params.validTill),
                        dateCreated: new Date(),
                        state: Boolean.TRUE
                )
                //TODO rajouter userId dans params
                newAnnonce.author = User.get(1)
                annonceService.save(newAnnonce)
                return response.status = 201
                break
            default :
                return response.status = 405
                break
        }
        return response.status = 406
    }


}
