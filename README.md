# SpringBoot-Food_Ordering
Dieses Projekt digitalisiert die Essensbestellung und -ausgabe für Sozial-Arbeiten-Wohnen, eine Organisation, die Menschen mit Behinderungen unterstützt. Ziel ist es, den bisherigen manuellen Prozess effizienter zu gestalten und die Bestellverwaltung zu vereinfachen.

Die Anwendung bietet vier Benutzerrollen mit spezifischen Funktionen und Zugriffsrechten: Verwaltung (Admins), Standortleitung, Gruppenleitung und Küchenpersonal. Jede Rolle hat eine eigene Ansicht und kann relevante Daten einsehen und bearbeiten. Die Verwaltung legt Benutzerkonten an und importiert Daten über eine CSV-Datei. Die Gruppenleitung erfasst Bestellungen 14 Tage im Voraus und kann tagesaktuelle Anpassungen bis 8:00 Uhr vornehmen. Die Standortleitung verwaltet Gruppen und Stellvertretungen. Das Küchenpersonal nutzt QR-Codes, um die Essensausgabe effizient zu verwalten und Doppelausgaben zu vermeiden.

Technologisch basiert die Anwendung auf einer Client-Server-Architektur mit einem zentralen Microservice, der alle Anfragen verarbeitet und mit der Datenbank kommuniziert.
