package io.ehdev.conrad.security.database.model

class UserModelGenerator {

    public static SecurityUserModel createUserModel() {
        def model = new SecurityUserModel()
        model.id = UUID.randomUUID()
        model.emailAddress = 'email@domain.com'
        model.name = 'bob'
        return model
    }
}
