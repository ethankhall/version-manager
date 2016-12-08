import { Injectable } from "@angular/core";
import { Http, URLSearchParams } from "@angular/http";
import "rxjs/add/operator/toPromise";
import { BaseUrlService } from "./base-url.services";
import { AuthenticationService } from "../authentication.services";
import { ProjectDetails } from "../../models/crom.models";

@Injectable()
export class ProjectApiService {
    constructor(private http: Http, private baseUrl: BaseUrlService, private authService: AuthenticationService) {
    }

    getProjects(page: number): Promise<string[]> {
        let params: URLSearchParams = new URLSearchParams();
        params.set("page", page.toString());

        return this.http.get(this.baseUrl.createFullUrl("/api/v1/projects"), { search: params })
            .toPromise()
            .then(response => (response.json().projectDetails as ProjectDescription[]).map(detail => detail.name))
            .catch(ProjectApiService.handleError);
    }

    getProjectDetails(name: string): Promise<ProjectDetails> {
        return this.http.get(this.baseUrl.createFullUrl("/api/v1/project/" + name), this.authService.createRequestOptionArgs())
            .toPromise()
            .then(response => {
                var details = response.json() as ProjectDetails;
                if(details.repos === undefined || details.repos == null) {
                    details.repos = [];
                }
                return details;
            })
            .catch(ProjectApiService.handleError);
    }

    createProject(name: string): Promise<ProjectCreateResponse> {
        if (!this.authService.isLoggedIn()) {
            throw Error("User not logged in");
        }

        return this.http
            .post(this.baseUrl.createFullUrl("/api/v1/project/" + name), null, this.authService.createRequestOptionArgs())
            .toPromise()
            .then(resp => ProjectCreateResponse.OK)
            .catch(error => {
                    console.error("An error occurred", error); // for demo purposes only
                    return ProjectCreateResponse.REJECTED
                }
            );
    }

    private static
    handleError(error: any): Promise < any > {
        console.error("An error occurred", error); // for demo purposes only
        return Promise.reject(error.message || error);
    }
}

class ProjectDescription {
    name: String;
}

export enum ProjectCreateResponse {
    OK,
    REJECTED
}