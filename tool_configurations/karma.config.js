module.exports = function(config) {
  let basePath = "generated/automated-testing/";
  let karmaPath = basePath + "karma_cljs/";
  let myAppPath = basePath + "my_app/";
  config.set({

    // Base path that will be used to resolve all patterns (eg. files, exclude)
    basePath: "..",


    // Frameworks to use.
    // Available frameworks: https://npmjs.org/browse/keyword/karma-adapter
    frameworks: [],


    // List of files / patterns to load in the browser
    files: [
      {
        pattern: basePath + "goog/base.js",
        included: true,
        served: true
      },
      {
        pattern: basePath + "goog/deps.js",
        included: true,
        served: true
      },
      {
        pattern: basePath + "js/karma_cljs.js",
        included: true,
        served: true
      },
      {
        pattern: basePath + "js/my_app.js",
        included: true,
        served: true
      },
      {
        pattern: basePath + "**/*.js",
        included: false,
        served: true
      },
      {
        pattern: karmaPath + "**/*.js",
        included: true,
        served: true
      },
      {
        pattern: myAppPath + "**/*.js",
      },
      {
        pattern: "src/load_karma_cljs.js",
        included: true,
        served: true
      },
      {
        pattern: "src/load_my_app.js",
        include: true,
        served: true
      },
      {
        pattern: "src/javascript/adapter.js",
        included: true,
        served: true
      },
    ],

    // list of files to exclude
    exclude: [
    ],

    // preprocess matching files before serving them to the browser
    // available preprocessors:
    // https://npmjs.org/browse/keyword/karma-preprocessor
    preprocessors: {
    },

    // test results reporter to use
    // possible values: 'dots', 'progress'
    // available reporters: https://npmjs.org/browse/keyword/karma-reporter
    reporters: ["dots"],


    // web server port
    port: 9876,


    // enable / disable colors in the output (reporters and logs)
    colors: false,


    // level of logging
    // possible values:
    // config.LOG_DISABLE || config.LOG_ERROR || config.LOG_WARN ||
    // config.LOG_INFO || config.LOG_DEBUG
    logLevel: config.LOG_WARN,


    // enable / disable watching file and executing tests whenever any file
    // changes
    autoWatch: false,


    // start these browsers
    // available browser launchers:
    // https://npmjs.org/browse/keyword/karma-launcher
    browsers: [],


    // Continuous Integration mode
    // if true, Karma captures browsers, runs the tests and exits
    singleRun: false,

    // Concurrency level
    // how many browser should be started simultaneous
    concurrency: Infinity
  });
};
