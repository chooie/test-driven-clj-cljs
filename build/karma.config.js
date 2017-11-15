let rootPath = "out/"
module.exports = function(config) {
  config.set({
    basePath: "..",
    frameworks: ["cljs-test"],
    client: {
      args: ["my_app.frontend.test_runner.run-all-tests"],
      captureConsole: true
    },
    files: [
      rootPath + "goog/base.js",
      rootPath + "js/main.js",
      {
        pattern: rootPath + "my_app/**/*.js",
        included: false
      }
    ],
    port: 9876,
    colors: true,
    autowatch: false,

    // Possible logging options:
    // config.LOG_DISABLE || config.LOG_ERROR || config.LOG_WARN ||
    // config.LOG_INFO || config.LOG_DEBUG
    logLevel: config.LOG_WARN,
    singleRun: false
  });
};
