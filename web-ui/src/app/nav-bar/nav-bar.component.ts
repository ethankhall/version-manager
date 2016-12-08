import { Component, OnInit } from "@angular/core";
import { Router, ActivatedRoute } from "@angular/router";
import { AuthenticationService } from "../services/authentication.services";
import { BaseUrlService } from "../services/rest-api/base-url.services";
import { UserService } from "../services/rest-api/user.services";

@Component({
    selector: "nav-bar",
    templateUrl: "nav-bar.component.html"
})
export class NavBarComponent implements OnInit {

    loggedIn: boolean = false;
    userName: string;

    constructor(private route: ActivatedRoute, private router: Router,
                private authService: AuthenticationService, private baseUrl: BaseUrlService,
                private userService: UserService) {
    }

    getGoogleLoginUrl(): string {
        return this.baseUrl.createFullUrl("/auth/google");
    }

    profile(): void {
        this.router.navigate(["/user"])
    }

    logout(): void {
        this.authService.logout();
        this.loggedIn = false;
    }

    ngOnInit(): void {
        this.loggedIn = this.authService.isLoggedIn();
        if(this.loggedIn) {
            this.userService.getProfile().then(user => this.userName = user.userName);
        } else {
            this.userName = "Unknown";
        }
    }
}