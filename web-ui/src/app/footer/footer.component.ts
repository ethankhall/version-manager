import { Component, OnInit } from "@angular/core";
import { Router, ActivatedRoute } from "@angular/router";
import { AuthenticationService } from "../services/authentication.services";
import { BaseUrlService } from "../services/rest-api/base-url.services";
import { UserService } from "../services/rest-api/user.services";

@Component({
    selector: "footer",
    templateUrl: "footer.component.html"
})
export class FooterComponent {

}