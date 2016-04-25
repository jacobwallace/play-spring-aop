# Playing with Spring AOP
Play! app that uses Spring AOP for authorization

## Overview

I chose the simple domain of mailboxes/conversations to illustrate how to use Spring AOP with Play! to protect various resources from unauthorized access.

In this sample app, some mailboxes are deemed to be "soft" deleted. Any resource associated with a soft deleted mailbox should return `404 Not Found`. Additionally, any new endpoints that contain a mailbox identifier should enforce the authorization check automatically.

For purposes of the example, the mailbox with ID = 99 is considered soft deleted.

Supported endpoints are listed below:
 
| HTTP Verb | Endpoint                                                     | Notes                                                                      |
|-----------|--------------------------------------------------------------|----------------------------------------------------------------------------|
| GET       | /mailboxes/:mailboxId/                                       | Gets a single mailbox                                                      |
| GET       | /mailboxes/:mailboxId/conversations/:conversationId          | Gets a single conversation                                                 |
| GET       | /mailboxes/:mailboxId/conversations/:conversationId/reversed | Gets a single conversation, except the order of the parameters is reversed |

## Usage

```bash
activator clean compile
activator run

curl http://localhost:9000/mailboxes/1 ==> 200 OK
curl http://localhost:9000/mailboxes/1/conversations/1 ==> 200 OK
curl http://localhost:9000/mailboxes/1/conversations/1/reversed ==> 200 OK

curl http://localhost:9000/mailboxes/99 ==> 404 Not Found
curl http://localhost:9000/mailboxes/99/conversations/1 ==> 404 Not Found
curl http://localhost:9000/mailboxes/99/conversations/1/reversed ==> 404 Not Found
```
 
## Targeting Endpoints with Mailbox IDs

The `MailboxGuardian` aspect is responsible for targeting all controller methods that contain an argument named `mailboxId`. If such an ID is found, the aspect checks that the corresponding mailbox is not deleted and vetoes the request if necessary. A veto is accomplished by throwing a `NotFoundException`, which ultimately gets mapped into a `404 Not Found` by `Global`.   

I originally tried to target `mailboxId` like this:

```java
@Before("execution(public * play.mvc.Controller+.*(..)) && args(..,mailboxId,..)")
public void checkMailboxAccess(Long mailboxId) {
  ...
}
```

However, there is an AspectJ compiler limitation to using more than one `..`. The aspect should detect a mailbox ID _anywhere_ in the method, so I had to refactor the aspect to target any controller method, and then manually check the parameters within the method:

```java
@Before("execution(public * play.mvc.Controller+.*(..))")
public void checkMailboxAccessIfMethodContainsMailboxId(JoinPoint joinPoint) {
  val parameters = parameters(joinPoint);

  if (parameters.containsKey("mailboxId")) {
    ...
  }
}
```