import com.mbds.annonces.Illustration
import com.mbds.annonces.User
import com.mbds.annonces.UserService

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class UserController {

    UserService userService

    static allowedMethods = [save: "POST", update: "POST", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond userService.list(params), model:[userCount: userService.count()]

    }

    def show(Long id) {
        respond userService.get(id)
    }

    def create() {
        respond new User(params)
    }

    def save(User user) {
        userBD(user, "SAVE")
    }

    def edit(Long id) {
        respond userService.get(id)
    }

    def update(User user) {
        userBD(user, "UPDATE")
    }

    def delete(Long id) {

        println("************* DELETE **************")

        if (id == null) {
            notFound()
            return
        }

        userService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'user.label', default: 'User'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

    def userBD(User user, String action) {
        // Les méthodes save et update effectue un traitement similaire.
        // Pour éviter la redondance de code, on appel cette méthode
        // Upload de l'image
        def file = request.getFile("myFile")
        def fileName

        if (file != null) {
            // Construction d'un nom unique pour l'image
            // Nom = username + _image_ + un numéro de réferencement unique
            int i = 1
            fileName = params.username + "_image_" + i + ".png"
            File image = new File(grailsApplication.config.maconfig.assets_path + fileName)
            if (image.exists()) {
                while (image.exists()) {
                    i += 1
                    fileName = params.username + "_image_" + i + ".png"
                    image = new File(grailsApplication.config.maconfig.assets_path + fileName)
                }
            }

            // On charge l'image dans le dossier asserts/image
            file.transferTo(image)
        } else {
            fileName = null
        }

        // Deux options :
        // On est en mode création : le user données en entrée est redéfini avec les paramètres du formulaire puis créé dans la base
        // On est en mode modif : le user est mis à jour (avec ajout de l'image si il y en a une)
        if (action == "SAVE") {
            user = new User(username: params.username, password: params.password, thumbnail: new Illustration(filename: fileName))
            try {
                userService.save(user)
            } catch (ValidationException e) {
                respond user.errors, view: 'create'
                return
            }
        } else {
            try {
                user.properties = params
                user.thumbnail = new Illustration(filename: fileName)
                userService.save(user)
            } catch (ValidationException e) {
                respond user.errors, view: 'create'
                return
            }
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'user.label', default: 'User'), user.id])
                redirect user
            }
            '*' { respond user, [status: CREATED] }
        }
    }
}
