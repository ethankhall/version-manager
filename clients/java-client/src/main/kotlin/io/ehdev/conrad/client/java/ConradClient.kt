package io.ehdev.conrad.client.java

class ConradClient(projectClient: ProjectConradClient, repoClient: RepositoryConradClient):
    ProjectConradClient by projectClient,
    RepositoryConradClient by repoClient {

}
