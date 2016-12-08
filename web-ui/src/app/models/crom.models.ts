export class ProjectDetails {
    name: String;
    repos: RepoName[];
    permissions: Permission[];
}

export class RepoName {
    name: String;
}

export class RepoDetails {
    projectName: string;
    repoName: string;
    url: string;
    permissions: Permission[];
}

export class Permission {
    permission: string;
}

export class Version {
    commitId: string;
    version: string;
    createdAt: string;
    versionParts: string[];
}


export class UserProfile {
    userName: string;
    email: string;
    displayName: string;
}