import { Injectable } from "@angular/core";
import { Http } from "@angular/http";
import "rxjs/add/operator/toPromise";
import { BaseUrlService } from "./base-url.services";
import { AuthenticationService } from "../authentication.services";
import { Version } from "../../models/crom.models";
import { RepoApiService } from "./repo-api.services";
import { VersionApiService } from "./version-api.services";

@Injectable()
export class MetaDataApiService {
    constructor(private http: Http, private baseUrlService: BaseUrlService, private authService: AuthenticationService) {
    }

    getFile(projectName: string, repoName: string, version: string, fileName: string): string {
        return this.allMetaDataUrl(projectName, repoName, version) + "/" + fileName;
    }

    findAllFiles(projectName: string, repoName: string, version: string): Promise<string[]> {
        return this.http.get(this.allMetaDataUrl(projectName, repoName, version), { headers: this.authService.createAuthHeaders() })
            .toPromise()
            .then( response => response.json().files as string[])
            .catch(MetaDataApiService.handleError);
    }

    private allMetaDataUrl(projectName: string, repoName: string, version: string): string {
        return MetaDataApiService.baseMetaDataUrl(this.baseUrlService, projectName, repoName, version);
    }

    static baseMetaDataUrl(baseUrlService: BaseUrlService, projectName: string, repoName: string, version: string): string {
        return VersionApiService.specificVersionUrl(baseUrlService, projectName, repoName, version) + "/metadata";
    }

    private static handleError(error: any): Promise<any> {
        console.error("An error occurred", error);
        return Promise.reject(error.message || error);
    }
}