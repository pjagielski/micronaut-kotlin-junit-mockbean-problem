# micronaut-kotlin-junit-mockbean-problem

## Requires Postgresql to start:
`docker run -p 5434:5432 --name realworld -e POSTGRES_USER=realworld -e POSTGRES_DB=realworld -d postgres:11`

## Steps to reproduce:
`./gradlew test`

```
09:18:15.174 [pool-2-thread-3] ERROR i.m.h.s.netty.RoutingInBoundHandler - Unexpected error occurred: Error instantiating bean of type [io.realworld.user.domain.UserReadRepository]: No bean of type [io.realworld.user.endpoint.UsersEndpointTest] exists. Ensure the class is declared a bean and if you are using Java or Kotlin make sure you have enabled annotation processing.
io.micronaut.context.exceptions.BeanInstantiationException: Error instantiating bean of type [io.realworld.user.domain.UserReadRepository]: No bean of type [io.realworld.user.endpoint.UsersEndpointTest] exists. Ensure the class is declared a bean and if you are using Java or Kotlin make sure you have enabled annotation processing.
	at io.micronaut.context.DefaultBeanContext.doCreateBean(DefaultBeanContext.java:1335)
	at io.micronaut.context.DefaultBeanContext$1.get(DefaultBeanContext.java:1699)
	at io.micronaut.inject.ParametrizedProvider.get(ParametrizedProvider.java:45)
	at io.micronaut.runtime.context.scope.refresh.RefreshScope.lambda$get$0(RefreshScope.java:91)
	at java.util.concurrent.ConcurrentHashMap.computeIfAbsent(ConcurrentHashMap.java:1660)
	at io.micronaut.runtime.context.scope.refresh.RefreshScope.get(RefreshScope.java:90)
	at io.micronaut.context.DefaultBeanContext.getScopedBeanForDefinition(DefaultBeanContext.java:1692)
	at io.micronaut.context.DefaultBeanContext.getBeanForDefinition(DefaultBeanContext.java:1625)
	at io.micronaut.context.DefaultBeanContext.getProxyTargetBean(DefaultBeanContext.java:774)
	at io.realworld.user.endpoint.$UsersEndpointTest$UserReadRepositoryDefinition$Intercepted.$resolveTarget(Unknown Source)
	at io.realworld.user.endpoint.$UsersEndpointTest$UserReadRepositoryDefinition$Intercepted.interceptedTarget(Unknown Source)
	at io.realworld.user.endpoint.$UsersEndpointTest$UserReadRepositoryDefinition$Intercepted.findByEmail(Unknown Source)
	at io.realworld.security.domain.UserAuthenticationService.authenticate(UserAuthenticationService.kt:16)
	at io.realworld.security.domain.$UserAuthenticationServiceDefinition$Intercepted.$$access0(Unknown Source)
	at io.realworld.security.domain.$UserAuthenticationServiceDefinition$Intercepted$$proxy0.invokeInternal(Unknown Source)
	at io.micronaut.context.AbstractExecutableMethod.invoke(AbstractExecutableMethod.java:145)
	at io.micronaut.aop.chain.InterceptorChain.lambda$new$1(InterceptorChain.java:90)
	at io.micronaut.aop.chain.InterceptorChain.proceed(InterceptorChain.java:147)
```
