# **BetVictor Exchange Rate Challenge**

## **DESIGN DECISIONS**

### **DataSource Clients**

* The Application integrates with the clients using the abstractions of exchange-rate-client
  and Configuration. There is no direct reference of Client Implementations for loosely coupling.


* The coupling point of application with client implementations is by scanning any components
  found under client-impl module. Therefore, if you wish to add a new client implementation,
  two steps are required:

  a) Make it a Component (to be scanned by spring)
  b) Declare its type in DataSourceClientType numeration. That enumeration is also used for
  configuration of the default client implementation

### **CACHE**

* Hazelcast was selected as a call-reduction layer against the DataSources to cache responses.
  The in-memory data-grid of hazelcast will have at least 1 backup (replication) across other nodes
  so even values fetched by node A will be accessible by node B even if traffic goes through B.


* Retention period is configured in hazelcast.xml on each cache and affects how often
  the APIs might be called to fetch data (see time-to-live-seconds)

