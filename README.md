# my-app

An example of how to setup a Clojure/ClojureScript project with a heavy toolset
for Test-Driven Development.

# External Dependencies
NOTE: You will need to manually install these dependencies before working on the
application.

- Java
    - For now, you can manually check the version required in the
    `my-app.build.external-dependencies` namespace
    - Provides the runtime for our application
- NodeJs
    - Required version can be found in `package.json`
    - Used for scripting and installing useful utilities (with NPM - the package
    manager)
- Boot
    - Required version can be found in `boot.properties`
    - Clojure build tool

# Working on the application
The idea behind this setup is that you should rarely ever have to restart the
REPL during development.

## Steps
- Start the development REPL
    - `boot my-app/start-cider-development-repl`
- Leave the REPL process running
- Start the karma server
    - `./start_karma.sh`
- Manually capture the browsers you want to run your frontend tests in by
visiting:
    - `localhost:9876`
- Connect to the running REPL
- Run the dev test checks
    - `(dev/t)`
- Stay in the habit of running these checks after every change you make

# Tasks
- See all available command line tasks for the application
    - `boot --help`
    - NOTE: This is really slow if not done from the REPL

## License

Copyright © 2017 Charles Hebert

Distributed under the Eclipse Public License either version 1.0 any later
version.
