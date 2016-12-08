import { Injectable } from "@angular/core";
import { Headers, RequestOptionsArgs } from "@angular/http";
import { CookieService } from "angular2-cookie/services/cookies.service";

@Injectable()
export class AuthenticationService {

    private AUTH_STORAGE = "crom_auth";
    private COOKIE_NAME = "crom_cookie";

    constructor(private cookieService: CookieService) {
    }

    setAuthString(auth: string) {
        localStorage.setItem(this.AUTH_STORAGE, auth);
    }

    resetAuth() {
        localStorage.removeItem(this.AUTH_STORAGE);
    }

    getAuthString(): string {
        let text = localStorage.getItem(this.AUTH_STORAGE);
        if(text === null || text === "") {
          var cookie = this.cookieService.get(this.COOKIE_NAME);
          if(cookie === null || cookie === undefined) {
            return null;
          } else {
            return cookie;
          }
        } else {
          return text;
        }
    }

    isLoggedIn(): boolean {
        let cookie = this.getAuthString();
        var loggedIn = cookie !== null && cookie.length !== 0;
        // console.log("is logged in: " + loggedIn);
        return loggedIn;
    }

    logout(): void {
        this.cookieService.remove(this.COOKIE_NAME);
    }

    addAuthHeaders(headers: Headers): Headers {
        if (!this.isLoggedIn()) {
            return headers;
        }

        headers.set("X-AUTH-TOKEN", this.getAuthString());
        return headers;
    }

    createAuthHeaders(): Headers {
        return this.addAuthHeaders(new Headers());
    }

    createRequestOptionArgs(): RequestOptionsArgs {
        return { headers: this.createAuthHeaders() }
    }
}
