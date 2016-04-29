# Playing with Spring AOP
Play! app that uses Spring AOP for authorization

## Overview
This sample app uses Play! with Spring AOP to enforce authorization checks that cut across several HTTP endpoints.

There are several fake endpoints using the simple domain of blog posts/comments to illustrate how this might be done in a real application.

| HTTP Verb | Endpoint                                                        | Notes                                                                      |
|-----------|-----------------------------------------------------------------|----------------------------------------------------------------------------|
| GET       | /posts/:postId/                                             | Gets a single post                                                       |
| GET       | /posts/:postId/comments/:commentId             | Gets a single comment                                                 |
| GET       | /posts/:postId/comments/:commentId/reversed    | Gets a single comment, except the order of the parameters is reversed |
| GET       | /posts/:postId/comments/:commentId/lightweight | Gets a "lightweight" version of a comment                             |

## Functional Requirements
- In this sample app, some posts/comments are deemed to be "soft" deleted. Any resource associated with a soft deleted post or comment should return `404 Not Found`.
- New endpoints should enforce the authorization checks automatically when they are added.
- Checks for viewing blog posts should be done _before_ checks for viewing comments.
- A post/comment should only be retrieved once for a given request (i.e. not by both an aspect and controller).
- A controller should be able to provide a hint to allow an aspect to query for a particular form of the post/comment (e.g. a lightweight version vs. full).

## Usage
For purposes of the example, the post and comment with ID = 99 are considered soft deleted.

```bash
activator clean compile
activator run

curl http://localhost:9000/posts/1 ==> 200 OK
curl http://localhost:9000/posts/1/comments/1 ==> 200 OK
curl http://localhost:9000/posts/1/comments/1/reversed ==> 200 OK
curl http://localhost:9000/posts/1/comments/1/lightweight ==> 200 OK

curl http://localhost:9000/posts/99 ==> 404 Not Found
curl http://localhost:9000/posts/99/comments/1 ==> 404 Not Found
curl http://localhost:9000/posts/99/comments/1/reversed ==> 404 Not Found
curl http://localhost:9000/posts/99/comments/1/lightweight ==> 404 Not Found

curl http://localhost:9000/posts/1/comments/99 ==> 404 Not Found
curl http://localhost:9000/posts/1/comments/99/reversed ==> 404 Not Found
curl http://localhost:9000/posts/1/comments/99/lightweight ==> 404 Not Found
```
 
## Targeting Endpoints with IDs
The `PostGuardian` and `CommentGuardian` aspects are responsible for targeting all controller methods that contain an argument named `postId` and `commentId`, respectively. If such an ID is found, the aspect checks that the corresponding post/comment is not deleted and vetoes the request if necessary. A veto is accomplished by throwing a `NotFoundException`, which ultimately gets mapped into a `404 Not Found` by `Global`.   

Ideally IDs could be targeted like this:

```java
@Before("execution(public * play.mvc.Controller+.*(..)) && args(..,postId,..)")
public void checkPostAccess(Long postId) {
  ...
}
```

However, there is an AspectJ compiler limitation to using more than one `..`. The aspect should detect an ID _anywhere_ in the method, so it's necessary to target any controller method, and then manually check the parameters within the method:

```java
@Before("execution(public * play.mvc.Controller+.*(..))")
public void checkPostAccessIfMethodContainsPostId(JoinPoint joinPoint) {
  val parameters = parameters(joinPoint);

  if (parameters.containsKey("postId")) {
    ...
  }
}
```
