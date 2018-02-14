# Test-Driven Clojure and ClojureScript
An example of how to setup a Clojure/ClojureScript project with a heavy toolset
for Test-Driven Development.

IMPORTANT: See 'Gotchas' section if you're having issues

# Thoughts on this Approach
This workflow allows me to work faster, but it is still slow compared to using
an alternative (e.g. NodeJs with Browserify). I want to get feedback in less
than 5 seconds. I can do this with Node, but I can't do this with clj/cljs. With
this workflow, I can get full feedback after ~30 seconds of running them. Quite
painful.

## How can it be made faster?
A large chunk of time (~10-20 seconds) is spent running a few frontend tests.
This is a major issue.

Ideas on how to improve the frontend feedback time include:

- Ditch Cljs
    - Cljs lazily loads Google Closure modules as it is evaluated. This is a
    major problem when using the Karma test runner because it doesn't cache it.
    The only solution I can think of is to not use cljs in the first place.
    - We could make use of dead-code elimination, but this takes over a
      minute for a small amount of code
- Build a REPL-based frontend test runner (competitor to Karma)
    - It would need to cache libraries like Google Closure heavily

Ideas on how to improve the smoke test time include:

- Ditch cljs (for the same reasons as above)

Ideas on how to improve other problem areas:

- Linting
    - Find an alternative linter to Eastwood. Eastwood can take ~10 seconds as
    it seems to evaluate all the namespaces rather than statically analyzing the
    code. A tool like ESlint takes fractions of seconds to lint more code.
- Namespace Refreshing
    - Not too bad. Incrementally refreshing takes milliseconds. A full refresh
      can take ~1-10 seconds (depending on if the REPL's 'warmed up'!?)
- General slowness
    -  Slow testing
        - Aggressive caching of test results (incremental testing)
        - See James Shore's
          [repository](https://github.com/jamesshore/lets_code_javascript) for
          an example of how to do this
        - No need to run a test if the source code hasn't changed
            - Does this apply to smoke tests?
    - Slow REPL startup
        - Still not sure how to address this

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

When doing any refactoring with clj-refactor, when it asks your permission to
evaluate, make sure to stop your running application. Otherwise, it will
redefine the value and you will lose the reference, requiring a REPL restart.

## License

Copyright © 2018 Charles Hebert

Distributed under the Eclipse Public License either version 1.0 any later
version.
