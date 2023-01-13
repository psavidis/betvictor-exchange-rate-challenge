BetVictor Exchange Rate Challenge

Design Decisions for API Clients

* The Application integrates with the clients using the abstractions of exchange-rate-client
  and Configuration. There is no direct reference of Client Implementations for loosely coupling.

* The coupling point of application with client implementations is by scanning any components
  found under client-impl module. Therefore, if you wish to add a new client implementation,
  two steps are required:

  a) Make it a Component (to be scanned by spring)
  b) Declare its type in DataSourceClientType numeration. That enumeration is also used for
  configuration of the default client implementation


