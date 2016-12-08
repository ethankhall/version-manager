import { Component, OnInit } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { UserService } from "../services/rest-api/user.services";
import { UserProfile } from "../models/crom.models";

@Component({
    templateUrl: "user.component.html"
})
export class UserComponent implements OnInit {

    user: UserProfile;

    constructor(private route: ActivatedRoute, private router: Router, private userService: UserService) {
    }


    ngOnInit(): void {
        this.userService.getProfile().then(user => this.user = user);
    }
}
