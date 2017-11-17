module.exports = function(config) {
  let basePath = "../out/";
  config.set({
    basePath: __dirname,
    files: [
      basePath + "karma_cljs.out/goog/base.js",
      {
        pattern: basePath + "karma_cljs.out/**/*.js",
        included: true,
        served: true
      },
      basePath + "karma_cljs.js",
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
