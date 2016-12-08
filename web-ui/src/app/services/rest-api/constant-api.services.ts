import { Injectable } from "@angular/core";
import { Http } from "@angular/http";
import "rxjs/add/operator/toPromise";
import { BaseUrlService } from "./base-url.services";
import { AuthenticationService } from "../authentication.services";
import { Version } from "../../models/crom.models";
import { RepoApiService } from "./repo-api.services";
import { handle, handleError } from "typings/dist/support/cli";

@Injectable()
export class ConstantApiService {
    constructor(private http: Http, private baseUrlService: BaseUrlService, private authService: AuthenticationService) {
    }

    findAllVersionBumpers(): Promise<BumperList> {
        return this.http.get(this.bumperApi())
            .toPromise()
            .then(response => response.json() as BumperList)
            .catch(ConstantApiService.handleError)
    }

    private bumperApi(): string {
        return this.baseUrlService.createFullUrl("/api/v1/constant/bumpers")
    }

    private static handleError(error: any): Promise<any> {
        console.error("An error occurred", error);
        return Promise.reject(error.message || error);
    }
}
export class Bumper {
    name: String;
    description: String;
}

export class BumperList {
    bumpers: Bumper[] = [];
}