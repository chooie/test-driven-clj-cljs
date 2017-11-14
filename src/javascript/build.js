let fs = require("fs");
let packageJson = JSON.parse(fs.readFileSync("package.json"));

let rawNodeVersion = process.version; // vxx.xx.xx
let actualNodeVersion = rawNodeVersion.substring(1);
let requiredNodeVersion = packageJson.engines.node;

if (actualNodeVersion !== requiredNodeVersion) {
  throw "Must have node version '" + requiredNodeVersion + "'. " +
    "You're using '" + actualNodeVersion + "'.";
}
