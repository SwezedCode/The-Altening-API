How to generate a new account:
```java
TheAltening theAltening = new TheAltening();
theAltening.generateAccount("API TOKEN");
```

How to favourite and private account:
```java
TheAltening theAltening = new TheAltening();
theAltening.favouriteCurrentAccount();
theAltening.privateCurrentAccount();

theAltening.favouriteAccount("API TOKEN", "ACCOUNT TOKEN");
theAltening.privateAccount("API TOKEN", "ACCOUNT TOKEN");
```

How to generate license info:
```java
TheAltening theAltening = new TheAltening();
theAltening.initializeLicenseInfo("TOKEN");
```

How to set authentication service:
```java
TheAltening theAltening = new TheAltening();
theAltening.setAuthService(AccountService.ServiceType.THEALTENING);
```
