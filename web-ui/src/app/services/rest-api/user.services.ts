import { Injectable } from "@angular/core";
import { Http } from "@angular/http";
import { BaseUrlService } from "./base-url.services";
import { AuthenticationService } from "../authentication.services";
import "rxjs/add/operator/toPromise";
import { UserProfile } from "../../models/crom.models";

@Injectable()
export class UserService {
    constructor(private http: Http, private baseUrl: BaseUrlService, private authService: AuthenticationService) {
    }

    getUserWatches(): Promise<string[]> {
        if(this.authService.isLoggedIn()) {
            let headers = this.authService.createAuthHeaders();
            return this.http.get(this.baseUrl.createFullUrl("/api/v1/user/watches"), { headers: headers })
                .toPromise()
                .then(request => (request.json().watches as Watch[]).map(watch => watch.projectName))
                .catch(UserService.handleError);
        } else {
            Promise.resolve([])
        }
    }

    getProfile(): Promise<UserProfile> {
        if(this.authService.isLoggedIn()) {
            let headers = this.authService.createAuthHeaders();
            return this.http.get(this.getProfileUrl(), { headers: headers })
                .toPromise()
                .then(request => request.json() as UserProfile)
                .catch(UserService.handleError);
        } else {
            var userProfile = new UserProfile();
            userProfile.userName = "Unknown";
            userProfile.displayName = "Unknown";
            userProfile.email = "";
            return Promise.resolve(userProfile)
        }
    }

    private getProfileUrl(): string {
        return this.baseUrl.createFullUrl("/api/v1/user/profile");
    }

    private static handleError(error: any): Promise<any> {
        console.error("An error occurred", error);
        return Promise.reject(error.message || error);
    }
}

class Watch {
    projectName: string;
}