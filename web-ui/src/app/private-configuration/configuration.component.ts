import { Component, OnInit } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { UserService } from "../services/rest-api/user.services";
import { BaseUrlService } from "../services/rest-api/base-url.services";
import { AuthenticationService } from "../services/authentication.services";
import { UserProfile } from "../../models/crom.models";

@Component({
    templateUrl: "configuration.component.html"
})
export class ConfigurationComponent implements OnInit {

    config: Config;
    isForced: boolean;

    constructor(private route: ActivatedRoute, private router: Router,
                private baseUrlService: BaseUrlService, protected authService: AuthenticationService) {
    }

    onSubmit(): void {
        this.baseUrlService.forceUrl(this.config.api);
        this.authService.setAuthString(this.config.auth);
        window.alert("Saves settings");
    }

    reset(): void {
        this.baseUrlService.reset();
        this.authService.resetAuth();
        window.alert("Settings Reset");
    }

    ngOnInit(): void {
        this.config = new Config(this.baseUrlService.getBaseUrl(), this.authService.getAuthString());
        this.isForced = this.baseUrlService.isForced();
    }
}

export class Config {

    api: string;
    auth: string;

    constructor(api: string, auth: string) {
        this.api = api;
        this.auth = auth;
    }

}
