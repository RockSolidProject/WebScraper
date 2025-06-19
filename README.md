# Welcome to our admin pannel / web scraper app
This app allows admins to connect with our backend server from our WebApp reposatory ([WebApp reposatory link](https://github.com/RockSolidProject/WebApp)),
then they can manage the data in our aplication and also add data with scraping from websites (that contain climbing centers, areas and routes in Slovenia):
* https://www.plezanje.net/plezalisca/slovenija //climbing areas and routes
* https://ksp.pzs.si/plezalisca.php?tip=3 //climbing centes

## Showcase
* Here you can see climbing centers that are curently in the db.
  
![Climbing centers in the database](https://github.com/user-attachments/assets/77e43f17-9855-4b2f-8bfa-164010326c9c)

* They can be updated:
  
![image](https://github.com/user-attachments/assets/7b24308f-d1e5-487e-9543-98f7a0110697)

* You can add 1 manualy:

![image](https://github.com/user-attachments/assets/fabe89b6-7e91-46f5-8dec-93cbe67be2a2)

* Then you can scrape from ksp pzs, and before scraping you can also filter/edit them.
![image](https://github.com/user-attachments/assets/5f5202cf-ef0d-4a26-9c3b-985bd44d2b67)

* Then we also have a genrator for random data.
![image](https://github.com/user-attachments/assets/94440cc6-76c9-437e-80e0-05ed1c30a6c1)

** You can also do everyting above for climbing areas and the reoutes they contain. **


# Requirements
* a server running our backend
* 2 env files
## config.json
```json
  {
    "backendApi": "http://localhost:3001",
    "climbingCenters": "/climbingCenter",
    "climbingAreas": "/climbingAreas",
    "climbingRoutes": "/climbingRoutes",
    "users": "/users",
    "login": "/login"
  }
```
* Local host should be raplace with your desired server

## envConfig.json
```json
{
  "username": "admin",
  "password": ""
}
```
* This file should contain your admins login info.
* Also you admin should have ID filled with only 0s (scraper works without).

# Icons sources
* App icon: https://www.flaticon.com/free-icon/user-gear_9572787
