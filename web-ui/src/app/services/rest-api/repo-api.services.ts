import { Injectable } from "@angular/core";
import { Http } from "@angular/http";
import "rxjs/add/operator/toPromise";
import { BaseUrlService } from "./base-url.services";
import { AuthenticationService } from "../authentication.services";
import { RepoDetails } from "../../models/crom.models";

@Injectable()
export class RepoApiService {
    constructor(private http: Http, private baseUrlService: BaseUrlService, private authService: AuthenticationService) {
    }

    getRepoDetails(projectName: string, repoName: string): Promise<RepoDetails> {
        return this.http.get(this.baseUrl(projectName, repoName), { headers: this.authService.createAuthHeaders()})
            .toPromise()
            .then(response => (response.json() as RepoDetails))
            .catch(RepoApiService.handleError);
    }

    createRepo(projectName: string, repoName: string, scmUrl: string, bumper: string): Promise<RepoCreateResponse> {
        var repoBaseUrl = RepoApiService.repoBaseUrl(this.baseUrlService, projectName, repoName);
        var body = { 'scmUrl': scmUrl, 'bumper': bumper };
        return this.http.post(repoBaseUrl, body, this.authService.createRequestOptionArgs())
            .toPromise()
            .then(response => (RepoCreateResponse.OK))
            .catch(error => {
                    console.error("An error occurred", error); // for demo purposes only
                    return RepoCreateResponse.REJECTED
                }
            );
    }

    private baseUrl(projectName: string, repoName: string): string {
        return RepoApiService.repoBaseUrl(this.baseUrlService, projectName, repoName);
    }

    static repoBaseUrl(baseUrlService: BaseUrlService, projectName: string, repoName: string): string {
        return baseUrlService.createFullUrl(`/api/v1/project/${projectName}/repo/${repoName}`);
    }

    private static handleError(error: any): Promise<any> {
        console.error("An error occurred", error);
        return Promise.reject(error.message || error);

    }
}

export enum RepoCreateResponse {
    OK,
    REJECTED
}