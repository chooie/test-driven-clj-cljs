(function (karma) {
  karma.start = function() {
    karma_cljs.core.start_running_tests(karma);
  };
}(window.__karma__));
