# MYERP : API de compta 

## Contexte
Cette application MyERP a été complétée par l'ajout de tests par Alexis Dumay dans le cadre de son parcours developpeur d'application Java effectué avec OpenClassrooms en 2020.

## Contenu
API de Comptabilité

## Pré-requis
Version de Java : 1.8
JDK : jdk1.8.0_201
Maven 3.6

## Badges SONAR
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=dumaya_OCComptaTestP9&metric=alert_status)](https://sonarcloud.io/dashboard?id=dumaya_OCComptaTestP9)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=dumaya_OCComptaTestP9&metric=coverage)](https://sonarcloud.io/dashboard?id=dumaya_OCComptaTestP9)

## Installation et déploiement
Les composants nécessaires lors du développement sont disponibles via des conteneurs docker. L'environnement de développement est assemblé grâce à docker-compose (cf docker/dev/docker-compose.yml).

Il comporte :

une base de données PostgreSQL contenant un jeu de données de démo (postgresql://127.0.0.1:9032/db_myerp)
Lancement
cd docker/dev
docker-compose up
Arrêt
cd docker/dev
docker-compose stop
Remise à zero
cd docker/dev
docker-compose stop
docker-compose rm -v
docker-compose up

Sources disponibles sur : https://github.com/dumaya/OCComptaTestP9
