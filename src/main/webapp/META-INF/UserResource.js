"use strict";

/**
 * Von der Klasse SongResource des Servers abgeleitete Klasse, die im Prinzip
 * dieselben Methoden besitzt. Hier rufen wir jedoch den REST-Webservice des
 * Servers auf, anstelle direkt auf eine Datenbank zuzugreifen.
 */
class UserResource {

    /**
     * Konstruktor.
     * @param {String} url Basis-URL des REST-Webservices (optional)
     */
    constructor(url) {
        this.url = url || "https://localhost:8443/jTodo/webresources/api/user/";
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

    /**
     * Songs suchen.
     * @param {String} query Suchparameter (optional)
     * @returns {Promise} Gefundene Songs
     */
    
    async findUser(query) {
        let url = this.url;

        if (query !== undefined) {
            url += "?query=" + encodeURI(query);
        }

        let response = await fetch(url, {
            headers: {
                "accept": "application/json"
            }
        });

        return await response.json();
    }
    
    /**
     * Einzelnen Song auslesen.
     * @returns {Promise} Gefundener Song
     */
    async getUser() {
        let response = await fetch(this.url,{
            headers: {
                "accept": "application/json"
            }
        });

        return await response.json();
    }

    /**
     * Aktualisieren eines Songs.
     * @param {Object} song Zu speichernder Song
     * @returns {Promise} Gespeicherter Song
     */
    async updateSong(song) {
        let response = await fetch(this.url + song.id + "/", {
            method: "POST",
            headers: {
                "accept": "application/json",
                "content-type": "application/json",
                "authorization": "Basic " + btoa(this.username + ":" + this.password)
            },
            body: JSON.stringify(song)
        });

        return await response.json();
    }
}
