import { Injectable } from "@angular/core";
import { Http, URLSearchParams } from "@angular/http";
import "rxjs/add/operator/toPromise";

@Injectable()
export class CromProjectService {
    constructor(private http: Http) {
    }

    getProjects(page: number): Promise<ProjectDetails[]> {
        let params: URLSearchParams = new URLSearchParams();
        params.set("page", page.toString());

        return this.http.get("http://localhost:8080/api/v1/projects", {search: params})
            .toPromise()
            .then(response => response.json().projectDetails as ProjectDetails[])
            .catch(CromProjectService.handleError);
    }

    private static handleError(error: any): Promise<any> {
        console.error('An error occurred', error); // for demo purposes only
        return Promise.reject(error.message || error);
    }
}

export class ProjectDetails {
    name: String;
}