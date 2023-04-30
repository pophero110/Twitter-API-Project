# Twitter REST APIs Spring Boot Application
**Developer**: Jeff Ou  
**Description**: This is a backend-only application that provides REST APIs with functionalities similar to Twitter's backend, allowing users to perform actions such as *creating, updating, and deleting tweets, creating profile, replying to a tweet,  searching for tweets, and adding hashtags*.
# Table of Contents
- [API Reference](#api-reference)


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