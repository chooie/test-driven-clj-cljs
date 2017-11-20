module.exports = function(config) {
  let basePath = "../generated/automated-testing/";
  config.set({
    basePath: __dirname,
    files: [
      basePath + "karma_cljs.out/goog/base.js",
      basePath + "karma_cljs.out/cljs_deps.js",
      {
        pattern: basePath + "*.js",
        included: false,
        served: true
      },
      {
        pattern: basePath + "karma_cljs.out/**/*.js",
        included: false,
        served: true
      },
      "../src/javascript/adapter.js",
    ],
    port: 9876,
    colors: true,
    autowatch: false,

    // Possible logging options:
    // config.LOG_DISABLE || config.LOG_ERROR || config.LOG_WARN ||
    // config.LOG_INFO || config.LOG_DEBUG
    logLevel: config.LOG_DEBUG,
    singleRun: false
  });
};
