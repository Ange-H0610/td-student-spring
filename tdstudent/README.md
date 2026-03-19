# TD Spring Boot - Gestion d'étudiants

## Description
Projet Spring Boot pour les exercices TD2 et TD3.

## Structure du projet
- **main** : branche principale
- **feature/td2** : implémentation du TD2 (requêtes HTTP de base)
- **feature/td3** : implémentation du TD3 (ResponseEntity et codes HTTP)

## Endpoints disponibles

### TD2 (branche feature/td2) - /api/*
| Méthode | URL | Description |
|---------|-----|-------------|
| GET | /api/welcome?name=xxx | Message de bienvenue |
| POST | /api/students | Ajout d'étudiants (JSON) |
| GET | /api/students | Liste des étudiants (texte) |

### TD3 (branche feature/td3) - /api/v2/*
| Méthode | URL | Codes HTTP possibles |
|---------|-----|---------------------|
| GET | /api/v2/welcome?name=xxx | 200, 400, 500 |
| POST | /api/v2/students | 201, 400, 500 |
| GET | /api/v2/students | 200, 400, 501, 500 |

## Comment tester

1. Lancer l'application :
   ```bash
   .\mvnw spring-boot:run