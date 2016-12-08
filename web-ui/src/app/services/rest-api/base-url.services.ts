import { Injectable } from "@angular/core";


@Injectable()
export class BaseUrlService {
    private storageName = "crom-service";

    private baseUrl: string = "http://localhost:8080";

    constructor() {
        this.updateBaseUrl();
    }

    private updateBaseUrl() {
        if (this.isForced()) {
            console.log("Your kernel has been tainted, good luck.");
            this.baseUrl = localStorage.getItem(this.storageName);
        } else if (window.location.hostname == "beta-www.crom.tech") {
            this.baseUrl = "http://beta-api.crom.tech";
        } else if (window.location.hostname == "www.crom.tech" || window.location.hostname == "crom.tech") {
            this.baseUrl = "http://api.crom.tech";
        } else if (!(window.location.hostname === "localhost" || window.location.hostname === "127.0.0.1")) {
            this.baseUrl = "http://api.crom.tech";
        }
    }

    getBaseUrl(): string {
        return this.baseUrl;
    }

    isForced(): boolean {
        let text = localStorage.getItem(this.storageName);
        return !(text === null || typeof text === "undefined" || text === "undefined");
    }

    createFullUrl(path: String): string {
        return this.baseUrl + path;
    }

    reset(): void {
        localStorage.removeItem(this.storageName);
        this.updateBaseUrl();
    }

    forceUrl(url: string): void {
        localStorage.setItem(this.storageName, url);
        this.baseUrl = url;
    }
}