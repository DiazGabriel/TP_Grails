package project

import com.mbds.annonces.Annonce
import com.mbds.annonces.Illustration
import com.mbds.annonces.User

class BootStrap {

    def init = { servletContext ->
        def userInstance = new User(username: "username",
                 password: "password",
                 thumbnail: new Illustration(filename: "thumb.png")).save()
        (1..5).each {
            def annonceInstance = new Annonce(
                        title: "title" + it,
                        description: "description",
                        validTill: new Date(),
                        state: Boolean.TRUE
                )
                .addToIllustration(new Illustration(filename: "filename_1"))
                .addToIllustration(new Illustration(filename: "filename_2"))
                .addToIllustration(new Illustration(filename: "filename_3"))

            userInstance.addToAnnonces(annonceInstance)
        }
        userInstance.save(flush: true, failOnError: true)
    }

    def destroy = {
    }
}
