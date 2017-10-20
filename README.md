# test-lein

An example of how to setup a leiningen project.

## Run the tests
`lein eftest`

## Development
- Check out `dev/user.clj` and `dev/fix.clj`
- The utilities in these files will allow you to almost always remain in the
REPL. No restart required.
- `fix` is for totally refreshing things in case an error occurs when evaluating
namespaces
- `user` provides all the tools necessary for the
[Reloaded Workflow]
(http://thinkrelevance.com/blog/2013/06/04/clojure-workflow-reloaded).

## License

Copyright © 2017 Charles Hebert

Distributed under the Eclipse Public License either version 1.0 any later
version.
