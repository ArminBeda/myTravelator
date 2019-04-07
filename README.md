MyTravelator
=========================

Kurzbeschreibung
----------------

Dies ist ein Beispiel für eine in Java realisierte, serverseitige MVC-Webanwendung.
Die Anwendung setzt dabei ganz klassisch auf der „Jakarta Enterprise Edition”
(ehemals „Java Enterprise Edition“) auf und läuft daher in einem speziell dafür
ausgelegten Applikationsserver. Sämtliche Anwendungslogik wird dabei vom Server
implementiert, so dass für jedes URL-Pattern der Anwendung ein komplett serverseitig
generierte HTML-Seite abgerufen und im Browser dargestellt wird.

Die Webanwendung namens „MyTravelator“ stellt dem User die Möglichkeit zur Verfügung, 
eigene Reisen anzulegen und zu organisieren. Dabei werden diese Reisen in die 
jeweiligen Länder katego-risiert und anschaulich dargestellt.
Durch die Möglichkeit sich zu registrieren ist eine sichere Privatsphäre gewährleistet. 
Der persönli-che Bereich mit den eigenen Reisen, ist somit nur mit den Logindaten 
erreichbar. Der MyTravelator verfügt über die Funktionalität, den aktuellen Status 
der Reisen anzuzeigen und zu gliedern. Diese werden im Dashboard unter den Ländern 
als Kacheln dargelegt. 


Verwendete Technologien
-----------------------

Die App nutzt Maven als Build-Werkzeug und zur Paketverwaltung. Auf diese Weise
werden die für Jakarta EE notwendigen APIs, darüber hinaus aber keine weiteren
Abhängigkeiten, in das Projekt eingebunden. Der Quellcode der Anwendung ist dabei
wie folgt strukturiert:

 * **Servlets** dienen als Controller-Schicht und empfangen sämtliche HTTP-Anfragen.
 * **Enterprise Java Beans** dienen als Model-Schicht und kapseln die fachliche Anwendungslogik.
 * **Persistence Entities** modellieren das Datenmodell und werden für sämtliche Datenbankzugriffe genutzt.
 * **Java Server Pages** sowie verschiedene statische Dateien bilden die View und generieren den
   auf dem Bildschirm angezeigten HTML-Code.

Folgende Entwicklungswerkzeuge kommen dabei zum Einsatz:

 * [NetBeans:](https://netbeans.apache.org/) Integrierte Entwicklungsumgebung für Java und andere Sprachen
 * [Maven:](https://maven.apache.org/) Build-Werkzeug und Verwaltung von Abhängigkeiten
 * [Git:](https://git-scm.com/") Versionsverwaltung zur gemeinsamen Arbeit am Quellcode
 * [TomEE:](https://tomee.apache.org/) Applikationsserver zum lokalen Testen der Anwendung
 * [Derby:](https://db.apache.org/derby/) In Java implementierte SQL-Datenbank zum Testen der Anwendung

Screenshots
-----------

<table style="max-width: 100%;">
    <tr>
        <td>
            <a href="Login.PNG">
                <img src="Login.PNG" style="display: block; width: 100%;" />
            </a>
        </td>
        <td>
            <a href="SignUp.PNG">
                <img src="SignUp.PNG" style="display: block; width: 100%;" />
            </a>
        </td>
    </tr>
    <tr>
        <td>
            Login
        </td>
        <td>
            Sign up
        </td>
    </tr>
</table>

<table style="max-width: 100%;">
    <tr>
        <td>
            <a href="Dashboard.PNG">
                <img src="Dashboard.PNG" style="display: block; width: 100%;" />
            </a>
        </td>
        <td>
            <a href="TravelList.PNG">
                <img src="TravelList.PNG" style="display: block; width: 100%;" />
            </a>
        </td>
    </tr>
    <tr>
        <td>
            Dashboard
        </td>
        <td>
            TravelList
        </td>
    </tr>
</table>

<table style="max-width: 100%;">
    <tr>
        <td>
            <a href="CreateTrip.PNG">
                <img src="CreateTrip.PNG" style="display: block; width: 100%;" />
            </a>
        </td>
        <td>
            <a href="CountryList.PNG">
                <img src="CountryList.PNG" style="display: block; width: 100%;" />
            </a>
        </td>
    </tr>
    <tr>
        <td>
            Create Trip
        </td>
        <td>
            Country List
        </td>
    </tr>
</table>

<table style="max-width: 100%;">
    <tr>
        <td>
            <a href="Settings.PNG">
                <img src="Settings.PNG" style="display: block; width: 100%;" />
            </a>
        </td>
		<td>
            <a href="RestClient.PNG">
                <img src="RestClient.PNG" style="display: block; width: 100%;" />
            </a>
        </td>
    </tr>
    <tr>
        <td>
            Settings
        </td>
		<td>
            RestClient
        </td>
    </tr>
</table>

Copyright
---------

Dieses Projekt ist lizenziert unter
[_Creative Commons Namensnennung 4.0 International_](http://creativecommons.org/licenses/by/4.0/)

© 2019 Vera Handel, Julian Karl, Yusef Önkol <br/>