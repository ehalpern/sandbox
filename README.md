# Frame - A Light Scala Application Framework

Frame is a lightweight scala framework for building production ready REST like services.
It employs a core of high-quality, well-used, scala/jvm libraries to tackle for 
configuration (Typesafe Config), modular construction (Google Guice), resilient 
concurrency and distribution (Akka) and asynchronous HTTP services (Spray).

The goal is to integrate and get out of the way.  Frame attempts to solve several 
of the background structural problems that are often neglected when piecing together 
a system out of libraries.  These include:

  - A simple system for handling, evolving and externalizing configuration based
    on typesafe Config
  - How to maintain a modular codebase with Guice
  - How to test akka actors and spray routes with minimal hassle
  - How to build end-to-end asynchronous services using akka and spray



