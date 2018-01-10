# my-app

An example of how to setup a Clojure/ClojureScript project with a heavy toolset
for Test-Driven Development.

# External Dependencies
- Java 1.8.0_73
- Node v??
- Boot 2.7.1 (Build tool)

# Working on the application
The idea behind this setup is that you should rarely ever have to restart the
REPL during development.

## Steps
- Start the development REPL
-- `boot my-app/start-cider-development-repl`
-- Leave it running
- Connect to the running REPL
- Run the dev test checks
-- `(dev/t)`
- After every change, run the dev test checks

# Tasks
- See all available command line tasks for the application
-- `boot --help`

## License

Copyright © 2017 Charles Hebert

Distributed under the Eclipse Public License either version 1.0 any later
version.
