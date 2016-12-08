import { Component, OnInit } from "@angular/core";
import { FormGroup, FormBuilder, Validators } from "@angular/forms";
import { Router, ActivatedRoute, Params } from "@angular/router";
import { RepoApiService, RepoCreateResponse } from "../services/rest-api/repo-api.services";
import { ConstantApiService, BumperList } from "../services/rest-api/constant-api.services";

@Component({
    templateUrl: "repo-create.component.html"
})
export class RepoCreateComponent implements OnInit {

    projectName: string = "";
    form: FormGroup;
    bumpers: BumperList = new BumperList();

    constructor(private repoService: RepoApiService, private router: Router, fb: FormBuilder,
                private route: ActivatedRoute, private constantApi: ConstantApiService) {
        this.form = fb.group({
            "repoName": ["", Validators.required],
            "scmUrl": [""],
            "bumper": ["", Validators.required]
        });
    }

    ngOnInit(): void {
        this.route.params.forEach((params: Params) => {
            this.projectName = params["projectName"];
        });

        this.constantApi.findAllVersionBumpers().then(result => {
            console.log(result);
            this.bumpers = result
        });
    }

    onSubmit(): void {
        var form = this.form.value;
        this.repoService.createRepo(this.projectName, form.repoName, form.scmUrl, form.bumper).then(result => {
            if (result == RepoCreateResponse.REJECTED) {
                window.alert("Unable to create project with name " + form.repoName);
            } else {
                this.router.navigate(['/project', this.projectName, form.repoName])
            }
        });
    }
}
