# my-app

An example of how to setup a Clojure/ClojureScript project with a heavy toolset
for Test-Driven Development.

IMPORTANT: See 'Gotchas' section if you're having issues

# External Dependencies
NOTE: You will need to manually install these dependencies before working on the
application.

- Java
    - For now, you can manually check the version required in the
    `my-app.build.external-dependencies` namespace
    - Provides the runtime for our application
    - Download [here](http://www.oracle.com/technetwork/java/javase/downloads/java-archive-javase8-2177648.html)
- NodeJs
    - Required version can be found in `package.json`
    - Used for scripting and installing useful utilities (with NPM - the package
    manager)
    - Download [here](https://nodejs.org/en/download/releases/)
- Boot
    - Required version can be found in `boot.properties`
    - Clojure build tool
    - Download [here](https://github.com/boot-clj/boot#install)

# Working on the application
The idea behind this setup is that you should rarely ever have to restart the
REPL during development.

## Steps
- Start the development REPL
    - `boot my-app/start-cider-development-repl`
- Leave the REPL process running
- Start the karma server
    - `./bash_scripts/start_karma.sh`
- Manually capture the browsers you want to run your frontend tests in by
visiting:
    - `localhost:9876`
- Connect to the running REPL
- Run the test checks
    - `(tester/all)`
    - `(tester/without-linting)`
    - `(tester/without-linting-and-smoke-tests)`
- Stay in the habit of running these checks after every change you make

# Running the production jar locally
- `./bash_scripts/build_and_run.sh`

# Tasks
- See all available command line tasks for the application
    - `boot --help`
    - NOTE: This is really slow if not done from the REPL

# Gotchas
When using Cider with refactor-nrepl, your namespaces will be evaluated
automatically, outside of your control. Therefore, it is important to disable
some features or it will **break the reloaded workflow**.

Here are the Emacs Cider variables to set:

- `(setq cljr-eagerly-build-asts-on-startup nil)`
- `(setq cljr-auto-clean-ns nil)`
- `(setq cljr-warn-on-eval t)`

When doing any refactoring with clj-refactor, and it asks your permission to
evaluate, make sure to stop your running application. Otherwise, it will
redefine the value and you will lose the reference, requiring a REPL restart.

## License

Copyright © 2018 Charles Hebert

Distributed under the Eclipse Public License either version 1.0 any later
version.
