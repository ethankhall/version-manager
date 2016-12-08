import { Injectable } from "@angular/core";
import { Http } from "@angular/http";
import "rxjs/add/operator/toPromise";
import { BaseUrlService } from "./base-url.services";
import { AuthenticationService } from "../authentication.services";
import { Version } from "../../models/crom.models";
import { RepoApiService } from "./repo-api.services";

@Injectable()
export class VersionApiService {
    constructor(private http: Http, private baseUrlService: BaseUrlService, private authService: AuthenticationService) {
    }

    findAllVersions(projectName: string, repoName: string): Promise<AllVersions> {
        return this.http.get(this.versionsUrl(projectName, repoName), { headers: this.authService.createAuthHeaders() })
            .toPromise()
            .then(response => {
                let json = response.json();
                let commits = json.commits as Version[];
                let latest = json.latest as Version;
                return new AllVersions(commits, latest);
            })
            .catch(VersionApiService.handleError);
    }

    findVersion(projectName: string, repoName: string, versionId: string): Promise<Version> {
        return this.http.get(this.versionUrl(projectName, repoName, versionId), { headers: this.authService.createAuthHeaders() })
            .toPromise()
            .then(response => response.json() as Version)
            .catch(VersionApiService.handleError);
    }

    private versionsUrl(projectName: string, repoName: string): string {
        return VersionApiService.versionBaseUrl(this.baseUrlService, projectName, repoName) + "s";
    }

    private versionUrl(projectName: string, repoName: string, versionId: string): string {
        return VersionApiService.specificVersionUrl(this.baseUrlService, projectName, repoName, versionId)
    }

    static specificVersionUrl(baseUrlService: BaseUrlService, projectName: string, repoName: string, versionId: string): string {
        return VersionApiService.versionBaseUrl(baseUrlService, projectName, repoName) + "/" + versionId;
    }

    static versionBaseUrl(baseUrlService: BaseUrlService, projectName: string, repoName: string): string {
        return RepoApiService.repoBaseUrl(baseUrlService, projectName, repoName) + "/version";
    }

    private static handleError(error: any): Promise<any> {
        console.error("An error occurred", error);
        return Promise.reject(error.message || error);
    }
}

export class AllVersions {
    versions: Version[];
    latest: Version;
    constructor(versions: Version[], latest: Version) {
        this.versions = versions;
        this.latest = latest;
    }
}
