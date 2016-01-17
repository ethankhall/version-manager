package io.ehdev.conrad.security.database.model

class UserModelGenerator {

    public static UserModel createUserModel() {
        def model = new UserModel()
        model.id = UUID.randomUUID()
        model.emailAddress = 'email@domain.com'
        model.firstName = 'bob'
        return model
    }
}
