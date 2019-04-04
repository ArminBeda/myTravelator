"use strict";

/**
 * Von der Klasse CountryResource des Servers abgeleitete Klasse, die im Prinzip
 * dieselben Methoden besitzt. Hier rufen wir jedoch den REST-Webservice des
 * Servers auf, anstelle direkt auf eine Datenbank zuzugreifen.
 */
class CountryResource {

    /**
     * Konstruktor.
     * @param {String} url Basis-URL des REST-Webservices (optional)
     */
    constructor(url) {
        this.url = url || "https://localhost:8443/myTravelator/api/country/";
        this.username = "";
        this.password = "";
    }

    /**
     * Benutzername und Passwort für die Authentifizierung merken.
     * @param {String} username Benutzername
     * @param {String} password Passwort
     */
    setAuthData(username, password) {
        this.username = username;
        this.password = password;
    }
    
    async findCountry(country_query) {

        let url = this.url;
        url = url + "name/" +country_query;

        let response = await fetch(url, {
            headers: {
                "accept": "application/json",
                "authorization": "Basic " + btoa(this.username + ":" + this.password)
            }
        });

        return await response.json();
    }
   
    async getCountryList() {
        let response = await fetch(this.url,{
            headers: {
                "accept": "application/json",
                "authorization": "Basic " + btoa(this.username + ":" + this.password)
            }
        });

        return await response.json();
    }
}
