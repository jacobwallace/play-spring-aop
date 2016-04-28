# Playing with Spring AOP
Play! app that uses Spring AOP for authorization

## Overview
This sample app uses Play! with Spring AOP to enforce authorization checks that cut across several HTTP endpoints.

There are several fake endpoints using the simple domain of mailboxes/conversations to illustrate how this might be done in a real application.

| HTTP Verb | Endpoint                                                        | Notes                                                                      |
|-----------|-----------------------------------------------------------------|----------------------------------------------------------------------------|
| GET       | /mailboxes/:mailboxId/                                          | Gets a single mailbox                                                      |
| GET       | /mailboxes/:mailboxId/conversations/:conversationId             | Gets a single conversation                                                 |
| GET       | /mailboxes/:mailboxId/conversations/:conversationId/reversed    | Gets a single conversation, except the order of the parameters is reversed |
| GET       | /mailboxes/:mailboxId/conversations/:conversationId/lightweight | Gets a "lightweight" version of a conversation                             |

## Functional Requirements
- In this sample app, some mailboxes/conversations are deemed to be "soft" deleted. Any resource associated with a soft deleted mailbox or conversation should return `404 Not Found`.
- New endpoints should enforce the authorization checks automatically when they are added.
- Mailbox authorization checks should be done _before_ conversation checks.
- A mailbox/conversation should only be retrieved once for a given request (i.e. not by both an aspect and controller).
- A controller should be able to provide a hint to allow an aspect to query for a particular form of the mailbox/conversation (e.g. a lightweight version vs. full).

## Usage
For purposes of the example, the mailbox and conversation with ID = 99 is considered soft deleted.

```bash
activator clean compile
activator run

curl http://localhost:9000/mailboxes/1 ==> 200 OK
curl http://localhost:9000/mailboxes/1/conversations/1 ==> 200 OK
curl http://localhost:9000/mailboxes/1/conversations/1/reversed ==> 200 OK
curl http://localhost:9000/mailboxes/1/conversations/1/lightweight ==> 200 OK

curl http://localhost:9000/mailboxes/99 ==> 404 Not Found
curl http://localhost:9000/mailboxes/99/conversations/1 ==> 404 Not Found
curl http://localhost:9000/mailboxes/99/conversations/1/reversed ==> 404 Not Found
curl http://localhost:9000/mailboxes/99/conversations/1/lightweight ==> 404 Not Found

curl http://localhost:9000/mailboxes/1/conversations/99 ==> 404 Not Found
curl http://localhost:9000/mailboxes/1/conversations/99/reversed ==> 404 Not Found
curl http://localhost:9000/mailboxes/1/conversations/99/lightweight ==> 404 Not Found
```
 
## Targeting Endpoints with Mailbox IDs
The `MailboxGuardian` aspect is responsible for targeting all controller methods that contain an argument named `mailboxId`. If such an ID is found, the aspect checks that the corresponding mailbox is not deleted and vetoes the request if necessary. A veto is accomplished by throwing a `NotFoundException`, which ultimately gets mapped into a `404 Not Found` by `Global`.   

Ideally `mailboxId` could be targeted like this:

```java
@Before("execution(public * play.mvc.Controller+.*(..)) && args(..,mailboxId,..)")
public void checkMailboxAccess(Long mailboxId) {
  ...
}
```

However, there is an AspectJ compiler limitation to using more than one `..`. The aspect should detect a mailbox ID _anywhere_ in the method, so it's necessary to target any controller method, and then manually check the parameters within the method:

```java
@Before("execution(public * play.mvc.Controller+.*(..))")
public void checkMailboxAccessIfMethodContainsMailboxId(JoinPoint joinPoint) {
  val parameters = parameters(joinPoint);

  if (parameters.containsKey("mailboxId")) {
    ...
  }
}
```
