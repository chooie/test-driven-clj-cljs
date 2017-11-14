let rootPath = "../out/"
module.exports = function(config) {
  config.set({
    basePath: __dirname,
    files: [
      rootPath + "/goog/base.js",
      {
        pattern: rootPath + '/**/*.js',
        included: false
      }
    ],
    port: 9876,
    colors: true,

    // Level of logging
    // possible values: config.LOG_DISABLE || config.LOG_ERROR ||
    //                  config.LOG_WARN || config.LOG_INFO || config.LOG_DEBUG
    logLevel: config.LOG_WARN
  });
};
