# duotail-smtp-server
A smtp server unconditionally accepts any incoming messages

## Target
This product is to create a simple SMTP server without any validation. It serves as the gate of a mail delivery system.

## Usage
### Telnet
You can use telnet to connect to the server and send a mail. The default port is 25.
```shell
telnet localhost 25
```
Then you can send a mail like this:
```shell
HELO localhost
```
```shell
MAIL FROM: test@abc.com
```
```shell
RCPT TO: first@duotail.com
```
```shell
DATA
```
```shell
Subject: Test mail
From: test@abc.com
To: first@duotail.com
This is a test mail.
.
```
```shell
QUIT
```

## Credit
The initial code of this project is branched from [fake-smtp-server](https://github.com/gessnerfl/fake-smtp-server/tree/2.3.0) v2.3.0. 
I've changed the package names to match with my other products but many thanks to the original authors.
