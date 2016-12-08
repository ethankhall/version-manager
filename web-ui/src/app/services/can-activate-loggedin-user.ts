import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from "@angular/router";
import { Injectable } from "@angular/core";
import { AuthenticationService } from "./authentication.services";


@Injectable()
export class CanActivateLoggedInUser implements CanActivate {

    constructor(private authService: AuthenticationService) {
    }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Promise<boolean> {
        return Promise.resolve(this.authService.isLoggedIn());
    }
}