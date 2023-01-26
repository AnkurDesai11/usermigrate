# User service

Springboot backend service to store user data in relational database (MySQL)

## Features

This service has 6 api endpoints for CRUD operations

* Create single user, accepts user details and multipart file to store images like user profile picture
* Create multiple users, accepts user details as array of objects, multipart file is not accepted with the assumption that source will send the multipart file url  which can then be retrieved and restored separately in destination database
* Read single user, using username
* Read multiple users, supports pagination, sorting and searching using keyword in specific field
* Update single user, userrname attribute in the user object passed in this call will determine which user is modified
* Delete single user, using username

This Spring boot service is a generic service intended to to work as a user service for most of my projects.
