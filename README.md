# Twitter REST APIs Spring Boot Application
**Developer**: Jeff Ou  
**Description**: This is a backend-only application that provides REST APIs with functionalities similar to Twitter's backend, allowing users to perform actions such as *creating, updating, and deleting tweets, creating profile, replying to a tweet,  searching for tweets, and adding hashtags*.
# Table of Contents

- [Technologies](#technologies) 
- [Project Dependencies](#project-dependencies)
- [Project Management](#project-management)
- [API Reference](#api-reference)
# Technologies
**Github Project**: resource management  
**IntelliJ**: development IDEA  
**Draw.io**: Create Entity Relationship Diagram  
**Postman**: APIs Testing and Documentation  
**PgAdmin 4**: PostgresSQL GUI  
**Sourcetree**: Git GUI  

# Project Dependencies
- spring-boot-starter-web: for building web applications using Spring MVC.
- spring-boot-starter-data-jpa: for using Spring Data JPA with Hibernate as the underlying implementation.
- postgresql: for working with PostgresSQL databases.
- spring-boot-starter-security: for securing web applications using Spring Security.
- jjwt-api: for creating and parsing JSON Web Tokens (JWTs).
- jjwt-impl: implementation of JSON Web Tokens (JWTs).
- jjwt-jackson: for handling JSON serialization and deserialization of JWTs.

# Project Management

## [Brainstorm](https://github.com/users/pophero110/projects/5/views/2?pane=issue&itemId=26910994)
1. **Determine what kind of application I want to build**
2. **Identify the entities that are relevant to the domain**
3. **Use ERD to visualize the relationship between the entities and include the attributes associated with each entity**
4. **Design RESTful APIs**
5. **Create user stories**
6. **Determine Development Process**
## [Planning](https://github.com/users/pophero110/projects/5/views/5)
*User stories order by priority*
- [Tweet Management](https://github.com/users/pophero110/projects/5/views/2?pane=issue&itemId=26852053)
- [Exception Management](https://github.com/users/pophero110/projects/5/views/2?pane=issue&itemId=26916819)
- [User Authentication](https://github.com/users/pophero110/projects/5/views/2?pane=issue&itemId=26832640)
- [User Profile](https://github.com/users/pophero110/projects/5/views/2?pane=issue&itemId=26832800)
- [Thread Management](https://github.com/users/pophero110/projects/5/views/2?pane=issue&itemId=26834733)
- [Hashtag Management](https://github.com/users/pophero110/projects/5/views/2?pane=issue&itemId=26852063)

# API Reference
## User

| Request Type | URL               | Functionality | Access | Request Body        |
|--------------|-------------------|---------------|--------|---------------------|
| POST         | /auth/users       | Register      | Public | { email, password } |
| POST         | /auth/users/login | Login         | Public | { email, password } |            

## Tweet
| Request Type | URL                      | Functionality             | Access  | Request Body |
|--------------|--------------------------|---------------------------|---------|--------------|
| GET          | /api/tweets              | Get all tweets            | Private |              |
| GET          | /api/tweets/users/userId | Get a user's tweets       | Private |              |
| GET          | /api/tweets/me           | Get current user's tweets | Private |              |
| POST         | /api/tweets              | Post a tweet              | Private | { content }  |
| GET          | /api/tweets/tweetId      | Get a specific tweet      | Private |              |
| PUT          | /api/tweets/tweetId      | Update a tweet            | Private | { content }  |
| DELETE       | /api/tweets/tweetId      | Delete a tweet            | Private | { content }  |

## Thread
| Request Type | URL                         | Functionality           | Access  | Request Body |
|--------------|-----------------------------|-------------------------|---------|--------------|
| POST         | /api/threads/tweets/tweetId | Reply to a tweet        | Private | { content }  |
| GET          | /api/threads/threadId       | Get tweet from a thread | Private |              |   

## Profile

| Request Type | URL                  | Functionality        | Access  | Request Body          |
|--------------|----------------------|----------------------|---------|-----------------------|
| POST         | /api/profiles        | Create a profile     | Private | { name, description } |
| PUT          | /api/profiles        | Update a profile     | Private | { name, description } |
| GET          | /api/profiles/userId | Get a user's profile | Private |                       |

## Hashtag

| Request Type | URL                          | Functionality            | Access  | Request Body    |
|--------------|------------------------------|--------------------------|---------|-----------------|
| POST         | /api/tweets/tweetId/hashtags | Add a hashtag to a tweet | Private | { hashtagName } |
| GET          | /api/tweets/search?hashtags= | Get tweets by hashtags   | Private |                 |
| GET          | /api/hashtags/trending       | Get trending hashtags    | Private |                 |